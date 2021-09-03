package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import net.minecraft.item.Item.Properties;

import java.util.Map;

public class ComicallyLargeSpoon extends ShovelItem {
    public ComicallyLargeSpoon(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //Base 10% chance for panic. Having a higher armor value increases the chance by one per armor point difference. Having lower armor points decreases the chance by the point difference.
        int panicChance = 10 + (attacker.getArmorValue() - target.getArmorValue());

        Map<Effect, EffectInstance> effectmap = attacker.getActiveEffectsMap();
        //Each level of strength increases panic chance by 2.
        if(effectmap.containsKey(Effects.DAMAGE_BOOST)){
            panicChance += (effectmap.get(Effects.DAMAGE_BOOST).getAmplifier()+1) * 2;
        }

        //for every 4 health difference, a 1 percent modifier is added to the panic chance.
        panicChance += ((attacker.getHealth() - target.getHealth())/4);
        
        if(attacker instanceof PlayerEntity){
            int finalPanicChance = panicChance;
            attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                float check = h.getCooldownValue();
                if(attacker.getRandom().nextInt(finalPanicChance - 1) < finalPanicChance &&  check == 1){
                    target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 5* 20, 5));
                }
            });
        }


        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return !enchantment.getDescriptionId().equals(Enchantments.FIRE_ASPECT.getDescriptionId());
    }


}
