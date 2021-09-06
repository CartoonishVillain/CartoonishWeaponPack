package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrownDynamite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Dynamite extends Item {
    public Dynamite(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        if(p_77659_2_.isHolding(Items.FLINT_AND_STEEL)){
            ThrownDynamite thrownDynamite = new ThrownDynamite(Register.THROWNDYNAMITE.get(), p_77659_1_);
            thrownDynamite.shootFromRotation(p_77659_2_, p_77659_2_.xRot, p_77659_2_.yRot, 0.0f, 1.2f, 1.0f);
            thrownDynamite.setPos(p_77659_2_.getX(), p_77659_2_.getY()+1, p_77659_2_.getZ());
            p_77659_1_.addFreshEntity(thrownDynamite);
            p_77659_2_.getCooldowns().addCooldown(this, 30);
            thrownDynamite.playSound(SoundEvents.TNT_PRIMED, 1, 1);
            p_77659_2_.getItemInHand(p_77659_3_).shrink(1);
            Hand hand;
            if(p_77659_3_ == Hand.MAIN_HAND) hand = Hand.OFF_HAND;
            else hand = Hand.MAIN_HAND;

            p_77659_2_.getItemInHand(hand).hurtAndBreak(1, p_77659_2_, (p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(hand);
            });
        }
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }
}
