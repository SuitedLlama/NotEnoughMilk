package suitedllama.notenoughmilk.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import suitedllama.notenoughmilk.milks.phantom.PhantomMilkItem;
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
	private void modifyAlpha(Args args, T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		float initialAlpha = args.get(7);
		if (entity.hasStatusEffect(NotEnoughMilkStatusEffects.NIGHTMARE)) {
			if (!entity.isSprinting() && initialAlpha != .33f) {
				if (initialAlpha > .33f) {
					float resultAlpha;
					resultAlpha = initialAlpha - .01f;
					args.set(7, resultAlpha);
				}
				if ((initialAlpha < .33f) && initialAlpha != .33f) {
					float resultAlpha;
					resultAlpha = initialAlpha + .01f;
					args.set(7, resultAlpha);
				}
				if (initialAlpha == .33f) {
					args.set(7, .33f);
				}
			}
			if (entity.isSprinting()) {
				if ((initialAlpha > .05f) && initialAlpha != .05f) {
					float resultAlpha;
					resultAlpha = initialAlpha - .01f;
					args.set(7, resultAlpha);
				}
				if ((initialAlpha < .05f) && initialAlpha != .05f) {
					float resultAlpha;
					resultAlpha = initialAlpha + .01f;
					args.set(7, resultAlpha);
				}
				if (initialAlpha == .05f) {
					args.set(7, .05f);
				}
			}
		}
	}

}
