package com.cartoonishvillain.cartoonishweaponpack.events;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapabilityManager;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingBrick;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrowingNetherBrick;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



import java.util.ArrayList;
import java.util.Random;


@Mod.EventBusSubscriber(modid = CartoonishWeaponPack.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {
    private static ArrayList<Entity> trackedEntities = new ArrayList<>();

    @SubscribeEvent
    public static void playerRegister(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player){
        PlayerCapabilityManager provider = new PlayerCapabilityManager();
        event.addCapability(new ResourceLocation(CartoonishWeaponPack.MOD_ID, "playercooldownreader"), provider);
        }

    }

    @SubscribeEvent
    public static void CooldownChecker(TickEvent.PlayerTickEvent event){
        if(!event.player.level.isClientSide()){
            event.player.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                h.setCooldownValue(event.player.getAttackStrengthScale(0.5f));

                if(h.getCardCooldownTicks() > 0) {
                    h.setCardCooldownTicks(h.getCardCooldownTicks() - 1);
                }

                if (h.getCardEffectTicks() > 0) {
                    h.setCardEffectTicks((short) (h.getCardEffectTicks() - 1));
                }
            });
        }
    }

    @SubscribeEvent
    public static void PlayerHurtEvent(LivingHurtEvent event){
        //occasional block only works with players on server side
        if(event.getEntity() instanceof Player && !event.getEntity().level.isClientSide()){
            Player player = (Player) event.getEntity();

            player.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
                //Reverse card code
                if (h.getCardEffectTicks() > 0) {
                    float dmg = event.getAmount();
                    DamageSource dmgSource = event.getSource();
                    event.setAmount(0);

                    if (dmgSource.getEntity() != null) {
                        dmgSource.getEntity().hurt(dmgSource, dmg);
                    }
                }
            });

            //check if the player is even holding a surf board
