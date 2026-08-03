package main;

import main.Utils.BukkitConfig;

public class Configs {

	public static BukkitConfig mainConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "config.yml");
	public static BukkitConfig ohotaConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "ohota.yml");
	public static BukkitConfig blockConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "blocks.yml");
	public static BukkitConfig cmdblockConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "cmdblocks.yml");
	public static BukkitConfig tochConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "toch.yml");
	public static BukkitConfig calcConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "calc.yml", "hueta");
	public static BukkitConfig testConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "test.yml", "hueta");
	public static BukkitConfig shopsConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(),"shops.yml", "Shops");
	public static BukkitConfig cmdbanConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(),"cmd_ban.yml", "blocker");
	public static BukkitConfig usebanConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(),"use_ban.yml", "blocker");
	public static BukkitConfig compassConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "compass.yml");
	public static BukkitConfig lootboxConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "lootboxes.yml");
	
	public static BukkitConfig reitemConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "reitem.yml");
	public static BukkitConfig loreConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(), "lore.yml");
	

	public static BukkitConfig minersConfig = BukkitConfig.create(hezar.inst, hezar.inst.getDataFolder(),"miner.yml", "minigames");
	
	
	public static void reload() {
		try {
			mainConfig.reload();
			ohotaConfig.reload();
			blockConfig.reload();
			cmdblockConfig.reload();
			tochConfig.reload();
			calcConfig.reload();
			testConfig.reload();
			shopsConfig.reload();
			cmdbanConfig.reload();
			usebanConfig.reload();
			compassConfig.reload();
			lootboxConfig.reload();
			
			reitemConfig.reload();
			loreConfig.reload();
			
			minersConfig.reload();
		} catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_lootbox() {
		try {
			lootboxConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_compass() {
		try {
			compassConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_cmdblock() {
		try {
			cmdblockConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_toch() {
		try {
			tochConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_block() {
		try {
			blockConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_shop() {
		try {
			shopsConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_ohota() {
		try {
			ohotaConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_blocker() {
		try {
			cmdbanConfig.reload();
			usebanConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_reitem() {
		try {
			reitemConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_lore() {
		try {
			loreConfig.reload();
		}catch (Exception e) { e.printStackTrace(); }
	}
	public static void reload_miner() {
		try {
			minersConfig.reload();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
