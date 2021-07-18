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
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.Iterator;
import java.util.List;

public class WitherMilkItem extends Item {

   public WitherMilkItem(Settings settings) {
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
         user.clearStatusEffects();
         user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 14400, 0));
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.WITHERING, 14400, 0));
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.STRIDERED, 14400, 0));
         witherServer(user);
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
   protected void witherServer(LivingEntity user) {
         StatusEffect statusEffect = StatusEffects.WITHER;
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
            } while(serverPlayerEntity.hasStatusEffect(statusEffect));
            Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, user);
            serverPlayerEntity.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 4.0f, 1.0f);
            serverPlayerEntity.addStatusEffect(new StatusEffectInstance(statusEffect, 200, 0));
         }
      }
   }
}