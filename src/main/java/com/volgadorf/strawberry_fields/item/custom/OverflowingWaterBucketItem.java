package com.volgadorf.strawberry_fields.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
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

        /*if (!canBlockContainFluid(world, targetPos, targetState)) {
            System.out.println("Can't place water here");
            return InteractionResult.PASS;
        } */

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


}
