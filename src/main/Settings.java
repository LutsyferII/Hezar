package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.ChatColor;
import org.bukkit.configuration.MemorySection;

public enum Settings {
	
	huy;
	
	private static final int PLACEHOLDERS_LIMIT = 3;
	private LinkedHashMap LHMmessage;
	private String[] message;
	public static boolean blow_blocks = true;
	public static boolean enabled;
	public static boolean onop;
	public static boolean debug=false;
	public static String mmotype="DAGGER";
	public static Double divisor= 2.0;
	public static List<String> block_worlds = new ArrayList<String>();
	
	public static void load(ConfigurationSection c) {
		enabled = c.getBoolean("enable");
		onop=c.getBoolean("onop");
		if(c.contains("debug")&&c.get("debug") instanceof Boolean) {
			debug =c.getBoolean("debug");
		}
		if(enabled==false) {
			return;
		}
		if(c.contains("blow_blocks")&&c.isBoolean("blow_blocks")) {
			blow_blocks = c.getBoolean("blow_blocks");
		}
		if(c.contains("doublehands")) {
			ConfigurationSection tempc = c.getConfigurationSection("doublehands");
			if(tempc.contains("type")&&tempc.isString("type")) {
				mmotype=tempc.getString("type");
			}
			if(tempc.contains("divisor")&&(tempc.isDouble("divisor") || tempc.isInt("divisor"))) {
				divisor=tempc.getDouble("divisor");
			}
		}
		if(c.contains("block_world_white")&&c.isList("block_world_white")) {
			block_worlds = new ArrayList<String>();
			block_worlds.addAll(c.getStringList("block_world_white"));
		}
		
		for (Settings m : values()) {
			Bukkit.getConsoleSender().sendMessage("§5[huy] Config = "+c.get(m.name()));
			if(c.get(m.name()) instanceof String) {
				String temp_str= c.getString(m.name());
			}else if(c.get(m.name()) instanceof List<?>) {
				List temp_list= c.getList(m.name());
				Bukkit.getConsoleSender().sendMessage("§5[huy] list = "+temp_list.getClass()+" : "+ temp_list+" | str = "+ temp_list.toString());
			}else if(c.get(m.name()) instanceof MemorySection) {
				MemorySection ms = (MemorySection)c.get(m.name());
				ConfigurationSection mm = c.getConfigurationSection(m.name());
				String[] keys = ms.getKeys(true).toArray(new String[0]);
				
				if(keys[0]!= null) {
					LinkedHashMap<String,Object> mp = new LinkedHashMap<String,Object>(); 
					for(int i=0;i<keys.length;i++) {
						mp.put(keys[i], ms.get(keys[i]));
					}
					m.LHMmessage=mp;
					mp.entrySet().forEach(entry ->{Bukkit.getConsoleSender().sendMessage("§5[huy] �1 LHM = "+entry.getKey()+"=>" + entry.getValue());});
				}
				Bukkit.getConsoleSender().sendMessage("§5[huy] ms = "+ms.getName()+" : "+ms.getKeys(true));
				Bukkit.getConsoleSender().sendMessage("§5[huy] ms keys = "+keys.toString());
			}
		}
	}


	public void send(CommandSender sender) {
		new Sender().send(sender);
	}
	public static void debug(String s) {
		if(debug) {
			Bukkit.getConsoleSender().sendMessage("§a[DEBUG]"+s);
		}
		
	}
	public static void debug(String s, String color) {
		if(debug) {
			Bukkit.getConsoleSender().sendMessage("§"+color+"[DEBUG]"+s);
		}
		
	}
	public static void debug(String s, int color) {
		if(debug) {
			Bukkit.getConsoleSender().sendMessage("§"+color+"[DEBUG]"+s);
		}
		
	}
	
	// ������ ������ ����������� �����, ����������� � ������ Sender
	public Sender replace(String from, String to) {
		Sender s = new Sender();
		s.replace(from, to);
		return s;
	}
	
	public class Sender {
		private String[] cache = new String[PLACEHOLDERS_LIMIT*2];
		private int placePosition = 0;
		
		public Sender replace(String from, String to) {
			cache[placePosition] = from;
			cache[placePosition+1] = to;
			placePosition += 2;
			return this;
		}
		
		public void send(CommandSender sender) {
			if(sender == null) return;
			if (Settings.this.message == null) return;

			for (String m : Settings.this.message) {
				if(m.isEmpty()) continue;
				String s = placeholders(m);
				sender.sendMessage(s);
			}
		}

		private String placeholders(String input) {
			if (!input.contains("%") && !input.contains("{")) {
				return input;
			}
			
			if (placePosition != 0 && cache != null && cache.length % 2 == 0 && cache.length > 1) {
				for (int i = 0; i < cache.length; i+=2) {
					if (cache[i] == null) break;
					input = input.replace(cache[i], cache[i+1]);
				}
			}
			
			return input;
		}
		
		public String get() {
			return placeholders(Settings.this.message[0]);
		}
		
		public String[] gets() {
			String[] msgs = new String[Settings.this.message.length];
			for (int i = 0; i < Settings.this.message.length; i++) {
				msgs[i] = placeholders(Settings.this.message[i]);
			}
			return msgs;
		}
	}
	
	
	
	public static void console_send(String s) {
		Bukkit.getConsoleSender().sendMessage("§a[HEZAR]"+s);
	}
	public static void console_send(String s, String color) {
		Bukkit.getConsoleSender().sendMessage("§"+color+"[HEZAR]"+s);
	}
	public static void console_send(String s, int color) {
		Bukkit.getConsoleSender().sendMessage("§"+color+"[HEZAR]"+s);
		
	}
	public static boolean isWhiteBlockWorld(String s) {
		if(block_worlds.contains(s)) {
			return true;
		}
		return false;
	}
	

	public String get() {
		return Settings.this.message[0];
	}
	public String get(int i) {
		return Settings.this.message[i];
	}
	public String[] gets() {
		return Settings.this.message;
	}
}
