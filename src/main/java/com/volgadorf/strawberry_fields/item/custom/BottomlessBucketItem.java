package com.volgadorf.strawberry_fields.item.custom;

import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BottomlessBucketItem extends BucketItem {

    private final Fluid content = Fluids.EMPTY;

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }

    public BottomlessBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(pPlayer, pLevel, itemstack, blockhitresult);
        if (ret != null) return ret;
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos1, direction, itemstack)) {
                    BlockState blockstate1 = pLevel.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof BucketPickup bucketpickup) {
                        ItemStack itemstack1 = bucketpickup.pickupBlock(pLevel, blockpos, blockstate1);
                        if (!itemstack1.isEmpty()) {
                            pPlayer.awardStat(Stats.ITEM_USED.get(this));
                            bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> pPlayer.playSound(p_150709_, 1.0F, 1.0F));
                            pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, blockpos);
                            ItemStack itemstack2 = createFilledResult2();
                            if (!pLevel.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)pPlayer, itemstack1);
                            }

                            return InteractionResultHolder.sidedSuccess(itemstack2, pLevel.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }


    public ItemStack createFilledResult2() {
        return new ItemStack(ModFoodItems.BOTTOMLESSBUCKET.get());
    }

}
