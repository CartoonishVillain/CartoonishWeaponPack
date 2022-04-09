package com.cartoonishvillain.cartoonishweaponpack.items;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum WeaponMaterials implements Tier {

    SPOON(1, 196, 2.5f, 0, 8, () ->{return  Ingredient.of(Items.IRON_INGOT);}),
    BIGSPOON(1, 1028, 2.5f, 0, 8, () ->{return  Ingredient.of(Items.NETHERITE_INGOT);}),
    MEAT(-1, 128, 1.2f, 0, 16, () ->{return  Ingredient.of(Items.COOKED_CHICKEN);}),
    GLOVE(-1, 512, 1.2f, 0, 8, () -> {return Ingredient.of(Items.RED_WOOL);});


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private  final float attackDamage;
    private  final  int enchantability;
    private  final Supplier<Ingredient> repairMaterial;
    WeaponMaterials(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
