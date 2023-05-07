package com.volgadorf.strawberry_fields.item.custom;

import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import java.util.Random;

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
            Random random = new Random();
            int currentValue = blockState.getValue(ComposterBlock.LEVEL);
            if (currentValue < 7) { // check if current value is less than 7
                if (random.nextDouble() < 0.5) { //only fill it up 50% of time to simulate behavior similar to wheat seeds
                    BlockState newState = blockState
                            .setValue(ComposterBlock.LEVEL, currentValue + 1); // set the new value
                    currentValue++; //also change the variable to check if it hits stage 7 for later
                    context.getLevel().setBlock(context.getClickedPos(), newState, 3); // update the block state
                }

                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.COMPOSTER_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                assert player != null;
                if (player.getMainHandItem().getItem().equals(ModFoodItems.RICE_SEED.get())){
                    Objects.requireNonNull(context.getPlayer()).swing(InteractionHand.MAIN_HAND);
                    player.getMainHandItem().shrink(1);
                }
                else{
                    Objects.requireNonNull(context.getPlayer()).swing(InteractionHand.OFF_HAND);
                    player.getOffhandItem().shrink(1);
                }

                // Play the green particle effect
                double x = context.getClickedPos().getX() + 0.5;
                double y = context.getClickedPos().getY() + 0.5;
                double z = context.getClickedPos().getZ() + 0.5;
                if (context.getLevel() instanceof ServerLevel) {
                    ((ServerLevel) context.getLevel()).sendParticles(ParticleTypes.COMPOSTER, x, y, z, 10, 0.2, 0.2, 0.2, 0.0);
                }
                if (currentValue >= ComposterBlock.READY - 1) {
                    // Composter is full, transition to the bonemeal state (this is 'later')
                    BlockState newState = blockState.setValue(ComposterBlock.LEVEL, ComposterBlock.READY);

                    //i should consider adding a time delay here to simulate vanilla interactions, but that uses tick and i don't want to overload server

                    //change the blockstate
                    context.getLevel().setBlock(context.getClickedPos(), newState, 3);
                    context.getLevel().levelEvent(1500, context.getClickedPos(), 0);
                    return InteractionResult.FAIL;
                }
            }

        } else {
            System.out.println("what the");
        }
        return super.useOn(context);
    }
}
