package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
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

	@Shadow
	public boolean removeStatusEffect(StatusEffect type) {
		return false;
	}

	@Shadow public abstract boolean clearStatusEffects();

	@Shadow public abstract boolean teleport(double x, double y, double z, boolean particleEffects);

	@Shadow public float bodyYaw;

	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo info) {
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.BAMBOOED) && MathHelper.nextInt(random, 0, 200) == 0){
			Vec3d vec3d = this.getVelocity();
			this.world.addParticle(ParticleTypes.SNEEZE, this.getX() - (double)(this.getWidth() + 1.0F) * 0.5D * (double)MathHelper.sin(this.bodyYaw * 0.017453292F), this.getEyeY() - 0.10000000149011612D, this.getZ() + (double)(this.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(this.bodyYaw * 0.017453292F), vec3d.x, 0.0D, vec3d.z);
			createSound(this, SoundEvents.ENTITY_PANDA_SNEEZE, SoundCategory.PLAYERS);
			if(!world.isClient){
				this.dropItem(Items.SLIME_BALL, 1);
			}
		}

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
		if (!this.hasStatusEffect(NotEnoughMilkStatusEffects.IRONED) && this.hasStatusEffect(StatusEffects.HEALTH_BOOST) && this.hasStatusEffect(StatusEffects.SLOWNESS)){
			this.removeStatusEffect(StatusEffects.HEALTH_BOOST);
			this.removeStatusEffect(StatusEffects.SLOWNESS);
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
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SPIDERED) || (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED)) && target instanceof LivingEntity) {
			createSound(target, SoundEvents.ENTITY_SPIDER_HURT, SoundCategory.PLAYERS);
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
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.ENDERMANNED) && target instanceof LivingEntity){
			if (!world.isClient) {
				double d = this.getX();
				double e = this.getY();
				double f = this.getZ();

				for (int i = 0; i < 24; ++i) {
					double g = this.getX() + ((this.world.getRandom().nextDouble() - 0.5D)) * 25.0D;
					double h = MathHelper.clamp(this.getY() + (double) ((this.world.getRandom().nextInt(25) - 8)), 0.0D, (double) (world.getDimensionHeight() - 1));
					double j = this.getZ() + ((this.world.getRandom().nextDouble() - 0.5D)) * 25.0D;
					if (this.hasVehicle()) {
						this.stopRiding();
					}
					if (this.teleport(g, h, j, true)) {
						createSound(this, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
						createSound(target, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
						createSound(target, SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.PLAYERS);
						break;
					}
				}
			}
		}

		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED) && target instanceof LivingEntity)	{
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.BAMBOOED) && target instanceof LivingEntity)	{
			createSound(target, SoundEvents.ENTITY_PANDA_SNEEZE, SoundCategory.PLAYERS);
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.STRAYED) && target instanceof LivingEntity)	{
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED) && target instanceof LivingEntity)	{
			createSound(target, SoundEvents.ENTITY_SHULKER_BULLET_HIT, SoundCategory.PLAYERS);
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.BUZZING) && target instanceof LivingEntity)	{
			createSound(target, SoundEvents.ENTITY_BEE_STING, SoundCategory.PLAYERS);
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.INKING) && target instanceof LivingEntity)	{
			createSound(target, SoundEvents.ENTITY_SQUID_SQUIRT, SoundCategory.PLAYERS);
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 160, 0));
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.WITHERING) && target instanceof LivingEntity)	{
			createSound(target, SoundEvents.ENTITY_WITHER_SKELETON_HURT, SoundCategory.PLAYERS);
			((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 0));
		}
	}
	private void randomTeleport(LivingEntity entity) {
		if (!world.isClient) {
			double d = entity.getX();
			double e = entity.getY();
			double f = entity.getZ();

			for (int i = 0; i < 16; ++i) {
				double g = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
				double h = MathHelper.clamp(entity.getY() + (double) (entity.getRandom().nextInt(16) - 8), 0.0D, (double) (world.getDimensionHeight() - 1));
				double j = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
				if (entity.hasVehicle()) {
					entity.stopRiding();
				}

				if (entity.teleport(g, h, j, true)) {
					world.playSound((PlayerEntity) null, d, e, f, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
					entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
					break;
				}
			}
		}
	}
	public void createSound(Entity entity, SoundEvent soundEvent, SoundCategory soundCategory){
		entity.playSound(soundEvent, 1.0F, 1.0F);
		world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory, 1.0F, 1.0F);
	}

}
 