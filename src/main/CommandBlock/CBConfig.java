package main.CommandBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class CBConfig{
	public static List<CMDBlock> list_cb = new ArrayList<>();
	
	public static void load(ConfigurationSection c) {
		list_cb = new ArrayList<>();
		Set<String> setofkeys = c.getKeys(false);
		for(String s : setofkeys) {
			list_cb.add(new CMDBlock(c.getConfigurationSection(s)));
		}
		
	}
	
	
	
	
	
	
}