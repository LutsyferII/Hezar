package main.blocker;

import java.util.ArrayList;
import java.util.List;

import main.Settings;

public class BlockerUse {

	List<MmoItemContainer> uses = new ArrayList<>();
	String world = "world";
	String name = "";
	public BlockerUse(String nm,List<String> list) {
		this.name = nm;
		this.uses = new ArrayList<>();
		for(String s : list) {
			uses.add(new MmoItemContainer(s));
		}
	}
	public BlockerUse(String nm,String cmd) {
		this.name = nm;
		this.uses = new ArrayList<>();
		uses.add(new MmoItemContainer(cmd));
		
	}

	public BlockerUse(String w,String nm,List<String> list) {
		this.world=w;
		this.name = nm;
		this.uses = new ArrayList<>();
		for(String s : list) {
			uses.add(new MmoItemContainer(s));
		}
	}
	public BlockerUse(String w,String nm,String cmd) {
		this.world=w;
		this.name = nm;
		this.uses = new ArrayList<>();
		uses.add(new MmoItemContainer(cmd));
		
	}
	public String getName() {
		return name;
	}
	public List<MmoItemContainer> getCmds(){
		return uses;
	}
	public boolean isEqual(String nm) {
		if(name.equals(nm)) {
			return true;
		}
		return false;
	}
	public boolean isWorld(String w) {
		if(world.equals(w)) {
			return true;
		}
		return false;
	}
	public boolean hasBan(String type,String id) {
		for(MmoItemContainer use : uses) {
			//Settings.debug("ПРОВЕРКА ЮЗА ПО ТИПУ ТИП: "+use.type+" ID: "+use.id);
			if(use.checkType(type)) {
				if(use.checkId(id)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	class MmoItemContainer{
		String type = "CONSUMABLE";
		String id = "";
		public MmoItemContainer(String s) {
			s = s.replaceAll(" ", "");
			String[] parts = s.split("\\.");
			if(parts.length==0) {
				Settings.debug("Не верный предмет в юз конфиге банов", 4);
				return;
			}else if(parts.length==1) {
				id = parts[0];
			}else if(parts.length==2) {
				type=parts[0];
				id=parts[1];
			}
		}
		public boolean checkType(String typ) {
			if(type.equalsIgnoreCase(typ)) {
				return true;
			}
			return false;
		}
		public boolean checkId(String i) {
			if(id.equalsIgnoreCase(i)) {
				return true;
			}
			return false;
		}
		
		
	}
}

