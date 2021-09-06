package com.cartoonishvillain.cartoonishweaponpack;

import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingBrick;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrownDynamite;
import com.cartoonishvillain.cartoonishweaponpack.items.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CartoonishWeaponPack.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CartoonishWeaponPack.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> COMICALLYLARGESPOON = ITEMS.register("comicallylargespoon", ()->new ComicallyLargeSpoon(WeaponMaterials.SPOON, 7, -3.2f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> SEATREADERBOARD = ITEMS.register("seatreaderboard", ()->new SeaTreaderBoard(new Item.Properties().tab(ItemGroup.TAB_TOOLS)));
    public static final RegistryObject<Item> LARGECHICKENLEG = ITEMS.register("largechickenleg", ()->new LargeChickenLeg(WeaponMaterials.MEAT, 5, -3.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> BOXINGGLOVES = ITEMS.register("boxingglove", ()->new BoxingGlove(WeaponMaterials.GLOVE, 1, -1f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite", ()->new Dynamite(new Item.Properties().tab(ItemGroup.TAB_TOOLS)));

    public static final RegistryObject<EntityType<ThrowingBrick>> THROWINGBRICK = ENTITY_TYPES.register("throwingbrick", () -> EntityType.Builder.<ThrowingBrick>of(ThrowingBrick::new, EntityClassification.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "throwingbrick").toString()));
    public static final RegistryObject<EntityType<ThrownDynamite>> THROWNDYNAMITE = ENTITY_TYPES.register("throwndynamite", () -> EntityType.Builder.<ThrownDynamite>of(ThrownDynamite::new, EntityClassification.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "throwndynamite").toString()));


}
