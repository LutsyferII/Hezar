package main.minigames.miner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import Configs.Errors;
import main.Futh.TimedThing;

public class Miner{
	String name = "мины";
	String lore = "Описание";
	int size = 4;
	int mines = 1;
	static Random rand = new Random();
	List<MineField> fields = new ArrayList<MineField>();
	
	
	public Miner(int s, int m, String n, String l) {
		name = n;
		lore = l;
		size = s;
		mines = m;
		
		int x = 1;
		int y = 1;
		for(int i = 0; i<size*size;i++) {
			FieldPos pos = new FieldPos(x,y);
			fields.add(new MineField(pos));
			if(x<4) {
				x++;
			}else {
				x=1;
				y++;
			}
		}
		List<Integer> have_mines = new ArrayList<Integer>();
		List<FieldPos> pos_mines = new ArrayList<FieldPos>();
		
		if(mines>=size*size) {
			Errors.error("Ошибка, количество мин больше или равно количеству полей");
			return;
		}
		
		for(int i = 0; i<mines;i++) {
			int mine = doMine(have_mines);
			have_mines.add(mine);
			MineField mina = fields.get(mine);
			pos_mines.add(mina.pos);
			mina.undermine();
			
		}
	
		
		
		for(Integer mine : have_mines) {
			MineField mina = fields.get(mine);
			for(MineField field : fields) {
				if(!field.is_mine&&mina.pos.isSosed(field.pos)) {
					field.near++;
				}
			}
		}
		
		
		
	}
	private int doMine(Collection<Integer> have) {
		int mine_pos = 0;
		int pos = rand.nextInt(fields.size()-1);
		
		if(have.contains(pos)) {
			mine_pos = doMine(have);
		}else {
			mine_pos = pos;
		}
		
		return mine_pos;
	}
	
	
	
}
