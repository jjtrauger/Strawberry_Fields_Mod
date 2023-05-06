package com.volgadorf.strawberry_fields.block.custom;

import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class RiceCropBlock extends CropBlock {

    /*

    i'd probably extend placeonwater and implement the name thing, since it's less code
    ItemNameBlockItem is just one method that's just one line

     */
    public RiceCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModFoodItems.RICE_SEED.get();
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return super.getAgeProperty();
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected int getBonemealAgeIncrease(@NotNull Level pLevel) {
        return 1;
    }


    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.WATER;
    }
}
