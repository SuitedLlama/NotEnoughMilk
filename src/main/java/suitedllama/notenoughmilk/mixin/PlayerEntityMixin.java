package suitedllama.notenoughmilk.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.milks.WitchMilkItem;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("all")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract boolean damage(DamageSource source, float amount);

	@Shadow public abstract void startFallFlying();
	@Shadow public abstract void stopFallFlying();

	@Shadow public abstract void jump();

	@Shadow protected boolean isSubmergedInWater;

	@Shadow public abstract boolean giveItemStack(ItemStack stack);

	private int cooldownSnowShoot;
	private int cooldownBlazeShoot;
	private int cooldownShulkerShoot;
	private int ironedTurretCooldown;
	private boolean songPlaying;
	private BlockPos songSource;


	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo info) throws InterruptedException {
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SNOWED) && this.isSneaking()) {
			if (cooldownSnowShoot <= 0) {
			SnowballEntity snowballEntity = new SnowballEntity(world, this);
			snowballEntity.setProperties(this, this.pitch, this.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(snowballEntity);
			this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			cooldownSnowShoot = 10;
			}
			else if (cooldownSnowShoot >= 0) {
			cooldownSnowShoot --;
			}
		}

		if (this.songSource != null && this.songSource.isWithinDistance(this.getPos(), 3.46D) && this.world.getBlockState(this.songSource).isOf(Blocks.JUKEBOX) && this.songPlaying == true && this.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED)) {
			ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
			if (this.isOnGround()){
				this.jump();
				this.swingHand(Hand.MAIN_HAND);
			}
		}
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.BLAZED) || this.hasStatusEffect(NotEnoughMilkStatusEffects.GHASTED)){
			FireballEntity fireballEntity = new FireballEntity(world, this, 0, 0, 0);
			if (!this.world.getDimension().isUltrawarm()){
				this.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
				this.removeStatusEffect(NotEnoughMilkStatusEffects.BLAZED);
				this.removeStatusEffect(StatusEffects.SLOW_FALLING);
				this.removeStatusEffect(NotEnoughMilkStatusEffects.GHASTED);
			}
			if (!this.isSneaking()){
				cooldownBlazeShoot = 25;
			}
			if (this.isSneaking()){
				if (cooldownBlazeShoot <= 0) {
					Vec3d vec3d = this.getRotationVec(1.0F);
					fireballEntity.setProperties(this, this.pitch, this.yaw, 0.0F, 0.0F, 0.0F);
					fireballEntity.updatePosition(this.getX() + vec3d.x * 4.0D, this.getY() + vec3d.y * 4.0D, fireballEntity.getZ() + vec3d.z * 4.0D);
					world.spawnEntity(fireballEntity);
					cooldownBlazeShoot = 25;
				}
				else if (cooldownBlazeShoot >= 0) {
					cooldownBlazeShoot --;
				}
			}

		}

		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.IRONED)) {
			if (!this.isSneaking() && this.hasStatusEffect(StatusEffects.STRENGTH) && this.hasStatusEffect(StatusEffects.SLOWNESS)){
				ironedTurretCooldown = 15;
				this.removeStatusEffect(StatusEffects.SLOWNESS);
				this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 999999, 0));
				this.removeStatusEffect(StatusEffects.STRENGTH);
			}
			if (this.isSneaking()){
				ironedTurretCooldown --;
				if(ironedTurretCooldown == 0){
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 999999, 4));
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 999999, 99));
				}
			}


		}
	if (!this.isFallFlying() && this.jumping) {
		if ((this.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED) || this.hasStatusEffect(NotEnoughMilkStatusEffects.BATTED))) {
			if (!this.onGround && !this.isFallFlying() && !this.isTouchingWater()) {
				this.startFallFlying();
			}

		}
	}

	if (this.hasStatusEffect(NotEnoughMilkStatusEffects.WITCHED)){
		if ((this.isSubmergedInWater) && !WitchMilkItem.waterBreathingPotionRecieved){
			this.giveItemStack(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LONG_WATER_BREATHING));
			WitchMilkItem.waterBreathingPotionRecieved = true;
		}
		if ((this.getAttacker() != null) && !WitchMilkItem.regenerationPotionRecieved){
			this.giveItemStack(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING));
			WitchMilkItem.regenerationPotionRecieved = true;
		}
		if ((this.isOnFire() || this.isInLava()) && !WitchMilkItem.fireResistancePotionRecieved){
			this.giveItemStack(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LONG_FIRE_RESISTANCE));
			WitchMilkItem.fireResistancePotionRecieved = true;
		}
		if ((this.getAttacking() instanceof HostileEntity || this.getAttacking() instanceof PlayerEntity) && !WitchMilkItem.strengthPotionRecieved){
			this.giveItemStack(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.LONG_STRENGTH));
			WitchMilkItem.strengthPotionRecieved = true;
		}
		if (WitchMilkItem.waterBreathingPotionRecieved && WitchMilkItem.regenerationPotionRecieved && WitchMilkItem.fireResistancePotionRecieved && WitchMilkItem.strengthPotionRecieved){
			this.removeStatusEffect(NotEnoughMilkStatusEffects.WITCHED);
		}
	}

	if ((this.isFallFlying() && this.onGround) && ((this.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED) || this.hasStatusEffect(NotEnoughMilkStatusEffects.BATTED)))){
		this.stopFallFlying();
	}

	if (this.isSneaking() && ((this.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED) || this.hasStatusEffect(NotEnoughMilkStatusEffects.BATTED)))) {
		this.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 10, 7));
		}



		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED)) {
			if (hasBeenDamaged(this)){
				if ((!world.isClient)) {
				double d = this.getX();
					double e = this.getY();
					double f = this.getZ();

					for (int i = 0; i < 16; ++i) {
						double g = this.getX() + (this.getRandom().nextDouble() - 0.5D) * 16.0D;
						double h = MathHelper.clamp(this.getY() + (double) (this.getRandom().nextInt(16) - 8), 0.0D, (double) (world.getDimensionHeight() - 1));
						double j = this.getZ() + (this.getRandom().nextDouble() - 0.5D) * 16.0D;
						if (this.hasVehicle()) {
							this.stopRiding();
						}

						if (this.teleport(g, h, j, true)) {
							world.playSound((PlayerEntity) null, d, e, f, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
							this.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
							break;
						}
					}
				}
				}
			if (this.isSneaking()) {
				if (cooldownShulkerShoot <= 0) {
					LivingEntity target = null;
					ShulkerBulletEntity shulkerBulletEntity = new ShulkerBulletEntity(world, this, target, this.getMovementDirection().getAxis());
					shulkerBulletEntity.setNoGravity(true);
					shulkerBulletEntity.setProperties(this, this.pitch, this.yaw, 0.5F, 0.75F, 1.0F);
					this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
					world.spawnEntity(shulkerBulletEntity);
					cooldownShulkerShoot = 40;
				}
				else if (cooldownShulkerShoot >= 0) {
					cooldownShulkerShoot --;
				}
			}
		}

	}
	@Inject(cancellable = true, at = @At("HEAD"), method = "handleFallDamage")
	public void handleFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info) {
		if ((this.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED) || this.hasStatusEffect(NotEnoughMilkStatusEffects.BATTED))){
			info.setReturnValue(false);
		}
	}

	@Inject(cancellable = true, at = @At("TAIL"), method = "interact")
	public void interact(Entity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ItemStack itemStack = (this.getStackInHand(hand));
		if (itemStack.getItem() == Items.SHEARS && ((LivingEntity) player).hasStatusEffect(NotEnoughMilkStatusEffects.SHROOMED)) {
		   if (!this.world.isClient) {
			  itemStack.damage(1, (LivingEntity)player, (Consumer)((playerEntity) -> {
				 	((LivingEntity) playerEntity).sendToolBreakStatus(hand);
			  }));
			  this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
			  int i = 1 + this.random.nextInt(3);

			  for(int j = 0; j < i; ++j) {
				 ItemEntity itemEntity = player.dropItem(Items.RED_MUSHROOM, 1);
				 if (itemEntity != null) {
					itemEntity.setVelocity(itemEntity.getVelocity().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
				 }
			  }
			((LivingEntity) player).removeStatusEffect(NotEnoughMilkStatusEffects.SHROOMED);
			  info.setReturnValue(ActionResult.SUCCESS);
		   } else {
			  info.setReturnValue(ActionResult.PASS);
		   }
		}
	 }
  	public boolean hasBeenDamaged(LivingEntity instance){
		if(this.world.getTime() - (((LivingEntityAccess)instance).lastDamageTime()) > 1L) {
			return false;
			}
		else {
			return true;
		}
	}

	@Environment(EnvType.CLIENT)
	public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
		this.songSource = songPosition;
		this.songPlaying = playing;
	}

	@Environment(EnvType.CLIENT)
	public boolean getSongPlaying() {
		return this.songPlaying;
	}

}
