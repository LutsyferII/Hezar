package main.backpack.reserve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import main.Settings;
import main.hezar;
import main.Utils.Filer;

public class ZBackpackManager {
	static LinkedHashMap<UUID,ZBackpack> backpacks = new LinkedHashMap<>();
	
	static LinkedHashMap<Player, List<String>> send = new LinkedHashMap<>();
	
	
	static BukkitTask task;
	
	static final int HALF_H = 36000;
	
	public static void createBackpack(ItemStack item,String name) {
		ZBackpack bp = new ZBackpack(item,name);
		backpacks.put(bp.getUUID(),bp);
	}
	public static ZBackpack createAndReturnBackpack(ItemStack item,String name) {
		ZBackpack bp = new ZBackpack(item, name);
		backpacks.put(bp.getUUID(),bp);
		return bp;
	}
	
	public static boolean hasBackpack(String ui) {
		UUID uuid = UUID.fromString(ui);
		return backpacks.containsKey(uuid);
	}
	public static ZBackpack getBackpack(String ui) {
		UUID uuid = UUID.fromString(ui);
		return backpacks.get(uuid);
	}
	
	public static List<String> getStringUUIDs(){
		List<String> uuid_s = new ArrayList<>();
		for(UUID uuid : backpacks.keySet()) {
			uuid_s.add(uuid.toString());
		}
		
		return uuid_s;
	}
	
	public static void load() {
		Settings.debug("{RESERVE BACKPACKS} Start load ", "c");
		File folder = new File(hezar.inst.getDataFolder(), "saved_backpacks");
		if(!folder.exists()) {
			Settings.debug("{RESERVE BACKPACKS} Create saved backpacks folder", "c");
			folder.mkdirs();
		}else {
			List<File> files = Arrays.asList(folder.listFiles());

			int num = 0;
			for(File file : files) {

					if(!checkFile(file)) {
						Settings.debug("{RESERVE BACKPACKS} Поймана папка или файл с . "+file.getName(), "4");
						continue;
					}
					try {
						//Settings.debug("{RESERVE BACKPACKS} Чтение из файла "+file.getName(), "c");
						FileInputStream file_stream = new FileInputStream(file);

						//Settings.debug("{RESERVE BACKPACKS} Доступно считываемых байтов в файловом потоке "+file_stream.available(), "c");
						
						//Settings.debug("{RESERVE BACKPACKS} Доступно считываемых байтов в обьектном потоке "+obj_stream.available(), "c");
						
						
						
						byte[] bytes = file_stream.readAllBytes();
						
						
						file_stream.close();
						
						
						ZBackpack bp = new ZBackpack(bytes);
						
						LocalDateTime now = LocalDateTime.now();
						
						long days = Duration.between(bp.last_open, now).toDays();
						
						if(days>60) {
							bp.getInventory().clear();
						}else if(days>45){
							Player p = Bukkit.getServer().getPlayer(bp.creator);
							if(send.containsKey(p)) {
								send.get(p).add("Рюкзак созданный "+bp.getCreateDate()+" последний раз открывался "+bp.getLastDate()+" будет очищен через "+(60-days)+"дней");
							}else {
								List<String> to_put = new ArrayList<>();
								to_put.add("Рюкзак созданный "+bp.getCreateDate()+" последний раз открывался "+bp.getLastDate()+" будет очищен через "+(60-days)+"дней");
								send.put(p,to_put);
							}
						}
						
						backpacks.put(bp.getUUID(),bp);
						
						
						//Settings.debug("{RESERVE BACKPACKS} Loaded backpakc. UUID "+bp.getUUID(), "a");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				num++;
			}
			Settings.debug("{RESERVE BACKPACKS} Loaded "+num+" backpakcs. List = "+backpacks.size(), "c");
		}
		
		
		task = Bukkit.getScheduler().runTaskTimer(hezar.inst, new Runnable() {
            @Override
            public void run() {
            	save();
            }
		},HALF_H, HALF_H);
		
		
		
		
	}
	
	
	public static boolean hasMassage(Player p) {
		return send.containsKey(p);
	}
	
	public static List<String> getMassage(Player p){
		return send.get(p);
	}
	
	
	private static boolean checkFile(File file) {
		if(file.isDirectory()) {
			return false;
		}else if(file.getName().matches("(.*)\\.(.*)")){
			String name = file.getName();
			String type = name.split("\\.")[1];
			if(type.equalsIgnoreCase("zbackpack")) {
				return true;
			}else {
				return false;
			}
		}else{
			return true;
		}
	}
	
	public static void save() {
		File folder = new File(hezar.inst.getDataFolder(), "saved_backpacks");
		if(!folder.exists()) {
			folder.mkdirs();
		}
		if(backpacks.isEmpty()) {
			return;
		}
		for(ZBackpack backpack : backpacks.values()) {
			File b_file = new File(folder,backpack.getStringUUID()+".zbackpack");
			
			try {
				if(!b_file.exists()) {
					b_file.createNewFile();
				}

				FileOutputStream file_stream = new FileOutputStream(b_file);
				//ObjectOutputStream obj_stream = new ObjectOutputStream(file_stream);
				
				byte[] bytes = backpack.toByte();
				
				
				
				file_stream.write(bytes);
				
				
				/*
				obj_stream.writeInt(backpack.getInventory().getSize());
				String uuid_s = backpack.uuid.toString();
				obj_stream.writeUTF(uuid_s);
				
				obj_stream.writeObject(backpack);
				
				
				
				*/
				//obj_stream.close();
				file_stream.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
}
