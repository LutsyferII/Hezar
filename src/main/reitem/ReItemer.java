package main.reitem;

import main.Futh.ZItem;

public class ReItemer {
	String id;
	ZItem from;
	ZItem to;
	public ReItemer(String tag,ZItem f,ZItem t) {
		id=tag;
		from=f;
		to=t;
	}
	
	public boolean checkid(String other_id) {
		return id.equalsIgnoreCase(other_id);
	}
	
}
