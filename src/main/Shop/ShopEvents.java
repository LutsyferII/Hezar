package main.Shop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import main.Settings;
import main.hezar;
import main.Futh.ZItem;
import main.Utils.ZEconomy;
import net.Indyuce.mmoitems.MMOItems;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.nbt.NBTTagCompound;


public class ShopEvents implements Listener{
	@EventHandler
	public void InventoryClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();  
		boolean is_shop=false;
		
		//ZShopManager.
		
		if(!(e.getInventory().getHolder() instanceof ZShopInventory)) {
			return;
		}else {
			e.setCancelled(true);
			is_shop = true;
		}
		
        //for(ZShopInventory shop : ZShopManager.shops) {
        	//if(e.getInventory().equals(shop.getInventory())){
        		//is_shop=true;
        		//shop_inv=shop;
        		//break;
        	//}
        	//num_shop++;
       // }
        //if(!is_shop) {
       // 	return;
        //}
		if(e.getCurrentItem()==null) {
        	return;
        }
		boolean is_shift_right = false;
		if(e.isShiftClick() && e.isRightClick()) {
			is_shift_right = true;
		}
		
        ItemStack clicked_i = e.getCurrentItem();
		NamespacedKey slotkey = new NamespacedKey(hezar.inst, "SLOT_ID");
		NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
		NamespacedKey shopkey = new NamespacedKey(hezar.inst, "SHOP_ID");
		NamespacedKey pricekey = new NamespacedKey(hezar.inst, "SHOP_PRICE");
		NamespacedKey moneykey = new NamespacedKey(hezar.inst, "SHOP_MONEY");
        NamespacedKey huykey = new NamespacedKey(hezar.inst, "SHOP_NEXT");
        NamespacedKey backkey = new NamespacedKey(hezar.inst, "SHOP_BACK");
		NamespacedKey shiftkey = new NamespacedKey(hezar.inst, "SHOP_SHIFT");
        
