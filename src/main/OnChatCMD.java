package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import main.hezar;
import main.Utils.CompleteHelper;
import main.Futh.HPlayer;

public class OnChatCMD{
	public static String new_massage;
	public static String must_massage;
	public static Player p;
	public static String massage;
	public OnChatCMD(Player p, String m) {
		this.p=p;
		this.massage=m;
	}
	
	
}
