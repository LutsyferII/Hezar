package main.reitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Configs.Errors;
import Configs.ReItemConfig;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import main.Settings;
import main.ZarjTags;
import main.Futh.Lore;

public class ReItemListener implements Listener  {


	@EventHandler
	public void InventoryClick(InventoryClickEvent e){
		if(e.isCancelled()||e.getAction()==InventoryAction.NOTHING||e.getCurrentItem()==null||e.getCursor()==null) {
			
			return;
		}
		if(e.getClick()==ClickType.RIGHT) {
			ItemStack reitemer = e.getCursor();
			NBTItem reitemer_nbt = NBTItem.get(reitemer);
			if(!reitemer_nbt.hasTag(ZarjTags.ZARJ_REITEM_ID.name())) {
				return;
			}
			e.setCancelled(true);
			String id = reitemer_nbt.getString(ZarjTags.ZARJ_REITEM_ID.name());
			
			
			
			ReItemer itemer = ReItemConfig.getReItemer(id);
			if(itemer==null) {
				Errors.error("{ReItemer} не существует такого трансформатора предметов!");
				return;
			}
			ItemStack from = itemer.from.getItemStack();
			ItemStack current = e.getCurrentItem(); 
			if(!from.isSimilar(current)) {
				e.getWhoClicked().sendMessage("§4Неверный предмет трансформации!");
				return;
			}
			
			if(current.getAmount()==from.getAmount()) {
				e.setCurrentItem(itemer.to.getItemStack());
				if(reitemer_nbt.hasTag(ZarjTags.ZARJ_REITEM_CONS.name())) {
					//Settings.debug("Найден тэг", 2);
					int cons = reitemer_nbt.getInteger(ZarjTags.ZARJ_REITEM_CONS.name());
					cons--;
					if(cons<1) {
						e.getCursor().setAmount(0);
					}else {
						ItemTag tag = new ItemTag(ZarjTags.ZARJ_REITEM_CONS.name(), cons);
						reitemer_nbt.addTag(tag);
						ItemMeta meta = reitemer_nbt.toItem().getItemMeta();
						List<String> new_lore = new ArrayList<>();
						for(String s :meta.getLore()) {
							if(s.contains(Lore.getLore("cons"))) {
								//Settings.debug("Найдена нужная строка в лоре", 2);
								s = Lore.getLore("cons")+cons;
							}
							new_lore.add(s);
						}
						meta.setLore(new_lore);
						reitemer.setItemMeta(meta);
					}
					
					
					
					
				}
			}else if(current.getAmount()>from.getAmount()){
				if(e.getInventory().firstEmpty()!=-1||haveEnoughSpace(e.getClickedInventory(),itemer.to.getItemStack(),itemer.to.amount)) {
					
					e.getClickedInventory().addItem(itemer.to.getItemStack());
					current.setAmount(current.getAmount()-from.getAmount());
					if(reitemer_nbt.hasTag(ZarjTags.ZARJ_REITEM_CONS.name())) {
						int cons = reitemer_nbt.getInteger(ZarjTags.ZARJ_REITEM_CONS.name());
						cons--;
						if(cons<1) {
							e.getCursor().setAmount(0);
						}else {
							ItemTag tag = new ItemTag(ZarjTags.ZARJ_REITEM_CONS.name(), cons);
							reitemer_nbt.addTag(tag);
							ItemMeta meta = reitemer_nbt.toItem().getItemMeta();
							List<String> new_lore = new ArrayList<>();
							for(String s :meta.getLore()) {
								if(s.contains(Lore.getLore("cons"))) {
									//Settings.debug("Найдена нужная строка в лоре", 2);
									s = Lore.getLore("cons")+cons;
								}
								new_lore.add(s);
							}
							meta.setLore(new_lore);
							reitemer.setItemMeta(meta);
						}
					}
				}
			}else {
				e.getWhoClicked().sendMessage("§4не хватает предметов для трансформации!");
				return;
			}
			
			
		}
	}
	private boolean haveEnoughSpace(Inventory inv,ItemStack item, int am) {
		int have = 0;
		for(ItemStack item_inv: inv.getStorageContents()) {
			if(item_inv==null) {
				return true;
			}
			if(item_inv.isSimilar(item)) {
				have+=item_inv.getMaxStackSize()-item_inv.getAmount();
				if(have>=am) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
