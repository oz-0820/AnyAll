package net.oz0820.spigot.anyall;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class AnyAllConfig {
    private static File file;
    private static FileConfiguration config;

    public AnyAllConfig() {
    }

    public static void init() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("AnyAll").getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var1) {
                System.out.println("Could not create file.");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException var1) {
            System.out.println("Could not save file.");
        }
    }

}
