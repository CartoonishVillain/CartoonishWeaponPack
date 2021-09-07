package com.cartoonishvillain.cartoonishweaponpack.entities;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class ThrownDynamite extends ThrowableItemProjectile {

    int ticksAlive = 100;


    public ThrownDynamite(EntityType<? extends ThrowableItemProjectile> p_i50155_1_, Level p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ThrownDynamite(EntityType<? extends ThrowableItemProjectile> type, Level world, LivingEntity livingEntity) {
        super(type, livingEntity, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Register.DYNAMITE.get());
    }


    @Override
    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.BlockInteraction.BREAK);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive--;
        if(ticksAlive < 0){
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.BlockInteraction.BREAK);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Register.DYNAMITE.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
