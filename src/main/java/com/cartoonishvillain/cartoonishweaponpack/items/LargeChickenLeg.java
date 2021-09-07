package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.google.common.collect.ImmutableSet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LargeChickenLeg extends DiggerItem {
    public LargeChickenLeg(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super((float)attackDamageIn, attackSpeedIn, tier, Tag.fromSet(ImmutableSet.of()), builderIn);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        AtomicReference<Float> theSwing = new AtomicReference<>();
        attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            theSwing.set(h.getCooldownValue());
        });
        if(target instanceof Player){
            if(attacker instanceof Player && theSwing.get() != 1.0f){return super.hurtEnemy(stack, target, attacker);}
            //Base chance for hunger is 15, with 2 added per hunger point the enemy is missing.
            int chance = 25 + ( 2* (20 - ((Player) target).getFoodData().getFoodLevel()));
            if(target.getRandom().nextInt(100) < chance){
                //if the chance is met, apply hunger 3 for 3 seconds
                target.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60, 2));
            }
        }else {
            if(attacker instanceof Player && theSwing.get() != 1.0f){return super.hurtEnemy(stack, target, attacker);}
            Vec3 direction = attacker.getPosition(0).subtract(target.getPosition(0));
            direction = direction.normalize();
            target.knockback(1.5f, direction.x, direction.z);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return !enchantment.getDescriptionId().equals(Enchantments.FIRE_ASPECT.getDescriptionId());
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(new TranslatableComponent("cartoonishweapons.chicken.tooltip").withStyle(ChatFormatting.BLUE));
    }


}
