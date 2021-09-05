package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.google.common.collect.ImmutableSet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;

import javax.tools.Tool;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class LargeChickenLeg extends ToolItem {
    public LargeChickenLeg(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
        super((float)attackDamageIn, attackSpeedIn, tier, ImmutableSet.of(), builderIn);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        AtomicReference<Float> theSwing = new AtomicReference<>();
        attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            theSwing.set(h.getCooldownValue());
        });
        if(target instanceof PlayerEntity){
            if(attacker instanceof PlayerEntity && theSwing.get() != 1.0f){return super.hurtEnemy(stack, target, attacker);}
            //Base chance for hunger is 15, with 2 added per hunger point the enemy is missing.
            int chance = 15 + ( 2* (20 - ((PlayerEntity) target).getFoodData().getFoodLevel()));
            if(random.nextInt(100) < chance){
                //if the chance is met, apply hunger 3 for 3 seconds
                target.addEffect(new EffectInstance(Effects.HUNGER, 60, 2));
            }
        }else {
            if(attacker instanceof PlayerEntity && theSwing.get() != 1.0f){return super.hurtEnemy(stack, target, attacker);}
            Vector3d direction = attacker.getPosition(0).subtract(target.getPosition(0));
            direction = direction.normalize();
            target.knockback(1, direction.x, direction.z);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return !enchantment.getDescriptionId().equals(Enchantments.FIRE_ASPECT.getDescriptionId());
    }


}
