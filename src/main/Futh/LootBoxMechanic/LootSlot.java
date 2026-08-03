package main.Futh.LootBoxMechanic;

import main.Futh.ZItem;

public class LootSlot {
	Double from = 0.0;
	Double to = 100.0;
	ZItem item;
	public LootSlot(Double f,Double t,ZItem zi) {
		this.from = f;
		this.to = t;
		this.item = zi;
	}
	public boolean isNeeded(Double val) {
		if(val>from&&val<=to) {
			return true;
		}else if(val==0.0&&from==0.0) {
			return true;
		}
		return false;
	}
	public ZItem getItem() {
		return item;
	}
}
