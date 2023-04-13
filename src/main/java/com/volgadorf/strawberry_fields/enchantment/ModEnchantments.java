package com.volgadorf.strawberry_fields.enchantment;


import com.volgadorf.strawberry_fields.Main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {


    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MOD_ID);

    public static RegistryObject<Enchantment> FROST_ASPECT =
            ENCHANTMENTS.register("frost_aspect",
                    () -> new FrostAspectEnchantment(Enchantment.Rarity.UNCOMMON,
                            EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

}
