package main.UI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ZInventory implements InventoryHolder {


	Inventory inv= Bukkit.createInventory(this, 27, "Z");
	ItemStack nol = new ItemStack(Material.BARRIER);
	
	
	
	
	
	
	
	
	public ItemStack changeNameModel(ItemStack item,String name, int model) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setCustomModelData(model);
		item.setItemMeta(meta);
		return item;
	}
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}

}
