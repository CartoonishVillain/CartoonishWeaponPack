package com.cartoonishvillain.cartoonishweaponpack.items;

import com.cartoonishvillain.cartoonishweaponpack.capabilities.PlayerCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

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
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        if (!p_77659_1_.isClientSide()) {
            ItemStack Board = p_77659_2_.getItemInHand(p_77659_3_);
            p_77659_2_.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 20 * 10, 0));
            Board.hurtAndBreak(1, p_77659_2_, (consumer) -> {
                consumer.broadcastBreakEvent(p_77659_3_);
            });
        }
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
        p_77624_3_.add(new TranslationTextComponent("cartoonishweapons.seatread.tooltip").withStyle(TextFormatting.BLUE));
    }
}
