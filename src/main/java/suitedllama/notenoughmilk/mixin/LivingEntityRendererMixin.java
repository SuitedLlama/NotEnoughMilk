package suitedllama.notenoughmilk.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import suitedllama.notenoughmilk.milks.phantom.PhantomTranslucentCount;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;


@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {


	protected LivingEntityRendererMixin(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@SuppressWarnings("UnresolvedMixinReference")
	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
	private void modifyAlpha(Args args, LivingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		if (entity.hasStatusEffect(NotEnoughMilkStatusEffects.NIGHTMARE)) {
			args.set(7, PhantomTranslucentCount.phantomAlpha);
			args.set(6, 0.5f);
			args.set(5, 0.5f);
			args.set(4, 0.5f);
		}
		if (!entity.hasStatusEffect(NotEnoughMilkStatusEffects.NIGHTMARE)) {
			args.set(7, 1.0f);
		}
	}
	@Inject(at = @At("HEAD"), method = "render")
	public void render(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if(entity.hasStatusEffect(NotEnoughMilkStatusEffects.NIGHTMARE)) {
			if (!entity.isSneaking()) {
				if ((PhantomTranslucentCount.phantomAlpha < .3F) && !entity.isSprinting()) {
					PhantomTranslucentCount.phantomAlpha = PhantomTranslucentCount.phantomAlpha + .01F;
				}
				if (entity.isSprinting() && (PhantomTranslucentCount.phantomAlpha > 0.04F)) {
					PhantomTranslucentCount.phantomAlpha = PhantomTranslucentCount.phantomAlpha - .01F;
				}
			}
			if (!entity.isSprinting()) {
				if ((PhantomTranslucentCount.phantomAlpha < .3F) && !entity.isSneaking()) {
					PhantomTranslucentCount.phantomAlpha = PhantomTranslucentCount.phantomAlpha + .01F;
				}
				if (entity.isSneaking() && (PhantomTranslucentCount.phantomAlpha > 0.04F)) {
					PhantomTranslucentCount.phantomAlpha = PhantomTranslucentCount.phantomAlpha - .01F;
				}
			}
		}

	}

}

