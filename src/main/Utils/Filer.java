package main.Utils;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;


public class Filer {
	
	
	
	
	public Filer(JavaPlugin instance, File folder) {
		
	}
	

	public static List<File> getFiles(File folder){
		List<File> files = new ArrayList<>();
		
		if(folder.isDirectory()) {
			File[] fls = folder.listFiles();
			files.addAll(Arrays.asList(fls ));
		}
		
		return files;
	}
	public static List<File> getFiles(JavaPlugin instance, String folder){
		List<File> files = new ArrayList<>();
		
		File nf = new File(instance.getDataFolder(),folder);
		
		File[] fls = nf.listFiles();
		files.addAll(Arrays.asList(fls));
		
		
		
		return files;
	}
}
