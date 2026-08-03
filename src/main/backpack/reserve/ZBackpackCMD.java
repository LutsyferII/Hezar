package main.backpack.reserve;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.item.NBTItem;
import main.Settings;
import main.hezar;
import main.Utils.CompleteHelper;
import net.Indyuce.mmoitems.MMOItems;

public class ZBackpackCMD implements CommandExecutor, TabCompleter {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	
	public ZBackpackCMD(){
		hezar.inst.getCommand("zbackpack").setExecutor(this);
		hezar.inst.getCommand("zbackpack").setTabCompleter(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length==1) {
			
			
			
		}else if(args.length==2) {
			if(args[0].equalsIgnoreCase("open")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("4?");
					return false;
				}
				String uuid_s = args[1];
				
				if(ZBackpackManager.hasBackpack(uuid_s)) {
					Player p = (Player) sender;
					p.openInventory(ZBackpackManager.getBackpack(uuid_s).getInventory());
					return true;
				}else {
					sender.sendMessage("4!");
					return false;
				}
				
				
				
			}else if(args[0].equalsIgnoreCase("give")) {

				if(!(sender instanceof Player)) {
					sender.sendMessage("4?");
					return false;
				}

				String uuid_s = args[1];
				
				if(ZBackpackManager.hasBackpack(uuid_s)) {
					Player p = (Player) sender;
					
					ZBackpack backpack = ZBackpackManager.getBackpack(uuid_s);
					
					Inventory inv = backpack.getInventory();
					
					int num = inv.getSize()/9;
					Settings.debug("{ZBACKPACK} inv num == "+num, "a");
					ItemStack item = null;
					switch(num) {
						case 3: 
							item = MMOItems.plugin.getItem("MISCELLANEOUS", "BACKPACK_3");
							break;

						case 4: 
							item = MMOItems.plugin.getItem("MISCELLANEOUS", "BACKPACK_4");
							break;
						case 5: 
							item = MMOItems.plugin.getItem("MISCELLANEOUS", "BACKPACK_5");
							break;
						case 6: 
							item = MMOItems.plugin.getItem("MISCELLANEOUS", "BACKPACK_6");
							break;
						
					}
					if(item==null) {
						sender.sendMessage("4!");
						return false;
						
					}
					NBTItem nbt_item = NBTItem.get(item);
					ItemTag tag = new ItemTag("ZARJ_BACKPACK_UUID",uuid_s);
					nbt_item.addTag(tag);
					item.setItemMeta(nbt_item.toItem().getItemMeta());
					p.getInventory().addItem(item);
					return true;
					
				}else {
					sender.sendMessage("4!");
					return false;
				}
			}else if(args[0].equalsIgnoreCase("info")) {
				String uuid_s = args[1];
				
				if(ZBackpackManager.hasBackpack(uuid_s)) {
					ZBackpack backpack = ZBackpackManager.getBackpack(uuid_s);
					sender.sendMessage("{BP} created: "+backpack.created.format(formatter));
					sender.sendMessage("{BP} creator: "+backpack.creator);
					sender.sendMessage("{BP} last update: "+backpack.last_open.format(formatter));
					int has = 0;
					for(ItemStack i : backpack.inv.getContents()) {
						if(i!=null) {
							has++;
						}
					}
					sender.sendMessage("{BP} size: "+has+"/"+backpack.inv.getSize());
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		return false;
		
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length==1) {
			return CompleteHelper.filter(args,"open","give","info");
			
		}else if(args.length==2) {
			return CompleteHelper.filter(args,ZBackpackManager.getStringUUIDs().toArray(new String[0]));
			
		}else {
			return null;
		}
	}


}
