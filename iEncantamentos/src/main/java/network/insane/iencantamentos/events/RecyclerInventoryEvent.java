package network.insane.iencantamentos.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class RecyclerInventoryEvent implements Listener {

    private int totalValue = 0;

    @EventHandler
    public void RecyclerInventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();

        if (item == null || open == null) {
            return;
        }

        if (player instanceof Player) {

            if (open.equals(player.getInventory())) {
                return;
            }

            if (open.getName().equals(ChatColor.DARK_GREEN + "Reciclador")) {
                event.setCancelled(true);

                if(event.isRightClick()) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o item clicado é "Negar"
                    if (item.hasItemMeta()) {
                        if (item.getItemMeta().hasDisplayName() &&
                                item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Negar")) {
                            resetRecyclerBottle(player);
                            player.closeInventory();
                        }

                        // Verificar se o item clicado é "Aceitar"
                        if (item.getItemMeta().hasDisplayName() &&
                                item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Aceitar")) {
                            int totalValue = getTotalValueFromRecyclerBottle(player);

                            if (totalValue > 0) {
                                // Dar xp ao jogador
                                player.giveExp(totalValue);

                                // Remover os livros encantados do inventário do jogador
                                removeRecycledBooks(player);

                                // Reseta o frasco
                                resetRecyclerBottle(player);

                                // Fechar o inventário do reciclador
                                player.closeInventory();

                                // Reinicializar o valor do frasco de vidro para zero

                                // Envie uma mensagem ao jogador informando que eles ganharam XP
                                player.sendMessage(ChatColor.GREEN + "Você ganhou "
                                        + ChatColor.YELLOW + totalValue + " XP "
                                        + ChatColor.GREEN + "reciclando seus livros encantados!");
                            }
                        }
                    }
                }

                // Verificar se o item clicado é um "Livro avançado"
                if (item.getType() == Material.ENCHANTED_BOOK && item.hasItemMeta()) {
                    ItemMeta meta = item.getItemMeta();
                    if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                        // Verificar se o inv do reciclador tem espaço
                        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();;

                        // Verificar se o inventário do jogador está cheio
                        if (isInventoryFull(player.getInventory())) {
                            player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de adicionar o livro.");
                            return;
                        }

                        if(event.getClickedInventory().equals(recyclerInventory)) {
                            event.setCancelled(true);
                            player.sendMessage(ChatColor.RED + "Clique no botão negar ou saia do inventario para recuperar seus livros!");
                        } else {
                            // Update Frask
                            int bookCost = 3500;
                            recycleBooks(bookCost);

                            updateRecyclerBottle(player, totalValue);

                            // Remova o item do inv do player
                            event.setCurrentItem(null);

                            // Add o item no inv do reciclador
                            recyclerInventory.addItem(item);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void RecyclerInventoryClosed(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        Inventory playerInventory = player.getInventory();

        // Verifique se o inventário fechado é o inventário do reciclador
        if(inventory.getName().equals(ChatColor.DARK_GREEN + "Reciclador")) {
            // Verifique se há algum item no inventário do reciclador
            for (ItemStack item : inventory.getContents()) {
                if(item != null && item.getType() != Material.AIR) {
                    // Verifique se o item é um "Livro Avançado"
                    if(item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                            // Verifique se o inventário do jogador está cheio
                            if (isInventoryFull(playerInventory)) {
                                player.sendMessage(ChatColor.RED + "Você não pode retirar o Livro Avançado porque o inventário está cheio!");
                            } else {
                                // Mova o "Livro Avançado" para o inventário do jogador
                                playerInventory.addItem(item);
                                // Limpe o espaço no inv do reciclador
                                resetRecyclerBottle(player);
                                inventory.remove(item);
                            }
                        }
                    }
                }
            }
        }
    }

    private void resetRecyclerBottle(Player player) {
        totalValue = 0;
        updateRecyclerBottle(player, 0); // Atualize o frasco para mostrar um valor de 0
    }

    private void recycleBooks(int bookCost) {
        // Calcular o valor de XP com base no livro e adicionar ao valor total

        double proportionMin = 0.15; // 0.15 = 15% do valor base

        double proportionMax = 0.6; // 0.6 = 60% do valor base

        // Calcular valor min e max
        int minValue = (int) (bookCost * proportionMin);
        int maxValue = (int) (bookCost * proportionMax);

        if(maxValue > bookCost) {
            maxValue = bookCost;
        }

        // Gerar um valor aleatório entre valorMinimo e valorMaximo (inclusive)
        Random random = new Random();
        int recyclerValue = random.nextInt(maxValue - minValue + 1) + minValue;

        // Garantir que recyclerValue seja menor que bookCost
        recyclerValue = Math.min(recyclerValue, bookCost - 1);

        // Atualizar o lore do frasco de vidro com o novo valor total de XP
        totalValue += recyclerValue;

    }

    private void updateRecyclerBottle(Player player, int totalValue) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int fraskPosition = 49;

        if(recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(fraskPosition);

            if(recyclerBottle != null) {
                ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();

                // Verifique o valor total e configure o tipo de frasco com base nesse valor
                if(totalValue == 0) {
                    recyclerBottle.setType(Material.GLASS_BOTTLE);
                    recyclerBottleMeta.setLore(null);
                } else {
                    recyclerBottle.setType(Material.EXP_BOTTLE);
                    // Obtenha o lore atual do frasco de vidro
                    List<String> recyclerBottleLore = recyclerBottleMeta.getLore();

                    // Encontre a linha do lore que contém o valor total
                    String lineTotalValue = ChatColor.WHITE + "Valor total: ";


                    for(int i = 0; i < recyclerBottleLore.size(); i++) {
                        String loreLine = recyclerBottleLore.get(i);
                        if(loreLine.startsWith(lineTotalValue)) {
                            // Atualize o valor total de XP no lore
                            recyclerBottleLore.set(i, lineTotalValue + ChatColor.GREEN + totalValue);
                            recyclerBottleLore.set(i + 1, "");
                            recyclerBottleLore.set(i + 2, ChatColor.GRAY + "Todos os seus livros serão");
                            recyclerBottleLore.set(i + 3, ChatColor.GRAY + "transformados em experiência com " + totalValue + " XP");
                            break;
                        }
                    }

                    // Defina o novo lore no item do frasco de vidro
                    recyclerBottleMeta.setLore(recyclerBottleLore);
                    recyclerBottle.setItemMeta(recyclerBottleMeta);
                    recyclerInventory.setItem(fraskPosition, recyclerBottle);
                }
            }
        }
    }

    private int getTotalValueFromRecyclerBottle(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();
        int fraskPosition = 49;

        if(recyclerInventory != null) {
            ItemStack recyclerBottle = recyclerInventory.getItem(fraskPosition);

            if(recyclerBottle != null && recyclerBottle.getType() == Material.EXP_BOTTLE) {
                ItemMeta recyclerBottleMeta = recyclerBottle.getItemMeta();
                List<String> recyclerBottleLore = recyclerBottleMeta.getLore();

                String lineTotalValue = ChatColor.WHITE + "Valor total: " + ChatColor.GREEN;

                // Iterar sobre as linhas do lore para encontrar o valor total
                for (String loreLine : recyclerBottleLore) {
                    if(loreLine.startsWith(lineTotalValue)) {
                        // Extrair o valor total do lore
                        String totalValueString = loreLine.substring(lineTotalValue.length());
                        try {
                            // Tentar analisar o valor total como um inteiro
                            int totalValue = Integer.parseInt(totalValueString.trim());
                            return totalValue;
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    }
                }
            }
        }

        return 0;
    }

    private void removeRecycledBooks(Player player) {
        Inventory recyclerInventory = player.getOpenInventory().getTopInventory();

        if(recyclerInventory != null) {
            // Iterar sobre os slots do inventário do reciclador
            for(int slot = 0; slot < recyclerInventory.getSize(); slot++) {
                ItemStack item = recyclerInventory.getItem(slot);

                // Verificar se o slot contém um item
                if(item != null && item.getType() == Material.ENCHANTED_BOOK) {
                    // Verificar se o livro reciclado é do tipo avançado
                    if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() &&
                    item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Livro Avançado")) {
                        // Remover o livro reciclado do inventário do jogador
                        recyclerInventory.setItem(slot, null);
                    }
                }
            }
        }
    }

    // Função para verificar se um inventário está cheio
    private boolean isInventoryFull(Inventory inventory) {
        for(ItemStack item : inventory.getContents()) {
            if(item == null || item.getType() == Material.AIR) {
                return false;
            }
        }

        return true;
    }
}
