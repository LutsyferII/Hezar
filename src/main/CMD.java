package main;

import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Configs.TochConfig;
import main.hezar;
import main.CommandBlock.CBConfig;
import main.Utils.CompleteHelper;
import main.blocker.CmdConfig;
import main.blocker.UseConfig;
import main.Futh.HPlayer;
import main.Futh.Lore;
import main.Futh.LootBoxMechanic.LootManager;
import main.Shop.ZShopManager;
import main.misc.calc;
import Configs.BlockConfig;
import Configs.CompassManager;
import Configs.OhotaConfig;
import Configs.ReItemConfig;
import main.blocks.BlockZarj;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.manager.ItemManager;


public class CMD implements CommandExecutor, TabCompleter {
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	public CMD(){
		hezar.inst.getCommand("hezar").setExecutor(this);
		hezar.inst.getCommand("hezar").setTabCompleter(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("Количесмтво аргументов: "+Integer.toString(args.length));
		boolean is_console=true;
		HPlayer hplayer = null;
		if (sender instanceof Player){
			is_console=false;
			Player p = (Player) sender;
			hplayer=hezar.getHPlayer(p);
			if(hplayer == null) {
				hezar.setHPlayer(p);
				hplayer=hezar.getHPlayer(p);
			}
			if (!sender.hasPermission("hezar.admin")) {
				sender.sendMessage("Ты чо, фемка? Права чекни!");
				return false;
			}
			
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")|| args[0].equalsIgnoreCase("перезарядка")) {
				hezar.inst.reload();
				if (sender instanceof Player) sender.sendMessage("Перезарядка, тр-тр-тр, Бах-Бах-Бах!!!");
				return true;
			}
			
			
			
		}else if(args.length==2) {
			if(args[0].equalsIgnoreCase("calc")) {
				if(args[1].matches("(.*),(.*)")) {
					String[] s_arr = args[1].split(",");
					List<Double> stat_list = new ArrayList<>();
					for(int i = 0; s_arr.length>i;i++) {
						stat_list.add(Double.parseDouble(s_arr[i]));
					}
					calc.getStats(sender, stat_list);
				}else if(args[1].equalsIgnoreCase("armor")) {
					
				}
				
				
				
				
			}else if(args[0].equalsIgnoreCase("shop")) {
				String shop_name = args[1];
				
				if(ZShopManager.isShop(shop_name)) {

					Player p = (Player) sender;
					p.openInventory(ZShopManager.getShop(shop_name).getPages().get(0).getInventory()) ;
				}else {
					Settings.debug("Нет такого магазина.");
				}
				
				
				
			}
			else if(args[0].equalsIgnoreCase("speed")&&!args[1].equalsIgnoreCase("check")) {
				if(this.isNumeric(args[1])) {
					float gg=Float.parseFloat(args[1]);
					if (is_console==false&&hplayer!=null) {
						sender.sendMessage(hplayer.p.getName());
						//hezar.online.get(p);
						hplayer.changeSpeedMovement(gg,sender);
						sender.sendMessage("Так, все заебок!!!");
					}else {
						Bukkit.getConsoleSender().sendMessage("§5[huy] §eБлять, дятел ты ёбанный, ты как скорость консоли менять собрался?!");
					}
					return true;
				}	//TochConfig.load(Configs.tochConfig.get());
				sender.sendMessage("Дошло до, но проверку номера не прошло!!!");
			}else if ((args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")|| args[0].equalsIgnoreCase("перезарядка"))) {
				if(args[1].equalsIgnoreCase("cmdblocks")||args[1].equalsIgnoreCase("cb")) {
					Configs.reload_cmdblock();
					CBConfig.load(Configs.cmdblockConfig.get());
					if (sender instanceof Player) sender.sendMessage("Перезарядка командных блоков, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("toch")||args[1].equalsIgnoreCase("tc")) {
					Configs.reload_toch();
					TochConfig.load(Configs.tochConfig.get());
					if (sender instanceof Player) sender.sendMessage("Перезарядка точилок, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("blocks")||args[1].equalsIgnoreCase("bl")) {
					Configs.reload_block();
					BlockConfig.cancelTask();
					BlockConfig.load();
					BlockConfig.runRegen();
					if (sender instanceof Player) sender.sendMessage("Перезарядка блоков, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("ohota")) {
					Configs.reload_ohota();
					OhotaConfig.load();
					if (sender instanceof Player) sender.sendMessage("Перезарядка охоты, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("shop")) {
					Configs.reload_shop();
					ZShopManager.loadShops();
					if (sender instanceof Player) sender.sendMessage("Перезарядка магазинов, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("banser")) {
					Configs.reload_blocker();;
					UseConfig.load(Configs.usebanConfig.get());
					CmdConfig.load(Configs.cmdbanConfig.get());
					if (sender instanceof Player) sender.sendMessage("Перезарядка блокировщика, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("compass")) {
					Configs.reload_compass();
					CompassManager.load();
					if (sender instanceof Player) sender.sendMessage("Перезарядка компаса, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("lootbox")||args[1].equalsIgnoreCase("loot")) {
					Configs.reload_lootbox();;
					LootManager.load();
					if (sender instanceof Player) sender.sendMessage("Перезарядка Лутбоксов, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("lore")||args[1].equalsIgnoreCase("lr")) {
					Configs.reload_lore();
					Lore.load(Configs.loreConfig.get());
					if (sender instanceof Player) sender.sendMessage("Перезарядка Лора, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else if(args[1].equalsIgnoreCase("reitem")||args[1].equalsIgnoreCase("ri")) {
					Configs.reload_reitem();;
					ReItemConfig.load(Configs.reitemConfig.get());
					if (sender instanceof Player) sender.sendMessage("Перезарядка Трансформации предметов, тр-тр-тр, Бах-Бах-Бах!!!");
					return true;
				}else {
					if (sender instanceof Player) sender.sendMessage("Нет такой перезарядки.");
					return true;
				}
				
				
				
				
				
			}else if (args[0].equalsIgnoreCase("save") && (args[1].equalsIgnoreCase("blocks")||args[1].equalsIgnoreCase("bl"))) {
				for(BlockZarj bz : BlockConfig.blocks) {
					BlockConfig.writeBlocks(bz);
				}
				return true;
			}else if(args[0].equalsIgnoreCase("speed")&&args[1].equalsIgnoreCase("check")) {
				if (is_console==false&&hplayer!=null) {
					sender.sendMessage("Твоя скорость = "+hplayer.p.getWalkSpeed()+"");
					//Censor.online.get(p);
				}else {
					Bukkit.getConsoleSender().sendMessage("§5[Censor] §eБлять, дятел ты ёбанный, ты как скорость консоли проверять собрался?!");
				}
				return true;
			}
			
			
			
		}else if(args.length==3) {

			if(args[0].equalsIgnoreCase("calc")) {
				if(args[1].equalsIgnoreCase("damage")) {
					if(args[2].matches("[0-9]*")){
						Double arg = Double.parseDouble(args[2]);
						Double dam = calc.getDamageFromP()*5;
						Double her = (dam/arg);
						Double sto = arg/2;
						Double coef = 1-her;
						
						Double new_damage = (coef*sto)/her;
						Double next_umn =1-Math.min(20,Math.max(new_damage-(4*arg/8),new_damage/5))/25;
						Double new_dam = dam/next_umn;
						her = (new_dam/arg);
						coef = 1-her;
						new_damage = (coef*sto)/her;
						sender.sendMessage("Нужно брони для выдерживания "+arg+" урона до "+dam+" |"+new_damage+"|");
						return true;
					}

				}
			}
			if(args[0].equalsIgnoreCase("speed")&&args[1].equalsIgnoreCase("check")) {
				
				Player p = Bukkit.getPlayer(args[2]);
				if(!p.isOnline()) {
					if(is_console) {
						Bukkit.getConsoleSender().sendMessage("§1"+"Игрок не в сети.");
					}else {
						sender.sendMessage("Игрок не в сети.");
					}
					return true;
				}
				if(is_console) {
					Bukkit.getConsoleSender().sendMessage("§1"+"Скорость игрока "+p.getName()+" = "+p.getWalkSpeed());
				}else {
					sender.sendMessage("Скорость игрока "+p.getName()+" = "+p.getWalkSpeed());
				}
				return true;
			}
				
				
				
			
				
		}else if(args.length==4) {
			if(args[0].equalsIgnoreCase("calc")) {
				if(args[1].equalsIgnoreCase("damage")) {
					if(args[2].matches("[0-9]*")){
						if(args[3].matches("[0-9]*")){
							Double raw_damage = Double.parseDouble(args[2]);
							Double armor = Double.parseDouble(args[3]);
							Double minecraft_armor = 30.0;
							Double armor_coef = 1.0;
							if(armor<30.0) {
								minecraft_armor=armor;
							}else {
								armor_coef = armor/30;
							}
							Double new_armor= armor*armor_coef;
							
							Double my_damage = raw_damage *(1-(new_armor/(new_armor+(raw_damage*10))));// 15 * (1-(a/(a+(15))))
							sender.sendMessage("Измененный дамаг = §4"+my_damage);
							Double final_dmg = my_damage * (1-Math.min(20,Math.max(minecraft_armor-(4*my_damage/8),minecraft_armor/5))/25);
							
							sender.sendMessage("Финальный урон при броне §e"+armor+"§f, его трансформации в §e"+new_armor+"§f и входящем уроне §4"+raw_damage+"§f равен §c"+final_dmg);
							return true;
							
						}
					}

				}
			}
		}
		
		
		
		else if(args.length==0) {
			if(is_console==true) {
				Bukkit.getConsoleSender().sendMessage("§5[huy] §eВводить что-то для лохов, да?");
			}else {
			sender.sendMessage("Упоротый ты дятел, аргументы где?!");
			}
		}else {
			if(is_console==true) {
				Bukkit.getConsoleSender().sendMessage("§5[huy] §eНу вот и чего ты ожидал? Куда столько аргументов?");
			}else {
				sender.sendMessage("Ну куда столько. куда?!!!");
			}
			
		}
		sender.sendMessage("Чота ни тааааак!1111!");
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return CompleteHelper.filter(args, "reload", "speed","save","calc","shop");
		}
		if (args.length == 2 && args[0].equalsIgnoreCase("speed")) {
			return CompleteHelper.filter(args, "0.1","0.2","0.5");
		}else if(args.length == 2 && args[0].equalsIgnoreCase("save")) {
			return CompleteHelper.filter(args, "blocks");
		}else if(args.length == 2 && args[0].equalsIgnoreCase("calc")) {
			return CompleteHelper.filter(args,"damage", "0,0,0,0");
		}else if(args.length == 2 && (args[0].equalsIgnoreCase("reload")||args[0].equalsIgnoreCase("rl"))) {
			return CompleteHelper.filter(args, "shop", "cmdblocks", "toch", "blocks", "ohota","compass","banser","lootbox","lore","reitem");
		}
		return null;
	}
	
	
	
}