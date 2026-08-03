package main.Futh;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import main.hezar;
import main.minigames.miner.ActiveMiner;
import main.minigames.miner.TimedMinerPapa;

public class TimeManager {

	static List<TimedMinerPapa> actives = new ArrayList<>();

	static List<BukkitTask> tasks = new ArrayList<>();
	
	
	public static void addActive(TimedMinerPapa tmp) {
		actives.add(tmp);
	}
	public static void activate(TimedMinerPapa tmp) {
		tasks.add(Bukkit.getScheduler().runTask(hezar.inst, tmp));
		
	}
}
