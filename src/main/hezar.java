package main;




import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Configs.BlockConfig;
import Configs.CompassManager;
import Configs.OhotaConfig;
import Configs.ReItemConfig;
import Configs.TestConfig;
import Configs.TochConfig;
import compass.CompassListener;
import main.Futh.BalanceEventer;
import main.Futh.HPlayer;
import main.Futh.Lore;
import main.Futh.LootBoxMechanic.LootManager;
import main.Shop.ShopEvents;
import main.Shop.ZShopManager;
import main.Sorter.SorterListener;
import main.Utils.ZEconomy;
import main.backpack.BackpackListener;
import main.backpack.reserve.ZBackpackManager;
import main.blocker.BansEventer;
import main.blocker.CmdConfig;
import main.blocker.UseConfig;
import main.blocks.BlockZarj;
import main.blocks.load.BlockSavedLoader;
import main.minigames.miner.MinerManager;
import main.misc.calc;
import main.reitem.ReItemListener;
import mmfix.MmListen;
import net.Indyuce.mmocore.MMOCore;
import main.HezarListen;
import main.CommandBlock.CBConfig;


public class hezar extends JavaPlugin {
	/* 
	 	███████████			 █	  	 ████   ███████
	 			██			█ █	     █	█		 █
	 		  ██		   █   █ 	 ████		 █
	 		██			  ███████	 ██			 █
	 	  ██			 █		 █	 █ █	█    █
	 	███████████		█		  █	 █  █	 ████	
	  
	 */
	
	
	
	
	public static hezar inst;
	public static Map<Player, HPlayer> online = new HashMap<>();
	
	@Override
	public void onEnable() {
		inst = this;
		Configs.reload();
		Settings.load(Configs.mainConfig.getConfigurationSection("settings"));
		//OhotaConfig.load();
		Bukkit.getPluginManager().registerEvents(new HezarListen(), this);
		Bukkit.getPluginManager().registerEvents(new ShopEvents(), this);
		Bukkit.getPluginManager().registerEvents(new BansEventer(), this);
		Bukkit.getPluginManager().registerEvents(new BalanceEventer(), this);
		Bukkit.getPluginManager().registerEvents(new BackpackListener(), this);
		Bukkit.getPluginManager().registerEvents(new CompassListener(), this);
		Bukkit.getPluginManager().registerEvents(new SorterListener(), this);
		Bukkit.getPluginManager().registerEvents(new ReItemListener(), this);
		Bukkit.getPluginManager().registerEvents(new MmListen(), this);
		
		//LootManager.load(Configs.lootboxConfig.get());
		
		
		
		
		ZBackpackManager.load();
		
		
		//BlockConfig.load();
		//for(BlockZarj bz : BlockConfig.blocks) {
		//	Bukkit.getConsoleSender().sendMessage("§5[huy] ЧТЕНИЕ ИЗ ФАЙЛА !"+bz.mmoid);
		//	BlockConfig.readBlocks(bz);}
		reload();
		BlockSavedLoader.load();
		BlockSavedLoader.Subload();
		BlockConfig.startSaving();
		MMOCore.plugin.reloadConfig(); 
	}
	@Override
	public void onLoad() {
		
	}
	
	public void reload() {
		
		
		Configs.reload();
		
		
		Lore.load(Configs.loreConfig.get());
		Settings.load(Configs.mainConfig.getConfigurationSection("settings"));
		Message.load(Configs.mainConfig.getConfigurationSection("messages"));
		HEngine.reload();
		ZEconomy.setupEconomy();
		
		
		ReItemConfig.load(Configs.reitemConfig.get());
		
		LootManager.load();
		
		OhotaConfig.load();
		BlockConfig.cancelTask();
		BlockConfig.load();
		BlockConfig.runRegen();
		CBConfig.load(Configs.cmdblockConfig.get());
		TochConfig.load(Configs.tochConfig.get());
		calc.load();
		TestConfig.load();
		ZShopManager.loadShops();
		CmdConfig.load(Configs.cmdbanConfig.get());
		UseConfig.load(Configs.usebanConfig.get());
		CompassManager.load();
		
		MinerManager.load(Configs.minersConfig.get());
		
		
	}

	@Override
	public void onDisable() {
		for(BlockZarj bz : BlockConfig.blocks) {
			BlockConfig.writeBlocks(bz);
		}
		ZBackpackManager.save();
		Bukkit.getConsoleSender().sendMessage("§5[huy] §6Старичок, иди на шашлычок!");
		
	}
	public static void setHPlayer(Player p) {
		HPlayer hpl=new HPlayer(p);
		online.put(p,hpl);
	}
	public static HPlayer getHPlayer(Player p) {
		return online.get(p);
	}
	
	
	
	
}