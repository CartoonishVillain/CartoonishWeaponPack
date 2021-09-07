package com.cartoonishvillain.cartoonishweaponpack.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThrowingNetherBrick extends ProjectileItemEntity {


    public ThrowingNetherBrick(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ThrowingNetherBrick(EntityType<? extends ProjectileItemEntity> type, World world, LivingEntity livingEntity) {
        super(type, livingEntity, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Items.NETHER_BRICK);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        if(p_213868_1_.getEntity() instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) p_213868_1_.getEntity();
            int armor = livingEntity.getArmorValue();
            float damage = 2 + (0.1f * armor);
            livingEntity.hurt(DamageSource.GENERIC, damage);
        }
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        int chance = 80;
        boolean breakBrick = true;
        if(this.random.nextInt(100) < chance && !this.level.isClientSide()){
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, this.level);
            itemEntity.setPos(p_70227_1_.getLocation().x(), p_70227_1_.getLocation().y(), p_70227_1_.getLocation().z());
            itemEntity.setItem(new ItemStack(Items.NETHER_BRICK, 1));
            this.level.addFreshEntity(itemEntity);
            breakBrick = false;
        }

        this.playSound(SoundEvents.STONE_HIT, 1.0F, 1.0F);
        if(breakBrick){this.playSound(SoundEvents.STONE_BREAK, 1.0f, 1.0f);}
        this.remove(false);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.NETHER_BRICK;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
