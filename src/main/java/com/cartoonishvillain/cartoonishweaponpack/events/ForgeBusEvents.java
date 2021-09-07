package com.cartoonishvillain.cartoonishweaponpack.events;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingBrick;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingNetherBrick;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.util.ArrayList;
import java.util.Random;


@Mod.EventBusSubscriber(modid = CartoonishWeaponPack.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {
    private static ArrayList<Entity> trackedEntities = new ArrayList<>();

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
            if(player.isHolding(Register.SEATREADERBOARD.get())){
                int chance = 10;
                ItemStack board;
                Hand hand;
                if(player.getItemInHand(Hand.MAIN_HAND).getItem().equals(Register.SEATREADERBOARD.get())) {board = player.getItemInHand(Hand.MAIN_HAND); hand = Hand.MAIN_HAND;}
                else {board = player.getItemInHand(Hand.OFF_HAND); hand = Hand.OFF_HAND;}
                // 10% chance for damage block
                if(player.getRandom().nextInt(100) < chance && valid_damage(event.getSource())){
                    board.hurtAndBreak((int) event.getAmount(), player, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(hand);
                    });
                    event.setCanceled(true);
                }
            } else if(player.getItemInHand(Hand.MAIN_HAND).getItem().equals(Register.BOXINGGLOVES.get()) && player.getItemInHand(Hand.OFF_HAND).getItem().equals(Register.BOXINGGLOVES.get())){
                int chance = 5;
                ItemStack damagedglove;
                Hand hand;
                //Choose which glove blocked the attack
                if(new Random().nextInt(2) == 0) {damagedglove = player.getItemInHand(Hand.MAIN_HAND); hand = Hand.MAIN_HAND;}
                else {damagedglove = player.getItemInHand(Hand.OFF_HAND); hand = Hand.OFF_HAND;}
                // 5% chance for damage block
                if(player.getRandom().nextInt(100) < chance && valid_damage(event.getSource())){
                    damagedglove.hurtAndBreak((int) event.getAmount()*2, player, (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(hand);
                    });
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void PlayerClickVanillaEvent(PlayerInteractEvent.RightClickItem event){
        if(!event.getPlayer().level.isClientSide()){
            ItemStack offhand = event.getPlayer().getItemInHand(Hand.OFF_HAND);
            if((event.getItemStack().getItem().equals(Items.BRICK) || event.getItemStack().getItem().equals(Items.NETHER_BRICK)) && event.getHand() == Hand.MAIN_HAND) {
                ProjectileItemEntity throwingBrick;

                if(event.getItemStack().getItem().equals(Items.BRICK)) {
                    throwingBrick = new ThrowingBrick(Register.THROWINGBRICK.get(), event.getWorld(), event.getPlayer());
                }else {
                    throwingBrick = new ThrowingNetherBrick(Register.THROWINGNETHERBRICK.get(), event.getWorld(), event.getPlayer());
                }
                throwingBrick.shootFromRotation(event.getPlayer(), event.getPlayer().xRot, event.getPlayer().yRot, 0.0f, 1f, 1.0f);
                event.getWorld().addFreshEntity(throwingBrick);
                event.getPlayer().getCooldowns().addCooldown(event.getItemStack().getItem(), 30);
                event.getItemStack().shrink(1);
            }
            else if (event.getItemStack().getItem().equals(Items.TNT) && (offhand.getItem().equals(Items.FLINT_AND_STEEL) || offhand.getItem().equals(Items.FIRE_CHARGE))){
                SnowballEntity snowballEntity = new SnowballEntity(EntityType.SNOWBALL, event.getWorld());
                snowballEntity.setPos(event.getPlayer().getX(), event.getPlayer().getY()+2, event.getPlayer().getZ());
                snowballEntity.shootFromRotation(event.getPlayer(), event.getPlayer().xRot, event.getPlayer().yRot, 0.0f, 0.6f, 1.0f);
                snowballEntity.setInvisible(true);
                TNTEntity tntEntity = new TNTEntity(EntityType.TNT, event.getWorld());
                event.getWorld().addFreshEntity(snowballEntity);
                tntEntity.setPos(snowballEntity.getX(), snowballEntity.getY(), snowballEntity.getZ());
                tntEntity.setDeltaMovement(snowballEntity.getDeltaMovement());
                event.getWorld().addFreshEntity(tntEntity);
                tntEntity.playSound(SoundEvents.TNT_PRIMED, 1.0f, 1.0f);
                snowballEntity.remove(false);

                event.getPlayer().getCooldowns().addCooldown(event.getItemStack().getItem(), 60);
                event.getItemStack().shrink(1);

                if(offhand.getItem().equals(Items.FIRE_CHARGE)){offhand.shrink(1);}
                else {
                    offhand.hurtAndBreak(1, event.getPlayer(), (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(Hand.OFF_HAND);
                    });
                }
            } else if(event.getItemStack().getItem().equals(Items.FIRE_CHARGE) && event.getHand() == Hand.MAIN_HAND){
                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(EntityType.SMALL_FIREBALL, event.getWorld());
                smallFireballEntity.shootFromRotation(event.getPlayer(), event.getPlayer().xRot, event.getPlayer().yRot, 0.0f, 5f, 1.0f);
                smallFireballEntity.setPos(event.getPlayer().getX(), event.getPlayer().getY()+1, event.getPlayer().getZ());
                event.getWorld().addFreshEntity(smallFireballEntity);
                event.getItemStack().shrink(1);
                event.getPlayer().getCooldowns().addCooldown(event.getItemStack().getItem(), 30);
                if(event.getPlayer().getRandom().nextInt(4) == 0){
                event.getPlayer().setSecondsOnFire(3);}
                trackedEntities.add(smallFireballEntity);
                smallFireballEntity.playSound(SoundEvents.BLAZE_SHOOT, 1, 1);
            }
        }
    }

    @SubscribeEvent
    public static void fireballTick(TickEvent.WorldTickEvent event){
        ArrayList<Entity> entitiesToRemove = new ArrayList<>();
        for(Entity entity : trackedEntities){
            if ((entity.getDeltaMovement().x < 1 && entity.getDeltaMovement().x > -1) && (entity.getDeltaMovement().z < 1 && entity.getDeltaMovement().z > -1)){
                entitiesToRemove.add(entity);
            }
        }
        for(Entity entity : entitiesToRemove){
            trackedEntities.remove(entity);
            entity.remove(true);
        }

    }

    @SubscribeEvent
    public static void fireballCancel(FMLServerStoppingEvent event){
        for(Entity entity : trackedEntities){
            entity.remove(false);
        }
    }

    private static boolean valid_damage(DamageSource damageSource){
        return !damageSource.isMagic() && !damageSource.isFire() && !damageSource.equals(DamageSource.CRAMMING) && !damageSource.equals(DamageSource.DROWN) && !damageSource.equals(DamageSource.FALL) && !damageSource.equals(DamageSource.LIGHTNING_BOLT) && !damageSource.equals(DamageSource.LAVA) && !damageSource.equals(DamageSource.HOT_FLOOR) && !damageSource.isBypassArmor();
    }
}
