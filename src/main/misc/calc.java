package main.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import main.Configs;
import main.Settings;

public class calc{

	static FileConfiguration calc_config;
	static List<CalcStat> stat_list = new ArrayList<>();
	static double damage_from_p = 3.0;

	public static void load() {
		calc_config = Configs.calcConfig.get();
		stat_list = new ArrayList<>();
		ConfigurationSection conf_stat = calc_config.getConfigurationSection("stats");
		Set<String> keyset = conf_stat.getKeys(false);
		for(String s : keyset) {
				stat_list.add(new CalcStat(conf_stat.getConfigurationSection(s)));
				Settings.debug("загрузка хуеты = " + s);
		}
		if(calc_config.contains("damage")&&calc_config.isConfigurationSection("damage")) {
			ConfigurationSection dam_c = calc_config.getConfigurationSection("damage");
			if(dam_c.contains("player")) {
				if(dam_c.isDouble("player")) {
					damage_from_p = dam_c.getDouble("player");
				}else if(dam_c.isInt("player")) {
					int temp =dam_c.getInt("player");
					damage_from_p = temp+0.0;
				}
			}
		}
	}
	
	
	public static void calcStat(CommandSender sender, CalcStat stat, Double full_stat) {
		if(full_stat==0.0) {
			return;
		}
		List<int[]> arrayList = new ArrayList<int[]>();
		String name = stat.name;
		Double pr_stat = full_stat / 100;
		String message = "§2";
		Set<String> keys = stat.stats.keySet();
		for (String key: keys) {
			Integer val = stat.stats.get(key);
			Double znach = pr_stat * val;
			message+= " "+name+" "+key+" = "+znach+" \n ";
		}
		sender.sendMessage("§2-----------------------------------------------------");
		sender.sendMessage(message);
		//sender.sendMessage("§2 "+string_list.get(type)+" шлема = "+arm_helmet+" \n "+string_list.get(type)+" нагрудника = "+arm_chest+"\n "+string_list.get(type)+" понож = "+arm_leggings+"\n "+string_list.get(type)+" обуви = "+arm_boots);
		sender.sendMessage("§2-----------------------------------------------------");
		
		
	}
	public static double getDamageFromP() {
		return damage_from_p;
	}
	public static void getStats(CommandSender sender,List<Double> d_list) {
		 //0 - Броня. 1 - Хп. 2 - Осн стат. 3 - доп стат.
		
		
		if(d_list.size()>=stat_list.size()) {
			int ya = 0;
			for(CalcStat stat : stat_list) {
				calcStat(sender,stat,d_list.get(ya));
				ya++;
			}
		}else {
			for(int i = 0;i<d_list.size();i++) {
				calcStat(sender,stat_list.get(i),d_list.get(i));
			}
			
		}
		
	}
}

























