package com.cartoonishvillain.cartoonishweaponpack.capabilities;

public interface IPlayerCapability {
    float getCooldownValue();
    void setCooldownValue(float value);
    short getCardEffectTicks();
    void setCardEffectTicks(short value);
    int getCardCooldownTicks();
    void setCardCooldownTicks(int value);
}
