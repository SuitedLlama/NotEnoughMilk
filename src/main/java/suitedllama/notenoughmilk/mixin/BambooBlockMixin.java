package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(BambooBlock.class)
public abstract class BambooBlockMixin extends Block {


	public BambooBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stackInHand = player.getStackInHand(hand);
		if (player.hasStatusEffect(NotEnoughMilkStatusEffects.BAMBOOED)) {
			player.playSound(SoundEvents.ENTITY_PANDA_BITE, 1.0F, 1.0F);
			player.getHungerManager().add(1, 1);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			return ActionResult.success(player.world.isClient);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}

