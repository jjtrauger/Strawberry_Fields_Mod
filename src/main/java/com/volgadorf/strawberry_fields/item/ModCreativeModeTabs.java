package com.volgadorf.strawberry_fields.item;

import com.volgadorf.strawberry_fields.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab VOLG_TAB;
    public static CreativeModeTab VOLG_TAB2;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        VOLG_TAB = event.registerCreativeModeTab(new ResourceLocation(Main.MOD_ID, "food_tab"),
                builder -> builder.icon(() -> new ItemStack(ModFoodItems.CHEEMS.get()))
                        .title(Component.translatable("creativemodetab.food_tab")));

        VOLG_TAB2 = event.registerCreativeModeTab(new ResourceLocation(Main.MOD_ID, "kitchen_tools_tab"),
                builder -> builder.icon(() -> new ItemStack(ModFoodItems.KNIFE.get()))
                        .title(Component.translatable("creativemodetab.kitchen_tools_tab")));
    }
}
