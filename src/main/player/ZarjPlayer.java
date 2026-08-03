package main.player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import main.hezar;

public class ZarjPlayer {

	LinkedHashMap<String,Integer> cooldowns = new LinkedHashMap<String,Integer>();
	List<BukkitTask> tasks = new ArrayList<>();
	UUID uuid;
	
	public ZarjPlayer(UUID u) {
		this.uuid =u;
	}
	
	public int checkCooldown(String s) {
		if(!cooldowns.containsKey(s)) {
			return 0;
		}else {
			return cooldowns.get(s);
		}
	}
	public void setCooldown(String s, int cd) {
		cooldowns.put(s, cd);
		if(cd>0) {
			
			BukkitRunnable br = new BukkitRunnable() {

				@Override
				public void run() {
					int new_cd = cooldowns.get(s)-5;
					cooldowns.replace(s, new_cd);
					if(new_cd<=0) {
						this.cancel();
					}
				}
				
				
			};
			br.runTaskTimer(hezar.inst, 5, 5);
			
		}
	}
	public int getCooldown(String s) {
		return cooldowns.get(s);
	}
	
	
	
}
