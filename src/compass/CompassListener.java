package compass;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import Configs.CompassManager;
import main.Settings;

public class CompassListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!e.hasItem()) {
			return;
		}
		if(e.getAction()!=Action.RIGHT_CLICK_AIR) {
			return;
		}
		ItemStack item = e.getItem();
		net.minecraft.world.item.ItemStack nbt_item  = CraftItemStack.asNMSCopy(item);
		if(!nbt_item.u().e("ZCOMPASS_TAG")) {
			return;
		}
		
		String tag = nbt_item.u().c("ZCOMPASS_TAG").e_();
		if(!CompassManager.hasCompass(tag)){
			Settings.debug("{COMPASS}На компасе недействительный тег! ("+tag+")", 4);
			return;
		}
		ZCompass compass= CompassManager.getCompass(tag);
		if(compass.getLocation()==null) {
			Settings.debug("{COMPASS}Отсутсвует локация! ("+tag+")", 4);
			return;
		}
		Location loc = compass.getLocation();
		Player p = e.getPlayer();
		p.setCompassTarget(loc);
		
		
	}
}
