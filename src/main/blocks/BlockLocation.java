package main.blocks;
public class BlockLocation{
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public BlockLocation(String s) {
		String[] cords = s.split(",");
		this.x = Integer.parseInt(cords[0]);
		this.y = Integer.parseInt(cords[1]);
		this.z = Integer.parseInt(cords[2]);
	}
	public BlockLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}