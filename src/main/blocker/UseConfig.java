package main.blocker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import com.sk89q.worldguard.WorldGuard;

import main.Settings;

public class UseConfig {
	static List<BlockerUse> regs_ban = new ArrayList<BlockerUse>();
	static List<BlockerUse> worlds_ban = new ArrayList<BlockerUse>();
	public static void load(ConfigurationSection c) {

		regs_ban = new ArrayList<BlockerUse>();
		worlds_ban = new ArrayList<BlockerUse>();
		if(c.contains("world")&&c.isConfigurationSection("world")) {
			ConfigurationSection world_c = c.getConfigurationSection("world");
			Set<String> keys = world_c.getKeys(false);
			for(String key : keys) {
				if(world_c.isList(key)) {
					worlds_ban.add(new BlockerUse(key,world_c.getStringList(key)));
				}else if(world_c.isString(key)){
					worlds_ban.add(new BlockerUse(key,world_c.getString(key)));
				}
			}
		}else {
			Settings.debug("{CMDBAN}??????????? ???? ? ???????? ?????????????", 4);
		}
		if(c.contains("region")&&c.isConfigurationSection("region")) {
			ConfigurationSection region_c = c.getConfigurationSection("region");
			Set<String> keys = region_c.getKeys(false);
			for(String key : keys) {
				if(region_c.isConfigurationSection(key)) {

					ConfigurationSection region_world_c = region_c.getConfigurationSection(key);
					Set<String> ks = region_world_c.getKeys(false);
					for(String k : ks) {
						if(region_world_c.isList(k)) {
							
							regs_ban.add(new BlockerUse(key,k,region_world_c.getStringList(k)));
						}else if(region_world_c.isString(key)){
							regs_ban.add(new BlockerUse(key,k,region_world_c.getString(k)));
						}
					}
				}
			}
		}
	}
	public static List<BlockerUse> getAllRegs() {
		return regs_ban; 
	}
	public static List<BlockerUse> getAllWorlds() {
		return worlds_ban; 
	}
	public static boolean regInBan(String reg) {
		for(BlockerUse use : regs_ban) {
			if(use.getName().equals(reg)) {
				return true;
			}
		}
		return false;
	}
	public static boolean worldInBan(String reg) {
		for(BlockerUse use : worlds_ban) {
			if(use.getName().equals(reg)) {
				return true;
			}
		}
		return false;
	}
	public static boolean hasBanReg(String world,String reg,String ban_type,String ban_id) {
			for(BlockerUse use : regs_ban) {
				if(use.isWorld(world)) {
					if(use.getName().equals(reg)) {
						if(use.hasBan(ban_type,ban_id)) {
							return true;
						}
					}
				}
				
			}
		
		
		return false;
	}
	public static boolean hasBanWorld(String world,String ban_type,String ban_id) {
		for(BlockerUse use : worlds_ban) {
			//Settings.debug("???????? ??? ?? ???? ???: "+use.getName());
			if(use.getName().equals(world)) {
				if(use.hasBan(ban_type,ban_id)) {
					return true;
				}
			}
		}
		return false;
	}
}
