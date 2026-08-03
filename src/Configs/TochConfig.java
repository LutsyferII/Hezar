package Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import main.toch.Tochilka;

public class TochConfig{
	public static List<Tochilka> tochilki = new ArrayList<>();
	public static void load(ConfigurationSection c) {
		tochilki = new ArrayList<>();
		Set<String> setofkeys = c.getKeys(false);
		for(String s : setofkeys) {
			tochilki.add(new Tochilka(c.getConfigurationSection(s)));
		}
		
	}
	public static boolean hasTochilka(int id) {
		for(Tochilka toch : tochilki) {
			if(toch.id==id) {
				return true;
			}
		}
		return false;
	}
	public static Tochilka getTochilka(int id) {
		for(Tochilka toch : tochilki) {
			if(toch.id==id) {
				return toch;
			}
		}
		return tochilki.get(0);
	}
	
}