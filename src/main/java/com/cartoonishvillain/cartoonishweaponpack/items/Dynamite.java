package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrownDynamite;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Dynamite extends Item {
    public Dynamite(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        if(p_77659_2_.isHolding(Items.FLINT_AND_STEEL)){
            ThrownDynamite thrownDynamite = new ThrownDynamite(Register.THROWNDYNAMITE.get(), p_77659_1_);
            thrownDynamite.shootFromRotation(p_77659_2_, p_77659_2_.getXRot(), p_77659_2_.getYRot(), 0.0f, 1.2f, 1.0f);
            thrownDynamite.setPos(p_77659_2_.getX(), p_77659_2_.getY()+1, p_77659_2_.getZ());
            p_77659_1_.addFreshEntity(thrownDynamite);
            p_77659_2_.getCooldowns().addCooldown(this, 30);
            thrownDynamite.playSound(SoundEvents.TNT_PRIMED, 1, 1);
            p_77659_2_.getItemInHand(p_77659_3_).shrink(1);
            InteractionHand hand;
            if(p_77659_3_ == InteractionHand.MAIN_HAND) hand = InteractionHand.OFF_HAND;
            else hand = InteractionHand.MAIN_HAND;

            p_77659_2_.getItemInHand(hand).hurtAndBreak(1, p_77659_2_, (p_220040_1_) -> {
                p_220040_1_.broadcastBreakEvent(hand);
            });
        }
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(Component.translatable("cartoonishweapons.dynamite.tooltip").withStyle(ChatFormatting.BLUE));
    }
}
