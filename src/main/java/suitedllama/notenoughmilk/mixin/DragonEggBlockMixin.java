package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.NotEnoughMilk;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(DragonEggBlock.class)
public abstract class DragonEggBlockMixin extends Block {

	@Shadow protected abstract void teleport(BlockState state, World world, BlockPos pos);

	public DragonEggBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "onUse")
	public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() == Items.BUCKET)  {
			this.milk(player.getStackInHand(hand), player, NotEnoughMilk.ENDER_DRAGON_MILK_BUCKET.getDefaultStack(), hand);
			cir.setReturnValue(ActionResult.success(world.isClient));
			teleport(state, world, pos);
		}
		super.onUse(state, world, pos, player, hand, hit);
	}

	protected void milk(ItemStack bucketStack, PlayerEntity player, ItemStack milkStack, Hand hand) {
		player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
		if ((bucketStack.getCount() == 1) && !player.isCreative()){
			player.setStackInHand(hand, milkStack);
		}
		else {
			ItemStack itemStack = ItemUsage.method_30012(bucketStack, player, milkStack);
			player.setStackInHand(hand, itemStack);
		}
	}
}

