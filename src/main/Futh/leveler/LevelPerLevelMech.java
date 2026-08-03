package main.Futh.leveler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import main.Settings;
import main.Futh.ZItem;

public class LevelPerLevelMech{
	public String name;
	public int counton = 0;
	public int chanceon = 0;
	public int exp = 0;
	public List<ZItem> itemlist = new ArrayList<>();
	public Random rand = new Random();
	public LevelPerLevelMech(ConfigurationSection c) {
		this.name=c.getName();
		if(c.contains("counton")) {
			this.counton = c.getInt("counton");
		}
		if(c.contains("chanceon")) {
			this.chanceon = c.getInt("chanceon");
		}
		if(!c.contains("drop")) {
			Settings.debug("§4[huy] Ошибка загрузки дропа с множителем уровня. Отсутствует дроп в "+this.name);
			return;
		}
		if(c.contains("exp")&&c.isInt("exp")) {
			exp=c.getInt("exp");
		}
		for(int i=0;i<c.getStringList("drop").size();i++) {
			itemlist.add(new ZItem(c.getStringList("drop").get(i)));
		}
		
		
		
	}
	public boolean hasExp() {
		if(exp>0) {
			return true;
		}else {
			return false;
		}
	}
	public int getExp() {
		return exp;
	}
	public boolean willnewdrop(ZItem oi,int integer) {
		Double drop = rand.nextDouble(100);
		Double newchance = (oi.chance/100)*(chanceon*integer)+oi.chance;
		Settings.debug("[huy] §2 Новый шанс ="+newchance+" на предмет "+oi.type+" "+oi.iid);
		if(drop<=newchance && drop>0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public int howMuchNewDrop(ZItem oi, int integer) {
		if(oi.maxamount==null || oi.maxamount==oi.amount) {
			return Math.round(oi.amount+(oi.amount/100)*(integer*counton));
		}else {
			int newamount = rand.nextInt(oi.amount, oi.maxamount+1);
			return Math.round(newamount+(newamount/100)*(integer*counton));
			
		}
	}
	
}