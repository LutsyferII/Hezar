package main.Shop;

import org.bukkit.inventory.ItemStack;

public class ItemSell {
	public int uid = 0;
	ItemStack item;
	public ItemSell(ItemStack item, int id) {
		this.item = item;
		uid=id;
	}
}
