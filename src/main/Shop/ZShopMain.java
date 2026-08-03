package main.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class ZShopMain {
	
	
	
	List<ZShopInventory> pages = new ArrayList<ZShopInventory>();
	int id = 0;
	public int npc = 1;
	String perm;
	public String name = "shop";
	boolean need_perm = false;
	String perm_desc;
	public ZShopMain(ConfigurationSection c,int id) {
		this.id = id;
		
		
		
		if(c.contains("name")&&c.isString("name")) {
			name = c.getString("name");
		}else {
			Settings.debug("Нет имени в магазине "+c.getCurrentPath());
		}
		if(c.contains("npc")&&c.isInt("npc")) {
			
			npc = c.getInt("npc");
		}else {
			Settings.debug("Нет npc в магазине "+c.getCurrentPath());
		}
		if(c.contains("sdelki")&&c.isConfigurationSection("sdelki")) {
			ConfigurationSection pages_conf = c.getConfigurationSection("sdelki");
			
			Set<String> keys = pages_conf.getKeys(false);
			int page_id = 0;
			for(String key : keys) {
				ConfigurationSection page_conf = pages_conf.getConfigurationSection(key);
				
				Set<String> page_keys = page_conf.getKeys(false);
				
				List<ZShopInventory> multi_pages = new ArrayList<ZShopInventory>();
				int her = 0;
				List<TradeSlot> slots = new ArrayList<TradeSlot>();
				List<TradeSlot> multi_slots = new ArrayList<TradeSlot>();
				int multi_her = 0;
				for(String pg_key : page_keys) {
					TradeSlot slot = new TradeSlot(page_conf.getConfigurationSection(pg_key),id);
					if(slot.is_multi) {
						slot.uid = multi_her;
						multi_slots.add(slot);
						multi_her++;
						if(multi_her>=5) {
							multi_pages.add(new ZShopInventory(multi_slots,name,true));
							multi_slots = new ArrayList<TradeSlot>();
							multi_her = 0;
						}
					}else {
						slots.add(slot);
						slot.setId(her);
						her++;
						if(her>=10) {
							pages.add(new ZShopInventory(slots,name,false));
							page_id++;
							slots = new ArrayList<TradeSlot>();
							her = 0;
						}
					}
					
					
					
				}
				if(!slots.isEmpty()) {
					pages.add(new ZShopInventory(slots,name,false));
					page_id++;
				}
				if(!multi_slots.isEmpty()) {
					multi_pages.add(new ZShopInventory(multi_slots,name,true));
					page_id++;
				}
				if(!multi_pages.isEmpty()) {
					pages.addAll(multi_pages);
					
					
				}
				
				
				 
				
			}
			int huy = 0;
			for(ZShopInventory inv : pages) {
				inv.id=huy;
				inv.updateSlotIds();
				inv.createSelf(inv.isMulti());
				huy++;
			}
			
			if(!pages.isEmpty()&&pages.size()>1) {
				for(ZShopInventory page : pages) {
					page.addPointer(pages.size());
				}
			}
			
			
			
			

			//Settings.debug("Кол-во слотов в  "+ name+" при загрузке : "+slots.size());
		}else{

			Settings.debug("Нет страниц в магазине "+c.getCurrentPath());
		}
		if(c.contains("perm")&&c.isString("perm")) {
			this.perm = c.getString("perm");
			need_perm = true;
		}else if(c.contains("permission")&&c.isString("permission")) {
			this.perm = c.getString("permission");
			need_perm = true;
		}

		if(need_perm&&c.contains("perm_desc")&&c.isString("perm_desc")) {
			this.perm_desc = c.getString("perm_desc");
		}else if(need_perm&&c.contains("permission_description")&&c.isString("permission_description")) {
			this.perm_desc = c.getString("permission_description");
		}
		
		
	
	}
	
	public int getNPC() {
		return npc;
	}
	
	public boolean needPerm() {
		return need_perm;
	}
	public ZShopInventory getFirstPage() {
		return pages.get(0);
	}
	
	public List<ZShopInventory> getPages(){
		return pages;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	

}
