package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)

public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	public boolean hasStatusEffect(StatusEffect effect) {
		return false;
	}


	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo info) {
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SNOWED) && (!world.isClient)) {
			int i;
			int j;
			int k;
			BlockState blockState = Blocks.SNOW.getDefaultState();

			for (int l = 0; l < 4; ++l) {
				i = MathHelper.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
				j = MathHelper.floor(this.getY());
				k = MathHelper.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
				BlockPos blockPos = new BlockPos(i, j, k);
				if (this.world.getBlockState(blockPos).isAir() && this.world.getBiome(blockPos).getTemperature(blockPos) < 0.8F && blockState.canPlaceAt(this.world, blockPos)) {
					this.world.setBlockState(blockPos, blockState);
				}

			}
		}
	}
	@Inject(cancellable = true, at = @At("HEAD"), method = "isClimbing")
	public void isClimbing(CallbackInfoReturnable<Boolean> cir) {
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SPIDERED) && this.horizontalCollision){
			cir.setReturnValue(true);
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED) && this.horizontalCollision){
			cir.setReturnValue(true);
		}
	}

	@Inject(at = @At("TAIL"), method = "onAttacking")
	public void onAttacking(Entity target, CallbackInfo info) {
		BlockState blockState = Blocks.COBWEB.getDefaultState();
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SPIDERED) || (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED))) {
			int i;
			int j;
			int k;
			for (int l = 0; l < 4; ++l) {
				i = MathHelper.floor(target.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
				j = MathHelper.floor(target.getY());
				k = MathHelper.floor(target.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
				BlockPos blockPos = new BlockPos(i, j, k);
				if (target.world.getBlockState(blockPos).isAir()  && blockState.canPlaceAt(target.world, blockPos)) {
					target.world.setBlockState(blockPos, blockState);
				}
			}
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED) && target instanceof LivingEntity)	{
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.STRAYED) && target instanceof LivingEntity)	{
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED) && target instanceof LivingEntity)	{
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 0));
		}
	}
}
 