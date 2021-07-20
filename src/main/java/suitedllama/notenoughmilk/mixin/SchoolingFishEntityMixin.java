package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(SchoolingFishEntity.class)
public abstract class SchoolingFishEntityMixin extends FishEntity {


	public SchoolingFishEntityMixin(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isEmpty() && player.hasStatusEffect(NotEnoughMilkStatusEffects.FISHER) && (player.getHungerManager().isNotFull() || player.isCreative()) ) {
			this.remove(RemovalReason.DISCARDED);
			player.getHungerManager().add(6, 0.3F);
			player.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
			return ActionResult.success(player.world.isClient);
		}
		return super.interactMob(player, hand);
	}
}