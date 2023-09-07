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

public class AdvancedEnchantsGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void AdvancedEnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_PURPLE + "Encantamentos - Livro Avançado");

        // Levels
        int level5 = 5;
        int level4 = 4;

        // Protection
        Enchantment protection = Enchantment.PROTECTION_ENVIRONMENTAL;
        // Fire Protection
        Enchantment protectionFire = Enchantment.PROTECTION_FIRE;

        // Sharpness
        Enchantment sharpness = Enchantment.DAMAGE_ALL;

        // Livros Encantados
        ItemStack p5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p5meta = (EnchantmentStorageMeta) p5.getItemMeta();
        p5meta.addStoredEnchant(protection, level5, true);
        p5.setItemMeta(p5meta);
        i.setItem(10, p5);

        ItemStack p4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta p4meta = (EnchantmentStorageMeta) p4.getItemMeta();
        p4meta.addStoredEnchant(protection, level4, true);
        p4.setItemMeta(p4meta);
        i.setItem(11, p4);

        ItemStack pFire4 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta pFire4Meta = (EnchantmentStorageMeta) pFire4.getItemMeta();
        pFire4Meta.addStoredEnchant(protectionFire, level4, true);
        pFire4.setItemMeta(pFire4Meta);
        i.setItem(12, pFire4);

        ItemStack s5 = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta s5Meta = (EnchantmentStorageMeta) s5.getItemMeta();
        s5Meta.addStoredEnchant(sharpness, level5, true);
        s5.setItemMeta(s5Meta);
        i.setItem(13, s5);

        // Flecha Voltar
        ItemStack arrowBack = new ItemStack(Material.ARROW);
        ItemMeta arrowBackMeta = arrowBack.getItemMeta();
        arrowBackMeta.setDisplayName(ChatColor.YELLOW + "Voltar");
        arrowBack.setItemMeta(arrowBackMeta);
        i.setItem(49, arrowBack);

        player.openInventory(i);
    }

}
