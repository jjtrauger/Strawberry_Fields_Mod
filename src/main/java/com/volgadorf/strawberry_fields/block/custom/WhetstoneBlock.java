package com.volgadorf.strawberry_fields.block.custom;

import com.volgadorf.strawberry_fields.item.ModFoodItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WhetstoneBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public WhetstoneBlock(Properties pProperties) {
        super(pProperties);
    }

    protected static final VoxelShape SHAPE1 = Block.box(6, 0, 4, 10, 2, 12);
    protected static final VoxelShape SHAPE2 = Block.box(4, 0, 6, 12, 2, 10);

    @Override
    public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        Direction facing = p_60555_.getValue(FACING);
        if (facing.equals(Direction.NORTH) || facing.equals(Direction.SOUTH)){
            return SHAPE1;
        }
        else{
            return SHAPE2;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (player.getMainHandItem().getItem().equals(ModFoodItems.KNIFE.get())){
            //heal 3 durability from knife
            player.getMainHandItem().hurt(-4, level.getRandom(), null);
            float pitch = 1.5F;
            float volume = 0.5F;
            level.playSound(player, blockPos, net.minecraft.sounds.SoundEvents.GRINDSTONE_USE, net.minecraft.sounds.SoundSource.BLOCKS, volume, pitch);
            //level.getGameTime();
            //level.playSound(player, blockPos, net.minecraft.sounds.SoundEvents.GRINDSTONE_USE, net.minecraft.sounds.SoundSource.BLOCKS, volume, pitch);
            return InteractionResult.SUCCESS;
        }
        else{
            return InteractionResult.PASS;
        }
    }
}
