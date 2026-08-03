package main.Sorter;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import main.Settings;
import main.hezar;
import main.Futh.StaticItem;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class SorterListener implements Listener  {
	
	@EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent e){
		
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest){
            Inventory inv = e.getInventory();
            int slot_num = 26;
            if(inv.getHolder() instanceof DoubleChest) {
            	slot_num = 53;
            }
            if(inv.getItem(slot_num)==null) {
            	ItemStack drop_to_chest = StaticItem.DROP_TO_CHEST.toItem();
            	inv.setItem(slot_num, drop_to_chest);
            }
            
            
        }
        
    }

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest){
            Inventory inv = e.getInventory();
            int slot_num = 26;
            if(inv.getHolder() instanceof DoubleChest) {
            	slot_num = 53;
            }
            ItemStack item = inv.getItem(slot_num);
            ItemMeta meta = item.getItemMeta();
    		NamespacedKey drop_key = new NamespacedKey(hezar.inst, "DROP_TO_CHEST");
            if(meta.getPersistentDataContainer().has(drop_key,  PersistentDataType.INTEGER)) {
            	inv.clear(slot_num);
            }
            
            
        }
	}
	
	@EventHandler
	public void onPlayerInteract(InventoryClickEvent e) {
		InventoryAction act = e.getAction();
		if(act== InventoryAction.NOTHING||e.isCancelled()||e.getCurrentItem()==null||(!(e.getClickedInventory().getHolder() instanceof Chest)&&!(e.getClickedInventory().getHolder() instanceof DoubleChest))) {
			return;
		}
		ItemStack check = e.getCurrentItem();
		ItemMeta meta = check.getItemMeta();

		NamespacedKey drop_key = new NamespacedKey(hezar.inst, "DROP_TO_CHEST");
		if(meta == null ||!meta.getPersistentDataContainer().has(drop_key, PersistentDataType.INTEGER)) {
			return;
		}else {
			e.setCancelled(true);
			Settings.debug("{SORTER} ClickedInv size = "+e.getClickedInventory().getSize(), 6); ;
			Settings.debug("{SORTER} Inv size = "+e.getInventory().getSize(), 6); 
			Settings.debug("{SORTER} Player Inv size = "+e.getWhoClicked().getInventory().getSize(), 6); ;
			Inventory chest = e.getClickedInventory();
			PlayerInventory inv = e.getWhoClicked().getInventory();
			if(e.getClick()==ClickType.LEFT) {
				for(ItemStack item : inv.getStorageContents()) { 
					if(item==null) {
						continue;
					}
					for(ItemStack in_item: chest.getContents()) {
						if(item==null) {
							break;
						}
						if(in_item==null) {
							continue;
						}
						if(in_item.isSimilar(item)) {
							if(in_item.getMaxStackSize()==1||in_item.getMaxStackSize()<=in_item.getAmount()) {
								continue;
							}
							int can_drop = in_item.getMaxStackSize()-in_item.getAmount(); //32 25 20 15 10
							Settings.debug("{SORTER} can_drop = "+can_drop, "c");
							Settings.debug("{SORTER} item amount = "+item.getAmount(), "c");
							if(can_drop>=item.getAmount()) {// 7 5 5 5 64
								can_drop-=item.getAmount();// 25 20 15 10
								Settings.debug("{SORTER} can_drop after decr = "+can_drop, "c");
								Settings.debug("{SORTER} in_item amount before = "+in_item.getAmount(), "c");
								in_item.setAmount(in_item.getAmount()+item.getAmount());
								Settings.debug("{SORTER} in_item amount after = "+in_item.getAmount(), "c");
								//chest.addItem(item); //39 44 49 54
								//in_item.setAmount(in_item.getAmount() + can_drop);
								inv.removeItem(item);
								item =null;
								break;
							}else { 
								//can_drop = item.getAmount() - can_drop;
								item.setAmount(item.getAmount()-can_drop); //54
								in_item.setAmount(in_item.getAmount()+can_drop); //64  
							}
						}
					}
				}
			}else if(e.getClick()==ClickType.SHIFT_LEFT) {
				Settings.debug("{SORTER} ШИФТ РОБИТ!", 6);

				for(ItemStack item : inv.getStorageContents()) {
					if(item==null) {
						continue;
					}
					boolean has_sim = false;
					int has_space = 0;
					
					for(ItemStack in_item: chest.getContents()) {
						
						if(item==null) {
							break;
						}
						
						if(in_item==null) {
							has_space++;
							continue;
						}else if(in_item!=null&&in_item.isSimilar(item)) {
							if(in_item.getMaxStackSize()==1||in_item.getMaxStackSize()<=in_item.getAmount()) {
								has_sim = true;
								continue;
							}
							int can_drop = in_item.getMaxStackSize()-in_item.getAmount(); //62
							if(can_drop>=item.getAmount()) {
								can_drop-=item.getAmount();//50
								in_item.setAmount(in_item.getAmount()+item.getAmount());
								
								//chest.addItem(item);
								//in_item.setAmount(in_item.getAmount() + can_drop);
								inv.removeItem(item);
								item =null;
								has_sim = false;
								
							}else {
								//can_drop = item.getAmount() - can_drop;
								item.setAmount(item.getAmount()-can_drop);
								in_item.setAmount(in_item.getAmount()+can_drop);
								has_sim = true;
							}
						}
					}
					if(item!=null&&has_sim&&has_space>0) {
						Settings.debug("{SORTER} Доработка!", 6);
						chest.addItem(item);
						inv.remove(item);
						item=null;
					}
					
					
				}
			}
			Settings.debug("{SORTER} Дроп завершён!", 6);
		}
		
		
	}
	static void giveItems(Inventory inv,ItemStack item,int amount) {
		int am = amount;
		int max_size = item.getMaxStackSize();
		int raz_to =  am/max_size;
		
		if(am%max_size!=0) {
			raz_to++;
		}
		
		for(int i = 0;i<raz_to;i++) {
			int now_am_to = max_size;
			if( am<max_size) {
				now_am_to= am;
			}
    		ItemStack to = new ItemStack(item);
    		to.setAmount(now_am_to);
    		Settings.debug("Хуета получается(2): "+to.getAmount());
    		inv.addItem(to);
    		
    		am-=max_size;
		}
	}
	void removeVanillaItem(PlayerInventory pi, ItemStack im, int amount) {

		int am = amount;
		for (ItemStack item :pi.getStorageContents()) {
			if(item==null) {
				continue;
			}
			if(item.isSimilar(im)) {
				int itemam = item.getAmount();
				if(am<=0) {
					break;
				}
				if(itemam<=am) {
					pi.removeItem(item);
					am-=itemam;
				}else{
					item.setAmount(itemam-am);
					break;
				}
				if(am<=0) {
					break;
				}
			}
			
			
		}
	}
	
	List<ItemStack> removeItemWithNBT(PlayerInventory pi,int amount, String s,String type) {
		int am = amount;
		List<ItemStack> deleted = new ArrayList<>();;
		
		for (ItemStack item :pi.getStorageContents()) {
			if(item==null) {
				continue;
				
			}
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			
			if(nbtbase.e("MMOITEMS_ITEM_TYPE")&&nbtbase.c("MMOITEMS_ITEM_TYPE").e_().equals(type)) {
				if(nbtbase.e("MMOITEMS_ITEM_ID")&&nbtbase.c("MMOITEMS_ITEM_ID").e_().equals(s)) {
					int itemam = item.getAmount();
					Settings.debug("Тест, хуест.["+"] am="+am+" кол-во в стаке="+itemam);
					if(am<=0) {
						break;
					}
					if(itemam<=am) {
						pi.removeItem(item);
						deleted.add(item);
						am-=itemam;
					}else{
						item.setAmount(itemam-am);
						ItemStack deleted_item = new ItemStack(item);;
						deleted_item.setAmount(am);
						deleted.add(deleted_item);
						
						break;
					}
					if(am<=0) {
						break;
					}
						
					
				
				}
			}
			
		}
		return deleted;
	}
	
}
