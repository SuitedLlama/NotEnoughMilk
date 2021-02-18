package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import suitedllama.notenoughmilk.NotEnoughMilk;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

	public BucketItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		if (entity instanceof SquidEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SQUID_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		   }
		else if (entity instanceof CatEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.CAT_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof BatEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.BAT_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ChickenEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.CHICKEN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if ((entity instanceof CodEntity || entity instanceof SalmonEntity || entity instanceof TropicalFishEntity || entity instanceof PufferfishEntity) && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.FISH_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof DonkeyEntity) {
			this.milk(stack, player, NotEnoughMilk.DONKEY_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof FoxEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.FOX_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof HorseEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.HORSE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PigEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PIG_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SnowGolemEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SNOW_GOLEM_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof MooshroomEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.MOOSHROOM_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		if (entity instanceof ShulkerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SHULKER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		if (entity instanceof CreeperEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.CREEPER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		return ActionResult.PASS;
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