package Configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import compass.ZCompass;
import main.Configs;

public class CompassManager {
	static FileConfiguration compass_config = Configs.compassConfig.get();
	static List<ZCompass> compasses = new ArrayList<ZCompass>();
	public static void load() {
		compass_config = Configs.compassConfig.get();
		compasses = new ArrayList<ZCompass>();
		
		Set<String> keys = compass_config.getKeys(false);
		for(String key : keys) {
			if(compass_config.isConfigurationSection(key)) {
				compasses.add(new ZCompass(compass_config.getConfigurationSection(key)));
			}
		}
	}
	public static boolean hasCompass(String tag) {
		for(ZCompass compass : compasses) {
			if(compass.checkTag(tag)) {
				return true;
			}
		}
		return false;
	}
	public static  ZCompass getCompass(String tag) {

		for(ZCompass compass : compasses) {
			if(compass.checkTag(tag)) {
				return compass;
			}
		}
		
		return null;
	}

}
