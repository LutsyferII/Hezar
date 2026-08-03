package main;

import org.bukkit.Bukkit;

import main.CMD;
import main.OnChatCMD;
import main.backpack.reserve.ZBackpackCMD;
import main.blocks.BlockCMD;
import main.convert.ConvertCMD;

public class HEngine {
	
	public static int syncPlayerTaskDelay;
	public static CMD hCMD = new CMD();
	public static ConvertCMD convCMD = new ConvertCMD();
	public static BlockCMD blocks = new BlockCMD();
	public static ZBackpackCMD backpack_cmd = new ZBackpackCMD();
	
	public static void reload() {

		Bukkit.getLogger().info("[Huy] Ёнгине инсталид!11231...");
	}
}