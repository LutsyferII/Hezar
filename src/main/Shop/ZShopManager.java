package main.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

import main.Configs;

public class ZShopManager {

	public static List<ZShopMain> shops = new ArrayList<>();
	
	
	
	
	public static void loadShops() {
		shops = new ArrayList<>();
		ConfigurationSection config = Configs.shopsConfig.get();
		Set<String> key_set = config.getKeys(false);
		int shop_id = 0;
		for(String key: key_set) {
			shops.add(new ZShopMain(config.getConfigurationSection(key),shop_id));
			shop_id++;
		}
		
		
		
	}
	public static boolean checkNPC(int id) {
		for(ZShopMain shop : shops) {
			if(shop.getNPC()==id) {
				return true;
			}
		}
		return false;
	}
	public static ZShopMain getShopByNPC(int id) {
		for(ZShopMain shop : shops) {
			if(shop.getNPC()==id) {
				return shop;
			}
		}
		return null;
	}
	public static ZShopMain getShopById(int id) {
		for(ZShopMain shop : shops) {
			if(shop.id==id) {
				return shop;
			}
		}
		return null;
		
	}
	
	public static ZShopMain getShopByInv(Inventory inv) {
		for(ZShopMain shop : shops) {
			
			for(ZShopInventory page : shop.pages) {
				if(inv.getHolder().equals(page)) {
					return shop;
				}
			}
		}
		return null;
	}
	
	
	
	public static boolean isShop(String name) {
		for(ZShopMain shop : shops) {
			if(shop.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public static ZShopMain getShop(String name) {
		for(ZShopMain shop : shops) {
			if(shop.getName().equalsIgnoreCase(name)) {
				return shop;
			}
		}
		return null;
	}
	
}
