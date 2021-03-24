package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.milks.PiglinMilkItem;
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

	@Shadow public float bodyYaw;

	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo info) {
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.GUARDED)){
			if(this.hasStatusEffect(StatusEffects.MINING_FATIGUE)){
				this.removeStatusEffect(StatusEffects.MINING_FATIGUE);
			}
		}
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.TURTLED)){
			if(!this.submergedInWater){
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 1));
			}
		}
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.STRIDERED)){
			this.checkBlockCollision();
			if (!this.isInLava()) {
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1));
				}
				if (this.isInLava()) {
					this.removeStatusEffect(StatusEffects.SLOWNESS);
					if(!this.isSneaking()){
						ShapeContext shapeContext = ShapeContext.of(this);
						if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.world.getFluidState(this.getBlockPos().up()).isIn(FluidTags.LAVA)) {
							this.onGround = true;
						} else {
							this.setVelocity(this.getVelocity().multiply(0.5D).add(0.0D, 0.05D, 0.0D));
						}
					}
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

	@Inject(cancellable = true, at = @At("HEAD"), method = "canWalkOnFluid")
	public void canWalkOnFluid(Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.STRIDERED) && !this.isSneaking()){
			cir.setReturnValue(fluid.isIn(FluidTags.LAVA));
		}
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "handleFallDamage")
	public void handleFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> cir) {
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.STRIDERED)){
			cir.setReturnValue(!this.isInLava());
		}
		super.handleFallDamage(fallDistance, damageMultiplier);
	}


	@Override
	public boolean isFireImmune() {
		if(this.hasStatusEffect(NotEnoughMilkStatusEffects.STRIDERED)){
			return true;
		}
		return super.isFireImmune();
	}


	@Inject(at = @At("TAIL"), method = "onAttacking")
	public void onAttacking(Entity target, CallbackInfo info) {
		BlockState blockState = Blocks.COBWEB.getDefaultState();
		if (target instanceof LivingEntity) {
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
					if (target.world.getBlockState(blockPos).isAir() && blockState.canPlaceAt(target.world, blockPos)) {
						target.world.setBlockState(blockPos, blockState);
					}
				}

			}

			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.EVOKED)) {
				double d = Math.min(target.getY(), this.getY());
				double e = Math.max(target.getY(), this.getY()) + 1.0D;
				float f = (float) MathHelper.atan2(target.getZ() - this.getZ(), target.getX() - this.getX());
				int j;
				if (this.squaredDistanceTo(target) < 9.0D) {
					float h;
					for (j = 0; j < 5; ++j) {
						h = f + (float) j * 3.1415927F * 0.4F;
						this.conjureFangs(this.getX() + (double) MathHelper.cos(h) * 1.5D, this.getZ() + (double) MathHelper.sin(h) * 1.5D, d, e, h, 0, this);
					}

					for (j = 0; j < 8; ++j) {
						h = f + (float) j * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
						this.conjureFangs(this.getX() + (double) MathHelper.cos(h) * 2.5D, this.getZ() + (double) MathHelper.sin(h) * 2.5D, d, e, h, 3, this);
					}
				} else {
					for (j = 0; j < 16; ++j) {
						double l = 1.25D * (double) (j + 1);
						int m = 1 * j;
						this.conjureFangs(this.getX() + (double) MathHelper.cos(f) * l, this.getZ() + (double) MathHelper.sin(f) * l, d, e, f, m, this);
					}
				}
				createSound(target, SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS);
			}

			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.ENDERMANNED) && target instanceof LivingEntity) {
				if (!world.isClient) {
					double d = target.getX();
					double e = target.getY();
					double f = target.getZ();

					for (int i = 0; i < 24; ++i) {
						double g = target.getX() + ((target.world.getRandom().nextDouble() - 0.5D)) * 25.0D;
						double h = MathHelper.clamp(target.getY() + (double) ((target.world.getRandom().nextInt(25) - 8)), 0.0D, (double) (world.getDimensionHeight() - 1));
						double j = target.getZ() + ((target.world.getRandom().nextDouble() - 0.5D)) * 25.0D;
						if (target.hasVehicle()) {
							target.stopRiding();
						}
						if (((LivingEntity) target).teleport(g, h, j, true)) {
							createSound(target, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
							createSound(this, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
							createSound(this, SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.PLAYERS);
							createSound(target, SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.PLAYERS);
							break;
						}
					}
				}
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SPITTER) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.PLAYERS);
				LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(world, this.getX(), this.getEyeY() + .25, this.getZ(), 0d, 0d, 0d);
				double d = target.getX() - this.getX();
				double e = target.getBodyY(0.3333333333333333D) - llamaSpitEntity.getY();
				double f = target.getZ() - this.getZ();
				float g = MathHelper.sqrt(d * d + f * f) * 0.2F;
				llamaSpitEntity.setVelocity(d, e + (double) g, f, 1.5F, 10.0F);
				this.world.spawnEntity(llamaSpitEntity);
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED) && target instanceof LivingEntity) {
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.RAVAGED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_RAVAGER_ATTACK, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 99));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.BAMBOOED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_PANDA_SNEEZE, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 6));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.STRAYED) && target instanceof LivingEntity) {
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_SHULKER_BULLET_HIT, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200, 0));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.NIGHTMARE) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 120, 0));
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, 0));
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 9999));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.BUZZING) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_BEE_STING, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
			}
			if (((LivingEntity) target).hasStatusEffect(NotEnoughMilkStatusEffects.GUARDED)) {
				createSound(target, SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS);
				if (!this.hasStatusEffect(NotEnoughMilkStatusEffects.GUARDED)) {
					this.damage(DamageSource.thorns(target), 4);
				}
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SILVERFISHED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_SILVERFISH_HURT, SoundCategory.PLAYERS);
				SilverfishEntity silverfishEntity = (SilverfishEntity) EntityType.SILVERFISH.create(this.getEntityWorld());
				assert silverfishEntity != null;
				silverfishEntity.refreshPositionAndAngles((double) target.getX() + 0.5D, (double) target.getY(), (double) target.getZ() + 0.5D, 0.0F, 0.0F);
				this.world.spawnEntity(silverfishEntity);
				silverfishEntity.playSpawnEffects();
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.ENDERMITED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_ENDERMITE_HURT, SoundCategory.PLAYERS);
				EndermiteEntity endermiteEntity = (EndermiteEntity) EntityType.ENDERMITE.create(this.getEntityWorld());
				assert endermiteEntity != null;
				endermiteEntity.refreshPositionAndAngles((double) target.getX() + 0.5D, (double) target.getY(), (double) target.getZ() + 0.5D, 0.0F, 0.0F);
				this.world.spawnEntity(endermiteEntity);
				endermiteEntity.playSpawnEffects();
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.VEXXED) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_VEX_DEATH, SoundCategory.PLAYERS);
				VexEntity vexEntity = (VexEntity) EntityType.VEX.create(this.getEntityWorld());
				assert vexEntity != null;
				vexEntity.refreshPositionAndAngles((double) target.getX() + 0.5D, (double) target.getY(), (double) target.getZ() + 0.5D, 0.0F, 0.0F);
				this.world.spawnEntity(vexEntity);
				vexEntity.playSpawnEffects();
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.INKING) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_SQUID_SQUIRT, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 160, 0));
			}
			if (this.hasStatusEffect(NotEnoughMilkStatusEffects.WITHERING) && target instanceof LivingEntity) {
				createSound(target, SoundEvents.ENTITY_WITHER_SKELETON_HURT, SoundCategory.PLAYERS);
				((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80, 0));
			}
		}
	}
	public void createSound(Entity entity, SoundEvent soundEvent, SoundCategory soundCategory){
		entity.playSound(soundEvent, 1.0F, 1.0F);
		world.playSound((PlayerEntity)null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, soundCategory, 1.0F, 1.0F);
	}

	private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup, Entity owner) {
		BlockPos blockPos = new BlockPos(x, y, z);
		boolean bl = false;
		double d = 0.0D;

		do {
			BlockPos blockPos2 = blockPos.down();
			BlockState blockState = this.world.getBlockState(blockPos2);
			if (blockState.isSideSolidFullSquare(this.world, blockPos2, Direction.UP)) {
				if (!this.world.isAir(blockPos)) {
					BlockState blockState2 = this.world.getBlockState(blockPos);
					VoxelShape voxelShape = blockState2.getCollisionShape(this.world, blockPos);
					if (!voxelShape.isEmpty()) {
						d = voxelShape.getMax(Direction.Axis.Y);
					}
				}

				bl = true;
				break;
			}

			blockPos = blockPos.down();
		} while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

		if (bl) {
			this.world.spawnEntity(new EvokerFangsEntity(this.world, x, (double)blockPos.getY() + d, z, yaw, warmup, (LivingEntity) owner));
		}

	}
}
 