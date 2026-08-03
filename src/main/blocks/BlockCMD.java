package main.blocks;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import Configs.BlockConfig;
import main.Settings;
import main.hezar;
import main.Utils.CompleteHelper;

public class BlockCMD implements CommandExecutor, TabCompleter {

	public BlockCMD() {
		hezar.inst.getCommand("zblocks").setExecutor(this);
		hezar.inst.getCommand("zblocks").setTabCompleter(this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		

		boolean is_console=true;

		if (sender instanceof Player){
			is_console=false;
			Player p = (Player) sender;
			sender.sendMessage("Отправитель: "+p.getName());
			if (!sender.hasPermission("hezar.admin")) {
				sender.sendMessage("Ты чо, фемка? Права чекни!");
				return false;
			}
			
		}

		if (args.length == 1) {
			
		}else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("give")) {
				if(is_console) {
					Settings.console_send("{BLOCK} Всё в порядке с башкой?",4);
					return false;
				}else {
					String id = args[1];
					Player p = (Player) sender;
					if(BlockConfig.haveBlock(id)) {
						ItemStack item =BlockConfig.getBlockZarj(id).createItem();
						p.getInventory().addItem(item);
					}else {
						p.sendMessage("§4Нет такого блока!");
					}
					
				}
				
				
			}
			
		}else if(args.length == 3) {
			if(args[0].equalsIgnoreCase("regen")) {
				if(args[1].equalsIgnoreCase("add")) {
					if(!is_console) {
						String id = args[2];
						if(BlockConfig.haveBlock(id)) {
							Player p = (Player) sender;
							Block block = p.getTargetBlock(Sets.newHashSet(Material.AIR,Material.WATER), 0);
							Location loc = block.getLocation();
							String l = loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
							Settings.debug("{BLOCK} test_2 "+l);
							BlockConfig.putRegen(id, l);
						}
						
						
					}
					
				}
			}else if(args[0].equalsIgnoreCase("give")) {
				String name = args[1];
				Player p = Bukkit.getPlayer(name);
				if(p!=null) {
					String id = args[2];
					if(BlockConfig.haveBlock(id)) {
						ItemStack item =BlockConfig.getBlockZarj(id).createItem();
						p.getInventory().addItem(item);
					}else {
						if(is_console) {
							Settings.console_send("Блока "+id+" не существует!",4);
							
						}else {
							sender.sendMessage("§4Нет такого блока!");
						}
						return false;
					}
				}else {
					if(is_console) {
						Settings.console_send("Игрока "+name+" нет на сервере!",4);
						
					}else {
						sender.sendMessage("§4Нет такого игрока на сервере!");
					}
					return false;
				}
			}
			
		}else if(args.length==4) {
			if(args[0].equalsIgnoreCase("regen")) {
				if(args[1].equalsIgnoreCase("add")) {
					String id = args[2];
					String loc = args[3];
					Settings.debug("{BLOCK} test_1");
					if(BlockConfig.haveBlock(id)) {

						Settings.debug("{BLOCK} test_2 "+id);
						BlockConfig.putRegen(id, loc);
					}
					
					
					
				}
			}
		}
		
		
		
		
		
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return CompleteHelper.filter(args, "regen");
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("regen")) {
			return CompleteHelper.filter(args, "add");
		}
		if (args.length == 3 && args[0].equalsIgnoreCase("regen")&& args[1].equalsIgnoreCase("add")) {
			return CompleteHelper.filter(args, "name");
		}
		if (args.length == 4 && args[0].equalsIgnoreCase("regen")&& args[1].equalsIgnoreCase("add")) {
			return CompleteHelper.filter(args, "0,0,0");
		}
		return null;
	}

	

}
