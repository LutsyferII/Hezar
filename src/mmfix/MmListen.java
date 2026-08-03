package mmfix;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import io.lumine.mythic.api.mobs.entities.SpawnReason;
import io.lumine.mythic.bukkit.entities.BukkitSlime;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import main.Settings;

public class MmListen implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMMSpawn(MythicMobSpawnEvent e){
		//Settings.debug("{DEBUG{ [SLIME FIX] reason == "+e.getSpawnReason());
		if (e.getEntity().getType() == EntityType.SLIME && 
	            !e.isFromMythicSpawner() && e.getSpawnReason() == SpawnReason.NATURAL) {
			Settings.debug("{DEBUG{ [SLIME FIX] first good!");
	            e.setCancelled(true); // Жёстко отменяем спавн новых слизней
	        }
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMMDeathn(MythicMobDeathEvent e){
		if(e.getEntity().getType() == EntityType.SLIME) {
			Slime slime = (Slime) e.getEntity();
			if (slime.getSize() > 1) {
	            slime.setSize(1);
	        }
		}
		
	}
}
