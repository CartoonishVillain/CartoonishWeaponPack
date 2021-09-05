package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

import java.util.concurrent.atomic.AtomicReference;

public class BoxingGlove extends ToolItem {
    public BoxingGlove(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super((float)p_i48460_2_, p_i48460_3_, p_i48460_1_, ImmutableSet.of(), p_i48460_4_);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        //both hands have gloves
        AtomicReference<Float> theSwing = new AtomicReference<>();
        attacker.getCapability(PlayerCapability.INSTANCE).ifPresent(h->{
            theSwing.set(h.getCooldownValue());
        });
        if(attacker.getItemInHand(Hand.OFF_HAND).getItem().equals(Register.BOXINGGLOVES.get()) && attacker instanceof PlayerEntity && theSwing.get() == 1.0f && !attacker.level.isClientSide()){
            int chance = random.nextInt(6);
            if(chance == 1){
                Vector3d direction = attacker.getPosition(0).subtract(target.getPosition(0));
                direction = direction.normalize();
                target.knockback(2.5f, direction.x, direction.z);
            }else if (chance == 2){
                Vector3d direction = attacker.getPosition(0).subtract(target.getPosition(0));
                direction = direction.normalize();
                target.knockback(2f, direction.x, direction.z);
            }

        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
