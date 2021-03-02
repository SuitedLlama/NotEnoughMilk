package suitedllama.notenoughmilk.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import suitedllama.notenoughmilk.statuseffects.NotEnoughMilkStatusEffects;

@SuppressWarnings("deprecation")
@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends Block {

    public GrassBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if (player.hasStatusEffect(NotEnoughMilkStatusEffects.SHEEPED) && player.getHungerManager().isNotFull() && stackInHand.isEmpty()){
            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
            world.playSound(player, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            player.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F);
            player.playSound(SoundEvents.ENTITY_SHEEP_AMBIENT, 1.0F, 1.0F);
            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
            player.getHungerManager().add(1, 1);
            int i = 1 + world.random.nextInt(8);

            for(int j = 0; j < i; ++j) {
                ItemEntity itemEntity = player.dropItem(Items.WHITE_WOOL, 1);
                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add((double)((world.random.nextFloat() - world.random.nextFloat()) * 0.1F), (double)(world.random.nextFloat() * 0.05F), (double)((world.random.nextFloat() - world.random.nextFloat()) * 0.1F)));
                }
            }
            return ActionResult.success(world.isClient);
        }
        else{
        return ActionResult.PASS;
        }
    }
}