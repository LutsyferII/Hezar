package main.player;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {
	static LinkedHashMap<UUID,ZarjPlayer> players = new LinkedHashMap<>();
	
	
	public void load() {
		
	}
	public static  void addPlayer(Player p) {
		if(players.containsKey(p.getUniqueId())) {
			return;
		}else {
			ZarjPlayer zp = new ZarjPlayer(p.getUniqueId());
			UUID uuid = p.getUniqueId();
			players.put(uuid, zp);
		}
	}
	public static  void addPlayer(UUID uuid) {
		if(players.containsKey(uuid)) {
			return;
		}else {
			ZarjPlayer zp = new ZarjPlayer(uuid);
			players.put(uuid, zp);
		}
	}
	
	public static  boolean hasPlayer(UUID uuid) {
		return players.containsKey(uuid);
	}
	public static  boolean hasPlayer(Player p) {
		return players.containsKey(p.getUniqueId());
	}
	
	public static  ZarjPlayer getPlayer(UUID uuid) {
		return players.get(uuid);
	}
	public static  ZarjPlayer getPlayer(Player p) {
		return players.get(p.getUniqueId());
	}
}
