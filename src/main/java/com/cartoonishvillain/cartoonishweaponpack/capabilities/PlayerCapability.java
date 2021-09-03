package com.cartoonishvillain.cartoonishweaponpack.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class PlayerCapability {

    @CapabilityInject(IPlayerCapability.class)
    public static Capability<IPlayerCapability> INSTANCE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IPlayerCapability.class, new Capability.IStorage<IPlayerCapability>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side) {
                CompoundNBT tag = new CompoundNBT();
                tag.putFloat("cooldown", instance.getCooldownValue());
                return tag;
            }

            @Override
            public void readNBT(Capability<IPlayerCapability> capability, IPlayerCapability instance, Direction side, INBT nbt) {
                CompoundNBT tag = (CompoundNBT) nbt;
                instance.setCooldownValue(((CompoundNBT) nbt).getFloat("cooldown"));
            }
        }, (Callable<PlayerCapabilityManager>) PlayerCapabilityManager::new);
    }
}