//            if(player.isHolding(Register.SEATREADERBOARD.get())){
//                int chance = 15;
//                ItemStack board;
//                InteractionHand hand;
//                if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(Register.SEATREADERBOARD.get())) {board = player.getItemInHand(InteractionHand.MAIN_HAND); hand = InteractionHand.MAIN_HAND;}
//                else {board = player.getItemInHand(InteractionHand.OFF_HAND); hand = InteractionHand.OFF_HAND;}
//                // 10% chance for damage block
//                if(player.getRandom().nextInt(100) < chance && valid_damage(event.getSource())){
//                    board.hurtAndBreak((int) event.getAmount(), player, (p_220040_1_) -> {
//                        p_220040_1_.broadcastBreakEvent(hand);
//                    });
//                    event.setCanceled(true);
//                }
//            } else if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(Register.BOXINGGLOVES.get()) && player.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Register.BOXINGGLOVES.get())){
//                int chance = 25;
//                ItemStack damagedglove;
//                InteractionHand hand;
//                //Choose which glove blocked the attack
//                if(new Random().nextInt(2) == 0) {damagedglove = player.getItemInHand(InteractionHand.MAIN_HAND); hand = InteractionHand.MAIN_HAND;}
//                else {damagedglove = player.getItemInHand(InteractionHand.OFF_HAND); hand = InteractionHand.OFF_HAND;}
//                // 5% chance for damage block
//                if(player.getRandom().nextInt(100) < chance && valid_damage(event.getSource())){
//                    damagedglove.hurtAndBreak((int) event.getAmount()*2, player, (p_220040_1_) -> {
//                        p_220040_1_.broadcastBreakEvent(hand);
//                    });
//                    event.setCanceled(true);
//                }
//            }
        }
    }

    @SubscribeEvent
    public static void PlayerClickVanillaEvent(PlayerInteractEvent.RightClickItem event){
        if(!event.getEntity().level.isClientSide()){
            ItemStack offhand = event.getEntity().getItemInHand(InteractionHand.OFF_HAND);
            if((event.getItemStack().getItem().equals(Items.BRICK) || event.getItemStack().getItem().equals(Items.NETHER_BRICK)) && event.getHand() == InteractionHand.MAIN_HAND) {
                ThrowableItemProjectile throwingBrick;

                if(event.getItemStack().getItem().equals(Items.BRICK)) {
                    throwingBrick = new ThrowingBrick(Register.THROWINGBRICK.get(), event.getLevel(), event.getEntity());
                }else {
                    throwingBrick = new ThrowingNetherBrick(Register.THROWINGNETHERBRICK.get(), event.getLevel(), event.getEntity());
                }
                throwingBrick.shootFromRotation(event.getEntity(), event.getEntity().getXRot(), event.getEntity().getYRot(), 0.0f, 1f, 1.0f);
                event.getLevel().addFreshEntity(throwingBrick);
                event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 30);
                event.getItemStack().shrink(1);
                CartoonishWeaponPack.giveAdvancement((ServerPlayer) event.getEntity(), event.getEntity().getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "bricked"));
            }
            else if (event.getItemStack().getItem().equals(Items.TNT) && (offhand.getItem().equals(Items.FLINT_AND_STEEL) || offhand.getItem().equals(Items.FIRE_CHARGE)) && (event.getEntity().isCrouching() || event.getEntity().isFallFlying())){
                Snowball snowballEntity = new Snowball(EntityType.SNOWBALL, event.getLevel());
                snowballEntity.setPos(event.getEntity().getX(), event.getEntity().getY()+2, event.getEntity().getZ());
                snowballEntity.shootFromRotation(event.getEntity(), event.getEntity().getXRot(), event.getEntity().getYRot(), 0.0f, 0.6f, 1.0f);
                snowballEntity.setInvisible(true);
                PrimedTnt tntEntity = new PrimedTnt(EntityType.TNT, event.getLevel());
                event.getLevel().addFreshEntity(snowballEntity);
                tntEntity.setPos(snowballEntity.getX(), snowballEntity.getY(), snowballEntity.getZ());
                tntEntity.setDeltaMovement(snowballEntity.getDeltaMovement());
                event.getLevel().addFreshEntity(tntEntity);
                tntEntity.playSound(SoundEvents.TNT_PRIMED, 1.0f, 1.0f);
                snowballEntity.remove(Entity.RemovalReason.DISCARDED);

                event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 60);
                event.getItemStack().shrink(1);

                CartoonishWeaponPack.giveAdvancement((ServerPlayer) event.getEntity(), event.getEntity().getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "bombs"));

                if(offhand.getItem().equals(Items.FIRE_CHARGE)){offhand.shrink(1);}
                else {
                    offhand.hurtAndBreak(1, event.getEntity(), (p_220040_1_) -> {
                        p_220040_1_.broadcastBreakEvent(InteractionHand.OFF_HAND);
                    });
                }
            } else if(event.getItemStack().getItem().equals(Items.FIRE_CHARGE) && event.getHand() == InteractionHand.MAIN_HAND){
                SmallFireball smallFireballEntity = new SmallFireball(EntityType.SMALL_FIREBALL, event.getLevel());
                smallFireballEntity.shootFromRotation(event.getEntity(), event.getEntity().getXRot(), event.getEntity().getYRot(), 0.0f, 5f, 1.0f);
                smallFireballEntity.setPos(event.getEntity().getX(), event.getEntity().getY()+1, event.getEntity().getZ());
                event.getLevel().addFreshEntity(smallFireballEntity);
                event.getItemStack().shrink(1);
                event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 30);
                if(event.getEntity().getRandom().nextInt(4) == 0){
                event.getEntity().setSecondsOnFire(3);}
                trackedEntities.add(smallFireballEntity);
                smallFireballEntity.playSound(SoundEvents.BLAZE_SHOOT, 1, 1);
                CartoonishWeaponPack.giveAdvancement((ServerPlayer) event.getEntity(), event.getEntity().getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "fireball"));
            }
        }
    }

    @SubscribeEvent
    public static void fireballTick(TickEvent.LevelTickEvent event){
        ArrayList<Entity> entitiesToRemove = new ArrayList<>();
        for(Entity entity : trackedEntities){
            if ((entity.getDeltaMovement().x < 1 && entity.getDeltaMovement().x > -1) && (entity.getDeltaMovement().z < 1 && entity.getDeltaMovement().z > -1)){
                entitiesToRemove.add(entity);
            }
        }
        for(Entity entity : entitiesToRemove){
            trackedEntities.remove(entity);
            entity.remove(Entity.RemovalReason.DISCARDED);
        }

    }

    @SubscribeEvent
    public static void fireballCancel(ServerStoppingEvent event){
        for(Entity entity : trackedEntities){
            entity.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    @SubscribeEvent
    public static void ItemToolTips(ItemTooltipEvent event){
        if (event.getItemStack().getItem().equals(Items.BRICK) || event.getItemStack().getItem().equals(Items.NETHER_BRICK)) {
            event.getToolTip().add(Component.translatable("cartoonishweapons.brick.tooltip").withStyle(ChatFormatting.BLUE));
        }
        else if (event.getItemStack().getItem().equals(Items.TNT)){
            event.getToolTip().add(Component.translatable("cartoonishweapons.tnt.tooltip").withStyle(ChatFormatting.BLUE));
        }
        else if (event.getItemStack().getItem().equals(Items.FIRE_CHARGE)){
            event.getToolTip().add(Component.translatable("cartoonishweapons.firecharge.tooltip").withStyle(ChatFormatting.BLUE));
            event.getToolTip().add(Component.translatable("cartoonishweapons.firecharge.warn").withStyle(ChatFormatting.RED));
        }

    }

    private static boolean valid_damage(DamageSource damageSource){
        //TODO: Update with fire, magic, and bypass armor
        return false;
//        return !damageSources.isMagic() && !damageSource.isFire() && !damageSource.equals(DamageSource.CRAMMING) && !damageSource.equals(DamageSource.DROWN) && !damageSource.equals(DamageSource.FALL) && !damageSource.equals(DamageSource.LIGHTNING_BOLT) && !damageSource.equals(DamageSource.LAVA) && !damageSource.equals(DamageSource.HOT_FLOOR) && !damageSource.isBypassArmor();
    }
}
