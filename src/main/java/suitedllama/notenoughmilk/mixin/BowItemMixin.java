package suitedllama.notenoughmilk.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.Random;

import static net.minecraft.item.BowItem.getPullProgress;

@Mixin(BowItem.class)
public abstract class BowItemMixin extends RangedWeaponItem {


	public BowItemMixin(Settings settings) {
		super(settings);
	}


	@Inject(at = @At("HEAD"), method = "onStoppedUsing")
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
		PlayerEntity playerEntity = (PlayerEntity) user;
		ItemStack itemStack = playerEntity.getArrowType(stack);
		if (user.hasStatusEffect(NotEnoughMilkStatusEffects.BONED) || user.hasStatusEffect(NotEnoughMilkStatusEffects.STRAYED)) {
			if (itemStack.isEmpty()) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				float f = getPullProgress(i);
				if (!((double)f < 0.1D)) {
					if (!world.isClient) {
						ArrowItem arrowItem = (ArrowItem)((ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW));
						PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack, playerEntity);
						persistentProjectileEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
						persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
						persistentProjectileEntity.setCritical(false);
						persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage());


						int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
						if (k > 0) {
							persistentProjectileEntity.setPunch(k);
						}

						if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
							persistentProjectileEntity.setOnFireFor(100);
						}

						world.spawnEntity(persistentProjectileEntity);
					}
					// removed random.nextfloat() because of errors that i dont know how to fix rn
					world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, (float) (1.0F / (0.5 * 0.4F + 1.2F) + f * 0.5F));
					playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
				}
			}
		}
	}
	@Inject(cancellable = true, at = @At("HEAD"), method = "use")
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (user.hasStatusEffect(NotEnoughMilkStatusEffects.BONED) || user.hasStatusEffect(NotEnoughMilkStatusEffects.STRAYED)) {
			user.setCurrentHand(hand);
			cir.setReturnValue(TypedActionResult.consume(itemStack));
		}
	}
}
