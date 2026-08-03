package main.Futh;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import Configs.Errors;
import main.Settings;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.manager.TypeManager;

public class ZItem{
	public double chance = 100.0;
	public boolean is_vanilla = true;
	public String type;
	public String iid;
	public int amount = 1;
	public Integer maxamount;
	public static Random rand = new Random();
	public static TypeManager types =  MMOItems.plugin.getTypes();
	
	public ZItem(String s) {
		if(!s.matches("(.*)\\{(.*)")||!s.matches("(.*)\\}(.*)")) {
			Errors.error("Неверное определение предмета!");
			return;
		}
		String[] f_splited = s.split("\\{");
		if(!f_splited[0].equalsIgnoreCase("vanilla")&&!f_splited[0].equalsIgnoreCase("mmoitem")) {
			Errors.error("Неверный тип предмета! ("+f_splited[0]+")");
			return;
		}
		String[] s_splited = f_splited[1].split("\\}");
		String stats = s_splited[0];
		
		if(!stats.matches("(?i)(.*)(;|^| +)id=(.*)")) {
			Errors.error("Не указано id предмета!");
			return;
		}
		this.iid = doStat(stats, "id=");
		if(s_splited.length>1) {
			String[] other = s_splited[1].split(" +");
			if(other.length>2) {
				String colvo = other[1];
				if(isNumeric(colvo)) {
					this.amount=Integer.parseInt(colvo);
				}else if(colvo.matches("(.*)-(.*)")){
					String[] amounts = colvo.split("-");
					this.amount=Integer.parseInt(amounts[0]);
					this.maxamount=Integer.parseInt(amounts[1]);
				}else {
					Bukkit.getConsoleSender().sendMessage("§4[huy] Ошибка загрузки предмета!!! Ошибка загрузки количества.");
				}
				
				String chance = other[2];
				this.chance=Double.parseDouble(chance);
				
			}else {
				this.amount=Integer.parseInt(other[1]);
				
			}
			
			
		}
		
			//this.amount = Integer.parseInt(doStat(stats,"(?i)(a=|amount=)"));
		if(f_splited[0].equalsIgnoreCase("mmoitem")) {
			this.is_vanilla = false;
			if(!stats.matches("(?i)(.*)(;|^| +)(t=|type=)(.*)")) {
				Errors.error("Не указано type mmo предмета!");
				return;
			}
			this.type = doStat(stats,"(t=|type=)");
			if(!types.has(this.type.toUpperCase())) {

				Errors.error("Указан несуществующий тип предмета!");
				return;
			}
		}
		
		
	}
	
	public ItemStack getItemStack() {
		if(is_vanilla) {
			return getVanillaItem();
		}else {
			return getMMOItem();
		}
	}
	
	public ItemStack getVanillaItem() {
		ItemStack item = new ItemStack(Material.STONE);
		if(!this.is_vanilla) {
			Settings.debug("Этот предмет не ванильный! Получи камнем в ебало!");
			return item;
		}
		iid=iid.toUpperCase();
		
		Material m = Material.getMaterial(this.iid);
		if(m==null) {
			Settings.debug("Такого материала нет! Получи камнем в ебало!");
			return item;
		}
		item = new ItemStack(m);
		item.setAmount(amount);
		return item;
	}
	public ItemStack getMMOItem() {
		ItemStack item = new ItemStack(Material.STONE);
		if(!type.isEmpty()) {
			type = type.toUpperCase();
			iid = iid.toUpperCase();
			item = MMOItems.plugin.getItem(type, iid);
			item.setAmount(amount);
		}
		return item;
		
	}
	public String doStat(String s, String regex_1) {
		if(s.split("(?i)(;|^| +)"+regex_1)[1].matches("(?i)(.*);(.*)")) {
			return s.split(regex_1)[1].split(";")[0];
		}else {
			return s.split(regex_1)[1];
		}
	}
	public boolean willdrop() {
		Double drop = rand.nextDouble(100.0);
		if(drop<=this.chance && drop>0) {
			return true;
		}else {
			return false;
		}
	}
	public int howMatchDrop(){
		if(maxamount==null) {
			return this.amount;
		}else if(amount==maxamount){
			return this.amount;
		}else{
		

			return rand.nextInt(this.amount, this.maxamount+1);
		}
		
	}
	public Double getRawChance() {
		return chance;
	}
	private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
	
}