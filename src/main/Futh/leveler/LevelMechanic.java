package main.Futh.leveler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.manager.profession.ProfessionManager;

public class LevelMechanic{
	public String name;
	public List<ItemStack> itemstack;
	public String profession = "ohota";
	public List<LevelPodMech> mechlist = new ArrayList<>();
	public List<LevelPerLevelMech> onlm = new ArrayList<>();
	static ProfessionManager proffs = MMOCore.plugin.professionManager;
	public LevelMechanic(ConfigurationSection c) {
		mechlist = new ArrayList<>();
		onlm = new ArrayList<>();
		if(c.contains("profession")&&c.isString("profession")) {
			String prof = c.getString("profession");
			if(proffs.has(prof)) {
				this.profession=c.getString("profession");
			}else {
				this.profession="main";
			}
		}
		
		ConfigurationSection pdch = c.getConfigurationSection("podmech");
		Set<String> setofkeys = pdch.getKeys(false);
		for(String s : setofkeys) {
			LevelPodMech mech = new LevelPodMech(pdch.getConfigurationSection(s));
			this.mechlist.add(mech);
		}
		
		
		this.name=c.getName();
		if(this.mechlist.size()==0) {
			return;
		}
		if(c.contains("perlevel")) {
			ConfigurationSection onlevelmech = c.getConfigurationSection("perlevel");
			Set<String> setofkeys_onlevel = onlevelmech.getKeys(false);
			for(String s : setofkeys_onlevel) {
				this.onlm.add(new LevelPerLevelMech(onlevelmech.getConfigurationSection(s)));
			}
		}
		
		
		
	}
	public LevelPodMech getPodMech(int integer) {
		int cur = 0;
		for(LevelPodMech mech : mechlist) {
			if(mech.getLevel()>cur&&mech.getLevel()<=integer) {
				cur=mech.getLevel();
			}
		}
		if(cur==0) {
			return null;
		}
		for(LevelPodMech mech : mechlist) {
			if(mech.levelEqual(cur)) {
				return mech;
			}
		}
		return null;
		 
	}
	public boolean haveEnouchLevel(int integer) {
		for(LevelPodMech mech : mechlist) {
			if(mech.hasEnoughLevel(integer)) {
				return true;
			}
		}
		return false;
	}
	static public ProfessionManager getProfManager() {
		return proffs;
	}
	
	
}