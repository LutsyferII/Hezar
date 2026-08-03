package main.toch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;

import main.Settings;

public class Tochilka{
	public int id; //ZARJ_TOCH_ID
	public Double chance = 100.0;
	public int rune_level = 1;
	public int min = 0;
	public int max = 10;
	public Double decr = 0.0;
	public Random rand = new Random();
	public boolean	down= false ;
	public boolean  broke_in_null= false;
	public boolean  broke_in_fall= false;
	public List<String[]> list_items = new ArrayList<>();
	public List<TochStat> list_stats = new ArrayList<>();
	public Tochilka(ConfigurationSection c ){
		if(!c.contains("id")||!c.isInt("id")) {
			Bukkit.getConsoleSender().sendMessage("§4{ТОЧИЛКА} Не удалось загрузить точилку "+c.getName()+" Отсутствует id.");
		}
		id = c.getInt("id");
		if(c.contains("chance")) {
			this.chance = c.getDouble("chance");
		}
		if(c.contains("decr")) {
			this.decr = c.getDouble("decr");
		}
		if(c.contains("down")&&c.isBoolean("down")){
			this.down=c.getBoolean("down");
			if(this.down) {
				if(c.contains("broke_in_null")&&c.isBoolean("broke_in_null")) {
					this.broke_in_null=c.getBoolean("broke_in_null");
				}
			}
		}
		if(c.contains("broke_in_fall")&&c.isBoolean("broke_in_fall")) {
			this.broke_in_fall=c.getBoolean("broke_in_fall");
		}
		
		
		if(c.contains("min")&&c.isInt("min")) {
			this.min = c.getInt("min");
		}
		if(c.contains("max")&&c.isInt("max")) {
			this.max = c.getInt("max");
		}
		if(c.contains("to")) {
			for(String s : c.getStringList("to")) {
				//Settings.debug("{ТОЧИЛКИ} полный текст предмета = "+s);
				String[] sarr = s.split("\\.");
				list_items.add(s.split("\\."));
				//Settings.debug("{ТОЧИЛКИ} Загружен предмет тип ="+list_items.get(list_items.size()-1)[0]+" id = "+list_items.get(list_items.size()-1)[1]);
			}
		}
		if(c.contains("stats")) {
			ConfigurationSection cs = c.getConfigurationSection("stats");
			Set<String> setofkeys = cs.getKeys(false);
			for(String s : setofkeys) {
				list_stats.add(new TochStat(cs.getConfigurationSection(s)));
			}
		}
		if(c.contains("rune")) {
			if(c.isInt("rune")) {
				rune_level = c.getInt("rune");
			}else if(c.isDouble("rune")) {
				rune_level = (int) c.getDouble("rune");
			}
		}
		Settings.debug("{ТОЧИЛКИ} ЗАГРУЖЕНА ТОЧИЛКА "+c.getName());
		
	}
	public boolean hasItem(String type, String mmoid) {
		Settings.debug("{ТОЧИЛКИ} HasItem тип = |"+type+"| id = |"+mmoid+"|");
		for(String[] sarr : list_items) {
			if(sarr[0].equalsIgnoreCase(type)&&sarr[1].equalsIgnoreCase(mmoid)) {
				return true;
			}
		}
		return false;
	}
	public boolean isUpdatable(int level) {
		if(level>=min&&level<max) {
			return true;
		}else {
			return false;
		}
		
	}
	public List<String> addLore(List<String> lore, int lvl,Double stat,TochStat ts,Double new_stat){

			boolean find = false;
			int i = 0;
			for(String s : lore) {
				String key_regex="(.*)((?iu)"+ts.name+")(.*)";  //
				if(s.matches(key_regex)) {
					
					String numb_regex = ":[a-zA-Z \\W]*([1-9]+[.[0-9]+]*)"; //"(.*)(:) *\\+([1-9][0-9]+[.[0-9]+]?)";
					Pattern patt = Pattern.compile(numb_regex);
					Matcher match = patt.matcher(s);
					if(!match.find()) {
						Settings.debug("{TOCHKA} пизда..."+s);
					}else {
						Settings.debug("{TOCHKA} Найдена хуета = "+match.group());
						for(int j = 0;j<match.groupCount();j++) {
							Settings.debug("{TOCHKA} Группа хуеты("+j+") = "+match.group(j+1));
						}
					}
					String huing = match.group(0).replaceFirst(match.group(1), stat+"");
					String zamen_str = match.group(0).replaceAll("\\+", "\\\\+");
					
					s = s.replaceAll(zamen_str, huing);
					Settings.debug("Меняемая хуета = "+zamen_str+" Стат = "+huing+" Првоерка лора = "+s);
					
					String new_key_regex="(.*)()(.*)";
					if(s.matches(new_key_regex)) {
						Pattern pattern = Pattern.compile("(.*)");
						Matcher matcher = pattern.matcher(s);
						if(!matcher.find()) {
							Settings.debug("{TOCHKA} пизда...");
						}else {
							Settings.debug("{TOCHKA} AllГруппа = "+matcher.group());
							for(int j = 0;j<matcher.groupCount();j++) {
								Settings.debug("{TOCHKA} Группа = "+matcher.group(j+1));
							}
						}
						
						
						
						String old_str=matcher.group(0);
						Double val = new_stat;
						String text_val = val.toString();
						if((val % 1) == 0) {
							text_val=val.intValue()+"";
						}else {
							String[] temp_arr = text_val.split("\\.");
							if(temp_arr[1].length()>3) {
								Settings.debug("{ТОЧИЛКА} Слишком кошерно. Обрезаем!"+temp_arr[1]);
								temp_arr[1]=temp_arr[1].substring(0,2);
								text_val=temp_arr[0]+"."+temp_arr[1];
							}
						}
						String new_str=" "+text_val;
						String regex_old = old_str.replaceAll("\\+", "\\\\+");
						Settings.debug("{TOCHKA} Значение до замены ("+i+"): |"+lore.get(i)+"| Строка на замену |"+s.replaceAll(regex_old, new_str)+"| Значение для замены = "+new_str+"     hhhh="+old_str+"  regex_hhhh="+regex_old);
						lore.set(i, s.replaceAll(regex_old, new_str));
						
					}else {
						String new_str=s+"  "+new_stat;
						lore.set(i, new_str);
					}
					/*Double origin_stat = stat;
					String text_orig = origin_stat.toString();
					if((origin_stat % 1) == 0) {
						text_orig=origin_stat.intValue()+"";
					}
					Settings.debug("{TOCHKA} Замена значения("+i+"): "+s+" фулл группа |"+stat+"| ");
					Double new_stat = origin_stat+ts.getFinalValue(lvl);
					String text_new_stat = new_stat.toString();
					if((origin_stat % 1) == 0) {
						text_new_stat=new_stat.intValue()+"";
					}else {
						String[] temp_arr = text_new_stat.split("\\.");
						if(temp_arr[1].length()>3) {
							Settings.debug("{ТОЧИЛКА} Слишком кошерно. Обрезаем!"+temp_arr[1]);
							temp_arr[1]=temp_arr[1].substring(0,2);
							text_new_stat=temp_arr[0]+temp_arr[1];
						}
					}
					Settings.debug("{TOCHKA} Значение до замены ("+i+"): |"+lore.get(i)+"| Строка на замену |"+s.replaceAll(text_orig, text_new_stat)+"| Значение для замены = "+new_stat.toString()+"     hhhh="+origin_stat);
					lore.set(i, s.replaceAll(text_orig, text_new_stat));
					*/
					find=true;
					break;
				}
				i++;
				
			}
			if(!find) {
				lore.add(ts.name+": "+(ts.value+(ts.value/100*(ts.perlevel*lvl))));
			}
			
		
		return lore;
		
		
	}
	
