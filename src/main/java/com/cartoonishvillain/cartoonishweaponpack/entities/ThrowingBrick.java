package com.cartoonishvillain.cartoonishweaponpack.entities;

import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class ThrowingBrick extends ThrowableItemProjectile {


    public ThrowingBrick(EntityType<? extends ThrowableItemProjectile> p_i50155_1_, Level p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ThrowingBrick(EntityType<? extends ThrowableItemProjectile> type, Level world, LivingEntity livingEntity) {
        super(type, livingEntity, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Items.BRICK);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if(p_213868_1_.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) p_213868_1_.getEntity();
            int armor = livingEntity.getArmorValue();
            float damage = 2 + (0.1f * armor);
            livingEntity.hurt(DamageSource.GENERIC, damage);
        }
    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        int chance = 60;
        boolean breakBrick = true;
        if(this.random.nextInt(100) < chance && !this.level.isClientSide()){
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, this.level);
            itemEntity.setPos(p_70227_1_.getLocation().x(), p_70227_1_.getLocation().y(), p_70227_1_.getLocation().z());
            itemEntity.setItem(new ItemStack(Items.BRICK, 1));
            this.level.addFreshEntity(itemEntity);
            breakBrick = false;
        }

        this.playSound(SoundEvents.STONE_HIT, 1.0F, 1.0F);
        if(breakBrick){this.playSound(SoundEvents.STONE_BREAK, 1.0f, 1.0f);}
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.BRICK;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
