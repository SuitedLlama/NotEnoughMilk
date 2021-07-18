package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class DrownedMilkItem extends Item {

    public static boolean tridentIsNeeded;

    public DrownedMilkItem(Settings settings) {
      super(settings);
   }

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
          assert user instanceof PlayerEntity;
          PlayerEntity playerEntity = (PlayerEntity) user;
          user.clearStatusEffects();
          assert user instanceof ServerPlayerEntity;
          user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.FISHER, 6000, 0));
          user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.DROWNER, 6000, 0));
          user.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 6000, 0));
          boolean foundTrident = false;
          for (int i = 0; i < playerEntity.getInventory().size(); i++) {
              ItemStack inventoryStack = playerEntity.getInventory().getStack(i);
              if ((inventoryStack.getItem() == Items.TRIDENT)) {
                  foundTrident = true;
                  tridentIsNeeded = false;
                  break;
              }
          }
          if (!foundTrident) {
              if(world.random.nextInt(100) <= 15)
              tridentIsNeeded = true;
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