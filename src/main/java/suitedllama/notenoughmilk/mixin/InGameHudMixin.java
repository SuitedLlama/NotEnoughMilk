package suitedllama.notenoughmilk.mixin;

import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;


import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow private @Final MinecraftClient client;

    @Shadow
    protected abstract void renderOverlay(Identifier texture, float opacity);

    @Final
    @Shadow private static Identifier PUMPKIN_BLUR;

    @Inject(at = @At(value = "JUMP", opcode = Opcodes.IFEQ, ordinal = 1), method = "render")
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo info){
        if ((this.client.options.getPerspective().isFirstPerson()) && this.client.player.hasStatusEffect(NotEnoughMilkStatusEffects.SNOWED)) {
            this.renderOverlay(PUMPKIN_BLUR, 1.0F);
        }
    }
    /*
    See if this works
     */
}
