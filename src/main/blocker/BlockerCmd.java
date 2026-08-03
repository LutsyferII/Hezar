package main.blocker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.Settings;

public class BlockerCmd {
	List<String> cmds = new ArrayList<>();
	String world = "world";
	String name = "";
	public BlockerCmd(String nm,List<String> list) {
		Settings.debug(" Загрузка запрета комманд. Мир:"+nm, "e");
		this.name = nm;
		this.cmds = new ArrayList<>();
		this.cmds.addAll(list);
	}
	public BlockerCmd(String nm,String cmd) {
		Settings.debug(" Загрузка запрета комманды мира. Мир:"+nm, "e");
		this.name = nm;
		this.cmds = new ArrayList<>();
		this.cmds.add(cmd);
		
	}
	public BlockerCmd(String w,String nm,List<String> list) {
		Settings.debug(" Загрузка запрета комманды региона мир:"+w+" регион:"+nm+" кол-во cmds:"+list.size(), "e");
		this.world = w;
		this.name = nm;
		this.cmds = new ArrayList<>();
		this.cmds.addAll(list);
	}
	public BlockerCmd(String w,String nm,String cmd) {
		Settings.debug(" Загрузка запрета комманды региона мир:"+w+" регион:"+nm+" кол-во cmds:"+1, "e");
		this.world = w;
		this.name = nm;
		this.cmds = new ArrayList<>();
		this.cmds.add(cmd);
		
	}
	
	public String getName() {
		return name;
	}
	public String getWorld() {
		return world;
	}
	public boolean isWorld(String w) {
		if(world.equals(w)) {
			return true;
		}
		return false;
	}
	public List<String> getCmds(){
		return cmds;
	}
	public boolean isEqual(String nm) {
		if(name.equals(nm)) {
			return true;
		}
		return false;
	}
	public boolean hasBan(String s) {
		for(String cmd : cmds) {
			if(cmd.equals(s)) {
				return true;
			}
		}
		return false;
	}
	public boolean justCheck(String s) {
		return cmds.contains(s);
	}
}
