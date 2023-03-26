package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BoxingGlove extends DiggerItem {
    public BoxingGlove(Tier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super((float)p_i48460_2_, p_i48460_3_, p_i48460_1_, BlockTags.MINEABLE_WITH_SHOVEL, p_i48460_4_);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //both hands have gloves
        AtomicReference<Float> theSwing = new AtomicReference<>();
        attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            theSwing.set(h.getCooldownValue());
        });
//        if(attacker.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Register.BOXINGGLOVES.get()) && attacker instanceof Player && theSwing.get() == 1.0f && !attacker.level.isClientSide()){
//            int chance = attacker.getRandom().nextInt(6);
//            if(chance == 1){
//                Vec3 direction = attacker.getPosition(0).subtract(target.getPosition(0));
//                direction = direction.normalize();
//                target.knockback(2.5f, direction.x, direction.z);
//                CartoonishWeaponPack.giveAdvancement((ServerPlayer) attacker, attacker.getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "boxing"));
//            }else if (chance == 2){
//                Vec3 direction = attacker.getPosition(0).subtract(target.getPosition(0));
//                direction = direction.normalize();
//                target.knockback(2f, direction.x, direction.z);
//                CartoonishWeaponPack.giveAdvancement((ServerPlayer) attacker, attacker.getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "boxing"));
//            }
//
//        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(Component.translatable("cartoonishweapons.boxingglove.tooltip").withStyle(ChatFormatting.BLUE));
    }
}
