package suitedllama.notenoughmilk.mixin;

import com.mojang.authlib.GameProfile;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }


    @Shadow protected @Final MinecraftClient client;

    @Shadow protected int ticksLeftToDoubleTapSprint;

    @Shadow public Input input;

    @Shadow
    private boolean isWalking() {
        return false;
    }
    @Inject(cancellable = true, at = @At("HEAD"), method = "tickMovement")
    public void tickMovement(CallbackInfo info) {
        boolean bl2 = this.input.sneaking;
        boolean bl3 = this.isWalking();
        boolean bl5 = (float)this.getHungerManager().getFoodLevel() > 6.0F || this.abilities.allowFlying;
        if ((this.onGround || this.isSubmergedInWater()) && !bl2 && !bl3 && this.isWalking() && !this.isSprinting()
                && bl5 && !this.isUsingItem() && !(this.hasStatusEffect(StatusEffects.BLINDNESS) && !(this.hasStatusEffect(NotEnoughMilkStatusEffects.INKED)))) {
            if (this.ticksLeftToDoubleTapSprint <= 0 && !this.client.options.keySprint.isPressed()) {
                this.ticksLeftToDoubleTapSprint = 7;
            } else {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && (!this.isTouchingWater() || this.isSubmergedInWater()) && this.isWalking() && bl5
                && !this.isUsingItem() && !(this.hasStatusEffect(StatusEffects.BLINDNESS) && !(this.hasStatusEffect(NotEnoughMilkStatusEffects.INKED)))
                && this.client.options.keySprint.isPressed()) {
            this.setSprinting(true);
        }

        
    }
}
