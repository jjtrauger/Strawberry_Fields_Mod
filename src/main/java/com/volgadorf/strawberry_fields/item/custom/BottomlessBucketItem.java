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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class BottomlessBucketItem extends Item {

    public BottomlessBucketItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        //this only lets you click on blocks and considers the one above it, so i need to find a way to click on fluids
        if (context.getPlayer() != null) {
            Level world = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            BlockState targetState = world.getBlockState(blockPos);
            Player player = context.getPlayer();


            // Set a waterlogged block to not be waterlogged
            if (canBlockRemoveFluid(world, blockPos, targetState)) {
                BlockState waterloggedState = targetState.setValue(BlockStateProperties.WATERLOGGED, false);
                world.setBlockAndUpdate(blockPos, waterloggedState);
                world.playSound(player, blockPos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.swing(context.getHand(), true);
                return InteractionResult.SUCCESS;
            }

            //

        }
        //water cant be placed here
        return InteractionResult.FAIL;
    }
    private boolean canBlockRemoveFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
    {
        return !blockstate.getFluidState().isEmpty() && blockstate.getBlock() instanceof LiquidBlockContainer;
    }

}
