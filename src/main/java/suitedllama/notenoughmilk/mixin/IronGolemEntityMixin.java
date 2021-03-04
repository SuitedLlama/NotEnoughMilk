package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import suitedllama.notenoughmilk.goals.IronGolemMesmerizedGoal;


@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends MobEntity {

	protected IronGolemEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(cancellable = true, at = @At("HEAD"), method = "initGoals")
	protected void initGoals(CallbackInfo ci) {
		this.goalSelector.add(2, new IronGolemMesmerizedGoal(this, .75D));
	}
}
