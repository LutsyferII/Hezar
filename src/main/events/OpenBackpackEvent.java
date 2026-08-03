package main.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import main.backpack.Backpack;


public class OpenBackpackEvent  extends Event implements Cancellable {

	boolean cancel = false;
	ItemStack item;
	Action action;
	Player player;
	private static final HandlerList handlers = new HandlerList();
	
	public OpenBackpackEvent(ItemStack item, Action act,Player p) {
		this.item = item;
		action = act;
		player = p;
		Backpack bp = new Backpack(item);
		Inventory inv = bp.getInventory();
		p.openInventory(inv);
	}
	
	public Action getAction() {
		return action;
	}
	
	public ItemStack getItem() {
		return item;
	}
	public Player getPlayer() {
		return player;
	}
	
	
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		// TODO Auto-generated method stub
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		 return handlers;
	}
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
}
