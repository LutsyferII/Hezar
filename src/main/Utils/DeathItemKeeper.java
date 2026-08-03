package main.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.Settings;



public class DeathItemKeeper {
	
	private List<ItemStack> items = new ArrayList<ItemStack>();
	private final Player player;
	
	private static Map<UUID, DeathItemKeeper> KEEP = new WeakHashMap<>();
	
	public DeathItemKeeper(Player p) {
		this.player = p;
	}
	public void addItem(ItemStack item) {
		items.add(item);
	}
	public boolean hasItems() {

		Settings.debug("{KEEPER} debug 5 + has = "+items.size());
		
		return !this.items.isEmpty();
	}
	public static void registerKeeper(DeathItemKeeper dik) {
		Settings.debug("{KEEPER} debug 0");
		KEEP.put(dik.player.getUniqueId(), dik);
	}
	public boolean hasPlayer(UUID uuid) {
		return KEEP.containsKey(uuid);
	}
	public static void giveItem(UUID uuid) {
		if(KEEP.containsKey(uuid)) {
			Settings.debug("{KEEPER} debug 1");
			DeathItemKeeper keeper = KEEP.remove(uuid);
			for (ItemStack drop : keeper.player.getInventory().addItem(keeper.items.toArray(new ItemStack[0])).values()) {
				keeper.player.getWorld().dropItem(keeper.player.getLocation(), drop);
			}
		}
	}
	
}
