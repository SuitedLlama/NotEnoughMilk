package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class EvokerMilkItem extends Item {

   public EvokerMilkItem(Settings settings) {
      super(settings);
   }

   public static boolean totemIsNeeded;

   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      if (user instanceof ServerPlayerEntity) {
         ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
         Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
         serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
      }

      if (user instanceof PlayerEntity && !((PlayerEntity)user).isCreative()) {
         stack.decrement(1);
      }

      if (!world.isClient) {
         user.clearStatusEffects();
         boolean foundTotem = false;
         for (int i = 0; i < ((PlayerEntity) user).getInventory().size(); i++) {
            ItemStack inventoryStack = ((PlayerEntity) user).getInventory().getStack(i);
            if ((inventoryStack.isItemEqual(Items.TOTEM_OF_UNDYING.getDefaultStack()))) {
               foundTotem = true;
               totemIsNeeded = false;
               break;
            }
         }
         if (!foundTotem) {
            if (user.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()) {
               totemIsNeeded = false;
               user.equipStack(EquipmentSlot.OFFHAND, Items.TOTEM_OF_UNDYING.getDefaultStack());
            } else {
               totemIsNeeded = true;
            }
         }
           user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.EVOKED, 6000, 0));
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