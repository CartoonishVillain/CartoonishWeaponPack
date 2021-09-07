package com.cartoonishvillain.cartoonishweaponpack.client;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.Register;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CartoonishWeaponPack.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderManager {
    @SubscribeEvent
    public static void registerRenders(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Register.THROWINGBRICK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(Register.THROWINGNETHERBRICK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(Register.THROWNDYNAMITE.get(), ThrownItemRenderer::new);

    }
}
