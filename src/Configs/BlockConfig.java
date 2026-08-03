package Configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import main.Configs;
import main.Settings;
import main.hezar;
import main.blocks.BlockRegen;
import main.blocks.BlockZarj;
import main.blocks.load.BlockSavedLoader;

public class BlockConfig{
	static FileConfiguration block_config;
	static Set<String> setofkeys;
	public static List<BlockZarj> blocks = new ArrayList<>();
	public static Map<Integer,List<Location>> loc_map = new HashMap<Integer,List<Location>>();
	public static void start() {
		
	}
	public static void load() {
		loc_map = new HashMap<Integer,List<Location>>();
		for(BlockZarj bz : blocks) {
			loc_map.put(bz.myid, bz.locs_placed);
		}
		
		blocks= new ArrayList<>();
		block_config = Configs.blockConfig.get();
		setofkeys = block_config.getKeys(false);
		for(String s : setofkeys) {
			blocks.add(new BlockZarj(block_config.getConfigurationSection(s)));
			Settings.debug("�������� ����"+blocks.get(blocks.size()-1).mmoid);
		}
		
		for(BlockZarj bz : blocks) {
			if(!loc_map.isEmpty()) {
				if(loc_map.containsKey(bz.myid)) {
					bz.locs_placed.addAll(loc_map.get(bz.myid));
				}
			}else {
				if(!BlockSavedLoader.isEmpty()) {
					BlockSavedLoader.SoloLoad(bz);
				}
			}
		}
	}
 	public static void cancelTask() {
		if(blocks.isEmpty()) {
			return;
		}
		for(BlockZarj bz : blocks) {
			if(!bz.regen_task.isEmpty()) {
				for(BukkitTask bt : bz.regen_task) {
					bt.cancel();
				}
			}
		}
	}
	public static void runRegen() {
		if(!blocks.isEmpty()) {
			Settings.debug("{START REGEN}");
			for(BlockZarj bz : blocks) {
				Settings.debug("{REGEN} "+ bz.mmoid );
				bz.startRegen();
			}
		}
	}
	public static void createBlockFileIfNot(BlockZarj bz) {
		File folder = new File(hezar.inst.getDataFolder(),"blocks_saved");
		String fileName = bz.myid+".yml";
		File userFile = new File(folder,fileName);
		if (!folder.exists() || folder.length() == 0) {
			folder.mkdirs();
        }
		if (!userFile.exists() || userFile.length() == 0) {
			YamlConfiguration configyml=YamlConfiguration.loadConfiguration(userFile);
			configyml.createSection(bz.mmoid);
			try {
				configyml.save(userFile);
			}catch (Exception e) {
	            Bukkit.getConsoleSender().sendMessage("[CENSOR](FILE ERROR) "+e.toString());
	        }
			
			
			
        }
		
	}
	public static void writeBlocks(BlockZarj bz) {
		if(bz.locs_placed.isEmpty()) {
			return;
		}
		createBlockFileIfNot(bz);
		File folder = new File(hezar.inst.getDataFolder(),"blocks_saved");
		String fileName = bz.myid+".yml";
		File userFile = new File(folder,fileName);
		YamlConfiguration configyml=YamlConfiguration.loadConfiguration(userFile);
		
		//ConfigurationSection csec=configyml.getConfigurationSection(bz.mmoid);
			//Bukkit.getConsoleSender().sendMessage("�5[huy] �1 TEST DONOS 1");
		List<String> templist = new ArrayList<String>();
		for(Location loc : bz.locs_placed) {
			templist.add(loc.getWorld().getName()+":"+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
		}
		
		
		configyml.set(bz.mmoid, templist);
		try {
			configyml.save(userFile);
		}catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("[hezar](FILE ERROR) "+e.toString());
        }
		
	}
	public static void readBlocks(BlockZarj bz) {
		if(BlockSavedLoader.hasBlock(bz.getMyId())) {
			Collection<String> locs = BlockSavedLoader.getBlock(bz.getMyId());
			Settings.debug("---------------------------------");
			Settings.debug("read block: "+bz.name, "c");
			//bz.locs_placed.addAll(locs);
			for(String loc_s : locs) {
				String[] temparr=loc_s.split(":");
				String[] cords = temparr[1].split(",");
				Location loc = new Location(Bukkit.getWorld(temparr[0]),Integer.parseInt(cords[0]),Integer.parseInt(cords[1]),Integer.parseInt(cords[2]));
				//Settings.debug("{BLOCK LOAD} load "+this.name, "a");
				if(loc.getBlock().getType()!=bz.item_block.getType()) {
					loc.getBlock().setType(bz.item_block.getType());
					if(bz.bd!=null) {
						loc.getBlock().setBlockData(bz.bd);
						
					}
				}
				loc.getBlock().setMetadata("ZARJ_BLOCK_ID", bz.meta_value);
				bz.locs_placed.add(loc);
			}
		}
	}
	public static void startSaving() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.runTaskTimer(hezar.inst, new Runnable() {
	        @Override
	        public void run() {
	        	for(BlockZarj bz : blocks) {
	        		writeBlocks(bz);
	        	}
	        }
		},144000,144000);
	}
	
	public static boolean haveBlockById(int id) {
		for(BlockZarj block : blocks) {
			if(block.equalMyId(id)) {
				return true;
			}
		}
		return false;
	}
	public static boolean haveBlock(String id) {
		for(BlockZarj block : blocks) {
			if(block.equalByName(id)) {
				return true;
			}
		}
		return false;
	}
	public static void putRegen(String id,String loc) {
		/*
		if(block_config.contains(id)) {
			ConfigurationSection c = block_config.getConfigurationSection(id);
			if(c.contains("Regen")) {
				Settings.debug("{BLOCKS} test 3 = "+c.getConfigurationSection("Regen").getKeys(false).iterator().next());
				ConfigurationSection regen_sec = c.getConfigurationSection("Regen").getConfigurationSection(c.getConfigurationSection("Regen").getKeys(false).iterator().next());
				if(regen_sec.contains("locations")) {
					List<String> locs = regen_sec.getStringList("locations");
					locs.add(loc);
					regen_sec.set("locations", locs);
				}
			}
		}
		*/
		for(BlockZarj block : blocks) {
			if(block.equalByName(id)) {
				if(!block.regens.isEmpty()) {
					BlockRegen regen = block.regens.get(0);
					regen.cmdAddLoc(loc);
					
				}
				if(block.hasRegenConfig()) {
					ConfigurationSection c = block.regen_sec;
					Settings.debug("{BLOCKS} test 3 = "+c.getKeys(false).iterator().next());
					ConfigurationSection regen_sec = c.getConfigurationSection(c.getKeys(false).iterator().next());
					if(regen_sec.contains("locations")) {
						List<String> locs = regen_sec.getStringList("locations");
						locs.add(loc);
						regen_sec.set("locations", locs);
					}
					if(regen_sec.contains("amount")&&regen_sec.isInt("amount")) {
						int am = regen_sec.getInt("amount");
						am++;
						regen_sec.set("amount", am);
					}
				}
				block.reloadRegen();
			}
		}
			
		
		
		Configs.blockConfig.save();
		
	}
	public static BlockZarj getBlockZarj(int id) {
		for(BlockZarj block : blocks) {
			if(block.equalMyId(id)) {
				return block;
			}
		}
		
		return null;
	}
	public static BlockZarj getBlockZarj(String name) {
		for(BlockZarj block : blocks) {
			if(block.equalByName(name)) {
				return block;
			}
		}
		
		return null;
	}
	public static List<BlockZarj> getBlocks(){
		return blocks;
	}


}