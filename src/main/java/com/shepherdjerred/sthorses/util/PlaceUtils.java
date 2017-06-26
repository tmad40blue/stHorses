package com.shepherdjerred.sthorses.util;

import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlaceUtils {
    public static String translateOldVariants(String variant) {
        switch (variant) {
        case "DONKEY":
            return "Horse";
        case "HORSE":
            return "Horse";
        case "MULE":
            return "Mule";
        case "SKELETON_HORSE":
            return "SkeletonHorse";
        default:
            return variant;
        }
    }

    public static void createHorse(Horse horse, List<String> lore) {
        String color = lore.stream().filter(str -> str.contains("Color: ")).findFirst().get().replace("Color: ", "");
        String style = lore.stream().filter(str -> str.contains("Style: ")).findFirst().get().replace("Style: ", "");
        horse.setColor(Horse.Color.valueOf(color));
        horse.setStyle(Horse.Style.valueOf(style));
    }

    public static void createLlama(Llama llama, List<String> lore) {
        String color = lore.stream().filter(str -> str.contains("Color: ")).findFirst().get().replace("Color: ", "");
        llama.setColor(Llama.Color.valueOf(color));
    }

    public static void giveSaddle (AbstractHorse abstractHorse) {
        ((Horse) abstractHorse).getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
    }

    public static void giveCarpet (AbstractHorse abstractHorse, String carpetColor) {
        ItemStack carpet = new ItemStack(Material.CARPET, 1);
        carpet.setDurability(ItemUtils.getCarpetColorAsShort(carpetColor));
        abstractHorse.getInventory().setItem(1, carpet);
    }
}
