package com.cartoonishvillain.cartoonishweaponpack.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapabilityManager implements  IPlayerCapability, ICapabilityProvider, INBTSerializable<CompoundNBT> {
    protected float cooldownValue = 0;
    public final LazyOptional<IPlayerCapability> holder = LazyOptional.of(() -> this);
    @Override
    public float getCooldownValue() {
        return cooldownValue;
    }

    @Override
    public void setCooldownValue(float value) {
        cooldownValue = value;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerCapability.INSTANCE){ return PlayerCapability.INSTANCE.orEmpty(cap, this.holder); }
        else return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("cooldown", cooldownValue);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        cooldownValue = nbt.getFloat("cooldown");
    }
}
