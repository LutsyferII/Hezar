package main.Futh.LootBoxMechanic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import Configs.Errors;
import main.Configs;
import main.Settings;
import main.Futh.ZItem;

public class LootManager {
	static public LinkedHashMap<String,LootBox> loot_boxes = new LinkedHashMap<String,LootBox>();
	static public ConfigurationSection config = Configs.lootboxConfig.get();
	
	
	public static void load() {
		loot_boxes = new LinkedHashMap<String,LootBox>();
		config = Configs.lootboxConfig.get();
		Set<String> keys =config.getKeys(false);
		for(String key : keys) {
			if(config.isConfigurationSection(key)) {
				ConfigurationSection loot_c = config.getConfigurationSection(key);
				if(loot_c.contains("items")&&loot_c.isList("items")) {
					List<ZItem> zitems = new ArrayList<ZItem>();
					for(String item_s : loot_c.getStringList("items")) {
						zitems.add(new ZItem(item_s));
					}
					LootBox lootbox = new LootBox(key,zitems);
					
					if(loot_c.contains("perm")&&loot_c.isString("perm")) {
						String perm = loot_c.getString("perm");
						lootbox.setPermission(perm);
					}else if(loot_c.contains("permission")&&loot_c.isString("permission")) {
						String perm = loot_c.getString("permission");
						lootbox.setPermission(perm);
						
					}
					
					loot_boxes.put(key, lootbox);
				}else {
					Errors.error("Ошбика при формировании лутбокса, отуствуют предметы в "+key);
					
				}
				
				
			}else {
				Errors.error("Ошбика при формировании лутбокса, секция лутболкса не ConfigurationSection!  "+key);
			}
			
		}
		
		Settings.debug("{LOOTBOX} Лутбоксы загружены в количестве: "+loot_boxes.size(), "a");
		
	}
	public static boolean hasLoot(String name) {
		return loot_boxes.containsKey(name);
		
	}
	public static LootBox getLoot(String name) {
		return loot_boxes.get(name);
	}
	
	
	
}
