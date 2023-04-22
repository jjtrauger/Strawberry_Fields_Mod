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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class OverflowingWaterBucketItem extends Item {
    //try extending BucketItem and implementing DispensibleContainerItem
    private final Fluid content = Fluids.WATER;

    public OverflowingWaterBucketItem(Properties builder) {
        super(builder);
    }


    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
    {
        return blockstate.getFluidState().isEmpty() && blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.content);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    //current behavior is not in line with vanilla buckets. it only works on waterlogged blocks. need to
    //also check for the block above usin old behavior or extend BucketItem instead
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        //if (context.getClickedFace() == Direction.UP && context.getPlayer() != null) {
        if (context.getPlayer() != null) {
            Level world = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            BlockState targetState = world.getBlockState(blockPos);
            Player player = context.getPlayer();

            // Check if the block can be replaced by water, like snow or something
            if (targetState.getMaterial().isReplaceable()
                    && !targetState.getMaterial().equals(Material.REPLACEABLE_PLANT)
                    && player.mayUseItemAt(blockPos, context.getClickedFace(), this.getDefaultInstance())) {
                world.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.swing(context.getHand(), true);
                return InteractionResult.SUCCESS;
            }

            // Waterlog the block if it can be waterlogged
            if (canBlockContainFluid(world, blockPos, targetState)) {
                BlockState waterloggedState = targetState.setValue(BlockStateProperties.WATERLOGGED, true);
                world.setBlockAndUpdate(blockPos, waterloggedState);
                world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.swing(context.getHand(), true);
                return InteractionResult.SUCCESS;
            }

            // break it and Drop the block if it contains a flower
            if (targetState.getBlock() instanceof FlowerBlock || targetState.getBlock() instanceof MushroomBlock) {
                Block.popResource(world, blockPos, new ItemStack(targetState.getBlock().asItem()));
                targetState.getBlock().playerWillDestroy(world, blockPos, targetState, player);
                world.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.swing(context.getHand(), true);
                return InteractionResult.SUCCESS;
            }

            // Don't Drop the block if it is grass, just break it
            if (targetState.getMaterial().equals(Material.REPLACEABLE_PLANT)) {
                targetState.getBlock().playerWillDestroy(world, blockPos, targetState, player);
                world.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
                world.playSound(player, blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.swing(context.getHand(), true);
                return InteractionResult.SUCCESS;
            }
        }
        //water cant be placed here
        return InteractionResult.FAIL;
    }
}
