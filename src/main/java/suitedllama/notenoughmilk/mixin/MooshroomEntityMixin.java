package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;



@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntityMixin extends MobEntity {

	protected MooshroomEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "interactMob")
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() == Items.BUCKET)  {
			info.setReturnValue(ActionResult.PASS);
		 }
	}
}