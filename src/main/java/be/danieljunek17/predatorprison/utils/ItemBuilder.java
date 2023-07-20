package be.danieljunek17.predatorprison.utils;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@Getter
public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(@NotNull Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(@NotNull ItemStack stack) {
        this.itemStack = stack.clone();
    }

    private void applyToMeta(UnaryOperator<ItemMeta> callback) {
        this.itemStack.setItemMeta(callback.apply(this.itemStack.getItemMeta()));
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder displayname(@NotNull String name) {
        applyToMeta(meta -> {
            meta.setDisplayName(name);
            return meta;
        });
        return this;
    }

    public ItemBuilder lore(@NotNull String... lore) {
        applyToMeta(meta -> {
            List<String> lores = meta.getLore();
            if (lores == null) lores = new ArrayList<>();
            lores.addAll(List.of(lore));
            meta.setLore(lores);
            return meta;
        });
        return this;
    }

    public ItemBuilder lore(@NotNull List<String> lore) {
        lore(lore.toArray(String[]::new));
        return this;
    }

    public ItemBuilder flag(@NotNull ItemFlag... flags) {
        applyToMeta(meta -> {
            meta.addItemFlags(flags);
            return meta;
        });
        return this;
    }

    public ItemBuilder customModelData(int customModelData) {
        applyToMeta(meta -> {
            meta.setCustomModelData(customModelData);
            return meta;
        });
        return this;
    }

    public ItemBuilder enchant(@NotNull Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder enchant(@NotNull Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder durability(int damage) {
        applyToMeta(meta -> {
            if (!(meta instanceof org.bukkit.inventory.meta.Damageable)) return meta;
            ((org.bukkit.inventory.meta.Damageable) meta).setDamage(damage);
            return meta;
        });
        return this;
    }

    public ItemBuilder unbreakable() {
        return unbreakable(true);
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        applyToMeta(meta -> {
            meta.setUnbreakable(unbreakable);
            return meta;
        });
        return this;
    }

    public ItemBuilder glow() {
        this.itemStack.addUnsafeEnchantment(Enchantment.LURE, 0);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

//    public ItemBuilder skullTexture(@NotNull String identifier) {
//        applyToMeta(meta -> {
//            SkullUtils.applySkin(meta, identifier);
//            return meta;
//        });
//        return this;
//    }

    public ItemBuilder armorColor(@NotNull Color color) {
        applyToMeta(meta -> {
            if (!(meta instanceof LeatherArmorMeta)) return meta;
            ((LeatherArmorMeta) meta).setColor(color);
            return meta;
        });
        return this;
    }
}