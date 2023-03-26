package com.cartoonishvillain.cartoonishweaponpack.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityManager implements  IPlayerCapability, ICapabilityProvider, INBTSerializable<CompoundTag> {
    protected float cooldownValue = 0;
    protected short reverseCardEffect = 0;
    protected int reverseCardCooldown = 0;
    public final LazyOptional<IPlayerCapability> holder = LazyOptional.of(() -> this);
    @Override
    public float getCooldownValue() {
        return cooldownValue;
    }

    @Override
    public void setCooldownValue(float value) {
        cooldownValue = value;
    }

    @Override
    public short getCardEffectTicks() {
        return reverseCardEffect;
    }

    @Override
    public void setCardEffectTicks(short value) {
        reverseCardEffect = value;
    }

    @Override
    public int getCardCooldownTicks() {
        return reverseCardCooldown;
    }

    @Override
    public void setCardCooldownTicks(int value) {
        reverseCardCooldown = value;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerCapability.INSTANCE){ return PlayerCapability.INSTANCE.orEmpty(cap, this.holder); }
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("cooldown", cooldownValue);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        cooldownValue = nbt.getFloat("cooldown");
    }
}
