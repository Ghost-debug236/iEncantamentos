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

public class RecyclerGUI implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    public void RecyclerInventory(Player player) {
        Inventory i = plugin.getServer().createInventory(null, 9 * 6, ChatColor.DARK_GREEN + "Reciclador");

        // Black Pane
        short black = 15;
        ItemStack blackPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, black);
        ItemMeta blackPaneMeta = blackPane.getItemMeta();
        blackPaneMeta.setDisplayName("");

        blackPane.setItemMeta(blackPaneMeta);
        i.setItem(45, blackPane);
        i.setItem(46, blackPane);
        i.setItem(47, blackPane);
        i.setItem(51, blackPane);
        i.setItem(52, blackPane);
        i.setItem(53, blackPane);

        // Negar
        short red = 14;
        ItemStack deny = new ItemStack(Material.STAINED_GLASS_PANE, 1, red);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.setDisplayName(ChatColor.RED + "Negar");
        List<String> denyLore = new ArrayList<>();
        denyLore.add(ChatColor.GRAY + "Clique para cancelar a reciclagem!");

        denyMeta.setLore(denyLore);
        deny.setItemMeta(denyMeta);
        i.setItem(48, deny);

        // Frasco reciclar
        int totalValue = 0;
        ItemStack recyclerBottle = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();
        recyclerBottleMeta.setDisplayName(ChatColor.YELLOW + "Experiência Reciclada");
        List<String> recyclerBottleLore = new ArrayList<>();
        recyclerBottleLore.add(ChatColor.WHITE + "Valor total: " + ChatColor.GREEN + totalValue);
        recyclerBottleLore.add("");
        recyclerBottleLore.add(ChatColor.GRAY + "Todos os seus livros serão");
        recyclerBottleLore.add(ChatColor.GRAY + "transformados em experiência com " + totalValue + " XP");

        recyclerBottleMeta.setLore(recyclerBottleLore);
        recyclerBottle.setItemMeta(recyclerBottleMeta);
        i.setItem(49, recyclerBottle);


        // Aceitar
        short green = 5;
        ItemStack accept = new ItemStack(Material.STAINED_GLASS_PANE, 1, green);
        ItemMeta acceptMeta = deny.getItemMeta();
        acceptMeta.setDisplayName(ChatColor.GREEN + "Aceitar");
        List<String> acceptLore = new ArrayList<>();
        acceptLore.add(ChatColor.GRAY + "Clique para reciclar todos");
        acceptLore.add(ChatColor.GRAY + "os itens selecionados!");
        acceptMeta.setLore(acceptLore);
        accept.setItemMeta(acceptMeta);
        i.setItem(50, accept);


        player.openInventory(i);
    }

}
