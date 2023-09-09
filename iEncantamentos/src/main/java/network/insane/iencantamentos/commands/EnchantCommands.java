package network.insane.iencantamentos.commands;

import network.insane.iencantamentos.listeners.EnchantGUI;
import network.insane.iencantamentos.listeners.RecyclerGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EnchantCommands implements Listener, CommandExecutor {

    public String cmd1 = "ie";
    public String cmd2 = "reciclar";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            if(cmd.getName().equalsIgnoreCase(cmd1)) {
                Player player = (Player) sender;
                EnchantGUI i = new EnchantGUI();

                i.EnchantInventory(player);
            }

            if(cmd.getName().equals(cmd2)) {
                Player player = (Player) sender;
                RecyclerGUI i = new RecyclerGUI();

                i.RecyclerInventory(player);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Somente jogadores podem executar este comando!");
        }
        return false;
    }


}
