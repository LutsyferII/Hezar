package main.minigames.miner;

public class FieldPos {
	
	int x = 1;
	int y = 1;
	
	public FieldPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean isSosed(FieldPos pos) {
		if((pos.x==x||pos.x+1==x||pos.x-1==x)&&(pos.y==y||pos.y+1==y||pos.y-1==y)&&(pos.x!=x||pos.y!=y)) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
