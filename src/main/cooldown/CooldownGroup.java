package main.cooldown;

import org.bukkit.scheduler.BukkitTask;

public class CooldownGroup {
	String id;
	int cooldown;
	
	
	
	
	public CooldownGroup(String i, int cd) {
		this.id = i;
		this.cooldown = cd;
	}
}
