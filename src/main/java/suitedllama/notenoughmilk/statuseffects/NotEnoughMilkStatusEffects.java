package suitedllama.notenoughmilk.statuseffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import suitedllama.notenoughmilk.NotEnoughMilk;

public class NotEnoughMilkStatusEffects {

    public static final StatusEffect INKED = new AccessibleStatusEffect(StatusEffectType.HARMFUL, 1508648);
    public static final StatusEffect SNOWED_IN = new AccessibleStatusEffect(StatusEffectType.HARMFUL, 11859951);
    public static final StatusEffect SHROOMED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13107200);
    public static final StatusEffect SHULKED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 15728880);


    public static void statusEffectInit(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "inked"), INKED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "snowed_in"), SNOWED_IN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shroomed"), SHROOMED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shulked"), SHULKED);

    }
    
}
