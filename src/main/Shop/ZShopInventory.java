package main.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import main.Settings;
import main.hezar;
import main.Futh.StaticItem;
import main.UI.ZInventory;

public class ZShopInventory extends ZInventory{
	
	
	
	Inventory inv= Bukkit.createInventory(this, 54, "Клан");
	public int id = 0;
	boolean multi = false;
	List<TradeSlot> slots = new ArrayList<TradeSlot>();
	
	public ZShopInventory(List<TradeSlot> slotes,String shop_name,boolean is_multi) {
		slots = new ArrayList<TradeSlot>();
		inv= Bukkit.createInventory(this, 54, shop_name);
		for(int i = 0;i<inv.getSize();i++) {
			inv.setItem(i, StaticItem.FILL.toItem());
		}
		slots = slotes;
		multi = is_multi;
		//createSelf(is_multi);
		Settings.debug("Создана страница для "+shop_name+" с id "+id);
		//slots.
	}
	
	public void updateSlotIds() {
		for(TradeSlot slot: slots) {
			slot.page_id = this.id;
			
		}
	}
	
	public void addPointer(int colvo) {
		if(colvo>1) {
			
			if(id<colvo-1) {

				ItemStack item = StaticItem.SHOP_NEXT.toItem(id);
				/*ItemMeta  meta = item.getItemMeta();
				NamespacedKey nkey = new NamespacedKey(hezar.inst, "SHOP_NEXT");
				NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
				meta.setDisplayName("§aСледующая страница");
				meta.getPersistentDataContainer().set(nkey,PersistentDataType.INTEGER, 1);
				meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, id);
				item.setItemMeta(meta);*/
				inv.setItem(53, item);
				
			}
			if(colvo>1&&id!=0) {
				ItemStack item = StaticItem.SHOP_BACK.toItem(id);
				/*
				ItemMeta  meta = item.getItemMeta();
				NamespacedKey nkey = new NamespacedKey(hezar.inst, "SHOP_BACK");
				NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
				meta.setDisplayName("§aПредыдущая страница");
				meta.getPersistentDataContainer().set(nkey,PersistentDataType.INTEGER, 1);
				meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, id);
				item.setItemMeta(meta);*/
				inv.setItem(45, item);
			}
		}
	}
	public void createSelf(boolean is_multi) {
		
		
		
		ItemStack pointer = StaticItem.POINTER.toItem();
		/*ItemMeta mt = pointer.getItemMeta();
		mt.setCustomModelData(1);
		mt.setDisplayName("§6Нажмите на товар для заключения сделки.");
		List<String> lr = new ArrayList<>();
		if(mt.getLore()!=null) {
			lr.addAll(lr);
		}
		lr.add("§cДля заключения максимально возможного кол-ва сделок нажмите Shift+ПКМ на товар.");
		mt.setLore(lr);
		pointer.setItemMeta(mt);*/
		Settings.debug("Создание инвентаря: ");
		Settings.debug("Общее кол-во слотов = "+this.slots.size());
		int num = 0;
		int raz = 1;
		//Settings.debug("Кол-во слотов в  "+ name+" : "+this.slots.size());
		NamespacedKey slotkey = new NamespacedKey(hezar.inst, "SLOT_ID");
		NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
		NamespacedKey shopkey = new NamespacedKey(hezar.inst, "SHOP_ID");
		NamespacedKey pricekey = new NamespacedKey(hezar.inst, "SHOP_PRICE");
		NamespacedKey moneykey = new NamespacedKey(hezar.inst, "SHOP_MONEY");
		NamespacedKey shiftkey = new NamespacedKey(hezar.inst, "SHOP_SHIFT");
		
		if(is_multi) {
			int slot_from_line = 3;
			int slot_to_line = 5;
			int slot_id = 0;
			for(TradeSlot slot : this.slots) {
				slot.setPageId(id);
				slot.setId(slot_id);
				slot_id++;
				Settings.debug("Раз = "+raz);
				if(slot.from.isEmpty()) {
					ItemStack huy = new ItemStack(Material.GOLD_NUGGET);
					ItemMeta  meta = huy.getItemMeta();
					meta.setCustomModelData(1012);
					meta.setDisplayName("§6Деньги: "+slot.price);
					huy.setItemMeta(meta);
					this.inv.setItem(slot_from_line+((raz-1)*9), huy);
				}else {
					int index_from = 0;
					for(ItemStack item : slot.from) {
						this.inv.setItem(slot_from_line-index_from+((raz-1)*9), item);
						index_from++;
						if(index_from>=3) {
							break;
						}
					}
				}
				this.inv.setItem(4+((raz-1)*9), pointer);
				if(slot.to.isEmpty()) {
					
					ItemStack huy = new ItemStack(Material.GOLD_NUGGET);
					ItemMeta  meta = huy.getItemMeta();
					meta.setCustomModelData(1010);
					meta.setDisplayName("§6Деньги: "+slot.money);

					meta.getPersistentDataContainer().set(slotkey,PersistentDataType.INTEGER, slot.uid);
					meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, slot.page_id);
					meta.getPersistentDataContainer().set(shopkey,PersistentDataType.INTEGER, slot.shop_id);
					meta.getPersistentDataContainer().set(pricekey,PersistentDataType.DOUBLE, slot.price);
					meta.getPersistentDataContainer().set(moneykey,PersistentDataType.DOUBLE, slot.money);
					
					huy.setItemMeta(meta);
					
					this.inv.setItem(slot_to_line+((raz-1)*9), huy);
				}else {
					int index_to = 0;
					for(ItemStack item : slot.to) {
						ItemStack huy = new ItemStack(item);
						ItemMeta  meta = huy.getItemMeta();
						if(!slot.can_shift) {
							meta.getPersistentDataContainer().set(shiftkey, PersistentDataType.BYTE, (byte)1);
							Settings.debug("{SLOT}  CAN'T BY!", "c");
						}
						meta.getPersistentDataContainer().set(slotkey,PersistentDataType.INTEGER, slot.uid);
						meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, slot.page_id);
						meta.getPersistentDataContainer().set(shopkey,PersistentDataType.INTEGER, slot.shop_id);
						meta.getPersistentDataContainer().set(pricekey,PersistentDataType.DOUBLE, slot.price);
						meta.getPersistentDataContainer().set(moneykey,PersistentDataType.DOUBLE, slot.money);
						huy.setItemMeta(meta);
						this.inv.setItem(slot_to_line+index_to+((raz-1)*9), huy);
						index_to++;
						if(index_to>=3) {
							break;
						}
					}
				}
				
				raz++;
				if(raz>=6) {
					break;
				}
				
			}
			
			
			
		}else{
			int slot_id = 0;
			for(TradeSlot slot : this.slots) {
				Settings.debug("Раз = "+raz);
				slot.setPageId(id);
				slot.setId(slot_id);
				slot_id++;
				if(!slot.from.isEmpty()) {
					this.inv.setItem(0+num, slot.from.get(0));
					
				}else {
					ItemStack huy = new ItemStack(Material.GOLD_NUGGET);
					ItemMeta  meta = huy.getItemMeta();
					meta.setCustomModelData(1012);
					meta.setDisplayName("§6Деньги: "+slot.price);
					huy.setItemMeta(meta);
					this.inv.setItem(0+num, huy);
					
					
				}
				
				this.inv.setItem(1+num, pointer);
				
				if(slot.to.isEmpty()) {
					
					ItemStack huy = new ItemStack(Material.GOLD_NUGGET);
					ItemMeta  meta = huy.getItemMeta();
					meta.setCustomModelData(1010);
					meta.setDisplayName("§6Деньги: "+slot.money);

					meta.getPersistentDataContainer().set(slotkey,PersistentDataType.INTEGER, slot.uid);
					meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, slot.page_id);
					meta.getPersistentDataContainer().set(shopkey,PersistentDataType.INTEGER, slot.shop_id);
					meta.getPersistentDataContainer().set(pricekey,PersistentDataType.DOUBLE, slot.price);
					meta.getPersistentDataContainer().set(moneykey,PersistentDataType.DOUBLE, slot.money);
					
					huy.setItemMeta(meta);
					
					this.inv.setItem(2+num, huy);
				}else {
					ItemStack huy = new ItemStack(slot.to.get(0));
					ItemMeta  meta = huy.getItemMeta();
					if(!slot.can_shift) {
						meta.getPersistentDataContainer().set(shiftkey, PersistentDataType.BYTE, (byte)1);
						Settings.debug("{SLOT}  CAN'T BY!", "c");
					}

					meta.getPersistentDataContainer().set(slotkey,PersistentDataType.INTEGER, slot.uid);
					meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, slot.page_id);
					meta.getPersistentDataContainer().set(shopkey,PersistentDataType.INTEGER, slot.shop_id);
					meta.getPersistentDataContainer().set(pricekey,PersistentDataType.DOUBLE, slot.price);
					meta.getPersistentDataContainer().set(moneykey,PersistentDataType.DOUBLE, slot.money);
					huy.setItemMeta(meta);
					this.inv.setItem(2+num, huy);
				}
				if((raz & 1) == 0) {
					num+=3;
				}else {
					num+=6;
				}
				//Settings.debug("Cлот "+raz+" создан");
				raz++;
				if(raz>=11) {
					break;
				}
				
			}
		}
		
		
		
		
		

		Settings.debug("___конец создания инвентаря___");

	}
	
	public boolean isMulti() {
		return multi;
	}
	
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inv;
	}
	

}
