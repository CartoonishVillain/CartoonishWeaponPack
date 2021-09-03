package com.cartoonishvillain.cartoonishweaponpack;

import com.cartoonishvillain.cartoonishweaponpack.items.ComicallyLargeSpoon;
import com.cartoonishvillain.cartoonishweaponpack.items.SurfBoard;
import com.cartoonishvillain.cartoonishweaponpack.items.WeaponMaterials;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CartoonishWeaponPack.MOD_ID);

    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> COMICALLYLARGESPOON = ITEMS.register("comicallylargespoon", ()->new ComicallyLargeSpoon(WeaponMaterials.SPOON, 7, -3.2f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
    public static final RegistryObject<Item> SURFBOARD = ITEMS.register("surfboard", ()->new SurfBoard(new Item.Properties().tab(ItemGroup.TAB_COMBAT)));

}
