package main.CommandBlock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class CMDBlock{
	public Location loc;
	public String cmd;
	public boolean asconsole = false;
	public CMDBlock(ConfigurationSection c){
		cmd = c.getString("cmd");
		if(c.contains("asconsole")) {
			asconsole = c.getBoolean("asconsole");
		}
		String world_s = c.getString("loc").split(":")[0];
		String[] lloc = c.getString("loc").split(":")[1].split(",");
		World world = Bukkit.getServer().getWorld(world_s);
		loc = new Location(world,Integer.parseInt(lloc[0]),Integer.parseInt(lloc[1]),Integer.parseInt(lloc[2]));
		
	}
}