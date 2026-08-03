package main.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class BlockRegen{
	public String world = "world";
	public int amount = 1;
	public int timer = 60;
	public List<BlockLocation> blocks_locs = new ArrayList<BlockLocation>();
	public BlockRegen(ConfigurationSection c) {
		if(c.contains("world")) {
			this.world = c.getString("world");
		}
		if(c.contains("amount")) {
			this.amount = c.getInt("amount");
		}
		if(c.contains("timer")) {
			this.timer=c.getInt("timer");
		}
		if(!c.contains("locations")) {
			Settings.debug("{BLOCK REGEN} [HEZAR] Not has location!");
			return;
		}else {
			if(c.getList("locations") != null) {
				
				List<String> locs = c.getStringList("locations");
				for(String s : locs) {
					if(s.matches("(.*):(.*)")) {
						List<BlockLocation> bl = addRegion(s);
						blocks_locs.addAll(bl);
						
					}else {
						blocks_locs.add(new BlockLocation(s));
					}
					
				}
				Settings.debug("{BLOCK REGEN} [HEZAR] Blocks regen added at |"+world+"| amount = "+amount);
			}else {
				Settings.debug("{BLOCK REGEN} [HEZAR] Has not locations after check!");
				return;
			}
			
		}
	}
	public List<BlockLocation> addRegion(String s){
		List<BlockLocation> bl = new ArrayList<BlockLocation>();
		String[] s_arr= s.split(":");
		String floc = s_arr[0];
		String tloc = s_arr[1];
		String[] from_locs = floc.split(",");
		String[] to_locs = tloc.split(",");
		Integer from_x = Integer.parseInt(from_locs[0]);
		Integer from_y = Integer.parseInt(from_locs[1]);
		Integer from_z = Integer.parseInt(from_locs[2]);
		
		Integer to_x = Integer.parseInt(to_locs[0]);
		Integer to_y = Integer.parseInt(to_locs[1]);
		Integer to_z = Integer.parseInt(to_locs[2]);
		
		
		
		int x_dist;
		int y_dist;
		int z_dist;
		
		boolean from_x_big = from_x>=to_x;
		boolean from_y_big = from_y>=to_y;
		boolean from_z_big = from_z>=to_z;
		
		int smallest_x;
		int smallest_y;
		int smallest_z;
		
		if(from_x_big) {
			x_dist = from_x-to_x+1;
			smallest_x = to_x;
		}else {
			x_dist = to_x - from_x+1;
			smallest_x = from_x;
		}
		if(from_y_big) {
			y_dist = from_y-to_y+1;
			smallest_y = to_y;
		}else {
			y_dist = to_y - from_y+1;
			smallest_y =from_y;
		}
		if(from_z_big) {
			z_dist = from_z-to_z+1;
			smallest_z = to_z;
		}else {
			z_dist = to_z - from_z+1;
			smallest_z = from_z;
		}
		
		
		
		
		
		for(int i = 0; i<x_dist;i++) {
			for(int j=0;j<y_dist;j++) {
				for(int h=0;h<z_dist;h++) {
					bl.add(new BlockLocation(smallest_x+i,smallest_y+j,smallest_z+h));
				}
					
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return bl;
	}
	public void putLocation(String s) {
		if(s.matches("(.*):(.*)")) {
			List<BlockLocation> bl = addRegion(s);
			blocks_locs.addAll(bl);
			
		}else {
			blocks_locs.add(new BlockLocation(s));
		}
	}
	public void cmdAddLoc(String s) {
		putLocation(s);
		amount++;
	}
	
}