package main.Futh;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class Lore {
	static HashMap<String,String> lore = new HashMap<>();
	public static void load(ConfigurationSection c) {
		Set<String> keys = c.getKeys(false);
		for(String key : keys) {
			if(!c.isString(key)) {
				continue;
			}
			lore.put(key, c.getString(key));
		}
	}
	public static boolean hasLore(String key) {
		return lore.containsKey(key);
	}
	public static String getLore(String key) {
		return lore.get(key);
	}

}
