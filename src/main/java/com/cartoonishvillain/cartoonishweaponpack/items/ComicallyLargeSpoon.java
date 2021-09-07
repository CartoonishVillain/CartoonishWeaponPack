package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ComicallyLargeSpoon extends ShovelItem {
    public ComicallyLargeSpoon(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //Base 20% chance for panic. Having a higher armor value increases the chance by one per armor point difference. Having lower armor points decreases the chance by the point difference.
        int panicChance = 20 + (attacker.getArmorValue() - target.getArmorValue());

        Map<MobEffect, MobEffectInstance> effectmap = attacker.getActiveEffectsMap();
        //Each level of strength increases panic chance by 2.
        if(effectmap.containsKey(MobEffects.DAMAGE_BOOST)){
            panicChance += (effectmap.get(MobEffects.DAMAGE_BOOST).getAmplifier()+1) * 2;
        }

        //for every 4 health difference, a 1 percent modifier is added to the panic chance.
        panicChance += ((attacker.getHealth() - target.getHealth())/4);

        //only allow players w/ a full swing meter to panic
        if(attacker instanceof Player){
            int finalPanicChance = panicChance;
            attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
                float check = h.getCooldownValue();
                if(attacker.getRandom().nextInt(100) < finalPanicChance &&  check == 1){
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5* 20, 5));
                }
            });
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
        p_77624_3_.add(new TranslatableComponent("cartoonishweapons.spoon.tooltip").withStyle(ChatFormatting.BLUE));
        p_77624_3_.add(new TranslatableComponent("cartoonishweapons.spoon.info").withStyle(ChatFormatting.GRAY));
    }


}
