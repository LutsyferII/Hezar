package main.minigames.miner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class MinerManager {
	
	static List<MinerTemplate> miners = new ArrayList<>();
	
	public static void load(ConfigurationSection c) {
		Set<String> keys = c.getKeys(false);
		
		for(String key:keys) {
			ConfigurationSection field = c.getConfigurationSection(key);
			MinerTemplate mt = new MinerTemplate(field);
			miners.add(mt);
		}
		
		
		
	}
	
	
	
	
}
