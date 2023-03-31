package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import com.cartoonishvillain.cartoonishweaponpack.entities.ThrownDynamite;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

import static com.cartoonishvillain.cartoonishweaponpack.Register.REVERSE;

public class ReverseCard extends Item {
    public ReverseCard(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() == REVERSE.get() && !player.level.isClientSide) {
            player.getCapability(PlayerCapability.INSTANCE).ifPresent(h -> {
                if (h.getCardCooldownTicks() > 0) {
                    player.displayClientMessage(Component.translatable("item.cartoonishweapons.reversecard.timer",
                            TimeBuilder(h.getCardCooldownTicks())), false);
                } else {
                    //Card is used
                    h.setCardCooldownTicks(1200);
                    h.setCardEffectTicks((short) 100);
                    player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
                    //play cure sound when player uses card.
                    player.level.playSound(null, player.getOnPos(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 1, 1);
                }
            });
        }
        return super.use(p_77659_1_, player, hand);
    }

    public boolean isFoil(ItemStack p_41172_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(Component.translatable("cartoonishweapons.reversecard.tooltip").withStyle(ChatFormatting.BLUE));
    }

    private String TimeBuilder(int duration){
        String timer = "(";
        int timermath = duration/20;
        if (timermath >= 60){
            timer += Integer.toString(timermath/60);
            while(timermath >= 60){timermath -= 60;}
            timer += ":";
        }else{
            timer += "00:";
        }
        if (timermath > 9){
            timer += Integer.toString(timermath);
            timer += ")";
        } else{
            timer += "0";
            timer += Integer.toString(timermath);
            timer += ")";
        }
        return timer;
    }
}
