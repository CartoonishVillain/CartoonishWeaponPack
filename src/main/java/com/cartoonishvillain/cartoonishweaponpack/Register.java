package com.cartoonishvillain.cartoonishweaponpack;

import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingBrick;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingNetherBrick;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrownDynamite;
import com.cartoonishvillain.cartoonishweaponpack.items.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Register {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CartoonishWeaponPack.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES
            , CartoonishWeaponPack.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

//    public static final RegistryObject<Item> COMICALLYLARGESPOON = ITEMS.register("comicallylargespoon", ()->new ComicallyLargeSpoon(WeaponMaterials.SPOON, ComicallyLargeSpoon.SpoonType.STANDARD, 5, -2.8f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
//    public static final RegistryObject<Item> GIGASPOON = ITEMS.register("gigaspoon", ()->new ComicallyLargeSpoon(WeaponMaterials.BIGSPOON, ComicallyLargeSpoon.SpoonType.GIGA, 10, -3.5f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
//    public static final RegistryObject<Item> SEATREADERBOARD = ITEMS.register("seatreaderboard", ()->new SeaTreaderBoard(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
//    public static final RegistryObject<Item> LARGECHICKENLEG = ITEMS.register("largechickenleg", ()->new LargeChickenLeg(WeaponMaterials.MEAT, 5, -3.0f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
//    public static final RegistryObject<Item> BOXINGGLOVES = ITEMS.register("boxingglove", ()->new BoxingGlove(WeaponMaterials.GLOVE, 1, -1f, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    //.tab(CreativeModeTab.TAB_TOOLS)
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite", ()-> new Dynamite(new Item.Properties()));
    public static final RegistryObject<Item> REVERSE = ITEMS.register("reversecard", ()-> new ReverseCard(new Item.Properties()));


    public static final RegistryObject<EntityType<ThrowingBrick>> THROWINGBRICK = ENTITY_TYPES.register("throwingbrick", () -> EntityType.Builder.<ThrowingBrick>of(ThrowingBrick::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "throwingbrick").toString()));
    public static final RegistryObject<EntityType<ThrowingNetherBrick>> THROWINGNETHERBRICK = ENTITY_TYPES.register("throwingnetherbrick", () -> EntityType.Builder.<ThrowingNetherBrick>of(ThrowingNetherBrick::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "throwingnetherbrick").toString()));
    public static final RegistryObject<EntityType<ThrownDynamite>> THROWNDYNAMITE = ENTITY_TYPES.register("throwndynamite", () -> EntityType.Builder.<ThrownDynamite>of(ThrownDynamite::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "throwndynamite").toString()));


}
