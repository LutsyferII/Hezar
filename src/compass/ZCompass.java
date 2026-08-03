package compass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import main.Settings;

public class ZCompass {
	String world = "world";
	Location loc;
	String tag ="null";
	String desc = "а‘тјр мудак, забыл описание добавить.";
	public ZCompass(ConfigurationSection c) {
		if(c.contains("tag")&&c.isString("tag")) {
			tag = c.getString("tag");
		}
		if(c.contains("world")&&c.isString("world")) {
			String w = c.getString("world");
			if(Bukkit.getServer().getWorld(w) == null){
				Settings.debug("{COMPASS}ќшибка, указан несуществующий мир в "+tag+"!", 4);
				return;
			}
		}
		if(  (  !c.contains("loc")&&!c.contains("location")  )  ||  (!c.isString("loc")&&!c.contains("location"))  ) {
			Settings.debug("{COMPASS}ќшибка, отсутсвует локаци€ в "+tag+"!", 4);
			return;
		}else {
			String location = "0,0,0";
			if(c.contains("loc")&&c.isString("loc")) {
				location = c.getString("loc");
			}else {
				location = c.getString("location");
			}
			String[] locs = location.split(",");
			if(locs.length<3) {
				Settings.debug("{COMPASS}ќшибка, неверна€ конфигураци€ локации в "+tag+"!", 4);
				return;
			}
			loc = new Location(Bukkit.getWorld(world),Integer.parseInt(locs[0]),Integer.parseInt(locs[1]),Integer.parseInt(locs[2]));
		}
		if(c.contains("desc")&&c.isString("desc")) {
			desc = c.getString("desc");
		}else if(c.contains("decription")&&c.isString("decription")) {
			desc = c.getString("decription");
		}
		
	}
	public boolean checkTag(String tag) {
		if(this.tag.equalsIgnoreCase(tag)) {
			return true;
		}else {
			return false;
		}
	}
	public Location getLocation() {
		return loc;
	}
	
	
}
