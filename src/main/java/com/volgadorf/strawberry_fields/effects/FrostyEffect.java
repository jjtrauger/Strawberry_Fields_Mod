package com.volgadorf.strawberry_fields.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrostyEffect extends MobEffect {
    protected FrostyEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, @NotNull LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        this.applyEffectTick(pLivingEntity, pAmplifier);
    }



    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {

        ServerLevel world = ((ServerLevel) entity.level);
        entity.extinguishFire();

        if (!entity.level.isClientSide ) {
                // Emit ITEM_SNOWBALL particles from the entity's position
                world.sendParticles(
                        net.minecraft.core.particles.ParticleTypes.ITEM_SNOWBALL,
                        entity.getX(),
                        entity.getY() + entity.getBbHeight() / 2.0,
                        entity.getZ(),
                        1,
                        0.5, 0.5, 0.5,
                        0.1
                );
        }
    }
}
