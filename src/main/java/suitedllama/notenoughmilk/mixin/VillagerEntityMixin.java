package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {


	public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "interactMob")
	public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() == Items.POPPY && player.hasStatusEffect(NotEnoughMilkStatusEffects.IRONED) && !player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
			this.produceParticles(ParticleTypes.HEART);
			info.setReturnValue(ActionResult.PASS);
		}
	}
}