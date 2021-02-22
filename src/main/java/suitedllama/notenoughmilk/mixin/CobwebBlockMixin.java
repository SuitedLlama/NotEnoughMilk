package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(CobwebBlock.class)
public abstract class CobwebBlockMixin extends Block {


	public CobwebBlockMixin(Settings settings) {
		super(settings);
	}

	/**
	 * @author SuitedLlama
	 */
	@Overwrite
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && !((((LivingEntity)entity).hasStatusEffect(NotEnoughMilkStatusEffects.SPIDERED)) || (((LivingEntity)entity).hasStatusEffect(NotEnoughMilkStatusEffects.CAVE_SPIDERED)))){
				entity.slowMovement(state, new Vec3d(0.25D, 0.05000000074505806D, 0.25D));
		}
		if (!(entity instanceof LivingEntity)){
			entity.slowMovement(state, new Vec3d(0.25D, 0.05000000074505806D, 0.25D));
		}
	}
}
