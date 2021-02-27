package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class SkeletonMilkItem extends Item {

    public SkeletonMilkItem(Settings settings) {
      super(settings);
   }

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
          assert user instanceof PlayerEntity;
          PlayerEntity playerEntity = (PlayerEntity) user;
          user.clearStatusEffects();
          user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.BONED, 3000, 0));
          boolean foundBow = false;
          for (int i = 0; i < playerEntity.inventory.size(); i++) {
              ItemStack inventoryStack = playerEntity.inventory.getStack(i);
              if ((inventoryStack.getItem() == Items.BOW)) {
                  foundBow = true;
                  break;
              }
          }
          if (!foundBow) {
              playerEntity.giveItemStack(new ItemStack(Items.BOW));
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