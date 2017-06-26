package com.shepherdjerred.sthorses.listeners;

import com.shepherdjerred.sthorses.Main;
import com.shepherdjerred.sthorses.util.ItemUtils;
import com.shepherdjerred.sthorses.util.StoreUtils;
import org.bukkit.Material;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class StoreListener implements Listener {

    private final List<InventoryAction> ALLOWED_ACTIONS = Arrays.asList(
            InventoryAction.PICKUP_ALL
    );

    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        // Check permission
        if (!player.hasPermission("stHorses.store")) {
            return;
        }

        // Null checks
        if (event.getClickedInventory() == null) {
            return;
        }

        // Check that the inventory belongs to a horse
        if (!(event.getClickedInventory().getHolder() instanceof AbstractHorse)) {
            return;
        }

        if (event.getSlot() != 0 && event.getSlot() != 1) {
            return;
        }

        if (event.getCurrentItem().getType() != Material.SADDLE && event.getCurrentItem().getType() != Material.CARPET) {
            return;
        }

        // Don't continue if we're ignoring shift-clicks
        if (Main.getInstance().getConfig().getBoolean("store.shiftClickIgnored")) {
            if (!ALLOWED_ACTIONS.contains(event.getAction())) {
                return;
            }
        }

        // Don't continue if the clicked saddle has lore
        if (event.getCurrentItem().getItemMeta().hasLore()) {
            return;
        }

        AbstractHorse abstractHorse = (AbstractHorse) event.getClickedInventory().getHolder();

        ItemStack saddle = new ItemStack(event.getCurrentItem().getType(), 1);
        ItemMeta saddleMeta = saddle.getItemMeta();
        List<String> lore = StoreUtils.createAbstractHorseLore(abstractHorse);

        if (abstractHorse instanceof Horse) {
            lore.addAll(StoreUtils.createHorseLore((Horse) abstractHorse));
        } else if (abstractHorse instanceof Llama) {
            lore.addAll(StoreUtils.createLlamaLore((Llama) abstractHorse));
            short colorCode = event.getCurrentItem().getDurability();
            String carpetColor = ItemUtils.getCarpetColorAsString(colorCode);
            saddle.setDurability(colorCode);
            lore.add("Carpet: " + carpetColor);            
        }

        saddleMeta.setDisplayName("stHorses Saddle");
        saddleMeta.setLore(lore);
        saddle.setItemMeta(saddleMeta);

        ItemUtils.addGlow(saddle);

        event.setCurrentItem(new ItemStack(Material.AIR));

        // Drop the horses chest, if carrying
        if (abstractHorse instanceof ChestedHorse) {
            ChestedHorse chestedHorse = (ChestedHorse) abstractHorse;
            if (chestedHorse.isCarryingChest()) {
                ItemStack chestToDrop = new ItemStack(Material.CHEST, 1);
                chestedHorse.getWorld().dropItem(chestedHorse.getLocation(), chestToDrop);
            }
        }

        // Drop the horses inventory
        abstractHorse.getInventory().forEach(item -> {
            if (item != null) {
                abstractHorse.getWorld().dropItem(abstractHorse.getLocation(), item);
                item.setType(Material.AIR);
            }
        });

        // Remove the horse
        abstractHorse.remove();

        // Check for full inventory; Drop item or give item to player
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), saddle);
        } else {
            player.getInventory().addItem(saddle);
        }

        event.setCancelled(true);

    }

}
