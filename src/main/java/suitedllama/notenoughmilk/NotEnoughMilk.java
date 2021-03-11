package suitedllama.notenoughmilk;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import suitedllama.notenoughmilk.milks.*;
import suitedllama.notenoughmilk.milks.phantom.PhantomMilkItem;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class NotEnoughMilk implements ModInitializer {

	public static final String MOD_ID = "notenoughmilk";

	public static final Item BAT_MILK_BUCKET = new BatMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item CAT_MILK_BUCKET = new CatMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item CHICKEN_MILK_BUCKET = new ChickenMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item FISH_MILK_BUCKET = new FishMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item DONKEY_MILK_BUCKET = new DonkeyMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item FOX_MILK_BUCKET = new FoxMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SQUID_MILK_BUCKET = new SquidMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item PIG_MILK_BUCKET = new PigMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SNOW_GOLEM_MILK_BUCKET = new SnowGolemMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item HORSE_MILK_BUCKET = new HorseMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item MOOSHROOM_MILK_BUCKET = new MooshroomMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SHULKER_MILK_BUCKET = new ShulkerMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item CREEPER_MILK_BUCKET = new CreeperMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item IRON_GOLEM_MILK_BUCKET = new IronGolemMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item PARROT_MILK_BUCKET = new ParrotMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item BLAZE_MILK_BUCKET = new BlazeMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item GHAST_MILK_BUCKET = new GhastMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item ZOMBIE_MILK_BUCKET = new ZombieMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SPIDER_MILK_BUCKET = new SpiderMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item CAVE_SPIDER_MILK_BUCKET = new CaveSpiderMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item WITCH_MILK_BUCKET = new WitchMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SLIME_MILK_BUCKET = new SlimeMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item MAGMA_CUBE_MILK_BUCKET = new MagmaCubeMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SKELETON_MILK_BUCKET = new SkeletonMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item STRAY_MILK_BUCKET = new StrayMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item RABBIT_MILK_BUCKET = new RabbitMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item BEE_MILK_BUCKET = new BeeMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item DOLPHIN_MILK_BUCKET = new DolphinMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item WITHER_SKELETON_MILK_BUCKET = new WitherSkeletonMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item ENDERMAN_MILK_BUCKET = new EndermanMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item POLAR_BEAR_MILK_BUCKET = new PolarBearMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item MULE_MILK_BUCKET = new MuleMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item TURTLE_MILK_BUCKET = new TurtleMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item WOLF_MILK_BUCKET = new WolfMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item ZOMBIFIED_PIGLIN_MILK_BUCKET = new ZombifiedMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item ZOGLIN_MILK_BUCKET = new ZombifiedMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item SHEEP_MILK_BUCKET = new SheepMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item PANDA_MILK_BUCKET = new PandaMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item VILLAGER_MILK_BUCKET = new VillagerMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item OCELOT_MILK_BUCKET = new CatMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item STRIDER_MILK_BUCKET = new StriderMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item VINDICATOR_MILK_BUCKET = new VindicatorMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item PILLAGER_MILK_BUCKET = new PillagerMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item PHANTOM_MILK_BUCKET = new PhantomMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item EVOKER_MILK_BUCKET = new EvokerMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item RAVAGER_MILK_BUCKET = new RavagerMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item LLAMA_MILK_BUCKET = new LlamaMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));
	public static final Item HOGLIN_MILK_BUCKET = new HoglinMilkItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));



	@Override
	public void onInitialize() {
		NotEnoughMilkStatusEffects.statusEffectInit();
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"bat_milk_bucket"), BAT_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"cat_milk_bucket"), CAT_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"chicken_milk_bucket"), CHICKEN_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"fish_milk_bucket"), FISH_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"donkey_milk_bucket"), DONKEY_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"fox_milk_bucket"), FOX_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"squid_milk_bucket"), SQUID_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"pig_milk_bucket"), PIG_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"snow_golem_milk_bucket"), SNOW_GOLEM_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"horse_milk_bucket"), HORSE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"mooshroom_milk_bucket"), MOOSHROOM_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"shulker_milk_bucket"), SHULKER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"creeper_milk_bucket"), CREEPER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"iron_golem_milk_bucket"), IRON_GOLEM_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"parrot_milk_bucket"), PARROT_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"blaze_milk_bucket"), BLAZE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"ghast_milk_bucket"), GHAST_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"zombie_milk_bucket"), ZOMBIE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"spider_milk_bucket"), SPIDER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"cave_spider_milk_bucket"), CAVE_SPIDER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"witch_milk_bucket"), WITCH_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"slime_milk_bucket"), SLIME_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"magma_cube_milk_bucket"), MAGMA_CUBE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"skeleton_milk_bucket"), SKELETON_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"stray_milk_bucket"), STRAY_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"rabbit_milk_bucket"), RABBIT_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"bee_milk_bucket"), BEE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"dolphin_milk_bucket"), DOLPHIN_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"wither_skeleton_milk_bucket"), WITHER_SKELETON_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"enderman_milk_bucket"), ENDERMAN_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"polar_bear_milk_bucket"), POLAR_BEAR_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"mule_milk_bucket"), MULE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"turtle_milk_bucket"), TURTLE_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"wolf_milk_bucket"), WOLF_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"zombified_piglin_milk_bucket"), ZOMBIFIED_PIGLIN_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"sheep_milk_bucket"), SHEEP_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"panda_milk_bucket"), PANDA_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"villager_milk_bucket"), VILLAGER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"ocelot_milk_bucket"), OCELOT_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"strider_milk_bucket"), STRIDER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"vindicator_milk_bucket"), VINDICATOR_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"pillager_milk_bucket"), PILLAGER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"phantom_milk_bucket"), PHANTOM_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"evoker_milk_bucket"), EVOKER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"ravager_milk_bucket"), RAVAGER_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"llama_milk_bucket"), LLAMA_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"hoglin_milk_bucket"), HOGLIN_MILK_BUCKET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID,"zoglin_milk_bucket"), ZOGLIN_MILK_BUCKET);

	}
}
