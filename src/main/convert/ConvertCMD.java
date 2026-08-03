package main.convert;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.sk89q.worldguard.WorldGuard;

import main.Settings;
import main.hezar;
import main.Futh.HPlayer;
import main.Utils.CompleteHelper;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.manager.ItemManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class ConvertCMD implements CommandExecutor, TabCompleter {
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	public ConvertCMD(){
		hezar.inst.getCommand("convert").setExecutor(this);
		hezar.inst.getCommand("convert").setTabCompleter(this);
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean is_console=true;
		HPlayer hplayer = null;
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("§5[huy] §eДятел, это только для игроков");
			return false;
			
		}
		Player p = (Player) sender;
		if (sender instanceof Player){
			is_console=false;
			hplayer=hezar.getHPlayer(p);
			if(hplayer == null) {
				hezar.setHPlayer(p);
				hplayer=hezar.getHPlayer(p);
			}
			/*
			sender.sendMessage("Отправитель: "+p.getName());
			if (!sender.hasPermission("hezar.admin")) {
				sender.sendMessage("Ты чо, фемка? Права чекни!");
				return false;
			}
			*/
			
		}
		//ItemManager itemManager = MMOItems.plugin.getItems();
		//MMOItem mmocucoin = itemManager.getMMOItem(MMOItems.plugin.getTypes().get("MATERIAL"), "CUCOIN");
		ItemStack cucoin = MMOItems.plugin.getItem(MMOItems.plugin.getTypes().get("MATERIAL"), "CUCOIN");
		//MMOItem mmosilvercoin = itemManager.getMMOItem(MMOItems.plugin.getTypes().get("MATERIAL"), "SILCERCOIN");
		ItemStack silvercoin = MMOItems.plugin.getItem(MMOItems.plugin.getTypes().get("MATERIAL"), "SILCERCOIN");
		//MMOItem mmogoldcoin = itemManager.getMMOItem(MMOItems.plugin.getTypes().get("MATERIAL"), "GOLDCOIN");
		ItemStack goldcoin = MMOItems.plugin.getItem(MMOItems.plugin.getTypes().get("MATERIAL"), "GOLDCOIN");
		
		String typecucoin = "CUCOIN";//CraftItemStack.asNMSCopy(cucoin).u().c("MMOITEMS_ITEM_ID").e_();
		String typesilvercoin = "SILCERCOIN";//CraftItemStack.asNMSCopy(silvercoin).u().c("MMOITEMS_ITEM_ID").e_();
		String typegoldcoin = "GOLDCOIN";//CraftItemStack.asNMSCopy(goldcoin).u().c("MMOITEMS_ITEM_ID").e_();
		
		if(p.hasPermission("hezar.convert.vip")) {
			if (args.length == 1) {
				
				if ((args[0].equalsIgnoreCase("silver") || args[0].equalsIgnoreCase("s"))) {
					PlayerInventory playerinv = p.getInventory();
					
					int colvocucoins = this.getAmount(typecucoin, playerinv);
					Settings.debug("Кол-во медных монет = "+colvocucoins);
					//cucoin.setAmount(64);
					if(colvocucoins>=64) {
						Settings.debug("Найдены медные монеты, пляшем дальше");
						//cucoin.setAmount(64);
						this.removeItemWithNBT(playerinv,64,typecucoin);
						//playerinv.removeItem(cucoin);
						silvercoin.setAmount(1);
						playerinv.addItem(silvercoin);
						return true;
					}else {
						sender.sendMessage("§4У вас не хватает медных монет!");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("gold") || args[0].equalsIgnoreCase("g")) {
					
					PlayerInventory playerinv = p.getInventory();
					int colvosicoins = this.getAmount(typesilvercoin, playerinv);
					if(colvosicoins>=64) {
						//silvercoin.setAmount(64);
						Settings.debug("Найдены серебряные монеты, пляшем дальше");
						this.removeItemWithNBT(playerinv,64,typesilvercoin);
						//playerinv.removeItem(silvercoin);
						goldcoin.setAmount(1);
						playerinv.addItem(goldcoin);
						return true;
					}else {
						sender.sendMessage("§4У вас не хватает серебряных монет!");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("old")) {
					PlayerInventory playerinv = p.getInventory();
					int colvocucoins = this.getAmount(typecucoin, playerinv);
					int colvosicoins = this.getAmount(typesilvercoin, playerinv);
					int colvogoldcoins = this.getAmount(typegoldcoin, playerinv);
					boolean havecu = colvocucoins>0;
					boolean havesi = colvosicoins>0;
					boolean havegold = colvogoldcoins>0;
					if(!havecu&&!havesi&&!havegold) {
						sender.sendMessage("§4Нет монет!");
						return false;
					}
					if(havecu) {
						this.removeItemWithNBT(playerinv,colvocucoins,typecucoin);
						cucoin.setAmount(colvocucoins);
						playerinv.addItem(cucoin);
					}
					if(havesi) {
						this.removeItemWithNBT(playerinv,colvosicoins,typesilvercoin);
						silvercoin.setAmount(colvosicoins);
						playerinv.addItem(silvercoin);
					}
					if(havegold) {
						this.removeItemWithNBT(playerinv,colvogoldcoins,typegoldcoin);
						goldcoin.setAmount(colvogoldcoins);
						playerinv.addItem(goldcoin);
					}
					return true;
				}else if(args[0].equalsIgnoreCase("all")) {
					PlayerInventory playerinv = p.getInventory();
					int colvocucoins = this.getAmount(typecucoin, playerinv);
					int colvosicoins = this.getAmount(typesilvercoin, playerinv);
					boolean havecu = colvocucoins>=64;
					boolean havesi = colvosicoins>=64;
					if(!havecu&&!havesi) {
						sender.sendMessage("§4Не достаточно монет для конвертации!");
						return false;
					}
					if(havecu){
						int colvo = colvocucoins/64;
						this.removeItemWithNBT(playerinv,colvo*64,typecucoin);
						silvercoin.setAmount(colvo);
						playerinv.addItem(silvercoin);
					}
					colvosicoins = this.getAmount(typesilvercoin, playerinv);
					havesi = colvosicoins>=64;
					if(havesi) {
						int colvo = colvosicoins/64;
						this.removeItemWithNBT(playerinv,colvo*64,typesilvercoin);
						goldcoin.setAmount(colvo);
						playerinv.addItem(goldcoin);
					}
					return true;
				}
			}else if(args.length==2) {
				if ((args[0].equalsIgnoreCase("silver") || args[0].equalsIgnoreCase("s"))&&this.isNumeric(args[1])) {
					
					PlayerInventory playerinv = p.getInventory();
					int colvocucoins = this.getAmount(typecucoin, playerinv);
					int arg2 = Integer.parseInt(args[1]);
					if(colvocucoins>=(64*arg2)) {
						//cucoin.setAmount(64*arg2);
						Settings.debug("Найдены медные монеты, пляшем дальше");
						//for(int i =0;i<arg2;i++) {
						this.removeItemWithNBT(playerinv,64*arg2,typecucoin);
						//playerinv.removeItem(cucoin);
						//}
						silvercoin.setAmount(arg2);
						playerinv.addItem(silvercoin);
						return true;
					}else {
						sender.sendMessage("§4У вас не хватает медных монет!");
						return false;
					}
				}else if((args[0].equalsIgnoreCase("gold") || args[0].equalsIgnoreCase("g"))&&this.isNumeric(args[1])) {
					
					PlayerInventory playerinv = p.getInventory();
					int colvosicoins = this.getAmount(typesilvercoin, playerinv);
					int arg2 = Integer.parseInt(args[1]);
					if(colvosicoins>=(64*arg2)) {
						//silvercoin.setAmount(64*arg2);
						Settings.debug("Найдены серебряные монеты, пляшем дальше");
						//for(int i =0;i<arg2;i++) {
						this.removeItemWithNBT(playerinv,64*arg2,typesilvercoin);
						//}
						goldcoin.setAmount(arg2);
						playerinv.addItem(goldcoin);
						return true;
					}else {
						sender.sendMessage("§4У вас не хватает серебряных монет!");
						return false;
					}
				}else if(args[0].equalsIgnoreCase("old")) {
					PlayerInventory playerinv = p.getInventory();
					if(args[1].equalsIgnoreCase("cu")) {
						int colvocucoins = this.getAmount(typecucoin, playerinv);
						boolean havecu = colvocucoins>0;
						if(havecu) {
							this.removeItemWithNBT(playerinv,colvocucoins,typecucoin);
							cucoin.setAmount(colvocucoins);
							playerinv.addItem(cucoin);
							return true;
						}
						return false;
					}else if(args[1].equalsIgnoreCase("si")) {
						int colvosicoins = this.getAmount(typesilvercoin, playerinv);
						boolean havesi = colvosicoins>0;
						if(havesi) {
							this.removeItemWithNBT(playerinv,colvosicoins,typesilvercoin);
							silvercoin.setAmount(colvosicoins);
							playerinv.addItem(silvercoin);
							return true;
						}
						return false;
					}else if(args[1].equalsIgnoreCase("gold")) {
						int colvogoldcoins = this.getAmount(typegoldcoin, playerinv);
						
						
						boolean havegold = colvogoldcoins>0;
						
						
						if(havegold) {
							this.removeItemWithNBT(playerinv,colvogoldcoins,typegoldcoin);
							goldcoin.setAmount(colvogoldcoins);
							playerinv.addItem(goldcoin);
							return true;
						}
						return false;
					}else {
						sender.sendMessage("§4Неверный аргумент №2!");
						return false;
					}
					
				}else if((args[0].equalsIgnoreCase("silver") || args[0].equalsIgnoreCase("s"))&&args[1].equalsIgnoreCase("all")) {
					PlayerInventory playerinv = p.getInventory();
					int colvocucoins = this.getAmount(typecucoin, playerinv);
					if(colvocucoins<64) {
						sender.sendMessage("§4Не хватает медных монет!");
						return false;
					}
					int colvo = colvocucoins/64;
					Settings.debug("Найдены медные монеты, пляшем дальше");
					this.removeItemWithNBT(playerinv,colvo*64,typecucoin);
					silvercoin.setAmount(1*colvo);
					playerinv.addItem(silvercoin);
					return true;
				}else if((args[0].equalsIgnoreCase("gold") || args[0].equalsIgnoreCase("g"))&&args[1].equalsIgnoreCase("all")) {
					PlayerInventory playerinv = p.getInventory();
					int colvosicoins = this.getAmount(typesilvercoin, playerinv);
					if(colvosicoins<64) {
						sender.sendMessage("§4Не хватает медных монет!");
						return false;
					}
					int colvo = colvosicoins/64;
					Settings.debug("Найдены медные монеты, пляшем дальше");
					this.removeItemWithNBT(playerinv,colvo*64,typesilvercoin);
					goldcoin.setAmount(1*colvo);
					playerinv.addItem(goldcoin);
					return true;
				}
			}else if(args.length==0) {
				PlayerInventory playerinv = p.getInventory();
				
				
				int colvocucoins = this.getAmount(typecucoin, playerinv);
				
				if(colvocucoins>=64) {
					//cucoin.setAmount(64);
					Settings.debug("Найдены медные монеты, пляшем дальше");
					this.removeItemWithNBT(playerinv,64,typecucoin);
					silvercoin.setAmount(1);
					playerinv.addItem(silvercoin);
					return true;
				}
				int colvosicoins = this.getAmount(typesilvercoin, playerinv);
				if(colvosicoins>=64) {
					//silvercoin.setAmount(64);
					Settings.debug("Найдены серебряные монеты, пляшем дальше");
					this.removeItemWithNBT(playerinv,64,typesilvercoin);
					goldcoin.setAmount(1);
					playerinv.addItem(goldcoin);
					return true;
				}
				
				sender.sendMessage("§4У вас не хватает монет!");
				return false;
				
			
				
				
			}else {
				if(is_console==true) {
					Bukkit.getConsoleSender().sendMessage("§5[huy] §eНу вот и чего ты ожидал? Куда столько аргументов?");
				}else {
					sender.sendMessage("Ну куда столько, куда?!!!");
				}
				
			}
		}else {
			if(args.length==1&&args[0].equalsIgnoreCase("old")) {
				PlayerInventory playerinv = p.getInventory();
				int colvocucoins = this.getAmount(typecucoin, playerinv);
				int colvosicoins = this.getAmount(typesilvercoin, playerinv);
				int colvogoldcoins = this.getAmount(typegoldcoin, playerinv);
				boolean havecu = colvocucoins>0;
				boolean havesi = colvosicoins>0;
				boolean havegold = colvogoldcoins>0;
				if(!havecu&&!havesi&&!havegold) {
					sender.sendMessage("§4Нет монет!");
					return false;
				}
				if(havecu) {
					this.removeItemWithNBT(playerinv,colvocucoins,typecucoin);
					cucoin.setAmount(colvocucoins);
					playerinv.addItem(cucoin);
				}
				if(havesi) {
					this.removeItemWithNBT(playerinv,colvosicoins,typesilvercoin);
					silvercoin.setAmount(colvosicoins);
					playerinv.addItem(silvercoin);
				}
				if(havegold) {
					this.removeItemWithNBT(playerinv,colvogoldcoins,typegoldcoin);
					goldcoin.setAmount(colvogoldcoins);
					playerinv.addItem(goldcoin);
				}
				return true;
			}else {
				p.sendMessage("§4Конвертация вам не доступна!");
				return false;
			}
		}
		
		sender.sendMessage("Чота ни тааааак!1111!");
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return CompleteHelper.filter(args, "silver", "gold", "old","all");
		}
		
		if (args.length == 2 && !args[0].equalsIgnoreCase("old")) {
			return CompleteHelper.filter(args, "1","8","16","all");
		}else if (args.length == 2 && args[0].equalsIgnoreCase("old")) {
			return CompleteHelper.filter(args, "cu","si","gold");
		}
		return null;
	}
	public int getAmount(String s, PlayerInventory pi) {
		int amount=0;
		for (ItemStack item :pi.getContents()) {
			if(item!=null) {
				NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
				if(nbtbase.e("MMOITEMS_ITEM_ID")) {
					String lol = CraftItemStack.asNMSCopy(item).u().c("MMOITEMS_ITEM_ID").e_();
					if(!lol.equals(null));{
						if(lol.equals(s)){
							amount +=item.getAmount();
						}
					}
				}
				
			}
            
        }
		return amount;
	}
	public static void removeItemWithNBT(PlayerInventory pi,int amount, String s) {
		int am = amount;
		for (ItemStack item :pi.getContents()) {
			NBTTagCompound nbtbase = CraftItemStack.asNMSCopy(item).u();
			
			if(nbtbase.e("MMOITEMS_ITEM_ID")&&nbtbase.c("MMOITEMS_ITEM_ID").e_().equals(s)) {
				int itemam = item.getAmount();
				Settings.debug("Тест, хуест.["+"] am="+am+" кол-во в стаке="+itemam);
				if(am<=0) {
					break;
				}
				if(itemam<=am) {
					pi.removeItem(item);
					am-=itemam;
				}else{
					item.setAmount(itemam-am);
					break;
				}
				if(am<=0) {
					break;
				}
					
				
			
			}
		}
	}

	
	
}