        if(clicked_i.getItemMeta().getPersistentDataContainer().has(slotkey,PersistentDataType.INTEGER)) {
        	Settings.debug("Охуеть");
        	int trade_id = clicked_i.getItemMeta().getPersistentDataContainer().get(slotkey,PersistentDataType.INTEGER);
        	int page_id = clicked_i.getItemMeta().getPersistentDataContainer().get(pagekey,PersistentDataType.INTEGER);
        	int shop_id = clicked_i.getItemMeta().getPersistentDataContainer().get(shopkey,PersistentDataType.INTEGER);
        	double price = clicked_i.getItemMeta().getPersistentDataContainer().get(pricekey,PersistentDataType.DOUBLE);
        	double money = clicked_i.getItemMeta().getPersistentDataContainer().get(moneykey,PersistentDataType.DOUBLE);
        	if(is_shift_right&&clicked_i.getItemMeta().getPersistentDataContainer().has(shiftkey, PersistentDataType.BYTE)) {
        		is_shift_right = false;
        		Settings.debug("{SHOP} IS OVER");
        	}
        	
        	
        	ZShopMain shop = ZShopManager.getShopById(shop_id);
        	
        	ZShopInventory page = shop.pages.get(page_id);
        	TradeSlot slot = page.slots.get(trade_id);
        	if(slot.need_perm&&slot.perm!=null) {
        		if(!p.hasPermission(slot.perm)) {
        			p.sendMessage("§4У вас нет прав это покупать!");
        			if(slot.perm_desc!=null) {
        				p.sendMessage(slot.perm_desc);
        			}
        			return;
        		}
        	}
        	
        	
        	
        	
        	
        	if(slot.from.isEmpty()) {
        		if(slot.to.isEmpty()) {
        			return;
        		}
        		if(price==0.0) {
	        		p.sendMessage("§4Жулик, не воруй!.");
        			return;
        		}
        		if(ZEconomy.economy.getBalance(p)<price) {
	        		p.sendMessage("§4Не хватает денег!.");
        			
        			return;
        			
        		}
        		int can_take = howMuchTrade(p.getInventory(),slot.to);
    			if(can_take==0) {
					p.sendMessage("Место бы не помешало");
					return;
				}
        		if(is_shift_right) {
        			int colvo_raz = (int)(ZEconomy.economy.getBalance(p)/price);;
        			
        			if(can_take<colvo_raz) {
        				colvo_raz=can_take;
        			}
        			
        			
        			
    	    		ZEconomy.economy.withdrawPlayer(p, price*colvo_raz);
    	        	if(money>0.0) {
    	            	ZEconomy.economy.depositPlayer(p, money*colvo_raz);
    	        	}
    	        	for(ZItem to:slot.zto) {
    	        		ItemStack item = to.getItemStack();
    	        		giveItems(p.getInventory(),item,item.getAmount()*colvo_raz);
	        		}
    	        	/*for(ItemStack to : slot.to) {
        	        	giveItems(p.getInventory(),to,to.getAmount()*colvo_raz);
    	        	}*/
        			
        		}else {
        			ZEconomy.economy.withdrawPlayer(p, price);
	        		if(money>0.0) {
	
	            		ZEconomy.economy.depositPlayer(p, money);
	        		}
	        		
	        		for(ZItem to:slot.zto) {
	        			p.getInventory().addItem(to.getItemStack());
	        		}
	        		/*
	        		for(ItemStack to:slot.to) {
	        			ItemStack gto = new ItemStack(to);
		        		p.getInventory().addItem(gto);
	        		}*/
        		}
        		
    			
    			
        		
        	}else { 
    			int trades = 0;
        		int skok_price = 0;
    			boolean need_price = false;
    			double p_balance = ZEconomy.economy.getBalance(p);
    			if(price>0.0) {
    				need_price = true;
        			if(p_balance<price) {
    	        		p.sendMessage("§4Не хватает денег!");
            			
            			return;
            		}else {
            			skok_price =(int)(p_balance/price);
            		}
    				
    			}
    			int can_sale = howMuchSale(p.getInventory(),slot.from);
    			boolean have_one_convert = false;
    			
    			if(can_sale==0) {
    				if(needMonets(slot.from)) {
    					if(!hasEnoughMonets(p.getInventory(),slot.from)) {
    						have_one_convert = canSaleWithConvert(p.getInventory(),slot.from);
    						if(have_one_convert) {
    							have_one_convert = canTradeWithConvert(p.getInventory(),slot);
    							if(!have_one_convert) {
    								p.sendMessage("§4Не хватает место под конвертацию!");
        	    					return;
    		    				}else {
    		    					trades = 1;
    		    				}
    							
    						}else {
    							p.sendMessage("§4Не хватает предметов!");
    	    					return;
    						}
    					}else {
    						p.sendMessage("§4Не хватает предметов!");
        					return;
    					}
        			}else {
        				p.sendMessage("§4Не хватает предметов!");
    					return;
        			}
    				
    			}else {
    				trades = can_sale;
    			}
    			if(need_price&&skok_price<trades) {
    				trades=skok_price;
    			}
    			
    			if(!slot.to.isEmpty()) {
    				Settings.debug("Колво предметов для");
    				int can_take = howMuchTrade(p.getInventory(),slot.to);
        			if(can_take==0) {
						p.sendMessage("Место бы не помешало");
						return;
					}
        			if(can_take<trades) {
        				trades=can_take;
        			}

	        		for(ZItem to:slot.zto) {
	        			ItemStack item = to.getItemStack();
	        			if(is_shift_right&&!have_one_convert) {
        					giveItems(p.getInventory(),item,item.getAmount()*trades);
        				}else {
        					p.getInventory().addItem(item);
        				}
	        		}
	        		/*
        			for(ItemStack im : slot.to) {
        				ItemStack item = new ItemStack(im);
        				
        				if(is_shift_right&&!have_one_convert) {
        					giveItems(p.getInventory(),item,item.getAmount()*trades);
        				}else {
        					p.getInventory().addItem(item);
        				}
        			}*/
    			}
    			if(have_one_convert) {
    				removeItemsWithConvert(p.getInventory(),slot.from);
    			}else {
    				for(ItemStack item : slot.from) {
    					if(is_shift_right) {
    						
            				removeVanillaItem(p.getInventory(), item, item.getAmount()*trades);
            				
        				}else {
        					removeVanillaItem(p.getInventory(), item, item.getAmount());
        					
        				}
    			}
    			}
    			
    			if(is_shift_right&&!have_one_convert) {
    				
    				if(need_price) {
						ZEconomy.economy.withdrawPlayer(p, price*trades);
					}
    				if(money>0.0) {
	                	ZEconomy.economy.depositPlayer(p, money*trades);
	        				
	        		}
    			}else {
    				if(need_price) {
						ZEconomy.economy.withdrawPlayer(p, price);
					}
    				if(money>0.0) {
	                	ZEconomy.economy.depositPlayer(p, money);
	        				
	        		}
    			}
    			return;
        	}
        }else if(clicked_i.getItemMeta().getPersistentDataContainer().has(huykey,PersistentDataType.INTEGER)) {
        	
        	int page_id = clicked_i.getItemMeta().getPersistentDataContainer().get(pagekey,PersistentDataType.INTEGER);
        	ZShopMain shop = ZShopManager.getShopByInv(e.getInventory());
        	ZShopInventory new_page = shop.pages.get(page_id+1);
        	p.openInventory(new_page.getInventory());
        	
        	
        }else if(clicked_i.getItemMeta().getPersistentDataContainer().has(backkey,PersistentDataType.INTEGER)) {
        	
        	int page_id = clicked_i.getItemMeta().getPersistentDataContainer().get(pagekey,PersistentDataType.INTEGER);
        	ZShopMain shop = ZShopManager.getShopByInv(e.getInventory());
        	ZShopInventory new_page = shop.pages.get(page_id-1);
        	p.openInventory(new_page.getInventory());
        	
        	
        	
        	
    		
    	
    }
        
        
        
		
		
		
        //ZClans.consoleSend("Успех. Открыт нужный инвентарь! "+e.getInventory());
        
        
        
       
		//e.getInventory()
        
		
		
	}
	
	
	
	
	
	@EventHandler
	void NPCShopEvent(NPCRightClickEvent e) {
		
		int npc_id = e.getNPC().getId();
		Settings.debug("Нажал на непися, его id = "+npc_id);
		if(!ZShopManager.checkNPC(npc_id)) {
			Settings.debug("Не тот id ");
			return;
		}
		ZShopMain shop = ZShopManager.getShopByNPC(npc_id);
		ZShopInventory shop_page = shop.getFirstPage();
		if(shop.needPerm()&&shop.perm!=null) {
    		if(!e.getClicker().hasPermission(shop.perm)) {
    			e.getClicker().sendMessage("§4У вас нет прав покупать в этом магазине!");
    			if(shop.perm_desc!=null) {
    				e.getClicker().sendMessage(shop.perm_desc);
    			}
    			return;
    		}
    	}
		e.getClicker().openInventory(shop_page.getInventory());
	}
	int howMuchTrade(PlayerInventory inv,List<ItemStack> items) {
		
		
		ItemStack[] inv_items = inv.getStorageContents();
		int free_space = 0;
		
		List<Dich> diches = new ArrayList<Dich>();
		
		for(ItemStack item:items) { // first = mech. 1-maxstack 0-size  | two 2 coins 64 -max_stack 28 - size
			int stack_max = item.getMaxStackSize();
			int this_size = 0;
			free_space = 0;
			for(ItemStack p_item : inv_items) {
				if(p_item==null||p_item.getType().isAir()) {
					free_space++;
					continue;
				}
				if(p_item.isSimilar(item)&&p_item.getAmount()<stack_max) {
					this_size+=stack_max-p_item.getAmount();
				}
			}
			Dich dich = new Dich(item,this_size,item.getAmount());
			diches.add(dich);
		}
		for(int i = 0;i<free_space;i++) {
			Dich dh = getSmallestDich(diches);
			dh.addSpace();
		}
		
		Dich dh = getSmallestDich(diches);
		
		
		int how_much =(int) dh.getCan();
		
		return how_much;
	}
	
	
	
	int howMuchSale(PlayerInventory inv, List<ItemStack> items) {
		
		List<Integer> all_cans= new ArrayList<>();
		for(ItemStack item : items) {
			
			int amount = getVanillaAmount(inv,item);
			amount= amount/item.getAmount();
			all_cans.add(amount);
		}
		
		int how_much = 0;
		if(all_cans.size()>1) {
			how_much =getSmallest(all_cans);
		}else if(all_cans.size()!=0) {
			how_much = all_cans.get(0);
		}
		
		return how_much;
	}
	
	
	private int getSmallest(List<Integer> all_can) {
		int can = 0;
		if(all_can.size()>1) {
			for(int i = 0;i<all_can.size();i++) {
				if(i==0) {
					can = all_can.get(i);
				}else {
					if(all_can.get(i)<can) {
						can=all_can.get(i);
					}
				}
			}
		}else if(!all_can.isEmpty()){
			can=all_can.get(0);
		}
		
		
		return can;
	}
	private double getSmallestDouble(List<Double> all_can) {
		double can = 0;
		if(all_can.size()>1) {
			for(int i = 0;i<all_can.size();i++) {
				if(i==0) {
					can = all_can.get(i);
				}else {
					if(all_can.get(i)<can) {
						can=all_can.get(i);
					}
				}
			}
		}else if(!all_can.isEmpty()){
			can=all_can.get(0);
		}
		
		
		return can;
	}
	private Dich getSmallestDich(List<Dich> all_can) {
		double can = 0;
		
		if(all_can.size()>1) {
			Dich huych = all_can.get(0);
			if(huych.getCan()==0.0) {
				return huych;
			}
			can = huych.getCan();
			for(int i = 0;i<all_can.size();i++) {
				if(all_can.get(i).getCan()<can) {
					huych=all_can.get(i);
					can=all_can.get(i).getCan();
				}
			}
			return huych;
		}else if(!all_can.isEmpty()){
			return all_can.get(0);
			//can=all_can.get(0).getCan();
		}
		return null;
	}
	void giveItems(PlayerInventory inv,ItemStack item,int amount) {
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
	
	int getMonets(PlayerInventory inv) {
		int monets = 0;
		String type = "MATERIAL";
		String cu = "CUCOIN";
		String ag = "SILCERCOIN";
		String au = "GOLDCOIN";
		for(ItemStack item : inv.getStorageContents()) {
			if(item==null) {
				continue;
			}
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			if(nbtbase.e("MMOITEMS_ITEM_ID")) {
				if(nbtbase.c("MMOITEMS_ITEM_TYPE").e_().equalsIgnoreCase(type)) {
					if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(cu)) {
						monets+=item.getAmount();
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(ag)) {
						monets+=(item.getAmount()*64);
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(au)) {
						monets+=(item.getAmount()*4096);
					}
				}
			}
		}
		return monets;
	}
	int getMonets(List<ItemStack> items) {
		int monets = 0;
		String type = "MATERIAL";
		String cu = "CUCOIN";
		String ag = "SILCERCOIN";
		String au = "GOLDCOIN";
		for(ItemStack item : items) {
			if(item==null) {
				continue;
			}
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			if(nbtbase.e("MMOITEMS_ITEM_ID")) {
				if(nbtbase.c("MMOITEMS_ITEM_TYPE").e_().equalsIgnoreCase(type)) {
					if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(cu)) {
						monets+=item.getAmount();
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(ag)) {
						monets+=(item.getAmount()*64);
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(au)) {
						monets+=(item.getAmount()*4096);
					}
				}
			}
		}
		return monets;
	}
	boolean haveMonetsOnOneTrade(PlayerInventory inv,int need) {
		int monets = 0;
		String type = "MATERIAL";
		String cu = "CUCOIN";
		String ag = "SILCERCOIN";
		String au = "GOLDCOIN";
		for(ItemStack item : inv.getStorageContents()) {
			if(item==null) {
				continue;
			}
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			if(nbtbase.e("MMOITEMS_ITEM_ID")) {
				if(nbtbase.c("MMOITEMS_ITEM_TYPE").e_().equalsIgnoreCase(type)) {
					if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(cu)) {
						monets+=item.getAmount();
						if(monets>=need) {
							return true;
						}
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(ag)) {
						monets+=(item.getAmount()*64);
						if(monets>=need) {
							return true;
						}
					}else if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equalsIgnoreCase(au)) {
						monets+=(item.getAmount()*4096);
						if(monets>=need) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	boolean canSaleWithConvert(PlayerInventory inv, List<ItemStack> from) {
		boolean can = false;
		ItemStack cu_coin =  MMOItems.plugin.getItem("MATERIAL", "CUCOIN");
		ItemStack ag_coin =  MMOItems.plugin.getItem("MATERIAL", "SILCERCOIN");
		ItemStack au_coin =  MMOItems.plugin.getItem("MATERIAL", "GOLDCOIN");
		int cu_amount = 0;
		int ag_amount = 0;
		int au_amount = 0;
		for(ItemStack item : inv.getStorageContents()) {// need 2 ag 8 cu    ahve 0 au 2 ag 1 cu
			if(item==null){
				continue;
			}
			if(item.isSimilar(cu_coin)) {
				cu_amount+=item.getAmount();
			}else if(item.isSimilar(ag_coin)) {
				ag_amount+=item.getAmount();
			}else if(item.isSimilar(au_coin)) {
				au_amount+=item.getAmount();
			}
		}
		for(ItemStack item : from) {
			if(item.isSimilar(cu_coin)) {
				cu_amount-=item.getAmount();
			}else if(item.isSimilar(ag_coin)) {
				ag_amount-=item.getAmount();
			}else if(item.isSimilar(au_coin)) {
				au_amount-=item.getAmount();
			}else {
				int have_amount = 0;
				for(ItemStack p_item : inv.getStorageContents()) {
					if(p_item==null){
						continue;
					}
					if(p_item.isSimilar(item)) {
						have_amount+=p_item.getAmount();
						if(have_amount>=item.getAmount()) {
							break;
						}
					}
				}
				if(have_amount<item.getAmount()) {
					return false;
				}
			}
		}
		if(cu_amount<0) {
			cu_amount+=64;
			ag_amount--;
		}
		if(ag_amount<0) {
			ag_amount+=64;
			au_amount--;
		}
		if(au_amount<0) {
			return false;
		}else {
			return true;
		}
	}
	boolean canTradeWithConvert(PlayerInventory inv, TradeSlot slot) {
		boolean have_to = false;
		if(slot.to!=null) {
			have_to=true;
		}
		List<ItemStack> from = slot.from;
		int need_monets = getMonets(from);
		if(getMonets(inv)<need_monets) {
			return false;
		}
		int free_space = 0;
		ItemStack cu_coin =  MMOItems.plugin.getItem("MATERIAL", "CUCOIN");
		ItemStack ag_coin =  MMOItems.plugin.getItem("MATERIAL", "SILCERCOIN");
		int have_cu = 0;
		int have_ag = 0;
		int need_cu = 0;
		int need_ag = 0;
		if(needMonets(from)) {
			for(ItemStack item : from) {
				if(item.isSimilar(cu_coin)) {
					need_cu+=item.getAmount();
				}else if(item.isSimilar(ag_coin)) {
					need_ag+=item.getAmount();
				}
			}
			
		}
		if(!have_to) {
			if(needMonets(from)) {
				for(ItemStack p_item : inv.getStorageContents()) {
					if(p_item==null||p_item.getType().isAir()) {
						free_space++;
						if(free_space>5) {
							return true;
						}
						continue;
					}
					if(p_item.isSimilar(cu_coin)) {
						have_cu+=p_item.getAmount();
					}else if(p_item.isSimilar(ag_coin)) {
						have_ag+=p_item.getAmount();
					}
				}
			}else {
				return true;
			}
			
		}else {
			List<Dich> diches = new ArrayList<Dich>();
			for(ItemStack item:slot.to) { // first = mech. 1-maxstack 0-size  | two 2 coins 64 -max_stack 28 - size
				int stack_max = item.getMaxStackSize();
				if(item.isSimilar(cu_coin)) {
					need_cu+=item.getAmount();
				}else if(item.isSimilar(ag_coin)) {
					need_ag+=item.getAmount();
				}
				int this_size = 0;
				free_space = 0;
				have_cu = 0;
				have_ag = 0;
				for(ItemStack p_item : inv.getStorageContents()) {
					if(p_item==null||p_item.getType().isAir()) {
						free_space++;
						if(free_space>5) {
							return true;
						}
						continue;
					}
					if(p_item.isSimilar(item)&&p_item.getAmount()<stack_max) {
						this_size+=stack_max-p_item.getAmount();
					}else if(p_item.isSimilar(cu_coin)) {
						have_cu+=p_item.getAmount();
					}else if(p_item.isSimilar(ag_coin)) {
						have_ag+=p_item.getAmount();
					}
				}
				Dich dich = new Dich(item,this_size,item.getAmount());
				diches.add(dich);
				
			}
			Settings.debug("Space = "+free_space);
			int new_space = free_space;
			boolean have_items_space = true;
			if(getSmallestDich(diches).getCan()<1.0) {
				have_items_space = false;
				for(int i = 0;i<free_space;i++) {
					Dich dich = getSmallestDich(diches);
					dich.addSpace();
					Settings.debug("AFTER dh can = "+dich.getCan());
					Settings.debug("AFTER List dh can = "+getSmallestDich(diches).getCan());
					new_space--;
					if(getSmallestDich(diches).getCan()>=1.0) {
						have_items_space = true;
						break;
					}
				}
			}
			free_space = new_space;
			Settings.debug("New Space = "+new_space);
			if(!have_items_space) {
				return false;
			}
			if(new_space>=2) {
				return true;
			}
		}
		int need_space_m = 0;
		boolean cu_convert = false;
		if(have_cu<need_cu) {
			Settings.debug("Не хватает меди. Есть: "+have_cu+" нужно: "+need_cu);
			need_ag++;
			cu_convert=true;
			if(have_cu==0) {
				need_space_m++;
			}
		}
		if(have_cu>=need_cu&&have_ag>=need_ag) {
			Settings.debug("Хватает и тех и тех");
			return true;
		}
		
		if(have_ag<need_ag) {
			if(have_ag==0) {
				need_space_m++;
			}
		}
		Settings.debug("Нужно ещё "+need_space_m);
		if(free_space>=need_space_m) {
			return true;
		}
		
		
		
		
		
		
		
		return false;
	}
	boolean needMonets(List<ItemStack> items) {
		String type = "MATERIAL";
		String cu = "CUCOIN";
		String ag = "SILCERCOIN";
		//String au = "GOLDCOIN";
		for(ItemStack item : items) {
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			if(nbtbase.e("MMOITEMS_ITEM_ID")&&nbtbase.e("MMOITEMS_ITEM_TYPE")&&nbtbase.c("MMOITEMS_ITEM_TYPE").e_().equals(type)) {
				if(nbtbase.c("MMOITEMS_ITEM_ID").e_().equals(cu)||nbtbase.c("MMOITEMS_ITEM_ID").e_().equals(ag)) {
					return true;
				}
			}
		}
		
		
		return false;
	}
	
	int howMuchEmpty(PlayerInventory inv) {
		int amount = 0;
		
		for(ItemStack item : inv.getStorageContents()) {
			if(item==null||item.getType().isAir()) {
				amount++;
			}
		}
		
		return amount;
	}
	
	
	boolean hasItem(PlayerInventory inv, ItemStack item) {
		for(ItemStack p_item : inv.getStorageContents()) {
			if(p_item.isSimilar(item)) {
				return true;
			}
		}
		return false;
	}
	boolean hasEnoughMonets(PlayerInventory inv, List<ItemStack> items) {
		ItemStack cu_coin =  MMOItems.plugin.getItem("MATERIAL", "CUCOIN");
		ItemStack ag_coin =  MMOItems.plugin.getItem("MATERIAL", "SILCERCOIN");
		int need_cu = 0;
		int need_ag = 0;
		int has_cu = 0;
		int has_ag = 0;
		for(ItemStack item : items) {
			if(item.isSimilar(cu_coin)) {
				need_cu+=item.getAmount();
			}else if(item.isSimilar(ag_coin)) {
				need_ag+=item.getAmount();
			}
		}
		if(need_cu==0&&need_ag==0) {
			return true;
		}
		for(ItemStack item : inv.getStorageContents()) {
			if(item==null) {
				continue;
			}else if(item.isSimilar(cu_coin)) {
				has_cu+=item.getAmount();
			}else if(item.isSimilar(ag_coin)) {
				has_ag+=item.getAmount();
			}
		}
		if(has_cu>=need_cu&&has_ag>=need_ag) {
			return true;
		}
		
		return false;
		
	}
	int getAmount(PlayerInventory inv,ItemStack item) {
		int have = 0;
		for(ItemStack p_item: inv.getStorageContents()) {
			if(p_item==null) {
				continue;
			}else if(p_item.isSimilar(item)){
				have+=p_item.getAmount();
			}
		}
		return have;
	}
	int getAmount(String type,String s, PlayerInventory pi) {
		int amount=0;
		
		for (ItemStack item :pi.getStorageContents()) {
			if(item!=null) {
				if(item.getType().getMaxDurability()!=0) {
					Damageable damageable = (Damageable) item.getItemMeta();
					if(damageable.hasDamage()) {
						continue;
					}
					//Settings.debug("Максимальная прочность "+item.getType().name()+"("+item.getItemMeta().getDisplayName()+") поломан на "+damageable.getDamage()+" из "+item.getType().getMaxDurability()+"  ["+damageable.hasDamage()+"]");
				}
				//Settings.debug("Максимальная прочность "+item.getItemMeta().getDisplayName()+" = "+item.getType().getMaxDurability()+" ломаемость = "+item.getItemMeta().isUnbreakable());
				NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
				if(nbtbase.e("MMOITEMS_ITEM_ID")) {
					String lol = nbtbase.c("MMOITEMS_ITEM_ID").e_();
					String tp = nbtbase.c("MMOITEMS_ITEM_TYPE").e_();
					if(!(lol==null)||!(tp==null));{
						if(lol.equals(s)&&tp.equals(type)){
							amount +=item.getAmount();
						}
					}
				}
				
			}
            
        }
		return amount;
	}
	int getVanillaAmount(PlayerInventory pi, ItemStack im) {
		int amount = 0;
		
		for (ItemStack item :pi.getStorageContents()) {
			if(item!=null) {
				if(item.isSimilar(im)) {
					if(item.getType().getMaxDurability()!=0) {
						Damageable damageable = (Damageable) item.getItemMeta();
						if(damageable.hasDamage()) {
							continue;
						}
						//Settings.debug("Максимальная прочность "+item.getType().name()+"("+item.getItemMeta().getDisplayName()+") поломан на "+damageable.getDamage()+" из "+item.getType().getMaxDurability()+"  ["+damageable.hasDamage()+"]");
					}
					amount+=item.getAmount();
				}
				
			}
		
		}
		return amount;
	}
	void removeItemsWithConvert(PlayerInventory inv, List<ItemStack> items) {

		ItemStack cu_coin =  MMOItems.plugin.getItem("MATERIAL", "CUCOIN");
		ItemStack ag_coin =  MMOItems.plugin.getItem("MATERIAL", "SILCERCOIN");
		ItemStack au_coin =  MMOItems.plugin.getItem("MATERIAL", "GOLDCOIN");
		int cu_have = 0;
		int ag_have = 0;
		int au_have = 0;
		boolean checked_silver = false;
		int cu_need = 0;
		int ag_need = 0;
		int au_need = 0;
		
		for(ItemStack item : items) {
			if(item.isSimilar(cu_coin)) {
				cu_have=getAmount(inv,item);
				if(cu_have>=item.getAmount()) {
					cu_have-=item.getAmount();
					removeVanillaItem(inv,item,item.getAmount());
					
				}else {
					if(cu_have>0) {
						removeVanillaItem(inv,item,cu_have);
						cu_need+=item.getAmount()-cu_have;
						cu_have=0;
					}else {
						cu_need+=item.getAmount();
					}
					
				}
				
			}else if(item.isSimilar(ag_coin)) {
				checked_silver = true;
				ag_have=getAmount(inv,item);
				if(ag_have>=item.getAmount()) {
					ag_have-=item.getAmount();
					removeVanillaItem(inv,item,item.getAmount());
					
				}else {
					if(ag_have>0) {
						removeVanillaItem(inv,item,ag_have);
						ag_need+=item.getAmount()-ag_have;
						ag_have=0;
					}else {
						ag_need = item.getAmount();
					}
					
				}
			}else if(item.isSimilar(au_coin)) {
				au_have=getAmount(inv,item);
				removeVanillaItem(inv,item,item.getAmount());
				//au_need+=item.getAmount();
			}else{
				removeVanillaItem(inv,item,item.getAmount());
			}
		}
		boolean need_dop_silver = false;
		if(cu_need>cu_have) {
			if(!checked_silver) {
				ag_have=getAmount(inv,ag_coin);
			}
			if(ag_have==0) {
				need_dop_silver=true;
			}
			
		}
		if(ag_need>ag_have||need_dop_silver) {
			removeVanillaItem(inv,au_coin,1);
			ItemStack silver = new ItemStack(ag_coin);
			silver.setAmount(64-ag_need);
			inv.addItem(silver);
		
		}
		if(cu_need>0) {
			removeVanillaItem(inv,ag_coin,1);
			ItemStack cu = new ItemStack(cu_coin);
			cu.setAmount(64-cu_need);
			inv.addItem(cu);
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


class Dich{
	int all_have = 0;
	int max_stack = 0;
	double can = 0.0;
	int need = 0;
	ItemStack item;
	public Dich(ItemStack im,int have,int nd) {
		this.all_have=have;
		this.item=im;
		this.need = nd;
		can = all_have/need;
		max_stack = item.getMaxStackSize();
		
		
		
		
	}
	
	
	public void addSpace() {
		all_have = all_have+max_stack;
		can = all_have/need;
	}
	
	
	
	
	public double getCanWithSpace(int space) {
		int cann = all_have;
		cann+=space*item.getMaxStackSize();
		return cann/need;
	}
	
	
	
	public double getCan() {
		return can;
	}
	
}
