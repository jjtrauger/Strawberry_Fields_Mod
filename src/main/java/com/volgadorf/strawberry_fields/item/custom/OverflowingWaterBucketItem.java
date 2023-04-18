package com.volgadorf.strawberry_fields.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class OverflowingWaterBucketItem extends Item {
    private final Fluid content = Fluids.WATER;

    public OverflowingWaterBucketItem(Properties builder) {
        super(builder);
    }

    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        Player player = context.getPlayer();

        Player entityplayer = context.getPlayer();
        assert entityplayer != null;

        BlockPos targetPos = pos.offset(face.getNormal());
        BlockState targetState = world.getBlockState(targetPos);

        if (canBlockContainFluid(world, targetPos, targetState)) {
            //System.out.println("Can't place water here");

            BlockState waterloggedState = targetState.setValue(BlockStateProperties.WATERLOGGED, true);
            world.setBlock(targetPos, waterloggedState, 3);
            world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        if (targetState.getBlock() != Blocks.AIR && targetState.getBlock().canHarvestBlock(targetState, world, targetPos, player)) {
            NonNullList<ItemStack> drops = NonNullList.create();
            assert player != null;
            targetState.getBlock().playerWillDestroy(world, targetPos, targetState, player);
            drops.forEach(itemStack -> Block.popResource(world, targetPos, itemStack));

            ItemStack itemStack = new ItemStack(targetState.getBlock().asItem());
            ItemEntity itemEntity = new ItemEntity(world, targetPos.getX(), targetPos.getY(), targetPos.getZ(), itemStack);
            itemEntity.setDeltaMovement(0, 0.23, 0); // Set upward velocity
            world.addFreshEntity(itemEntity);

        }

        BlockState waterState = Blocks.WATER.defaultBlockState();
        world.setBlockAndUpdate(targetPos, waterState);

        world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);

        return InteractionResult.SUCCESS;
    }


    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
    {
        return blockstate.getFluidState().isEmpty() && blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.content);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }
/*
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        return pEntityLiving instanceof Player && ((Player)pEntityLiving).getAbilities().instabuild ? itemstack : new ItemStack(Items.BOWL);
    } */


}
