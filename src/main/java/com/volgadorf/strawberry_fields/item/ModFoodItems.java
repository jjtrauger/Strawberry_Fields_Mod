package com.volgadorf.strawberry_fields.item;

import com.volgadorf.strawberry_fields.Main;
import com.volgadorf.strawberry_fields.block.ModBlocks;
import com.volgadorf.strawberry_fields.item.custom.KnifeItem;
import com.volgadorf.strawberry_fields.item.custom.MagicBucketItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFoodItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> PAST_MILK = ITEMS.register("past_milk",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> CHEEMS = ITEMS.register("cheems",
            () -> new Item(new Item.Properties().stacksTo(64)));

    //nameitemblock is reserved for items and blocks that have the same name; consider making own class and extendin PlaceOnWater
    //and implementing ItemNameBlockItem for crops that replant using product or whenever i want the item names to be the same
    public static final RegistryObject<Item> RICE_SEED = ITEMS.register("rice_seed",
            //maybe change PlaceOnWaterBlockItem to ItemNameBlockItem and implementing PlaceOnWaterBlockItem in the rice seed class
            () -> new PlaceOnWaterBlockItem(ModBlocks.RICE_CROP.get(),
                    new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> RICE = ITEMS.register("rice",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
            () -> new KnifeItem(Tiers.IRON, 2, -1.4f, new Item.Properties().durability(30)));

    public static final RegistryObject<Item> OFWB = ITEMS.register("over_flowing_water_bucket",
            () -> new MagicBucketItem(() -> Fluids.WATER, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> OFLB = ITEMS.register("over_flowing_lava_bucket",
            () -> new MagicBucketItem(() -> Fluids.LAVA, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BOTTOMLESSBUCKET = ITEMS.register("bottomless_bucket",
            () -> new MagicBucketItem(() -> Fluids.EMPTY, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SUSHI = ITEMS.register("sushi",
            () -> new Item(new Item.Properties().stacksTo(64).food(Foods.SUSHI_FOOD)));

    public static final RegistryObject<Item> COOKED_RICE = ITEMS.register("cooked_rice",
            () -> new Item(new Item.Properties().stacksTo(64).food(Foods.COOKED_RICE_FOOD)));

    public static class Foods{
        public static final FoodProperties SUSHI_FOOD = new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(1.0f)
                .effect( () -> new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 1), 1.0f)
                .build();

        public static final FoodProperties COOKED_RICE_FOOD = new FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.5f)
                .build();
    }



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
