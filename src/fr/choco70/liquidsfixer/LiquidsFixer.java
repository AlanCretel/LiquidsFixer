package fr.choco70.liquidsfixer;

import fr.choco70.liquidsfixer.listeners.LiquidFlow;
import fr.choco70.liquidsfixer.utils.configManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class LiquidsFixer extends JavaPlugin{

    private configManager configManager;

    @Override
    public void onEnable(){
        configManager = new configManager();
        configManager.reloadConfig();
        configManager.updateConfig();

        getServer().getPluginManager().registerEvents(new LiquidFlow(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "LiquidsFixer: Plugin enabled");
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "LiquidsFixer: Plugin disabled");
    }

    public configManager getConfigManager(){
        return this.configManager;
    }

}
