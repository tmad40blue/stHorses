package blue.tmad40.sthorses.files;

import blue.tmad40.sthorses.Main;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHelper {

    @SuppressWarnings("deprecation")
    public static void loadConfigs() {

        Main.getInstance().saveDefaultConfig();
        //Main.getInstance().getConfig().setDefaults(YamlConfiguration.loadConfiguration(Main.getInstance().getResource("config.yml")));
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveConfig();

        FileManager.getInstance().loadFiles();

    }

}
