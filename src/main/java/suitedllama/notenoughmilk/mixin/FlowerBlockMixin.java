package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(FlowerBlock.class)
public abstract class FlowerBlockMixin extends PlantBlock {


	protected FlowerBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(player.hasStatusEffect(NotEnoughMilkStatusEffects.BUZZING) && !player.hasStatusEffect(StatusEffects.REGENERATION) && !player.hasStatusEffect(StatusEffects.INSTANT_HEALTH)){
		if (player.getHealth() < player.getMaxHealth()){
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
			}
		if (player.getHealth() >= player.getMaxHealth()) {
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100));
			}
			player.playSound(SoundEvents.BLOCK_BEEHIVE_WORK, 1.0F, 1.0F);
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			return ActionResult.success(player.world.isClient);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
