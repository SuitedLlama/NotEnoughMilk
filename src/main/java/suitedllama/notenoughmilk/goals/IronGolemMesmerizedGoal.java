package suitedllama.notenoughmilk.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import java.util.EnumSet;
import java.util.Optional;

/*
JoeMama fixed this
*/

public class IronGolemMesmerizedGoal extends Goal {

    protected final MobEntity mob;
    private final double speed;
    private final World world;
    @Nullable
    protected PlayerEntity closestPlayer;
    private int cooldown;

    public IronGolemMesmerizedGoal(MobEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.world = mob.world;
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else if (!world.isClient()) {
            this.closestPlayer = world.getClosestPlayer(this.mob, 10);

            return this.getClosestPlayer()
                    .filter(player -> player.hasStatusEffect(NotEnoughMilkStatusEffects.VILLAGE_DADDY))
                    .isPresent();
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        Optional<PlayerEntity> closestPlayer = this.getClosestPlayer();

        if (closestPlayer.isEmpty()) {
            return false;
        }

        PlayerEntity player = closestPlayer.get();
        double distance = this.mob.squaredDistanceTo(player);
        return distance > 2.1d;
    }

    @Override
    public boolean canStop() {
        Optional<PlayerEntity> closestPlayer = this.getClosestPlayer();
        return closestPlayer.isPresent() && closestPlayer.get().squaredDistanceTo(this.mob) > 9.5d;
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.mob.getNavigation().stop();
        this.cooldown = 100;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, (float) (this.mob.getBodyYawSpeed() + 20), (float) this.mob.getLookPitchSpeed());
        this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);

        this.getClosestPlayer().ifPresent((entity) -> entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5, 1)));
    }

    private Optional<PlayerEntity> getClosestPlayer() {
        return Optional.ofNullable(this.closestPlayer);
    }

}