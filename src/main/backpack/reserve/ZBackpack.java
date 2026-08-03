package main.backpack.reserve;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import main.Settings;

public class ZBackpack  implements InventoryHolder{
	Inventory inv;

	static final int size_smallest = 9;
	static final int size_small = 18;
	static final int size_normal = 27;
	static final int size_big = 36;
	static final int size_large = 45;
	static final int size_biggest = 54;
	static final String NBT_TYPE = "ZARJ_BACKPACK_TYPE";
	static final String NBT_UUID = "ZARJ_BACKPACK_UUID";
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	//Date created = new Date(System.currentTimeMillis());
	LocalDateTime created = LocalDateTime.now();
	LocalDateTime last_open;
	String creator = "admin";
	
	UUID uuid;
	public ZBackpack(ItemStack item, String name) {
		this.uuid = UUID.randomUUID();
		//created = new Date(System.currentTimeMillis());
		created = LocalDateTime.now();
		last_open = LocalDateTime.now();
		this.creator = name;
		NBTItem nbt_item = NBTItem.get(item);
		
		int type = nbt_item.getInteger(NBT_TYPE);
		String s_uuid = uuid.toString();
		ItemTag item_tag =  new ItemTag(NBT_UUID,s_uuid);
		nbt_item.addTag(item_tag);
		
		item.setItemMeta(nbt_item.toItem().getItemMeta());
		
		
		
		switch(type){
			case 0: 
				inv = Bukkit.getServer().createInventory(this, size_smallest, "");
				break;
			case 1: 
				inv = Bukkit.getServer().createInventory(this, size_small, "");
				break;
			case 2: 
				inv = Bukkit.getServer().createInventory(this, size_normal, "");
				break;
			case 3: 
				inv = Bukkit.getServer().createInventory(this, size_big, "");
				break;
			case 4: 
				inv = Bukkit.getServer().createInventory(this, size_large, "");
				break;
			case 5: 
				inv = Bukkit.getServer().createInventory(this, size_biggest, "");
				break;
			case 6: 
				inv = Bukkit.getServer().createInventory(this, size_smallest, "");
				break;
		
		}
		
		
		
	}
	public ZBackpack(byte[] inf) {
		//Settings.debug(""+inf.length,"a");
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inf);
		
        
		try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			inv = Bukkit.getServer().createInventory(this, dataInput.readInt(), "");
			
			try {
				ZBackpackLoader loader = (ZBackpackLoader) dataInput.readObject();
				last_open = loader.last_open;
				created = loader.created;
				creator = loader.creator;
				uuid = UUID.fromString(loader.uuid);
				inv.setContents(loader.items);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			/*
			creator = dataInput.readUTF();
			uuid = UUID.fromString(dataInput.readUTF());
			try {
				last_open = (LocalDateTime) dataInput.readObject();
				created =(LocalDateTime) dataInput.readObject();
				
				inv.setContents((ItemStack[]) dataInput.readObject());
				
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			*/
			
			/*
			for (int i = 0; i < inv.getSize(); i++) {
	            try {
	            	Object obj = dataInput.readObject();
	            	ItemStack item = (ItemStack) obj;
	            	inv.setItem(i, item);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        */
			dataInput.close();
			inputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
        
        
	}
	
	
	public ZBackpackLoader toLoader() {
		return new ZBackpackLoader(uuid.toString(),creator,created,last_open,inv.getContents(),inv.getSize());
	}
	public byte[] toByte(){

		
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			
			
			dataOutput.writeInt(inv.getSize());
			dataOutput.writeObject(this.toLoader());
		
			
			
			/*
			for(int i = 0;i<inv.getSize();i++) {
				ItemStack item = inv.getItem(i);
				dataOutput.writeObject(item);
				
			}
			*/
			/*
			dataOutput.writeObject(inv.getContents());
			dataOutput.writeObject(created);
			dataOutput.writeUTF(uuid.toString());
			dataOutput.writeUTF(creator);
			dataOutput.writeObject(last_open);*/
			bytes = outputStream.toByteArray();
			Settings.debug("{ZBP} bytes size on save = "+bytes.length, size_big);
			dataOutput.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return bytes;
	}
	public UUID getUUID() {
		return uuid;
	}
	public String getStringUUID() {
		return uuid.toString();
	}
	
	
	public void updateDate(LocalDateTime new_time) {
		last_open = new_time;
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
	public String getCreateDate() {
		return created.format(formatter);
	}

	public String getLastDate() {
		return last_open.format(formatter);
	}
	
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}
	
}
