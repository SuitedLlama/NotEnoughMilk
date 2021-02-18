package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("all")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract boolean damage(DamageSource source, float amount);

	private int cooldownSnowShoot;
	private int cooldownShulkerShoot;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo info) {
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SNOWED_IN) && this.isSneaking()) {
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

		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED)) {
			if ((this.getAttacking() != null)){
				LivingEntity attacking = this.getAttacking();
				attacking.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100));
			}
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

}
