package com.cartoonishvillain.cartoonishweaponpack.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonConfig {
    public static final String CATEGORY = "Vanillia Overrides";

    public ConfigHelper.ConfigValueListener<Boolean> THROWBRICKS;
    public ConfigHelper.ConfigValueListener<Boolean> THROWNETHERBRICKS;
    public ConfigHelper.ConfigValueListener<Boolean> THROWTNTCROUCH;
    public ConfigHelper.ConfigValueListener<Boolean> THROWTNTELYTRA;
    public ConfigHelper.ConfigValueListener<Boolean> FIRECHARGE;

    public CommonConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        this.THROWBRICKS = subscriber.subscribe(builder.comment("Toggles if this mod will allow you to throw bricks (For compatibility with other mods that do so)").define("throwBrick", true));
        this.THROWNETHERBRICKS = subscriber.subscribe(builder.comment("Toggles if this mod will allow you to throw nether bricks (For compatibility with other mods that do so)").define("throwNetherBrick", true));
        this.THROWTNTCROUCH = subscriber.subscribe(builder.comment("Toggles if this mod will allow you to throw tnt while crouching (For compatibility with other mods that do so)").define("throwTnt", true));
        this.THROWTNTELYTRA = subscriber.subscribe(builder.comment("Toggles if this mod will allow you to throw tnt while flying with elytra (For compatibility with other mods that do so)").define("throwTntElytra", true));
        this.FIRECHARGE = subscriber.subscribe(builder.comment("Toggles if this mod will allow you to throw fire charges (For compatibility with other mods that do so)").define("throwFireCharge", true));
    }
}

