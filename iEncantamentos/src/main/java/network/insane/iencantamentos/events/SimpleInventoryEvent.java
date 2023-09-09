package network.insane.iencantamentos.events;

import network.insane.iencantamentos.listeners.EnchantGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SimpleInventoryEvent implements Listener {

    @EventHandler
    public void SimpleInventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();

        if(item == null || open == null) {
            return;
        }

        if(player instanceof Player) {

            if(open.equals(player.getInventory())) {
                return;
            }

            if(open.getName().equals(ChatColor.DARK_PURPLE + "Encantamentos - Livro Simples")) {
                event.setCancelled(true);

                if(event.isRightClick()) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o player clicou na flecha
                    if(item.getType() == Material.ARROW && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Voltar")) {
                            EnchantGUI enchantGUI = new EnchantGUI();
                            enchantGUI.EnchantInventory(player);
                        }
                    }
                }
            }
        }

    }
}
