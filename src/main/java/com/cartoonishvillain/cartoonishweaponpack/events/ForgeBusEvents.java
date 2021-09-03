package com.cartoonishvillain.cartoonishweaponpack.events;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapabilityManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = CartoonishWeaponPack.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {

    @SubscribeEvent
    public static void playerRegister(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof PlayerEntity){
        PlayerCapabilityManager provider = new PlayerCapabilityManager();
        event.addCapability(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "playercooldownreader"), provider);
        }

    }

    @SubscribeEvent
    public static void CooldownChecker(TickEvent.PlayerTickEvent event){
        if(!event.player.level.isClientSide()){
            event.player.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                h.setCooldownValue(event.player.getAttackStrengthScale(0.5f));
            });
        }
    }
}
