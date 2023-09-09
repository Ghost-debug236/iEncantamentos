package network.insane.iencantamentos.listeners;

import network.insane.iencantamentos.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class SimpleEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void SimpleEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Simples");

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
