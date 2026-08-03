package main.blocker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class CmdConfig {
	static List<BlockerCmd> regs_ban = new ArrayList<BlockerCmd>();
	static List<BlockerCmd> worlds_ban = new ArrayList<BlockerCmd>();
	public static void load(ConfigurationSection c) {
		regs_ban = new ArrayList<BlockerCmd>();
		worlds_ban = new ArrayList<BlockerCmd>();
		if(c.contains("world")&&c.isConfigurationSection("world")) {
			ConfigurationSection world_c = c.getConfigurationSection("world");
			Set<String> keys = world_c.getKeys(false);
			for(String key : keys) {
				if(world_c.isList(key)) {
					worlds_ban.add(new BlockerCmd(key,world_c.getStringList(key)));
				}else if(world_c.isString(key)){
					worlds_ban.add(new BlockerCmd(key,world_c.getString(key)));
				}
			}
		}else {
			Settings.debug("{CMDBAN}Отсутствуют миры в конфигах комманд", 4);
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
							regs_ban.add(new BlockerCmd(key,k,region_world_c.getStringList(k)));
						}else if(region_world_c.isString(key)){
							regs_ban.add(new BlockerCmd(key,k,region_world_c.getString(k)));
						}
					}
				}
			}
		}
	}
	public static List<BlockerCmd> getAllRegs() {
		return regs_ban; 
	}
	public static List<BlockerCmd> getAllWorlds() {
		return worlds_ban; 
	}
	public static boolean regInBan(String reg) {
		for(BlockerCmd cmd : regs_ban) {
			if(cmd.getName().equals(reg)) {
				return true;
			}
		}
		return false;
	}
	public static boolean worldInBan(String reg) {
		for(BlockerCmd cmd : worlds_ban) {
			if(cmd.getName().equals(reg)) {
				return true;
			}
		}
		return false;
	}
	public static boolean hasBanReg(String world,String reg,String ban) {
		for(BlockerCmd cmd : regs_ban) {
			if(cmd.isWorld(world)) {
				if(cmd.getName().equals(reg)) {
					if(cmd.hasBan(ban)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	public static boolean hasBanWorld(String world,String ban) {
		for(BlockerCmd cmd : worlds_ban) {
			if(cmd.getName().equals(world)) {
				if(cmd.hasBan(ban)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
}
