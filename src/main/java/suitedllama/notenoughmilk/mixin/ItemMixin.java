package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import org.spongepowered.asm.mixin.injection.At;


@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {

	private int ironCount;

	@Inject(cancellable = true, at = @At("TAIL"), method = "use")
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        ItemStack stackInHand = user.getStackInHand(hand);
		if (stackInHand.getItem() == Items.BOWL && user.hasStatusEffect(NotEnoughMilkStatusEffects.SHROOMED)) {
            this.milk(stackInHand, user, Items.MUSHROOM_STEW.getDefaultStack(), hand);
            info.setReturnValue(TypedActionResult.success(stackInHand, world.isClient()));
        }

		if (stackInHand.getItem() == Items.IRON_INGOT && user.hasStatusEffect(NotEnoughMilkStatusEffects.IRONED)) {
			if (!user.hasStatusEffect(StatusEffects.HEALTH_BOOST)){
				ironCount = 0;
				stackInHand.decrement(1);
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 999999, 0));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 999999, 0));
				ironCount ++;
				user.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 1.0F);
				info.setReturnValue(TypedActionResult.success(stackInHand, world.isClient()));
			}
			else if (user.hasStatusEffect(StatusEffects.HEALTH_BOOST) && (ironCount < 11)){
				stackInHand.decrement(1);
				if(ironCount % 4 == 0) {
					user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 999999, ironCount / 4));
				}
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 999999, ironCount));
				ironCount ++;
				user.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 1.0F);
				info.setReturnValue(TypedActionResult.success(stackInHand, world.isClient()));
			}
			else if (user.hasStatusEffect(StatusEffects.HEALTH_BOOST) && (ironCount >= 12)){
				info.setReturnValue(TypedActionResult.pass(stackInHand));
			}
		}

	}
	@Inject(cancellable = true, at = @At("TAIL"), method = "useOnEntity")
	public void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
		ItemStack stackInHand = user.getStackInHand(hand);
		if (stackInHand.getItem() == Items.POPPY && user.hasStatusEffect(NotEnoughMilkStatusEffects.IRONED) && entity instanceof VillagerEntity && !user.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE)) {
			user.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 3000, 0));
			info.setReturnValue(ActionResult.success(true));
		}
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "finishUsing")
	public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> info) {
		Item item = stack.getItem();
		if ((stack.getItem() == Items.COOKIE) && user.hasStatusEffect(NotEnoughMilkStatusEffects.PARROTED)){
			user.damage(DamageSource.GENERIC, 99999);
		}
		if(user.hasStatusEffect(NotEnoughMilkStatusEffects.CARNIVOROUS) && stack.isFood()){
			if (!item.getFoodComponent().isMeat()){
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 2));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0));
			}

			if (item.getFoodComponent().isMeat()){
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0));
			}
		}
	}

    protected void milk(ItemStack bucketStack, PlayerEntity player, ItemStack milkStack, Hand hand) {
		player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
		if ((bucketStack.getCount() == 1) && !player.isCreative()){
			player.setStackInHand(hand, milkStack);
		}
		else {
		ItemStack itemStack = ItemUsage.exchangeStack(bucketStack, player, milkStack);
			player.setStackInHand(hand, itemStack);
		    }
	    }

 }
