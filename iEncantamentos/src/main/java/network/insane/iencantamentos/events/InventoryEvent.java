package network.insane.iencantamentos.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import network.insane.iencantamentos.listeners.AdvancedEnchantsGUI;

import java.util.ArrayList;
import java.util.List;

public class InventoryEvent implements Listener {

    @EventHandler
    public void InventoryClicked(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory open = player.getOpenInventory().getTopInventory();
        Inventory openLower = player.getOpenInventory().getBottomInventory();
        ItemStack item = event.getCurrentItem();
        ItemStack[] inventoryContents = player.getInventory().getContents();

        if (item == null || open == null) {
            return;
        }

        if (player instanceof Player) {

            if (open.equals(player.getInventory())) {
                return;
            }

            if (open.getName().equals(ChatColor.DARK_PURPLE + "Feiticeiro")) {
                event.setCancelled(true);

                if(event.isRightClick() || event.getClickedInventory().equals(openLower)) {
                    event.setCancelled(true);
                } else {
                    // Verificar se o player clicou em um custom frasco xp total
                    if (customXpBottle(item)) {
                        if(player.getInventory().firstEmpty() != -1) {
                            // Obter a quantidade de xp armazenada no frasco
                            int xpAmount = getStoredExperience(item);

                            if (xpAmount > 0) {
                                // Remover frasco de xp
                                event.setCurrentItem(null);

                                // Criar um frasco de xp custom
                                ItemStack xpBottle = createCustomXpBottle(xpAmount);
                                ItemMeta meta = xpBottle.getItemMeta();

                                if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência")) {
                                    // Remover xp do player
                                    player.setLevel(0);
                                    player.setExp(0);
                                    player.setTotalExperience(0);

                                    // Add frasco de xp custom no inv do player
                                    player.getInventory().addItem(xpBottle);

                                    // Atualizar o inv do player
                                    player.closeInventory();
                                    player.updateInventory();
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu frasco.");
                        }
                    }

                    // Frasco de Experiência Avançado
                    if (item.getType() == Material.EXP_BOTTLE && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();

                        if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência Avançado")) {
                            if(player.getInventory().firstEmpty() != -1) {
                                // Xp Avançado
                                int xpAdvancedCost = 5000;
                                int xp = player.getTotalExperience();
                                if (xp >= xpAdvancedCost) {
                                    // Remover a quant de xp correto
                                    player.setTotalExperience(xp - xpAdvancedCost);
                                    player.setLevel(0);
                                    // Criar um frasco de xp avançado para o jogador
                                    ItemStack advancedXpBottle = createCustomXpBottle(xpAdvancedCost);
                                    player.getInventory().addItem(advancedXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Avançado " + ChatColor.GREEN + "por " + ChatColor.YELLOW + xpAdvancedCost + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu frasco.");
                            }
                        // Frasco de Experiência Normal
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência Normal")) {
                            if (player.getInventory().firstEmpty() != -1) {
                                // Xp Normal
                                int xpNormalCost = 2000;
                                int xp = player.getTotalExperience();
                                if (xp >= xpNormalCost) {
                                    // Remover a quant de xp correto
                                    player.setTotalExperience(xp - xpNormalCost);
                                    player.setLevel(0);
                                    // Criar um frasco de xp avançado para o jogador
                                    ItemStack normalXpBottle = createCustomXpBottle(xpNormalCost);
                                    player.getInventory().addItem(normalXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Normal " + ChatColor.GREEN + "por " + ChatColor.YELLOW + xpNormalCost + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu frasco.");
                            }
                        // Frasco de Experiência Simples
                        } else if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência Simples")) {
                            if (player.getInventory().firstEmpty() != -1) {
                                // Xp Simples
                                int xpSimpleCost = 1500;
                                int xp = player.getTotalExperience();
                                if (xp >= xpSimpleCost) {
                                    // Remover a quant de xp correto
                                    player.setTotalExperience(xp - xpSimpleCost);
                                    player.setLevel(0);
                                    // Criar um frasco de xp avançado para o jogador
                                    ItemStack simpleXpBottle = createCustomXpBottle(xpSimpleCost);
                                    player.getInventory().addItem(simpleXpBottle);

                                    //Atualizar item do minecart no inventario do jogador
                                    updateMinecartItem(player);
                                    updateXpTotal(player);
                                    player.updateInventory();

                                    player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW + "Frasco Simples " + ChatColor.GREEN + "por " + ChatColor.YELLOW + xpSimpleCost + "XP");
                                } else {
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu frasco.");
                            }
                        }
                    }

                    // Sistema livro de Encantamentos Avançado quando Clicado com Botão Esquerdo
                    if (item.getType() == Material.BOOK && item.hasItemMeta()) {
                        ItemMeta meta = item.getItemMeta();
                        String eBookAdvancedType = "Avançado";
                        String eBookSimpleType = "Simples"

                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();

                            for (String loreLine : lore) {
                                if(loreLine.equals(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookAdvancedType)) {
                                    int eBookAdvancedCost = 3500;
                                    int xp = player.getTotalExperience();

                                    ItemStack existingBook = null;
                                    for (ItemStack stack : inventoryContents) {
                                        if (stack != null && stack.getType() == Material.BOOK) {
                                            if (stack.getAmount() < 64) {
                                                existingBook = stack;
                                                break;
                                            }
                                        }
                                    }

                                    if (existingBook != null || player.getInventory().firstEmpty() != -1) {
                                        if (xp >= eBookAdvancedCost) {
                                            // Remover o xp do player
                                            player.setTotalExperience(xp - eBookAdvancedCost);
                                            player.setLevel(0);

                                            // Criar um Livro de Encantamentos do Tipo Avançado
                                            ItemStack eBookAdvanced = createCustomBook(eBookAdvancedType);

                                            // Add livro ao inv
                                            player.getInventory().addItem(eBookAdvanced);

                                            updateMinecartItem(player);
                                            updateXpTotal(player);
                                            player.updateInventory();

                                            player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW +
                                                    "Livro Encantado" + ChatColor.GREEN + " do tipo " +
                                                    ChatColor.YELLOW + eBookAdvancedType);
                                        } else {
                                            event.setCancelled(true);
                                            player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu livro.");
                                    }
                                }

                                if() {
                                    int eBookSimpleCost = 3500;
                                    int xp = player.getTotalExperience();

                                    ItemStack existingBook = null;
                                    for (ItemStack stack : inventoryContents) {
                                        if (stack != null && stack.getType() == Material.BOOK) {
                                            if (stack.getAmount() < 64) {
                                                existingBook = stack;
                                                break;
                                            }
                                        }
                                    }

                                    if (existingBook != null || player.getInventory().firstEmpty() != -1) {
                                        if (xp >= eBookAdvancedCost) {
                                            // Remover o xp do player
                                            player.setTotalExperience(xp - eBookAdvancedCost);
                                            player.setLevel(0);

                                            // Criar um Livro de Encantamentos do Tipo Avançado
                                            ItemStack eBookAdvanced = createCustomBook(eBookAdvancedType);

                                            // Add livro ao inv
                                            player.getInventory().addItem(eBookAdvanced);

                                            updateMinecartItem(player);
                                            updateXpTotal(player);
                                            player.updateInventory();

                                            player.sendMessage(ChatColor.GREEN + "Você adquiriu um " + ChatColor.YELLOW +
                                                    "Livro Encantado" + ChatColor.GREEN + " do tipo " +
                                                    ChatColor.YELLOW + eBookAdvancedType);
                                        } else {
                                            event.setCancelled(true);
                                            player.sendMessage(ChatColor.RED + "Você não possui XP suficiente!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Seu inventário está cheio. Libere espaço antes de usar seu livro.");
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                // Verificação para ver se está clicando com botão direito no bottom inv com GUI aberto
                if (event.getClickedInventory().equals(openLower)) {
                    event.setCancelled(true);
                } else {
                    // Sistema livro de Encantamentos Avaçando quando Clicado com Botão Direito
                    if (item.getType() == Material.BOOK && event.isRightClick()) {
                        ItemMeta meta = item.getItemMeta();
                        if (meta.hasLore()) {
                            List<String> lore = meta.getLore();
                            // Verificar se a lore contem as linhas especificas
                            if (lore.contains(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + "Avançado")) {
                                // Abrir um novo GUI
                                AdvancedEnchantsGUI advancedEnchantsGUI = new AdvancedEnchantsGUI();
                                advancedEnchantsGUI.AdvancedEnchantInventory(player);
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateMinecartItem(Player player) {
        int xp = player.getTotalExperience();
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if(inventory != null) {
            ItemStack myxp = inventory.getItem(13);

            if(myxp != null && myxp.getType() == Material.MINECART) {
                ItemMeta myxpmeta = myxp.getItemMeta();
                myxpmeta.setDisplayName(ChatColor.YELLOW + "Sua experiência");

                List<String> xplore = new ArrayList<>();
                xplore.add(ChatColor.WHITE + "XP atual: " + ChatColor.GRAY + xp);
                myxpmeta.setLore(xplore);

                myxp.setItemMeta(myxpmeta);
            }
        }
    }

    private void updateXpTotal(Player player) {
        int xp = player.getTotalExperience();
        Inventory inventory = player.getOpenInventory().getTopInventory();

        if(inventory != null) {
            ItemStack myxp = inventory.getItem(29);

            if(myxp != null && myxp.getType() == Material.EXP_BOTTLE) {
                if(xp > 0) {
                    ItemMeta myxpmeta = myxp.getItemMeta();
                    myxpmeta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");

                    List<String> xplore = new ArrayList<>();
                    xplore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xp);
                    xplore.add("");
                    xplore.add(ChatColor.GRAY + "Você receberá um frasco de ");
                    xplore.add(ChatColor.GRAY + "experiência com " + xp + " XP");
                    myxpmeta.setLore(xplore);

                    myxp.setItemMeta(myxpmeta);
                    inventory.setItem(29, myxp);
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
                    inventory.setItem(29, gBottle);
                }
            }
        }
    }

    @EventHandler
    public void onRightClickInBottle(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        ItemMeta frasco = item.getItemMeta();

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(customXpBottle(item)) {
                int xpAmount = getStoredExperience(item);

                if(xpAmount > 0) {
                    event.setCancelled(true);

                    // Verificar a quant do frasco no slot
                    int amountInHand = item.getAmount();

                    if(amountInHand == 1) {
                        // Se houver apenas 1 remover
                        player.getInventory().setItemInHand(new ItemStack(Material.AIR));
                        player.giveExp(xpAmount);
                        player.sendMessage(ChatColor.GREEN + "Você consumiu um " + frasco.getDisplayName() + ChatColor.GREEN + " com " + ChatColor.YELLOW + xpAmount + "XP");
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    } else {
                        // Se houver mais de um 1 apenas diminuir no slot
                        item.setAmount(amountInHand - 1);
                        player.giveExp(xpAmount);
                        player.sendMessage(ChatColor.GREEN + "Você consumiu um " + frasco.getDisplayName() + ChatColor.GREEN + " com " + ChatColor.YELLOW + xpAmount + "XP");
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
                    }

                }
            }
        }
    }

    // Verificar se um item é um frasco de xp custom
        private boolean customXpBottle(ItemStack item) {
            if(item.getType() == Material.EXP_BOTTLE && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.YELLOW + "Frasco de Experiência")) {
                    return true;
                }
            }
            return false;
        }

        // Obter a quantidade de xp armazenada em um frasco de xp
        private int getStoredExperience(ItemStack item) {
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                for (String line : lore) {
                    if(line.startsWith(ChatColor.WHITE + "Custo: " + ChatColor.GREEN)) {
                        try {
                            // Extrair a quantidade de experiência da linha do lore
                            String xpString = line.substring((ChatColor.WHITE + "Custo: " + ChatColor.GREEN).length());
                            return Integer.parseInt(xpString);
                        } catch (NumberFormatException e) {
                            // Se houver um erro ao analisar a quantidade de experiência, retornar 0
                            return 0;
                        }
                        }
                    }
                }
            return 0;
        }

        // Criar um frasco de xp custom com a quant de xp
        private ItemStack createCustomXpBottle(int xpAmount) {
            ItemStack xpBottle = new ItemStack(Material.EXP_BOTTLE, 1);
            ItemMeta meta = xpBottle.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "Frasco de Experiência");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "Custo: " + ChatColor.GREEN + xpAmount);
            meta.setLore(lore);
            xpBottle.setItemMeta(meta);
            return xpBottle;
        }

        // Criar um livro custom com tipo do livro
        private ItemStack createCustomBook(String eBookType) {
            ItemStack customBook = new ItemStack(Material.BOOK);
            ItemMeta customBookMeta = customBook.getItemMeta();

            customBookMeta.setDisplayName(ChatColor.YELLOW + "Livro de Encantamentos");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Tipo: " + ChatColor.WHITE + eBookType);
            customBookMeta.setLore(lore);
            customBook.setItemMeta(customBookMeta);
            return customBook;
        }
    }
