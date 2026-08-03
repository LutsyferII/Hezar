package main.blocks.load;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileLoadBlock {
	List<Location> locs = new ArrayList<Location>();
	
	public FileLoadBlock(File file) {
		
		
	}
	
	
	public List<Location> getLocations(){
		return locs;
	}

}
