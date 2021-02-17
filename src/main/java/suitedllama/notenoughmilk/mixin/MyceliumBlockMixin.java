package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(MyceliumBlock.class)
public abstract class MyceliumBlockMixin extends Block {

    public MyceliumBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.hasStatusEffect(NotEnoughMilkStatusEffects.SHROOMED) && player.getHungerManager().isNotFull()){
            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
            world.playSound(player, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            player.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 1.0F, 1.0F);
            player.getHungerManager().add(1, 1);
            return ActionResult.success(world.isClient);
        }
        else{
        return ActionResult.PASS;
        }
    }
}