package main.minigames.miner;

public class MineField {
	boolean is_mine = false;
	FieldPos pos;
	int x = 1;
	int y = 1;
	int near = 0;
	public MineField(boolean mine, int x, int y) {
		this.is_mine = mine;
		pos = new FieldPos(x,y);
	}
	public MineField( int x, int y) {
		pos = new FieldPos(x,y);
	}
	public MineField(FieldPos p) {
		pos = p;
	}
	public MineField(boolean mine,FieldPos p) {
		this.is_mine = mine;
		pos = p;
	}
	public void undermine() {
		is_mine = true;
	}
	public void setNear(int n) {
		near = n;
	}
	public boolean checkField() {
		return is_mine;
	}
}
