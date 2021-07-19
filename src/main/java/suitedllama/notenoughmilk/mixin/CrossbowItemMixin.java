package suitedllama.notenoughmilk.mixin;

import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends RangedWeaponItem {


	@Shadow
	private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
		return null;
	}

	protected CrossbowItemMixin(Settings settings) {
		super(settings);
	}

	/**
	 * @author SuitedLlama
	 */
	@Overwrite
	private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
		if (!world.isClient) {
			boolean bl = projectile.getItem() == Items.FIREWORK_ROCKET;
			Object projectileEntity2;
			if (bl) {
				projectileEntity2 = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
			} else {
				projectileEntity2 = createArrow(world, shooter, crossbow, projectile);
				if (creative || simulated != 0.0F || shooter.hasStatusEffect(NotEnoughMilkStatusEffects.PILLAGING)) {
					((PersistentProjectileEntity)projectileEntity2).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
				}
			}

			if (shooter instanceof CrossbowUser) {
				CrossbowUser crossbowUser = (CrossbowUser)shooter;
				crossbowUser.shoot(crossbowUser.getTarget(), crossbow, (ProjectileEntity)projectileEntity2, simulated);
			} else {
				Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
				Quaternion quaternion = new Quaternion(new Vec3f(vec3d), simulated, true);
				Vec3d vec3d2 = shooter.getRotationVec(1.0F);
				Vec3f vector3f = new Vec3f(vec3d2);
				vector3f.rotate(quaternion);
				((ProjectileEntity)projectileEntity2).setVelocity((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), speed, divergence);
			}
			if(!shooter.hasStatusEffect(NotEnoughMilkStatusEffects.PILLAGING)){
				crossbow.damage(bl ? 3 : 1, shooter, (e) -> {
					e.sendToolBreakStatus(hand);
				});
			}
			world.spawnEntity((Entity)projectileEntity2);
			world.playSound((PlayerEntity)null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
		}

	}
}


