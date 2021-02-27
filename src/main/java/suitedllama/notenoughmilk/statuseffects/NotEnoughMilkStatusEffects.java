package suitedllama.notenoughmilk.statuseffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import suitedllama.notenoughmilk.NotEnoughMilk;

public class NotEnoughMilkStatusEffects {

    public static final StatusEffect INKED = new AccessibleStatusEffect(StatusEffectType.HARMFUL, 1508648);
    public static final StatusEffect SNOWED = new AccessibleStatusEffect(StatusEffectType.HARMFUL, 11859951);
    public static final StatusEffect SHROOMED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13107200);
    public static final StatusEffect SHULKED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 15728880);
    public static final StatusEffect IRONED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6579300);
    public static final StatusEffect PARROTED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 3342130);
    public static final StatusEffect BATTED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 20);
    public static final StatusEffect BLAZED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16744960);
    public static final StatusEffect GHASTED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16744960);
    public static final StatusEffect SPIDERED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 2621440);
    public static final StatusEffect CAVE_SPIDERED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 5120);
    public static final StatusEffect WITCHED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6553700);
    public static final StatusEffect BONED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13158600);
    public static final StatusEffect STRAYED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13158600);
    public static final StatusEffect BUZZING = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16762880);
    public static final StatusEffect FISHER = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 54015);


    public static void statusEffectInit(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "inked"), INKED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "snowed"), SNOWED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shroomed"), SHROOMED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shulked"), SHULKED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "ironed"), IRONED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "parroted"), PARROTED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "batted"), BATTED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "blazed"), BLAZED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "ghasted"), GHASTED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "spidered"), SPIDERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "cave_spidered"), CAVE_SPIDERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "witched"), WITCHED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "boned"), BONED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "strayed"), STRAYED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "buzzing"), BUZZING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "fisher"), FISHER);


    }
    
}
