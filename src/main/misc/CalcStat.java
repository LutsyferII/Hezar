package main.misc;

import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class CalcStat{
	String name = "stat";
	LinkedHashMap<String,Integer> stats = new LinkedHashMap<String, Integer>();;
	
	public CalcStat(ConfigurationSection c) {
		name = c.getName();
		Set<String> keyset = c.getKeys(false);
		for(String s : keyset) {
			stats.put(s, c.getInt(s));
		}
	}
}