	public List<String> removeLevelLore(List<String> lore, int lvl,Double stat,TochStat ts,Double all_st) {
		int i=0;
		boolean old = false;
		if(lvl<=min) {
			old=true;
		}
		for(String s : lore) {
			String key_regex="(.*)((?iu)"+ts.name+")(.*)";  //
			
			
				if(s.matches(key_regex)) {
					

						String numb_regex = ":[a-zA-Z \\W]*([1-9]+[.[0-9]+]*)"; //"(.*)(:) *\\+([1-9][0-9]+[.[0-9]+]?)";
						Pattern patt = Pattern.compile(numb_regex);
						Matcher match = patt.matcher(s);
						if(!match.find()) {
							Settings.debug("{TOCHKA} пизда..."+s);
						}else {
							Settings.debug("{TOCHKA} Найдена хуета = "+match.group());
							for(int j = 0;j<match.groupCount();j++) {
								Settings.debug("{TOCHKA} Группа хуеты("+j+") = "+match.group(j+1));
							}
						}
						String huing = match.group(0).replaceFirst(match.group(1), all_st+"");
						String zamen_str = match.group(0).replaceAll("\\+", "\\\\+");
						
						s = s.replaceAll(zamen_str, huing);
						Settings.debug("Меняемая хуета = "+zamen_str+" Стат = "+huing+" Првоерка лора = "+s);
						
					
						
						String new_key_regex="(.*)()(.*)";
						if(s.matches(new_key_regex)) {
							Pattern pattern = Pattern.compile("(.*)");
							Matcher matcher = pattern.matcher(s);
							if(!matcher.find()) {
								System.out.println("{TOCHKA} пизда...");
							}
							String old_str=matcher.group(0);
							String new_str;
							if(lvl!=1){
								
								Double val = stat;
								String text_val = val.toString();
								if((val % 1) == 0) {
									text_val=val.intValue()+"";
								}else {
									String[] temp_arr = text_val.split("\\.");
									if(temp_arr[1].length()>3) {
										Settings.debug("{ТОЧИЛКА} Слишком кошерно. Обрезаем!"+temp_arr[1]);
										temp_arr[1]=temp_arr[1].substring(0,2);
										text_val=temp_arr[0]+"."+temp_arr[1];
									}
								}
								new_str="  +"+text_val;
								Settings.debug("{TOCHKA} Значение до замены ("+i+"): |"+lore.get(i)+"| Строка на замену |"+s.replaceAll("(.*)", new_str)+"| Значение для замены = "+new_str+"     hhhh="+old_str+"  regex_hhhh="+old_str);
								
							}else {
								new_str = "";
							}
							String regex_old = old_str.replaceAll("\\+", "\\\\+");
							lore.set(i, s.replaceAll(" (.*)", new_str));
							
							break;
						}
				}
			
				
			
			i++;
			
		}
		
		return lore;
	}
	public String getItemType(ItemStack i) {
		/*Material s_type = i.getType();
		List<Material> armor_types = new ArrayList<>();
		List<Material> pw_types = new ArrayList<>();
		List<Material> mw_types = new ArrayList<>();
		armor_types.add(Material.IRON_HELMET);
		armor_types.add(Material.IRON_CHESTPLATE);
		armor_types.add(Material.IRON_LEGGINGS);
		armor_types.add(Material.IRON_BOOTS);
		armor_types.add(Material.IRON_HELMET);
		armor_types.add(Material.IRON_CHESTPLATE);
		armor_types.add(Material.IRON_LEGGINGS);
		armor_types.add(Material.IRON_BOOTS);*/
		return "suka";
		
		
	}
	public boolean willUpgrade(int level) {
		Double new_chance=chance-(decr*(level-min));
		Double fill =  rand.nextDouble(0.0, 100.0);
		if(fill<=new_chance) {
			return true;
		}else {
			return false;
		}
	}
	public static ItemMeta toZero(ItemMeta im,short maxDur) {
		((Damageable) im).setDamage(maxDur);
		return im;
	}
	public boolean isOld(int level) {
		if(level<=min) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
}
