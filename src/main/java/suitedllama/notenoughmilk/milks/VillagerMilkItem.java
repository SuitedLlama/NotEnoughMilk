package suitedllama.notenoughmilk.milks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.NotEnoughMilk;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class VillagerMilkItem extends Item {

   public VillagerMilkItem(Settings settings) {
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
         user.addStatusEffect(new StatusEffectInstance(NotEnoughMilkStatusEffects.VILLAGE_DADDY, 6000, 0));
         VillagerEntity villagerEntity = new VillagerEntity(EntityType.VILLAGER, world, VillagerType.forBiome(world.method_31081(user.getBlockPos())));
         villagerEntity.updatePosition(user.getX(), user.getY(), user.getZ());
         villagerEntity.setBaby(true);
         if(user.getDisplayName()!= null){
            villagerEntity.setCustomName(user.getDisplayName().copy().append((new LiteralText(" Jr."))));
         }
         world.spawnEntity(villagerEntity);
      }
      this.produceParticles(ParticleTypes.HEART,user,world);
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
   @Environment(EnvType.CLIENT)
   protected void produceParticles(ParticleEffect parameters, LivingEntity entity, World world) {
      for(int i = 0; i < 5; ++i) {
         double d = world.random.nextGaussian() * 0.02D;
         double e = world.random.nextGaussian() * 0.02D;
         double f = world.random.nextGaussian() * 0.02D;
         world.addParticle(parameters, entity.getParticleX(1.0D), entity.getRandomBodyY() + 1.0D, entity.getParticleZ(1.0D), d, e, f);
      }

   }
}