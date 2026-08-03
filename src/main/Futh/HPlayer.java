package main.Futh;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HPlayer{
	public Player p;
	public float walk_speed;
	public HPlayer(Player p) {
		this.p=p;
	}
	public void changeSpeedMovement(float a,CommandSender sender) {
		this.walk_speed=a;
		float base_speed=p.getWalkSpeed();
		if(base_speed==this.walk_speed) {
			sender.sendMessage("Бля, чел, у тебя и так такая скорость.");
		}
		p.setWalkSpeed(this.walk_speed > 1F ? 1F : this.walk_speed< 0F ? 0F : this.walk_speed);
	}
	
	
}