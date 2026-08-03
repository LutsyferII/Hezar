package main.Futh.LootBoxMechanic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import main.Settings;
import main.Futh.ZItem;

import java.util.Set;

public class LootBox {
	String name;
	List<LootSlot> slots = new ArrayList<LootSlot>();
	static Random rand = new Random();
	
	String perm;
	boolean need_perm = false;
	
	public LootBox(String nm,Collection<ZItem> zitems) {
		this.name = nm;
		
		LinkedHashMap<ZItem,Double> items = new LinkedHashMap<ZItem,Double>();
		for(ZItem item : zitems) {
			items.put(item,item.getRawChance());
		}
		Double all_chance = 0.0;
		for(Double chance : items.values()) {
			all_chance += chance;
		}
		if(all_chance!=100.0) {
			Double mnoz = 100.0/all_chance;
			Double from = 0.0;
			for(Entry<ZItem,Double> entry : items.entrySet()) {
				Double new_val = entry.getValue()*mnoz;
				entry.setValue(new_val );
				
				slots.add(new LootSlot(from,from+new_val ,entry.getKey()));
				from +=new_val;
			}
		}else {
			Double from = 0.0;
			for(Entry<ZItem,Double> entry : items.entrySet()) {
				Double val = entry.getValue();
				slots.add(new LootSlot(from,from+val ,entry.getKey()));
				from +=val;
			}
			
		}
		
		
		
		
	}
	
	public void setPermission(String p) {
		this.perm = p;
		need_perm = true;
	}
	public boolean needPerm() {
		return need_perm;
	}
	
	public String getPermission() {
		return perm;
	}
	
	public ZItem rollItems() {
		
		Double pos = rand.nextDouble(0.0, 100.0);
		for(LootSlot slot : slots) {
			Settings.debug("{LOOTBOX} Значение = "+pos+" | SLOT: FROM = "+slot.from+" | TO = " +slot.to, 6);
			if(slot.isNeeded(pos)) {
				return slot.getItem();
			}
		}
		
		
		
		
		return null;
	}
	
	
	
}
