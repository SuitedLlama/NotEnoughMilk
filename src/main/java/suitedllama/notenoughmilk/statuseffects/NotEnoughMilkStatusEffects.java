package suitedllama.notenoughmilk.statuseffects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import suitedllama.notenoughmilk.NotEnoughMilk;

public class NotEnoughMilkStatusEffects {

    public static final StatusEffect INKING = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 1508648);
    public static final StatusEffect SNOWED = new AccessibleStatusEffect(StatusEffectType.NEUTRAL, 11859951);
    public static final StatusEffect SHROOMED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13107200);
    public static final StatusEffect SHULKED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 15728880);
    public static final StatusEffect IRONED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6579300);
    public static final StatusEffect PARROTED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 3342130);
    public static final StatusEffect BLAZED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16744960);
    public static final StatusEffect GHASTED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16744960);
    public static final StatusEffect SPIDERED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 2621440);
    public static final StatusEffect CAVE_SPIDERED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 5120);
    public static final StatusEffect WITCHED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6553700);
    public static final StatusEffect BONED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13158600);
    public static final StatusEffect STRAYED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 13158600);
    public static final StatusEffect BUZZING = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16762880);
    public static final StatusEffect FISHER = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 54015);
    public static final StatusEffect WITHERING = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 986895);
    public static final StatusEffect ENDERMANNED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 7864470);
    public static final StatusEffect TURTLED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6592100);
    public static final StatusEffect CARNIVOROUS = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 9830400);
    public static final StatusEffect SHEEPED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16448250);
    public static final StatusEffect BAMBOOED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 8243325);
    public static final StatusEffect VILLAGE_DADDY = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 14469300);
    public static final StatusEffect STRIDERED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6553600);
    public static final StatusEffect PILLAGING = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6579300);
    public static final StatusEffect NIGHTMARE = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 1644865);
    public static final StatusEffect EVOKED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16437760);
    public static final StatusEffect RAVAGED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6581870);
    public static final StatusEffect SPITTER = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 16777150);
    public static final StatusEffect DROWNER = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 61695);
    public static final StatusEffect SILVERFISHED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6579300);
    public static final StatusEffect ENDERMITED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 6553700);
    public static final StatusEffect VEXXED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 65535);
    public static final StatusEffect GUARDED = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 65470);
    public static final StatusEffect DIMENSIONAL = new AccessibleStatusEffect(StatusEffectType.BENEFICIAL, 9830525);


    public static void statusEffectInit(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "inking"), INKING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "snowed"), SNOWED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shroomed"), SHROOMED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "shulked"), SHULKED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "ironed"), IRONED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "parroted"), PARROTED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "blazed"), BLAZED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "ghasted"), GHASTED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "spidered"), SPIDERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "cave_spidered"), CAVE_SPIDERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "witched"), WITCHED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "boned"), BONED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "strayed"), STRAYED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "buzzing"), BUZZING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "fisher"), FISHER);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "withering"), WITHERING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "endermanned"), ENDERMANNED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "turtled"), TURTLED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "carnivorous"), CARNIVOROUS);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "sheeped"), SHEEPED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "bambooed"), BAMBOOED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "village_daddy"), VILLAGE_DADDY);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "stridered"), STRIDERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "pillaging"), PILLAGING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "nightmare"), NIGHTMARE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "evoked"), EVOKED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "ravaged"), RAVAGED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "spitter"), SPITTER);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "drowner"), DROWNER);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "silverfished"), SILVERFISHED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "endermited"), ENDERMITED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "vexxed"), VEXXED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "guarded"), GUARDED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(NotEnoughMilk.MOD_ID, "dimensional"), DIMENSIONAL);

    }

}
