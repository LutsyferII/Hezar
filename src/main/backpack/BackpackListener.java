package main.backpack;

import java.time.LocalDateTime;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import Configs.Errors;
import io.lumine.mythic.lib.api.item.NBTItem;
import main.Settings;
import main.hezar;
import main.backpack.reserve.ZBackpack;
import main.backpack.reserve.ZBackpackManager;
import main.events.OpenBackpackEvent;

public class BackpackListener implements Listener {
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(!e.getResult().equals(Result.ALLOWED)) {
			return;
		}
		Player p = e.getPlayer();
		
		if(!ZBackpackManager.hasMassage(p)) {
			return;
		}
		
		p.sendMessage("§4Некоторые ваши рюкзаки скоро будут очищенны!");
		p.sendMessage("§4Рюкзаки автоматически очищаются, если не открывались дольше 60-ти дней!");
		for(String s : ZBackpackManager.getMassage(p)) {
			p.sendMessage("§4"+s);
		}
		
		
		
		
		
	}
	
	
	@EventHandler
 	public void InventoryMove(InventoryMoveItemEvent e){
		
	}
	
	
	@EventHandler
 	public void InventoryClick(InventoryClickEvent e){
		Inventory inv = e.getInventory();
		/*
		if(e.getInventory().getHolder() instanceof Backpack) {
			Settings.debug("{BACKPACK{ IS TRUE","c");
		}
		if(e.getClickedInventory().getHolder() instanceof Backpack) {
			Settings.debug("{BACKPACK{ IS FALSE","c");
		}
		*/
		if(!(inv.getHolder() instanceof Backpack)&&!(inv.getHolder() instanceof ZBackpack)) {
			//Settings.debug("{BACKPACK} Не тот рюкзак!", 4); 
			return;
		}
		if(e.isCancelled()) {
			Settings.debug("{BACKPACK} ДЕЙСТВИЕ ОТМЕНЕНО РАНЕЕ!", 4); 
			return;
		}
		if(e.getAction() == InventoryAction.NOTHING) {
			//Settings.debug("{BACKPACK} ДЕЙСТВИЯ НЕТ!", 4); 
			return;
		}
		
		//Settings.debug("{BACKPACK} ДЕЙСТВИЕ="+e.getAction(), 6); 
		//if(e.getAction()==InventoryAction.PICKUP_ONE||e.getAction()==InventoryAction.PICKUP_ALL||e.getAction()==InventoryAction.PICKUP_HALF||e.getAction()==InventoryAction.MOVE_TO_OTHER_INVENTORY||e.getAction()==InventoryAction.DROP_ALL_SLOT||e.getAction()==InventoryAction.DROP_ONE_SLOT) {
		ItemStack item = e.getCurrentItem();
		
		
		if(e.getClick()==ClickType.NUMBER_KEY&&((e.getClickedInventory().getHolder() instanceof Backpack)||(e.getClickedInventory().getHolder() instanceof ZBackpack))) {

			e.getWhoClicked().sendMessage("§4Не нажимайте на клавиши быстрого доступа в инвентаре рюкзака!");
			e.setCancelled(true);
			return;
		}
		net.minecraft.world.item.ItemStack nbt_item  = CraftItemStack.asNMSCopy(item);
		if(nbt_item.u().e("ZBACKPACK_TYPE")||nbt_item.u().e("ZARJ_BACKPACK_TYPE")) {
			e.getWhoClicked().sendMessage("§4Не трожь рюкзак!");
			e.setCancelled(true);
			return;
		}
			
			
		//}
			/*else if(e.getAction()==InventoryAction.PLACE_ALL||e.getAction()==InventoryAction.DROP_ALL_CURSOR||e.getAction()==InventoryAction.DROP_ONE_CURSOR) {
			ItemStack cursor = e.getCursor();
			ItemMeta meta = cursor.getItemMeta();
			PersistentDataContainer data =meta.getPersistentDataContainer();
			NamespacedKey data_key =  new NamespacedKey(hezar.inst, "ZBACKPACK_DATA");
			if(data.has(data_key, PersistentDataType.BYTE_ARRAY)) {
				e.getWhoClicked().sendMessage("§4Нельзя оперировать другими рюкзака, пока открыт этот!");
				e.setCancelled(true);
				return;
			}
		
		}*/
		
		
		
	}
	 
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){

		Inventory inv = e.getInventory();
		if(!(inv.getHolder() instanceof Backpack)) {
			//Settings.debug("{BACKPACK} Не тот инвентарь!", 4);
			return;
		}
		Backpack backpack =(Backpack) inv.getHolder();
		backpack.saveTags();
	}
	
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Action act = e.getAction();
		if(act!= Action.RIGHT_CLICK_AIR) {
			return;
		}
		if(!e.hasItem()) {
			return;
		}
		ItemStack item = e.getItem();
		NBTItem nbt_item  = NBTItem.get(item);
		if(!nbt_item.hasTag("ZBACKPACK_TYPE")&&!nbt_item.hasTag("ZARJ_BACKPACK_TYPE")) {
			return;
			//Settings.debug("{BACKPACK} Нет тега на рюкзак!", 4);
		}else if(nbt_item.hasTag("ZBACKPACK_TYPE")) {
			OpenBackpackEvent event = new OpenBackpackEvent(item,act,e.getPlayer());
			Bukkit.getPluginManager().callEvent(event);
		}else if(nbt_item.hasTag("ZARJ_BACKPACK_TYPE")) {
			if(nbt_item.hasTag("ZARJ_BACKPACK_UUID")) {
				String uuid_s = nbt_item.getString("ZARJ_BACKPACK_UUID");
				if(ZBackpackManager.hasBackpack(uuid_s)) {
					ZBackpack bp = ZBackpackManager.getBackpack(uuid_s);
					bp.updateDate(LocalDateTime.now());
					e.getPlayer().openInventory(bp.getInventory());
				}else {
					Errors.error("Несуществующий UUID рюкзака!");
					e.getPlayer().sendMessage("§4Ошибка открытия рюкзака, не найден такой рюкзак, если вы уверены, что все должно работать, обратитесь к администрации сервера!");
				}
				
				
				
			}else {
				ZBackpack bp = ZBackpackManager.createAndReturnBackpack(item,e.getPlayer().getName());
				e.getPlayer().openInventory(bp.getInventory());
			}
		}
	}
	

	@EventHandler
	public void onChester(PlayerInteractEvent e) {
		
		if(!e.hasItem()) {
			return;
		}
		ItemStack item = e.getItem();
		net.minecraft.world.item.ItemStack nbt_item  = CraftItemStack.asNMSCopy(item);
		if(nbt_item.u().e("ZCHEST")) {
			Action act = e.getAction();
			Settings.debug("---------------------------------", "c");
			Settings.debug(act.name(), "c");
			Settings.debug("---------------------------------", "c");
			ItemMeta meta = item.getItemMeta();
			NamespacedKey loc = new NamespacedKey(hezar.inst,"ZCHEST_LOC");
			if(act==Action.RIGHT_CLICK_AIR&&meta.getPersistentDataContainer().has(loc,PersistentDataType.STRING)) {
				String loc_s =  meta.getPersistentDataContainer().get(loc, PersistentDataType.STRING);
				String world_s = loc_s.split(":")[0];
				String[] other = loc_s.split(":")[1].split(",");
				
				Block block = Bukkit.getWorld(world_s).getBlockAt(Integer.parseInt(other[0]),Integer.parseInt(other[1]),Integer.parseInt(other[2]));
				if(block.getState() instanceof Chest) {
					PlayerInteractEvent ev = new PlayerInteractEvent(e.getPlayer(), Action.RIGHT_CLICK_BLOCK, item, block,BlockFace.SELF);	
					Bukkit.getPluginManager().callEvent(ev);

					Chest chest =(Chest) block.getState();
					Inventory inv = chest.getInventory();
					e.getPlayer().openInventory(inv);
				}
				Settings.debug(block.getLocation().toString(), "2");
			}else if(act==Action.RIGHT_CLICK_BLOCK){
				Block block = e.getClickedBlock();
				if(block.getType()!=Material.CHEST) {
					e.getPlayer().sendMessage("§4Необходимо кликнуть по сундуку!");
					return;
				}
				Location location = block.getLocation();
				String loc_s = location.getWorld().getName()+":"+location.getBlockX()+","+location.getBlockY()+","+location.getBlockZ();
				meta.getPersistentDataContainer().set(loc,PersistentDataType.STRING, loc_s);
				item.setItemMeta(meta);
			}else if(act==Action.RIGHT_CLICK_AIR) {
				e.getPlayer().sendMessage("§4Нет привязанного сундука!");
				return;
				
			}
		}else {
			return;
		}
		
	}

	@EventHandler
	public void onChesterCHECK(PlayerInteractEvent e) {
		if(e.getClickedBlock()!=null&&e.getClickedBlock().getType()==Material.CHEST) {
			Settings.debug("---------------------------------", "c");
			Settings.debug(e.getAction().name(), "c");
			Settings.debug(e.getPlayer().getName(), "c");
			Block b = e.getClickedBlock();
			Chest chest =(Chest) b.getState();
			//Inventory inv = chest.getInventory();
			//e.getPlayer().openInventory(inv);
			Settings.debug("---------------------------------", "c");
			
		}
	}
	
	@EventHandler
	public void onOpenBackpack(OpenBackpackEvent e) {
		Settings.debug("{BACKPACK} Эвент открытия бэкпака успешен!", 6);
		
		
	}

}
