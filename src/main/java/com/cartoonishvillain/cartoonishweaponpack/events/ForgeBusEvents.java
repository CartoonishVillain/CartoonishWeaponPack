package com.cartoonishvillain.cartoonishweaponpack.events;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingBrick;
import com.cartoonishvillain.cartoonishweaponpack.items.SurfBoard;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

    @SubscribeEvent
    public static void PlayerHurtEvent(LivingHurtEvent event){
        //occasional block only works with players on server side
        if(event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide()){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            //check if the player is even holding a surf board
            if(player.isHolding(Register.SURFBOARD.get())){
                int chance = 10;
                ItemStack board;
                Hand hand;
                if(player.getItemInHand(Hand.MAIN_HAND).getItem().equals(Register.SURFBOARD.get())) {board = player.getItemInHand(Hand.MAIN_HAND); hand = Hand.MAIN_HAND;}
                else {board = player.getItemInHand(Hand.OFF_HAND); hand = Hand.OFF_HAND;}
                // 10% chance for damage block
                if(player.getRandom().nextInt(100) < chance && valid_damage(event.getSource())){
                    board.hurtAndBreak((int) event.getAmount(), player, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(hand);
                    });
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void PlayerClickVanillaEvent(PlayerInteractEvent.RightClickItem event){
        if(event.getItemStack().getItem().equals(Items.BRICK) && !event.getPlayer().level.isClientSide()){
            ThrowingBrick throwingBrick = new ThrowingBrick(Register.THROWINGBRICK.get(), event.getWorld(), event.getPlayer());
            throwingBrick.setItem(new ItemStack(Items.BRICK, 1));
            throwingBrick.shootFromRotation(event.getPlayer(), event.getPlayer().xRot, event.getPlayer().yRot, 0.0f, 1.5f, 1.0f);
            event.getWorld().addFreshEntity(throwingBrick);
            event.getPlayer().getCooldowns().addCooldown(event.getItemStack().getItem(), 30);
            event.getItemStack().shrink(1);
        }
    }

    private static boolean valid_damage(DamageSource damageSource){
        return !damageSource.isMagic() && !damageSource.isFire() && !damageSource.equals(DamageSource.CRAMMING) && !damageSource.equals(DamageSource.DROWN) && !damageSource.equals(DamageSource.FALL) && !damageSource.equals(DamageSource.LIGHTNING_BOLT) && !damageSource.equals(DamageSource.LAVA) && !damageSource.equals(DamageSource.HOT_FLOOR) && !damageSource.isBypassArmor();
    }
}
