package fr.choco70.liquidsfixer.utils;

import fr.choco70.liquidsfixer.LiquidsFixer;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class configManager{

    private LiquidsFixer plugin = LiquidsFixer.getPlugin(LiquidsFixer.class);

    public void reloadConfig(){
        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }
        File config = new File(plugin.getDataFolder() + "/config.yml");
        if(!config.exists()){
            try {
                config.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        plugin.getConfig().options().copyDefaults(true);
        try {
            plugin.getConfig().save(new File(plugin.getDataFolder() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig(){
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/config.yml"));
    }

    public void saveConfig(YamlConfiguration config){
        try {
            config.save(new File(plugin.getDataFolder() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConfig(){
        List<World> worlds = plugin.getServer().getWorlds();
        YamlConfiguration config = getConfig();
        for (World world : worlds){
            if(!config.isConfigurationSection("WORLDS." + world.getName())){
                config.set("WORLDS." + world.getName() + ".WATER", true);
                config.set("WORLDS." + world.getName() + ".LAVA", true);
                config.set("WORLDS." + world.getName() + ".WATER_LOGGED", true);
                config.set("WORLDS." + world.getName() + ".WATER_PLANTS", true);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "LiquidsFixer: Added world " + world.getName() + " to config.");
            }
            else{
                if(!config.isSet("WORLDS." + world.getName() + ".WATER")){
                    config.set("WORLDS." + world.getName() + ".WATER", true);
                }
                else if(!config.isSet("WORLDS." + world.getName() + ".LAVA")){
                    config.set("WORLDS." + world.getName() + ".LAVA", true);
                }
                else if(!config.isSet("WORLDS." + world.getName() + ".WATER_LOGGED")){
                    config.set("WORLDS." + world.getName() + ".WATER_LOGGED", true);
                }
                else if(!config.isSet("WORLDS." + world.getName() + ".WATER_PLANTS")){
                    config.set("WORLDS." + world.getName() + ".WATER_PLANTS", true);
                }
            }
        }
        saveConfig(config);
        Set<String> currentWorlds = getConfig().getConfigurationSection("WORLDS").getKeys(false);
        for (String currentWorld : currentWorlds) {
            if(plugin.getServer().getWorld(currentWorld) == null){
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "LiquidsFixer: Removed world " + currentWorld + " from config.");
                config.set("WORLDS." + currentWorld, null);
            }
        }
        saveConfig(config);
    }
}
