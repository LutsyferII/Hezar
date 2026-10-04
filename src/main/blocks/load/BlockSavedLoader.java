package main.blocks.load;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import Configs.BlockConfig;
import Configs.Errors;
import main.Settings;
import main.hezar;
import main.blocks.BlockZarj;
import org.bukkit.util.Vector;

public class BlockSavedLoader {
	static Map<Integer,List<String>> locs_s = new HashMap<>();
    public static final Map<String, BlockHolder> pendingBlocks = new ConcurrentHashMap<>();
	public static void load() {
		File folder = new File(hezar.inst.getDataFolder(),"blocks_saved");
		for(File saved_block : folder.listFiles()) {
			String file_name = saved_block.getName();
			if(saved_block.isDirectory()) {
				Errors.error("is directory, not file! "+file_name);
				continue;
			}else {
				Settings.debug("{BLOCKS}{LOAD} Load file = "+file_name);
			}
			String[] splited_name = file_name.split("\\.");
			if(!splited_name[1].equals("yml")) {
				Errors.error("is not yml!");
				continue;
			}
			String name = splited_name[0];
			if(!name.matches("[0-9]*")) {
				Errors.error("wrong id! ["+name+"]");
				continue;
			}
			int id = Integer.parseInt(name);
			YamlConfiguration c =YamlConfiguration.loadConfiguration(saved_block);
			for(String key : c.getKeys(false)) {
				if(c.isList(key)) {
					List<String> s_locs = c.getStringList(key);
					locs_s.put(id,s_locs);
				}
			}
			Settings.debug("{BLOCS} loaded block = "+id);
		}
	}
	public static boolean hasBlock(int id) {
		return locs_s.containsKey(id);
	}
	public static List<String> getBlock(int id) {
		return locs_s.get(id);
	}
	public static void removeBlock(int id) {
		locs_s.remove(id);
	}
	public static boolean isEmpty() {
		return locs_s.isEmpty();
	}
	public static void Subload() {
        for(Entry<Integer,List<String>> block : locs_s.entrySet()) {
        	if(!BlockConfig.haveBlockById(block.getKey())) {
        		continue;
        	}

			Settings.debug("{BLOCK LOAD} [HEZAR] ");
        	int id = block.getKey();
        	BlockZarj zb = BlockConfig.getBlockZarj(id);
        	List<String> locs = block.getValue();
        	final int chunkSize;
        	if(locs.size()<25) {
        		chunkSize = locs.size();
        	}else {
        		chunkSize = 25;
        	}
        	int totalLocs = locs.size();
        	
        	for (int i = 0; i < totalLocs; i += chunkSize) {
                final int start = i;
                final int end = Math.min(i + chunkSize, totalLocs-1);
                

                List<String> locsString = locs.subList(start, end);
                Bukkit.getScheduler().runTaskAsynchronously(hezar.inst, () -> {

                    ChunkedBlockLoader task = prepareLocs(start,chunkSize,totalLocs,locsString,zb);
                    Bukkit.getScheduler().runTask(hezar.inst, task);
                });
                //
                
                //executor.submit(task);
            }
        }
        //executor.shutdown();
    }
	static ChunkedBlockLoader prepareLocs(int i, int chunkSize, int totalLocs, List<String> locs, BlockZarj zb){
		
        List<Location> locsReal = new ArrayList<>();
        for(String locString : locs) {
    		String[] temparr = locString.split(":");
            String[] cords = temparr[1].split(",");
            Location locObj = new Location(Bukkit.getWorld(temparr[0]),
                    Integer.parseInt(cords[0]), // x-координата
                    Integer.parseInt(cords[1]), // y-координата
                    Integer.parseInt(cords[2])); // z-координата
            if(locObj.getWorld() != null){
                locsReal.add(locObj);
            }else{
                Vector vec = locObj.toVector();
                if(!pendingBlocks.containsKey(temparr[0])){
                    pendingBlocks.put(temparr[0],new BlockHolder(zb.getMyId()));
                }
                pendingBlocks.get(temparr[0]).addVector(vec);
            }
        }
        ChunkedBlockLoader task = new ChunkedBlockLoader(locsReal, zb);
    	return task;
	}
	public static void SoloLoad(BlockZarj block,Collection<Location> locs) {
		ExecutorService executor = Executors.newCachedThreadPool(); 
		int chunkSize = 25;
    	if(locs.size()<25) {
    		chunkSize = locs.size();
    	}
    	int totalLocs = locs.size();
    	for (int i = 0; i < totalLocs; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, totalLocs);
            ChunkedBlockLoader task = new ChunkedBlockLoader(locs, block);
            executor.submit(task);
        }
    	executor.shutdown();
	}
	public static void SoloLoad(BlockZarj block) {
		ExecutorService executor = Executors.newCachedThreadPool(); 
		List<String> locs = locs_s.get(block.getMyId());
		if(locs == null || locs.isEmpty()) {
			return;
		}
		int chunkSize = 25;
    	if(locs.size()<25) {
    		chunkSize = locs.size();
    	}
    	int totalLocs = locs.size();
    	for (int i = 0; i < totalLocs; i += chunkSize) {
            int start = i;
            int end = Math.min(i + chunkSize, totalLocs);

            List<String> locsString = locs.subList(start, end);
            ChunkedBlockLoader task = prepareLocs(start,chunkSize,totalLocs,locsString,block);
            executor.submit(task);
        }
    	executor.shutdown();
	}

}
