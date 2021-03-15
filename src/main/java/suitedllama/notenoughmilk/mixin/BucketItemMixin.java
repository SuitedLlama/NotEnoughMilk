package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
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
		else if (entity instanceof ShulkerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SHULKER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof CreeperEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.CREEPER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof IronGolemEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.IRON_GOLEM_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ParrotEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PARROT_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof BlazeEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.BLAZE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof GhastEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.GHAST_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ZombieEntity && !(entity instanceof ZombifiedPiglinEntity) && !(entity instanceof DrownedEntity) && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ZOMBIE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SpiderEntity && !(entity instanceof CaveSpiderEntity) && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SPIDER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof CaveSpiderEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.CAVE_SPIDER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof WitchEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.WITCH_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SlimeEntity && !(entity instanceof MagmaCubeEntity)  && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SLIME_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof MagmaCubeEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.MAGMA_CUBE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SkeletonEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SKELETON_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof StrayEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.STRAY_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof RabbitEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.RABBIT_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof BeeEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.BEE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof DolphinEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.DOLPHIN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof WitherSkeletonEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.WITHER_SKELETON_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof EndermanEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ENDERMAN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PolarBearEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.POLAR_BEAR_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof MuleEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.MULE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof TurtleEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.TURTLE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof WolfEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.WOLF_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ZombifiedPiglinEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ZOMBIFIED_PIGLIN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SheepEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SHEEP_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PandaEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PANDA_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof VillagerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.VILLAGER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof OcelotEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.OCELOT_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof StriderEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.STRIDER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof VindicatorEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.VINDICATOR_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PillagerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PILLAGER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PhantomEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PHANTOM_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof EvokerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.EVOKER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof RavagerEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.RAVAGER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof LlamaEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.LLAMA_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ZoglinEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ZOGLIN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof HoglinEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.HOGLIN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PiglinEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.PIGLIN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof DrownedEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.DROWNED_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof SilverfishEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.SILVERFISH_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof EndermiteEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ENDERMITE_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof VexEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.VEX_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof GuardianEntity && !(entity instanceof ElderGuardianEntity) && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.GUARDIAN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof ElderGuardianEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.ELDER_GUARDIAN_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof WitherEntity && entity.isAlive() && !entity.isBaby()) {
			this.milk(stack, player, NotEnoughMilk.WITHER_MILK_BUCKET.getDefaultStack(), hand);
			return ActionResult.success(player.world.isClient);
		}
		else if (entity instanceof PlayerEntity && entity.isAlive()) {
			PlayerEntity milkedPlayer = (PlayerEntity) entity;
			this.milk(stack, player, NotEnoughMilk.PLAYER_MILK_BUCKET.getDefaultStack().setCustomName(milkedPlayer.getDisplayName().copy().append((new LiteralText("'s Milk")))), hand);
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