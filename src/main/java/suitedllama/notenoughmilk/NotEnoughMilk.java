package suitedllama.notenoughmilk;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import suitedllama.notenoughmilk.milks.*;
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


	}
}
