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

import java.util.ArrayList;
import java.util.List;


public class EnchantGUI implements Listener {
    private Plugin plugin = Main.getPlugin(Main.class);

    public void EnchantInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 5, ChatColor.DARK_PURPLE + "Feiticeiro");
        int xp = player.getTotalExperience();

        // Ver a experiência pelo MineCart
        ItemStack myxp = new ItemStack(Material.MINECART);
        ItemMeta myxpmeta = myxp.getItemMeta();
        myxpmeta.setDisplayName(ChatColor.YELLOW + "Sua experiência");
        // Lore Minecart
        List<String> xplore = new ArrayList<>();
        xplore.add(ChatColor.WHITE + "XP atual: " + ChatColor.GRAY + xp);
        myxpmeta.setLore(xplore);

        myxp.setItemMeta(myxpmeta);
        i.setItem(13, myxp);

        // Frasco de Experiência Simples
        int xpSimpleCost = 1500;
        ItemStack xpBottleSimple = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta xpBottleSimpleMeta = xpBottleSimple.getItemMeta();
        xpBottleSimpleMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência Simples");
        List<String> xpBottleSimpleLore = new ArrayList<>();
        xpBottleSimpleLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xpSimpleCost);
        xpBottleSimpleLore.add("");
        xpBottleSimpleLore.add(ChatColor.GRAY + "Você receberá um frasco de ");
        xpBottleSimpleLore.add(ChatColor.GRAY + "experiência com " + xpSimpleCost + " XP");

        xpBottleSimpleMeta.setLore(xpBottleSimpleLore);
        xpBottleSimple.setItemMeta(xpBottleSimpleMeta);
        i.setItem(19, xpBottleSimple);

        // Frasco de Experiência Normal
        int xpNormalCost = 3000;
        ItemStack xpBottleNormal = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta xpBottleNormalMeta = xpBottleNormal.getItemMeta();
        xpBottleNormalMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência Normal");
        List<String> xpBottleNormalLore = new ArrayList<>();
        xpBottleNormalLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xpNormalCost);
        xpBottleNormalLore.add("");
        xpBottleNormalLore.add(ChatColor.GRAY + "Você receberá um frasco de ");
        xpBottleNormalLore.add(ChatColor.GRAY + "experiência com " + xpNormalCost + " XP");

        xpBottleNormalMeta.setLore(xpBottleNormalLore);
        xpBottleNormal.setItemMeta(xpBottleNormalMeta);
        i.setItem(20, xpBottleNormal);

        // Frasco de Experiência Avançado
        int xpAdvancedCost = 5000;
        ItemStack xpBottleAdvanced = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta xpBottleAdvancedMeta = xpBottleAdvanced.getItemMeta();
        xpBottleAdvancedMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência Avançado");
        List<String> xpBottleAdvancedLore = new ArrayList<>();
        xpBottleAdvancedLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xpAdvancedCost);
        xpBottleAdvancedLore.add("");
        xpBottleAdvancedLore.add(ChatColor.GRAY + "Você receberá um frasco de ");
        xpBottleAdvancedLore.add(ChatColor.GRAY + "experiência com " + xpAdvancedCost + " XP");

        xpBottleAdvancedMeta.setLore(xpBottleAdvancedLore);
        xpBottleAdvanced.setItemMeta(xpBottleAdvancedMeta);
        i.setItem(28, xpBottleAdvanced);

        // Sistema das poções de exp
        // Add frasco xp se o player tiver xp
        if(xp > 0) {
            ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE);
            ItemMeta xpBottleMeta = xpBottle.getItemMeta();
            xpBottleMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");
            List<String> xpBottleLore = new ArrayList<>();
            xpBottleLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xp);
            xpBottleLore.add("");
            xpBottleLore.add(ChatColor.GRAY + "Você receberá um frasco de ");
            xpBottleLore.add(ChatColor.GRAY + "experiência com " + xp + " XP");

            xpBottleMeta.setLore(xpBottleLore);
            xpBottle.setItemMeta(xpBottleMeta);
            i.setItem(29, xpBottle);
        } else {
            ItemStack gBottle = new ItemStack(Material.GLASS_BOTTLE);
            ItemMeta gBottleMeta = gBottle.getItemMeta();
            gBottleMeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");
            List<String> gBottleLore = new ArrayList<>();
            gBottleLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xp);
            gBottleLore.add("");
            gBottleLore.add(ChatColor.RED + "Você não possui XP");
            gBottleLore.add(ChatColor.RED + "para ser coletado!");

            gBottleMeta.setLore(gBottleLore);
            gBottle.setItemMeta(gBottleMeta);
            i.setItem(29, gBottle);
        }

        // Livros de Encantamentos
        // Simples
        int eBookSimpleCost = 1500;
        String eBookSimpleType = "Simples";
        ItemStack eBookSimple = new ItemStack(Material.BOOK);
        ItemMeta eBookSimpleMeta = eBookSimple.getItemMeta();
        eBookSimpleMeta.setDisplayName(ChatColor.YELLOW + "Livro de Encantamentos");
        List<String> eBookSimpleLore = new ArrayList<>();
        eBookSimpleLore.add(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookSimpleType);
        eBookSimpleLore.add("");
        eBookSimpleLore.add(ChatColor.GRAY + "Clique com o " + ChatColor.WHITE + "botão direito " + ChatColor.GRAY + "para ver ");
        eBookSimpleLore.add(ChatColor.GRAY + "os encantamentos do tipo " + eBookSimpleType.toLowerCase());
        eBookSimpleLore.add("");
        eBookSimpleLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + eBookSimpleCost);

        eBookSimpleMeta.setLore(eBookSimpleLore);
        eBookSimple.setItemMeta(eBookSimpleMeta);
        i.setItem(24, eBookSimple);

        // Normal
        int eBookNormalCost = 2000;
        String eBookNormalType = "Normal";
        ItemStack eBookNormal = new ItemStack(Material.BOOK);
        ItemMeta eBookNormalMeta = eBookNormal.getItemMeta();
        eBookNormalMeta.setDisplayName(ChatColor.YELLOW + "Livro de Encantamentos");
        List<String> eBookNormalLore = new ArrayList<>();
        eBookNormalLore.add(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookNormalType);
        eBookNormalLore.add("");
        eBookNormalLore.add(ChatColor.GRAY + "Clique com o " + ChatColor.WHITE + "botão direito " + ChatColor.GRAY + "para ver ");
        eBookNormalLore.add(ChatColor.GRAY + "os encantamentos do tipo " + eBookNormalType.toLowerCase());
        eBookNormalLore.add("");
        eBookNormalLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + eBookNormalCost);

        eBookNormalMeta.setLore(eBookNormalLore);
        eBookNormal.setItemMeta(eBookNormalMeta);
        i.setItem(25, eBookNormal);

        // Avançado
        int eBookAdvancedCost = 3500;
        String eBookAdvancedType = "Avançado";
        ItemStack eBookAdvanced = new ItemStack(Material.BOOK);
        ItemMeta eBookAdvancedMeta = eBookAdvanced.getItemMeta();
        eBookAdvancedMeta.setDisplayName(ChatColor.YELLOW + "Livro de Encantamentos");
        List<String> eBookAdvancedLore = new ArrayList<>();
        eBookAdvancedLore.add(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookAdvancedType);
        eBookAdvancedLore.add("");
        eBookAdvancedLore.add(ChatColor.GRAY + "Clique com o " + ChatColor.WHITE + "botão direito " + ChatColor.GRAY + "para ver ");
        eBookAdvancedLore.add(ChatColor.GRAY + "os encantamentos do tipo " + eBookAdvancedType.toLowerCase());
        eBookAdvancedLore.add("");
        eBookAdvancedLore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + eBookAdvancedCost);

        eBookAdvancedMeta.setLore(eBookAdvancedLore);
        eBookAdvanced.setItemMeta(eBookAdvancedMeta);
        i.setItem(33, eBookAdvanced);

        player.openInventory(i);
    }

}
