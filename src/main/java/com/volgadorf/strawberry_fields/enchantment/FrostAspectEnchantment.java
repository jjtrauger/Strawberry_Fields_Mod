package com.volgadorf.strawberry_fields.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class FrostAspectEnchantment extends Enchantment {
    public FrostAspectEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean checkCompatibility(Enchantment pEnch) {
        return super.checkCompatibility(pEnch) && pEnch != Enchantments.FIRE_ASPECT;
    }
    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (!pAttacker.level.isClientSide){
            ServerLevel world = ((ServerLevel) pAttacker.level);
            BlockPos pos = pTarget.blockPosition();

            if (pLevel == 1){
                pTarget.extinguishFire();
                pAttacker.level.playSound(null, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 1.0F, 2.0F);
                // Apply Slowness effect to the target
                if (pTarget instanceof LivingEntity) {
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
                }
            }

            else if (pLevel == 2){
                pTarget.extinguishFire();
                pAttacker.level.playSound(null, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 1.0F, 2.0F);
                // Apply Slowness effect to the target
                if (pTarget instanceof LivingEntity) {
                    ((LivingEntity) pTarget).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                }
            }

            // Spawn snowflake particles around the target
            world.sendParticles(
                    // Snowball poof particle type
                    net.minecraft.core.particles.ParticleTypes.ITEM_SNOWBALL,
                    // Spawn position
                    pTarget.getX(),
                    pTarget.getY() + pTarget.getBbHeight() / 2.0,
                    pTarget.getZ(),
                    // Number of particles
                    50,
                    // Spread values
                    0.5, 0.5, 0.5,
                    // Speed
                    0.1
            );
        }
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }
}
