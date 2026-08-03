package main.backpack.reserve;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class ZBackpackLoader implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	ItemStack[] items;
	LocalDateTime created;
	LocalDateTime last_open;
	String creator;
	
	String uuid;
	int size;

	ZBackpackLoader(String ui,String c, LocalDateTime cr, LocalDateTime lo, ItemStack[] it,int s){
		uuid = ui;
		creator = c;
		created = cr;
		last_open = lo;
		items = it;
		size = s;
	}

	public ItemStack[] getItems() {
		return items;
	}
	public int getSize() {
		return size;
	}
	public String getStringUUID() {
		return uuid.toString();
	}
	
	public String getCreator() {
		return creator;
	}
	
	public LocalDateTime getWhenCreate() {
		return created;
	}
	
	public LocalDateTime getLastUpdate() {
		return last_open;
	}
	
	
	
	
	
	
}
