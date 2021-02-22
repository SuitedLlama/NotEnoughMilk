package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
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
import net.minecraft.world.World;
import suitedllama.notenoughmilk.mixin.PlayerEntityMixin;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class WitchMilkItem extends Item {

   public WitchMilkItem(Settings settings) {
      super(settings);
   }


   public static boolean waterBreathingPotionRecieved;
   public static boolean fireResistancePotionRecieved;
   public static boolean regenerationPotionRecieved;
   public static boolean strengthPotionRecieved;

   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      if (user instanceof ServerPlayerEntity) {
         ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
         Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
         serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
      }

      if (user instanceof PlayerEntity && !((PlayerEntity)user).abilities.creativeMode) {
         stack.decrement(1);
      }

      if (!world.isClient) {
           this.waterBreathingPotionRecieved = false;
           this.regenerationPotionRecieved = false;
           this.fireResistancePotionRecieved = false;
           this.strengthPotionRecieved = false;
           user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.WITCHED, 6000, 0));
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