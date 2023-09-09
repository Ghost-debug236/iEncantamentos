package network.insane.iencantamentos.listeners;

import network.insane.iencantamentos.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class NormalEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void NormalEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Normal");

        // Levels
        int level5 = 5;
        int level4 = 4;

        // Protection
        Enchantment protection = Enchantment.PROTECTION_ENVIRONMENTAL;
        // Fire Protection
        Enchantment protectionFire = Enchantment.PROTECTION_FIRE;

        // Sharpness
        Enchantment sharpness = Enchantment.DAMAGE_ALL;

        // P4
        ItemStack p4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p4meta = (EnchantmentStorageMeta) p4.getItemMeta();
        p4meta.addStoredEnchant(protection, level4, true);
        p4.setItemMeta(p4meta);
        i.setItem(10, p4);

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
