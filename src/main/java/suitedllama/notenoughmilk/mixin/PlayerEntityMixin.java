package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.projectile.ShulkerBulletEntity;
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
//test
    private int cooldown;
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo info) {
		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SNOWED_IN) && this.isSneaking()) {
			if (cooldown <= 0) {
			SnowballEntity snowballEntity = new SnowballEntity(world, this);
			snowballEntity.setProperties(this, this.pitch, this.yaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(snowballEntity);
			this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			cooldown = 10;
			}
			else if (cooldown >= 0) {
			cooldown --;
			}
		}

		if (this.hasStatusEffect(NotEnoughMilkStatusEffects.SHULKED) && this.isSneaking()) {
			if (cooldown <= 0) {
				LivingEntity target = null;
				ShulkerBulletEntity shulkerBulletEntity = new ShulkerBulletEntity(world, this, target, this.getMovementDirection().getAxis());
				shulkerBulletEntity.setNoGravity(true);
				shulkerBulletEntity.setProperties(this, this.pitch, this.yaw, 0.5F, 0.75F, 1.0F);
				this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
				world.spawnEntity(shulkerBulletEntity);
				cooldown = 40;
			}
			else if (cooldown >= 0) {
				cooldown --;
			}
		}
		}

	@Inject(cancellable = true, at = @At("TAIL"), method = "interact")
	public ActionResult interact(Entity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
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
			  return ActionResult.SUCCESS;
		   } else {
			  info.setReturnValue(ActionResult.PASS);
			  return ActionResult.PASS;
		   }
		}
		return ActionResult.PASS;
	 }
  

}
