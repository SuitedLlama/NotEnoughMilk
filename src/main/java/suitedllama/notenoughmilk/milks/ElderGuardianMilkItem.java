package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.Iterator;
import java.util.List;

public class ElderGuardianMilkItem extends Item {

   public ElderGuardianMilkItem(Settings settings) {
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
         user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 8000, 0));
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.FISHER, 8000, 0));
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.DROWNER, 8000, 0));
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.GUARDED, 8000, 0));
         user.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 8000, 0));
         assert user instanceof ServerPlayerEntity;
         ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user;
         serverPlayerEntity.getServerWorld().setWeather(0, 6000,true,true);
         boolean foundTrident = false;
         for (int i = 0; i < playerEntity.inventory.size(); i++) {
            ItemStack inventoryStack = playerEntity.inventory.getStack(i);
            if ((inventoryStack.getItem() == Items.TRIDENT)) {
               foundTrident = true;
               break;
            }
         }
         if (!foundTrident) {
            user.dropItem(Items.TRIDENT, 1);
         }
         fatigueServer(user);
      }

      return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
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
   protected void fatigueServer(LivingEntity user) {
         StatusEffect statusEffect = StatusEffects.MINING_FATIGUE;
         List<ServerPlayerEntity> list = ((ServerWorld)user.world).getPlayers((serverPlayerEntityx) -> {
            return serverPlayerEntityx.getUuid() != user.getUuid() && serverPlayerEntityx.interactionManager.isSurvivalLike(); 
         });
         
         Iterator var7 = list.iterator();
      for(int i = 0; i < user.getServer().getCurrentPlayerCount(); i++){
         cycleTargetPlayers:
         while(true) {
            ServerPlayerEntity serverPlayerEntity;
            do {
               if (!var7.hasNext()) {
                  break cycleTargetPlayers;
               }

               serverPlayerEntity = (ServerPlayerEntity)var7.next();
            } while(serverPlayerEntity.hasStatusEffect(statusEffect) && serverPlayerEntity.getStatusEffect(statusEffect).getAmplifier() >= 2 && serverPlayerEntity.getStatusEffect(statusEffect).getDuration() >= 1200);

            serverPlayerEntity.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.ELDER_GUARDIAN_EFFECT, user.isSilent() ? 0.0F : 1.0F));
            serverPlayerEntity.addStatusEffect(new StatusEffectInstance(statusEffect, 6000, 2));
         }
      }
   }
}