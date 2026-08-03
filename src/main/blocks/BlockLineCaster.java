package main.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import Configs.BlockConfig;
import Configs.Errors;

public class BlockLineCaster {

	public boolean is_vanilla = true;
	public String iid;
	public int amount = 1;
	public Integer maxamount;
	Block block;
	BlockZarj zblock;
	
	public BlockLineCaster(String s) {
		if(!s.matches("(.*)\\{(.*)")||!s.matches("(.*)\\}(.*)")) {
			Errors.error("Неверное определение предмета!");
			return;
		}
		String[] f_splited = s.split("\\{");
		if(!f_splited[0].equalsIgnoreCase("vanilla")&&!f_splited[0].equalsIgnoreCase("zblock")) {
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
		}
		if(f_splited[0].equalsIgnoreCase("zblock")) {
			this.is_vanilla = false;
		}
	}
	public boolean checkMat() {
		if(is_vanilla) {
			if(Material.getMaterial(this.iid)==null) {
				return false;
			}else {
				return true;
			}
		}else if(BlockConfig.getBlockZarj(iid)==null){
			return false;
		}else {
			return true;
		}
	}
	public Material getVanillaBlock() {
		Material mat = Material.getMaterial(this.iid);
		if(mat==null) {
			Errors.error("Указанный тип блока не существует! "+iid);
			
		}
		return mat;
	}
	public BlockZarj getZarjBlock() {
		return BlockConfig.getBlockZarj(iid);
	}
	
	
	
	
	
	
	
	
	
	
	
	public String doStat(String s, String regex_1) {
		if(s.split("(?i)(;|^| +)"+regex_1)[1].matches("(?i)(.*);(.*)")) {
			return s.split(regex_1)[1].split(";")[0];
		}else {
			return s.split(regex_1)[1];
		}
	}
	private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

}
