package suitedllama.notenoughmilk.goals;

import java.util.EnumSet;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

public class IronGolemMesmerizedGoal extends Goal {
    // TODO GOLEM Doesnt do a thing
    //private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(10.0D).includeInvulnerable().includeTeammates().ignoreEntityTargetRules().includeHidden();
    public TargetPredicate TEMPTING_ENTITY_PREDICATE = TargetPredicate.DEFAULT.setBaseMaxDistance(10.0D);

    protected final MobEntity mob;
    private final double speed;
    protected PlayerEntity closestPlayer;
    private int cooldown;
    private boolean active;

    public IronGolemMesmerizedGoal(MobEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        } else {
            this.closestPlayer = this.mob.world.getClosestPlayer(TEMPTING_ENTITY_PREDICATE, this.mob);
            if (this.closestPlayer == null) {
                return false;
            } else {
                return this.closestPlayer.hasStatusEffect(NotEnoughMilkStatusEffects.VILLAGE_DADDY);
            }
        }
    }

    public void start() {
        this.active = true;
    }

    public void stop() {
        this.closestPlayer = null;
        this.mob.getNavigation().stop();
        this.cooldown = 100;
        this.active = false;
    }

    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, (float) (this.mob.getBodyYawSpeed() + 20), (float) this.mob.getLookPitchSpeed());
        if (this.mob.squaredDistanceTo(this.closestPlayer) < 6.25D) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);
        }
    }

}
