package com.volgadorf.strawberry_fields.item.custom;

import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RiceSeedItem extends PlaceOnWaterBlockItem {
    public RiceSeedItem(Block p_220226_, Properties p_220227_) {
        super(p_220226_, p_220227_);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 64;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        Block block = blockState.getBlock();
        Player player = context.getPlayer();
        if (block instanceof ComposterBlock) {
            int currentValue = blockState.getValue(ComposterBlock.LEVEL);
            if (currentValue < 7) { // check if current value is less than 7
                BlockState newState = blockState
                        .setValue(ComposterBlock.LEVEL, currentValue + 1); // set the new value
                context.getLevel().setBlock(context.getClickedPos(), newState, 3); // update the block state
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                assert player != null;
                if (player.getMainHandItem().getItem().equals(ModFoodItems.RICE_SEED.get())){
                    Objects.requireNonNull(context.getPlayer()).swing(InteractionHand.MAIN_HAND);
                }
                else{
                    Objects.requireNonNull(context.getPlayer()).swing(InteractionHand.OFF_HAND);
                }

            }
        } else {
            System.out.println("what the");
        }
        return super.useOn(context);
    }
}
