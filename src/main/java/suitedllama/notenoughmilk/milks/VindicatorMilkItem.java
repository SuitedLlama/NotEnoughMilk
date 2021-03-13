package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public class VindicatorMilkItem extends Item {

    public VindicatorMilkItem(Settings settings) {
        super(settings);
    }

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
            boolean foundBanner = false;
            boolean foundAxe = false;
            boolean foundShield = false;
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.isItemEqual(Raid.getOminousBanner()))) {
                    foundBanner = true;
                    break;
                }
            }
            if (!foundBanner) {
                if (user.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    user.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
                } else {
                    ((PlayerEntity) user).giveItemStack(Raid.getOminousBanner());
                }
            }
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.getItem() == Items.IRON_AXE)) {
                    foundAxe = true;
                    break;
                }
            }
            if (!foundAxe) {
                ((PlayerEntity)user).giveItemStack(Items.IRON_AXE.getDefaultStack());
            }
            for (int i = 0; i < playerEntity.inventory.size(); i++) {
                ItemStack inventoryStack = playerEntity.inventory.getStack(i);
                if ((inventoryStack.getItem() == Items.SHIELD)) {
                    foundShield = true;
                    break;
                }
            }
            if (!foundShield) {
                if (user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()) {
                    user.equipStack(EquipmentSlot.OFFHAND, (new ItemStack(Items.SHIELD)));
                } else {
                    ((PlayerEntity)user).giveItemStack(Items.SHIELD.getDefaultStack());
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