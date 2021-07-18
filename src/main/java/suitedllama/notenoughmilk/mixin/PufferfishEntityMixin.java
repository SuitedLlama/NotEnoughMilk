package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(PufferfishEntity.class)
public abstract class PufferfishEntityMixin extends FishEntity {


	public PufferfishEntityMixin(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isEmpty() && player.hasStatusEffect(NotEnoughMilkStatusEffects.FISHER) && (player.getHungerManager().isNotFull() || player.isCreative())) {
			this.remove(RemovalReason.DISCARDED);
			player.getHungerManager().add(1, 0.1F);
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 0));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0));
			player.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
			return ActionResult.success(player.world.isClient);
		}
		return super.interactMob(player, hand);
	}
}