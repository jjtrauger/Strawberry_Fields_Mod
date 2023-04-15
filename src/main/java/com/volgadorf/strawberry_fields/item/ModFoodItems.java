package com.volgadorf.strawberry_fields.item;

import com.volgadorf.strawberry_fields.Main;
import com.volgadorf.strawberry_fields.block.ModBlocks;
import com.volgadorf.strawberry_fields.item.custom.KnifeItem;
import com.volgadorf.strawberry_fields.item.custom.OverflowingWaterBucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
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
            () -> new PlaceOnWaterBlockItem(ModBlocks.RICE_CROP.get(),
                    new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> RICE = ITEMS.register("rice",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
            () -> new KnifeItem(Tiers.IRON, 2, -1.4f, new Item.Properties().durability(30)));

    public static final RegistryObject<Item> OFWB = ITEMS.register("over_flowing_water_bucket",
            () -> new OverflowingWaterBucketItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
