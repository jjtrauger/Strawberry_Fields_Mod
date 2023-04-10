package com.volgadorf.strawberry_fields.block;

import com.volgadorf.strawberry_fields.Main;
import com.volgadorf.strawberry_fields.block.custom.Cheems_Wheel_Block;
import com.volgadorf.strawberry_fields.block.custom.CuttingTableBlock;
import com.volgadorf.strawberry_fields.block.custom.RiceCropBlock;
import com.volgadorf.strawberry_fields.item.ModCreativeModeTabs;
import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> CHEEMS_FULL = registerBlock2("cheems_full",
            () -> new Cheems_Wheel_Block(BlockBehaviour.Properties.of(Material.WOOL).noOcclusion()
                    .strength(1f)), ModCreativeModeTabs.VOLG_TAB);

    public static final RegistryObject<Block> CUTTING_TABLE = registerBlock("cutting_table",
            () -> new CuttingTableBlock(BlockBehaviour.Properties.of(Material.BAMBOO)
                    .strength(0.5f).noOcclusion().noParticlesOnBreak()), ModCreativeModeTabs.VOLG_TAB);

    public static final RegistryObject<Block> RICE_CROP = BLOCKS.register("rice_crop",
            () -> new RiceCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));



    private static <T extends Block> RegistryObject<T> registerBlock2(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem2(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }



    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModFoodItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(64)));
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem2(String name, RegistryObject<T> block) {
        return ModFoodItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(8)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
