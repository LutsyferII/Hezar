package Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import main.Futh.ZItem;
import main.reitem.ReItemer;

public class ReItemConfig {
	
	public static List<ReItemer> reitems = new ArrayList<>();

	public static void load(ConfigurationSection c) {
		if(c.contains("reitems")) {
			reitems.clear();
			ConfigurationSection items_c = c.getConfigurationSection("reitems");
			Set<String> keys = items_c.getKeys(false);
			for(String key : keys) {
				if(!items_c.isConfigurationSection(key)) {
					Errors.error("В "+key+" неверный формат!");
					continue;
				}
				ConfigurationSection key_c = items_c.getConfigurationSection(key);
				if(!key_c.contains("from")||!key_c.contains("to")||!key_c.isString("from")||!key_c.isString("to")) {
					Errors.error("{ReItem} в "+key+" отсутствуют либо имеют неверный формат предметы!");
					continue;
				}
				ZItem from = new  ZItem(key_c.getString("from"));
				ZItem to = new  ZItem(key_c.getString("to"));
				
				ReItemer reitem = new ReItemer(key,from,to);
				reitems.add(reitem);
			}
		}
	}
	public static boolean hasReItemer(String id) {
		for(ReItemer reitem : reitems) {
			if(reitem.checkid(id)) {
				return true;
			}
		}
		return false;
	}
	public static ReItemer getReItemer(String id) {

		for(ReItemer reitem : reitems) {
			if(reitem.checkid(id)) {
				return reitem;
			}
		}
		return null;
	}
}
