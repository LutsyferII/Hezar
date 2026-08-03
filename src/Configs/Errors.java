package Configs;

import org.bukkit.Bukkit;

public class Errors {
	public static void messega(String s) {
		
		Bukkit.getConsoleSender().sendMessage("§a[HEZAR]"+s);
		
		
	}
	public static void messega(String s, String color) {
		
		Bukkit.getConsoleSender().sendMessage("§"+color+"[HEZAR]"+s);
		
		
	}
	public static void messega(String s, int color) {
		
		Bukkit.getConsoleSender().sendMessage("§"+color+"[HEZAR]"+s);
		
		
	}
	public static void error(String s) {
		
		Bukkit.getConsoleSender().sendMessage("§4[HEZAR]{ERROR}"+s);
		
		
	}
}
