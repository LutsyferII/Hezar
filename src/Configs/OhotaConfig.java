package Configs;

import main.Configs;
import main.Settings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import main.ohota.OhotaDrop;
import main.Utils.BukkitConfig;


public class OhotaConfig{
	static FileConfiguration ohcf;
	static Set<String> setofkeys;
	static List<OhotaDrop> listohd;
	public static void load() {
		ohcf = Configs.ohotaConfig.get();
		setofkeys = ohcf.getKeys(false);
		listohd = new ArrayList<>();
		Settings.debug("[huy] §2------------------ОХОТА------------------");
		for(String s : setofkeys) {
			Settings.debug("[huy] §2Загрузка хуйни = "+s);
			listohd.add(new OhotaDrop(ohcf.getConfigurationSection(s)));
			
		}
	}
	public static Set<String> getSetOfKeys() {
		return setofkeys;
	}
	public static  Object[] getArrayOfKeys() {
		return setofkeys.toArray();
	}
	public static List<OhotaDrop> getListOhoty(){
		return listohd;
	}
	
}