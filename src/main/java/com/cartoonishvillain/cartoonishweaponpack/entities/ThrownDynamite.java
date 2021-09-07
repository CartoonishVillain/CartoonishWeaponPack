package com.cartoonishvillain.cartoonishweaponpack.entities;

import com.cartoonishvillain.cartoonishweaponpack.Register;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrownDynamite extends ProjectileItemEntity {

    int ticksAlive = 100;


    public ThrownDynamite(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ThrownDynamite(EntityType<? extends ProjectileItemEntity> type, World world, LivingEntity livingEntity) {
        super(type, livingEntity, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Register.DYNAMITE.get());
    }


    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if(!this.level.isClientSide()){
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.Mode.BREAK);}
        this.remove(false);
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive--;
        if(ticksAlive < 0){
            if(!this.level.isClientSide()){
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 2, Explosion.Mode.BREAK);}
            this.remove(false);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Register.DYNAMITE.get();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
