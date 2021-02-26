package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.milks.BeeMilkItem;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin extends Block {


	public BeehiveBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "onUse")
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack stackInHand = player.getStackInHand(hand);
		if (player.hasStatusEffect(NotEnoughMilkStatusEffects.BUZZING) && stackInHand.isEmpty()) {
			player.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 1.0F, 1.0F);
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 2));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 6000, 2));
			player.removeStatusEffect(NotEnoughMilkStatusEffects.BUZZING);
			cir.setReturnValue(ActionResult.success(player.world.isClient));
		}

	}
}

