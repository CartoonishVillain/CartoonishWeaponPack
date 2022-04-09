package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.CartoonishWeaponPack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SeaTreaderBoard extends Item {


    public SeaTreaderBoard(Properties p_i48470_1_) {
        super(p_i48470_1_);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 150;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        if (!p_77659_1_.isClientSide()) {
            ItemStack Board = p_77659_2_.getItemInHand(p_77659_3_);
            p_77659_2_.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20 * 4, 0));
            Board.hurtAndBreak(1, p_77659_2_, (consumer) -> {
                consumer.broadcastBreakEvent(p_77659_3_);
            });
            CartoonishWeaponPack.giveAdvancement((ServerPlayer) p_77659_2_, p_77659_2_.getServer(), new ResourceLocation(CartoonishWeaponPack.MOD_ID, "tread"));

        }
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(new TranslatableComponent("cartoonishweapons.seatread.tooltip").withStyle(ChatFormatting.BLUE));
    }
}
