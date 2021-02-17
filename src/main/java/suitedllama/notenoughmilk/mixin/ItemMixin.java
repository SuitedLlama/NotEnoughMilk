package suitedllama.notenoughmilk.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

import org.spongepowered.asm.mixin.injection.At;


@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {

	@Inject(cancellable = true, at = @At("TAIL"), method = "use")
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        ItemStack stackInHand = user.getStackInHand(hand);
        if (stackInHand.getItem() == Items.BOWL && user.hasStatusEffect(NotEnoughMilkStatusEffects.SHROOMED)) {
            this.milk(stackInHand, user, Items.MUSHROOM_STEW.getDefaultStack(), hand);
            info.setReturnValue(TypedActionResult.success(stackInHand, world.isClient()));
            return TypedActionResult.success(stackInHand, world.isClient());
        }
        
        return TypedActionResult.pass(stackInHand);
    }
    
    protected void milk(ItemStack bucketStack, PlayerEntity player, ItemStack milkStack, Hand hand) {
		player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
		if ((bucketStack.getCount() == 1) && !player.isCreative()){
			player.setStackInHand(hand, milkStack);
		}
		else {
		ItemStack itemStack = ItemUsage.method_30012(bucketStack, player, milkStack);
			player.setStackInHand(hand, itemStack);
		    }
	    }
 }
