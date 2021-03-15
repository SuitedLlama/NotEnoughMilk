package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class PillagerMilkItem extends Item {

    public PillagerMilkItem(Settings settings) {
        super(settings);
    }
    public static boolean bannerIsNeeded;
    public static boolean crossbowIsNeeded;
    public static boolean shieldIsNeeded;

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        user.playSound(SoundEvents.EVENT_RAID_HORN, 64.0F, 1.0F);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
            stack.decrement(1);
        }
        if (!world.isClient) {
            assert user instanceof PlayerEntity;
            PlayerEntity playerEntity = (PlayerEntity) user;
            user.clearStatusEffects();
            user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.PILLAGING, 6000, 0));
            boolean foundBanner = false;
            boolean foundCrossbow = false;
            boolean foundShield = false;
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.isItemEqual(Raid.getOminousBanner()))) {
                    foundBanner = true;
                    bannerIsNeeded = false;
                    break;
                }
            }
            if (!foundBanner) {
                if (user.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    bannerIsNeeded = false;
                    user.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
                } else {
                    bannerIsNeeded = true;
                }
            }
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.getItem() == Items.CROSSBOW)) {
                    foundCrossbow = true;
                    crossbowIsNeeded = false;
                    break;
                }
            }
            if (!foundCrossbow) {
                crossbowIsNeeded = true;
            }
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.getItem() == Items.SHIELD)) {
                    foundShield = true;
                    shieldIsNeeded = false;
                    break;
                }
            }
            if (!foundShield) {
                if (user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()) {
                    shieldIsNeeded = false;
                    user.equipStack(EquipmentSlot.OFFHAND, (new ItemStack(Items.SHIELD)));
                } else {
                    shieldIsNeeded = true;
                }
            }
        }
        return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
    }


    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

}