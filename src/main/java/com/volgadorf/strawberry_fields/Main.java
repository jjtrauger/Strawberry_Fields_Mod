package com.volgadorf.strawberry_fields;


import com.mojang.logging.LogUtils;
import com.volgadorf.strawberry_fields.block.ModBlocks;
import com.volgadorf.strawberry_fields.block.entity.ModBlockEntities;
import com.volgadorf.strawberry_fields.effects.ModEffects;
import com.volgadorf.strawberry_fields.enchantment.ModEnchantments;
import com.volgadorf.strawberry_fields.item.ModCreativeModeTabs;
import com.volgadorf.strawberry_fields.item.ModFoodItems;
import com.volgadorf.strawberry_fields.recipe.ModRecipes;
import com.volgadorf.strawberry_fields.screen.CuttingTableScreen;
import com.volgadorf.strawberry_fields.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("strawberry_fields")
public class Main {

    public static final String MOD_ID = "strawberry_fields";

    //for later
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModFoodItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        //modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::buildContents);
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void buildContents(CreativeModeTabEvent.BuildContents event) {
        // Add to ingredients tab
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModFoodItems.PAST_MILK);
            event.accept(ModFoodItems.CHEEMS);
            event.accept(ModBlocks.CHEEMS_FULL); // Takes in an ItemLike, assumes block has registered item
            event.accept(ModBlocks.CUTTING_TABLE);
            event.accept(ModFoodItems.RICE);
            event.accept(ModFoodItems.RICE_SEED);
        }
        if(event.getTab() == ModCreativeModeTabs.VOLG_TAB) {
            event.accept(ModFoodItems.PAST_MILK);
            event.accept(ModFoodItems.CHEEMS);
            event.accept(ModBlocks.CHEEMS_FULL);
            event.accept(ModFoodItems.RICE);
            event.accept(ModFoodItems.RICE_SEED);
            event.accept(ModFoodItems.SUSHI);
            event.accept(ModFoodItems.COOKED_RICE);
        }
        if(event.getTab() == ModCreativeModeTabs.VOLG_TAB2) {
            event.accept(ModFoodItems.KNIFE);
            event.accept(ModBlocks.CUTTING_TABLE);
            event.accept(ModBlocks.WHETSTONE);
        }

        if(event.getTab() == ModCreativeModeTabs.VOLG_TAB3) {
            event.accept(ModFoodItems.OFWB);
        }
    }





    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.CUTTING_TABLE_MENU.get(), CuttingTableScreen::new);
        }
    }
}
