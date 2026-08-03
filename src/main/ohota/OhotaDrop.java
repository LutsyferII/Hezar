package main.ohota;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import main.Settings;
import main.Futh.leveler.LevelMechanic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OhotaDrop{
	public String mobname;
	public String name;
	public ConfigurationSection mechancs;
	public List<LevelMechanic> listmechan= new ArrayList<>();
	
	public OhotaDrop(ConfigurationSection c) {
		mobname=c.getString("mob");
		this.name=c.getName();
		this.mechancs=c.getConfigurationSection("mechanics");
		Set<String> setofkeys = mechancs.getKeys(false);
		for(String s : setofkeys) {
			Settings.debug("[huy] §2Механика = "+s);
			this.listmechan.add(new LevelMechanic(mechancs.getConfigurationSection(s)));
		}
	}
	public void load(ConfigurationSection c) {
		
	}
}