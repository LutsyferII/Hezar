package main.minigames.miner;

import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class MinerTemplate {

	String name = "мины";
	String lore = "Описание";
	int size = 4;
	int mines = 1;
	int time = 1800;
	
	public MinerTemplate(ConfigurationSection c) {
		if(c.contains("size")&&c.isInt("size")) {
			this.size = c.getInt("size");
		}else {
			Settings.debug("В "+c.getName()+" минере отсутствует параметр size, либо имеет неверный формат!", 3);
		}
		if(c.contains("mines")&&c.isInt("mines")) {
			this.mines = c.getInt("mines");
		}else {
			Settings.debug("В "+c.getName()+" минере отсутствует параметр mines, либо имеет неверный формат!", 3);
			
		}
		if(c.contains("time")&&c.isInt("time")) {
			this.time = c.getInt("time");
		}else {
			Settings.debug("В "+c.getName()+" минере отсутствует параметр time, либо имеет неверный формат!", 3);
			
		}
		if(c.contains("name")&&c.isString("name")) {
			this.name = c.getString("name");
		}else {
			Settings.debug("В "+c.getName()+" минере отсутствует параметр name, либо имеет неверный формат!", 3);
			
		}
		if(c.contains("lore")&&c.isString("lore")) {
			this.lore = c.getString("lore");
		}else {
			Settings.debug("В "+c.getName()+" минере отсутствует параметр lore, либо имеет неверный формат!", 3);
			
		}
	}
	
	public Miner createMiner() {
		return new Miner(this.size,mines,name,lore);
	}
	
	
	
}
