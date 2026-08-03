package Configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import main.Configs;
import main.Settings;
import main.blocks.BlockZarj;

public class TestConfig{
	static FileConfiguration config;
	static Set<String> setofkeys;
	public static void start() {
		
	}
	public static void load() {
		
		config = Configs.testConfig.get();
		setofkeys = config.getKeys(false);
		
		if(config.contains("test")) {
			Settings.debug("Результат хуеты "+config.get("test").getClass().getName(),"4");
		}
		
		
		
		
	}
	
}