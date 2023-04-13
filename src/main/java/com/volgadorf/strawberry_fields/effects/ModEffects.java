package com.volgadorf.strawberry_fields.effects;

import com.volgadorf.strawberry_fields.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MOD_ID);

    public static RegistryObject<MobEffect> FROSTY =
            MOB_EFFECTS.register("frosty_effect",
                    () -> new FrostyEffect(MobEffectCategory.NEUTRAL, 0xFFFFFF));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
