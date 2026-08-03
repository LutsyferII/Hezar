package main.backpack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import main.Settings;
import main.hezar;

public class Backpack  implements InventoryHolder{

	Inventory inv;
	static final int size_smallest = 9;
	static final int size_small = 18;
	static final int size_normal = 27;
	static final int size_big = 36;
	static final int size_large = 45;
	static final int size_biggest = 54;
	
	
	ItemStack hold;
	
	public Backpack(Player p) {
		p.getInventory().getHolder();
		inv = Bukkit.createInventory(this, 54, "Ðþêçàê");
		
	}
	public Backpack(ItemStack item) {
		hold = item;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer data =meta.getPersistentDataContainer();
		NamespacedKey data_key =  new NamespacedKey(hezar.inst, "ZBACKPACK_DATA");
		if(data.has(data_key, PersistentDataType.BYTE_ARRAY)) {
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(data.get(data_key, PersistentDataType.BYTE_ARRAY) );
	            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
				inv = Bukkit.getServer().createInventory(this, dataInput.readInt(), "Ðþêçàê");
				/*try {
					inv.setContents((ItemStack[]) dataInput.readObject());
				} catch (IllegalArgumentException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				for (int i = 0; i < inv.getSize(); i++) {
	                try {
	                	inv.setItem(i,(ItemStack) dataInput.readObject());
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
				dataInput.close();
				inputStream.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {

			net.minecraft.world.item.ItemStack nbt_item  = CraftItemStack.asNMSCopy(item);
			if(nbt_item.u().e("ZBACKPACK_TYPE")) {
				int type = nbt_item.u().h("ZBACKPACK_TYPE");
				Settings.debug("{BACKPACK} TYPE = "+type, 6);
				if(type==0) {
					inv = Bukkit.getServer().createInventory(this, size_smallest, "Ðþêçàê");
				}else if(type==1) {
					inv = Bukkit.getServer().createInventory(this, size_small, "Ðþêçàê");
				}else if(type==2) {
					inv = Bukkit.getServer().createInventory(this, size_normal, "Ðþêçàê");
				}else if(type==3) {
					inv = Bukkit.getServer().createInventory(this, size_big, "Ðþêçàê");
				}else if(type==4) {
					inv = Bukkit.getServer().createInventory(this, size_large, "Ðþêçàê");
				}else if(type==5) {
					inv = Bukkit.getServer().createInventory(this, size_biggest, "Ðþêçàê");
				}else {
					inv = Bukkit.getServer().createInventory(this, size_smallest, "Ðþêçàê");
					
				}
			}
			
		}
		
	}
	public void saveTags() {

		 try {
			 Settings.debug("{BACKPACK} Ñîõðàíåíèå â ïðåäìåò "+hold.getItemMeta().getDisplayName() );
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeInt(inv.getSize());
			for(int i = 0;i<inv.getSize();i++) {
				dataOutput.writeObject(inv.getItem(i));
			}
			byte[] datas = outputStream.toByteArray();
			ItemMeta meta = hold.getItemMeta();
			PersistentDataContainer data =meta.getPersistentDataContainer();
			NamespacedKey data_key =  new NamespacedKey(hezar.inst, "ZBACKPACK_DATA");
			meta.getPersistentDataContainer().set(data_key,PersistentDataType.BYTE_ARRAY, datas);
			hold.setItemMeta(meta);
			 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveTags(ItemStack item) {

		 try {
		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeInt(inv.getSize());
			dataOutput.writeObject(inv.getContents());
			byte[] datas = outputStream.toByteArray();
			ItemMeta meta = item.getItemMeta();
			PersistentDataContainer data =meta.getPersistentDataContainer();
			NamespacedKey data_key =  new NamespacedKey(hezar.inst, "ZBACKPACK_DATA");
			data.set(data_key,PersistentDataType.BYTE_ARRAY, datas);
			hold.setItemMeta(meta);
			 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ItemStack getItem() {
		return hold;
	}
	
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}
	
	
}
