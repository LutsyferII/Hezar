package main.Futh.leveler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import Configs.Errors;
import main.Futh.ZItem;
import main.Futh.LootBoxMechanic.LootBox;
import main.Futh.LootBoxMechanic.LootManager;

public class LevelPodMech{
	public String name;
	public int level;
	public List<ZItem> itemlist = new ArrayList<>();
	public List<LootBox> loot_boxes = new ArrayList<>();
	int exp = 0;
	public LevelPodMech(ConfigurationSection c) {
		this.name=c.getName();
		this.level=c.getInt("level");
		if(c.contains("drop")&&c.isList("drop")) {
			List<String> drops = c.getStringList("drop");
			for(String s : drops) {
				itemlist.add(new ZItem(s));
			}
		}
		if(c.contains("exp")&&c.isInt("exp")) {
			exp=c.getInt("exp");
		}
		if(c.contains("loot")&&c.isList("loot")) {
			List<String> loots = c.getStringList("loot");
			for(String s : loots) {
				if(LootManager.hasLoot(s)) {
					loot_boxes.add(LootManager.getLoot(s));
				}else {
					Errors.error("{LevelMech} Несуществующый лутбокс "+s+" в "+name);
				}
			}
			
		}else if(c.contains("lootbox")&&c.isList("lootbox")) {
			List<String> loots = c.getStringList("lootbox");
			for(String s : loots) {
				if(LootManager.hasLoot(s)) {
					loot_boxes.add(LootManager.getLoot(s));
				}else {
					Errors.error("{LevelMech} Несуществующый лутбокс "+s+" в "+name);
				}
			}
		}
	}
	public int getExp() {
		return exp;
	}
	public boolean hasExp() {
		if(exp<=0) {
			return false;
		}else {
			return true;
		}
	}
	
	public int getLevel() {
		return level;
	}
	public boolean hasEnoughLevel(int lvl) {
		if(lvl<level) {
			return false;
		}else {
			return true;
		}
	}
	public boolean levelEqual(int lvl) {
		if(lvl==level) {
			return true;
		}else {
			return false;
		}
	}
	public List<ZItem> getItems(){
		return itemlist;
	}
	
	public boolean hasLootBoxes() {
		return !loot_boxes.isEmpty();
	}
	public boolean hasDrop() {
		return !itemlist.isEmpty();
	}
	
	public List<LootBox> getLootBoxes(){
		return loot_boxes;
	}
	
}