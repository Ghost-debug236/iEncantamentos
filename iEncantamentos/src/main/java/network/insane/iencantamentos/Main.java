package network.insane.iencantamentos;

import network.insane.iencantamentos.commands.EnchantCommands;
import network.insane.iencantamentos.events.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("IEncantamentos foi ativado!");

        EnchantCommands commands = new EnchantCommands();
        getCommand(commands.cmd1).setExecutor(commands);
        getCommand(commands.cmd2).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new InventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new SimpleInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new NormalInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new AdvancedInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new RecyclerInventoryEvent(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("IEncantamentos foi desativado!");
    }

}
