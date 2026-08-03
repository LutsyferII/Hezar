package main.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import org.bukkit.Bukkit;

public class BukkitConfig {

	private FileConfiguration config = new YamlConfiguration();
	private File configFile;
	private JavaPlugin instance;
	private File folder;
	private String name;
	private String new_folder_path=null;
	private boolean is_folder = false;
	
	public BukkitConfig(JavaPlugin instance, File folder, String name,String new_folder) {
		this.instance = instance;
		this.folder = folder;
		this.name = name;
		this.new_folder_path=new_folder;
		
	}
	public BukkitConfig(JavaPlugin instance, File folder, String name) {
		this.instance = instance;
		this.folder = folder;
		this.name = name;
		
	}
	public BukkitConfig(JavaPlugin instance, File folder, String name,boolean is_folder) {
		this.instance = instance;
		this.folder = folder;
		this.name = name;
		this.is_folder = is_folder;
		
	}
	
	public static BukkitConfig create(JavaPlugin instance, File folder, String name) {
		return new BukkitConfig(instance, folder, name);
	}
	public static BukkitConfig create(JavaPlugin instance, File folder, String name,String new_folder) {
		return new BukkitConfig(instance, folder, name, new_folder);
	}
	public static BukkitConfig create(JavaPlugin instance, File folder, String name,boolean is_folder) {
		return new BukkitConfig(instance, folder, name,is_folder);
	}
	public void reload() {
		
		
		
		
		
		
		if (new_folder_path!=null) {
			//String new_path=folder.getAbsolutePath()+"\\"+new_folder_path+"\\"+name;
			//Bukkit.getConsoleSender().sendMessage("§5[huy] §2Путь для конгфига "+name+" = "+new_path);
			File new_folder = new File(folder,new_folder_path);
			
			configFile = new File(new_folder, name);
			//Bukkit.getConsoleSender().sendMessage("§5[huy] §2Путь для конгфига "+name+" = "+configFile.getAbsolutePath());
			
		}else {
			configFile = new File(folder, name);
		}
		
		
		if(is_folder) {
			
			File[] sub_fold = folder.listFiles();
			if(!configFile.exists() || configFile.length() == 0) {

				configFile.mkdirs();
			}
			for(File f : sub_fold) {
				if(f.getName().equalsIgnoreCase(name)) {
					
					String[] sub_files = f.list();
					for(String s : sub_files) {
						instance.saveResource(s, false);
					}
					
					break;
				}
			}
			
			
			
			
			
		}else {
			if (!configFile.exists() || configFile.length() == 0) {
				if(this.new_folder_path!=null) {
					configFile.getParentFile().mkdirs();
					instance.saveResource(new_folder_path+"\\"+name, false);
				}else {
					configFile.getParentFile().mkdirs();
					instance.saveResource(name, false);
				}
	        }
			config = new YamlConfiguration();
			try {
				config.load(configFile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	public void save() {
		try {
			config.save(configFile);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public FileConfiguration get() {
        return config;
    }
	
	public ConfigurationSection getConfigurationSection(String arg0) {
		return config.getConfigurationSection(arg0);
	}
}
