package suitedllama.notenoughmilk.milks;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;
import java.util.Iterator;

public class CreeperMilkItem extends Item {

   public CreeperMilkItem(Settings settings) {
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
         user.world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 5.0F, true, Explosion.DestructionType.BREAK);
         this.spawnEffectsCloud(user);
         user.damage(DamageSource.explosion(user), 20);
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

   private void spawnEffectsCloud(LivingEntity user) {
      Collection<StatusEffectInstance> collection = user.getStatusEffects();
      if (!collection.isEmpty()) {
         AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(user.world, user.getX(), user.getY(), user.getZ());
         areaEffectCloudEntity.setRadius(2.5F);
         areaEffectCloudEntity.setRadiusOnUse(-0.5F);
         areaEffectCloudEntity.setWaitTime(10);
         areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
         areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
         Iterator var3 = collection.iterator();

         while(var3.hasNext()) {
            StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
         }

         user.world.spawnEntity(areaEffectCloudEntity);
      }

   }
}