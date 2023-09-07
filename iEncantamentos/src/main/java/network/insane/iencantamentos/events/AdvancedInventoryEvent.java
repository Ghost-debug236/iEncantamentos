package network.insane.iencantamentos.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import network.insane.iencantamentos.listeners.EnchantGUI;

import java.util.*;

public class AdvancedInventoryEvent implements Listener {

    @EventHandler
    public void AdvancedInventoryClicked(InventoryClickEvent event) {
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

            if(open.getName().equals(ChatColor.DARK_PURPLE + "Encantamentos - Livro Avançado")) {
                event.setCancelled(true);

                if(event.isRightClick()) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o player clicou na flecha
                    if(item.getType() == Material.ARROW && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Voltar")) {
                            EnchantGUI enchantGUI = new EnchantGUI();
                            enchantGUI.newInventory(player);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onRightClickInAdvancedBook(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        String typeBook = "Avançado";

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(customBook(item, typeBook)) {
                // Verifica se o player tem espaço no inv para o livro
                if(player.getInventory().firstEmpty() != -1) {
                    int amountInHand = item.getAmount();
                    if(amountInHand == 1) {
                        // Se houver apenas 1 remover
                        player.getInventory().setItemInHand(new ItemStack(Material.AIR));

                        createEnchantedBook(player);

                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        // Se houver mais de um apenas diminuir no slot
                        item.setAmount(amountInHand - 1);

                        createEnchantedBook(player);

                        player.sendMessage(ChatColor.GREEN + "Você trocou seu " + ChatColor.YELLOW + "Livro Avançado" + ChatColor.GREEN + " por um " +
                                ChatColor.YELLOW + "encantamento!");
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu livro.");
                }
            }
        }
    }

    public void createEnchantedBook(Player player) {
        String armor = "Armaduras";
        String arms = "Armas";

        // Lista de encantamentos possíveis
        List<Enchantment> possibleEnchantments = new ArrayList<>();
        possibleEnchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        possibleEnchantments.add(Enchantment.PROTECTION_FIRE);
        possibleEnchantments.add(Enchantment.DAMAGE_ALL);

        // Escolher aleatoriamente um encantamento da lista ou nivel se precisar
        Random random = new Random();
        int randomLevel4between5= random.nextInt(2) + 4;
        Enchantment chosenEnchantment = possibleEnchantments.get(random.nextInt(possibleEnchantments.size()));

        // Criar um livro encantado com o encantamento escolhido!
        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

        enchantedBookMeta.setDisplayName(ChatColor.YELLOW + "Livro Avançado");

        // Verificar os encantamentos
        if(chosenEnchantment == Enchantment.PROTECTION_ENVIRONMENTAL) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, randomLevel4between5, true);
            List<String> lore = new ArrayList<>();
            lore.add("Encanta: " + armor);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.PROTECTION_FIRE) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, 4, true);
            List<String> lore = new ArrayList<>();
            lore.add("Encanta: " + armor);
            enchantedBookMeta.setLore(lore);
        } else if(chosenEnchantment == Enchantment.DAMAGE_ALL) {
            enchantedBookMeta.addStoredEnchant(chosenEnchantment, 5, true);
            List<String> lore = new ArrayList<>();
            lore.add("Encanta: " + arms);
            enchantedBookMeta.setLore(lore);
        }

        enchantedBook.setItemMeta(enchantedBookMeta);

        // Add enchant book ao inv do player
        player.getInventory().addItem(enchantedBook);
    }

    private boolean hasEnchantment(ItemStack item, Enchantment enchantment, int level) {
        Map<Enchantment, Integer> existingEnchantments = item.getEnchantments();
        return existingEnchantments.containsKey(enchantment) && existingEnchantments.get(enchantment) == level;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        if(clickedItem != null && cursorItem != null){
            // Verificar se é um item encantado
            if(isEnchantedBook(cursorItem)) {
                if(isValidTargetItem(clickedItem, cursorItem)) {
                    EnchantmentStorageMeta cursorMeta = (EnchantmentStorageMeta) cursorItem.getItemMeta();
                    Map<Enchantment, Integer> enchantments = cursorMeta.getStoredEnchants();

                    for(Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        Enchantment enchantment = entry.getKey();
                        int level = entry.getValue();

                        if(!hasEnchantment(clickedItem, enchantment, level)) {
                            // Remover o livro encantado do inv do player
                            event.setCursor(new ItemStack(Material.AIR));

                            // Aplicar encantamento
                            applyEnchantment(clickedItem, cursorItem);

                            player.sendMessage(ChatColor.GREEN + "O item foi encantado com sucesso!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Esse item já possui um encantamento igual!");
                        }
                    }
                }
            }
        }
    }

    private boolean isEnchantedBook(ItemStack item) {
        if(item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                return true;
            }
        }
        return false; // Não possui nome custom
    }


    private boolean isValidTargetItem(ItemStack item,ItemStack enchantedBook) {
        Material itemType = item.getType();
        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

        // Verificar se o item é uma armadura e o encantamento
        if ((itemType == Material.DIAMOND_HELMET || itemType == Material.DIAMOND_CHESTPLATE ||
                itemType == Material.DIAMOND_LEGGINGS || itemType == Material.DIAMOND_BOOTS ||
                itemType == Material.GOLD_HELMET || itemType == Material.GOLD_CHESTPLATE ||
                itemType == Material.GOLD_LEGGINGS || itemType == Material.GOLD_BOOTS ||
                itemType == Material.IRON_HELMET || itemType == Material.IRON_CHESTPLATE ||
                itemType == Material.IRON_LEGGINGS || itemType == Material.IRON_BOOTS ||
                itemType == Material.CHAINMAIL_HELMET || itemType == Material.CHAINMAIL_CHESTPLATE ||
                itemType == Material.CHAINMAIL_LEGGINGS || itemType == Material.CHAINMAIL_BOOTS ||
                itemType == Material.LEATHER_HELMET || itemType == Material.LEATHER_CHESTPLATE ||
                itemType == Material.LEATHER_LEGGINGS || itemType == Material.LEATHER_BOOTS) &&
                (enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) ||
                        enchantments.containsKey(Enchantment.PROTECTION_FIRE))) {
            return true;
        } else if((itemType == Material.DIAMOND_SWORD || itemType == Material.DIAMOND_AXE) &&
                (enchantments.containsKey(Enchantment.DAMAGE_ALL))) {
            return true;
        }

        return false;
    }

    private void applyEnchantment(ItemStack targetItem, ItemStack enchantedBook) {
        if(!isEnchantedBook(enchantedBook)) {
            return;
        }

        EnchantmentStorageMeta bookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
        Map<Enchantment, Integer> enchantments = bookMeta.getStoredEnchants();

            // Loop através de todos os encantamentos no livro encantado
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment enchantment = entry.getKey();

                int level = entry.getValue();
                // Aplicar encantamento ao item de destino
                targetItem.addUnsafeEnchantment(enchantment, level);

            }
    }

    // Verificar se um item é um enchant book custom
    private boolean customBook(ItemStack item, String eBookType) {
        if(item != null && item.getType() == Material.BOOK && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if(meta.hasLore()) {
                return meta.getLore().contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookType);
            }
        }
        return false;
    }

}
