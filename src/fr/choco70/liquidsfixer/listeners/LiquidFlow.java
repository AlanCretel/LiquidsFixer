package fr.choco70.liquidsfixer.listeners;

import fr.choco70.liquidsfixer.LiquidsFixer;
import fr.choco70.liquidsfixer.utils.configManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class LiquidFlow implements Listener{

    LiquidsFixer plugin = LiquidsFixer.getPlugin(LiquidsFixer.class);
    configManager configManager = plugin.getConfigManager();
    YamlConfiguration config = configManager.getConfig();

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent e){
        World world = e.getBlock().getWorld();
        Biome biome = e.getBlock().getBiome();
        if(e.getBlock().isLiquid()){
            if(e.getBlock().getType() == Material.WATER){
                if(config.getBoolean("WORLDS." + world.getName() + ".WATER", true) && config.getBoolean("BIOMES." + biome + ".WATER", true)){
                    e.setCancelled(true);
                }
            }
            else{
                if(config.getBoolean("WORLDS." + world.getName() + ".LAVA", true) && config.getBoolean("BIOMES." + biome + ".LAVA", true)){
                    e.setCancelled(true);
                }
            }
        }
        else if(e.getBlock().getBlockData() instanceof Waterlogged){
            if(config.getBoolean("WORLDS." + world.getName() + ".WATER_LOGGED", true) && config.getBoolean("BIOMES." + biome + ".WATER_LOGGED", true)){
                e.setCancelled(true);
            }
        }
        else if(e.getBlock().getType() == Material.KELP || e.getBlock().getType() == Material.KELP_PLANT || e.getBlock().getType() == Material.SEAGRASS || e.getBlock().getType() == Material.TALL_SEAGRASS){
            if(config.getBoolean("WORLDS." + world.getName() + ".WATER_PLANTS", true) && config.getBoolean("BIOMES." + biome + ".WATER_PLANTS", true)){
                e.setCancelled(true);
            }
        }
        else{
            plugin.getServer().getConsoleSender().sendMessage("LiquidsFixer Error: " + e.getBlock().getType().name());
        }
    }

}
