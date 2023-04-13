package com.volgadorf.strawberry_fields.enchantment;

import com.volgadorf.strawberry_fields.effects.ModEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class FrostAspectEnchantment extends Enchantment {

    public FrostAspectEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public boolean canEnchant(@NotNull ItemStack pStack) {
        return pStack.getItem() instanceof SwordItem || pStack.getItem() instanceof AxeItem;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean checkCompatibility(@NotNull Enchantment pEnch) {
        return super.checkCompatibility(pEnch) && pEnch != Enchantments.FIRE_ASPECT;
    }
    @Override
    public void doPostAttack(LivingEntity pAttacker, @NotNull Entity pTarget, int pLevel) {
        if (!pAttacker.level.isClientSide){

            if (pLevel == 1){

                pAttacker.level.playSound(null, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 1.0F, 2.0F);
                // Apply Slowness effect to the target
                if (pTarget instanceof LivingEntity && !pAttacker.level.isClientSide) {
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false));
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(ModEffects.FROSTY.get(), 40, 0, false, false));
                }
            }

            else if (pLevel == 2){
                pTarget.extinguishFire();
                pAttacker.level.playSound(null, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 1.0F, 2.0F);
                // Apply Slowness effect to the target
                if (pTarget instanceof LivingEntity && !pAttacker.level.isClientSide) {
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, false));
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(ModEffects.FROSTY.get(), 100, 0, false, false));
                }
            }
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
