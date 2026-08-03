package main.Futh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import main.hezar;

public enum StaticItem {
	SHOP_BACK(1001){
		@Override
		public ItemStack toItem(int id) {
			ItemStack item = toItem();
			ItemMeta  meta = item.getItemMeta();
			NamespacedKey nkey = new NamespacedKey(hezar.inst, "SHOP_BACK");
			NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
			meta.setDisplayName("§aПредыдущая страница");
			meta.getPersistentDataContainer().set(nkey,PersistentDataType.INTEGER, 1);
			meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, id);
			item.setItemMeta(meta);
			return item;
		}
	},
	SHOP_NEXT(1002){
		@Override
		public ItemStack toItem(int id) {
			ItemStack item = toItem();
			ItemMeta  meta = item.getItemMeta();
			NamespacedKey nkey = new NamespacedKey(hezar.inst, "SHOP_NEXT");
			NamespacedKey pagekey = new NamespacedKey(hezar.inst, "PAGE_ID");
			meta.setDisplayName("§aСледующая страница");
			meta.getPersistentDataContainer().set(nkey,PersistentDataType.INTEGER, 1);
			meta.getPersistentDataContainer().set(pagekey,PersistentDataType.INTEGER, id);
			item.setItemMeta(meta);
			return item;
		}
		
	},
	POINTER(Material.IRON_NUGGET,1,"§6Нажмите на товар для заключения сделки."){

		@Override
		public ItemStack toItem() {
			ItemStack item = super.toItem();
			ItemMeta  meta = item.getItemMeta();
			
			meta.setLore(new ArrayList<String>(Arrays.asList("§cДля заключения максимально возможного","§cкол-ва сделок нажмите Shift+ПКМ на товар.")));
			item.setItemMeta(meta);
			return item;
		}
	},
	FILL(Material.DIAMOND_HOE,15," "){
		@Override
		public ItemStack toItem() {
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(model_data);
			meta.setDisplayName(name);
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			item.setItemMeta(meta);
		
			//meta.setAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
			return item;
			
		}
	},
	DROP_TO_CHEST(Material.FEATHER,1001,"§6Закинуть в сундук совпадающие."){
		@Override
		public ItemStack toItem() {
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(model_data);
			meta.setDisplayName(name);
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.setLore(Arrays.asList("§cЛКМ - переложить только в существующие стаки.","§cSHIFT + ЛКМ - переложить и в пустые слоты."));
			NamespacedKey drop_key = new NamespacedKey(hezar.inst, "DROP_TO_CHEST");
			meta.getPersistentDataContainer().set(drop_key,PersistentDataType.INTEGER, 1);
			item.setItemMeta(meta);
		
			//meta.setAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
			return item;
			
		}
	};
	
	public Material material = Material.EMERALD_BLOCK;
	int model_data = 0;
	int amount = 1;
	String name="item";
	
	StaticItem(int model) {
		this.model_data=model;
	}
	
	StaticItem(int model,int amount) {
		this.model_data=model;
		this.amount=amount;
	}
	StaticItem(Material mat,int model,int amount) {
		this.model_data=model;
		this.amount=amount;
		this.material=mat;
	}
	StaticItem(Material mat,int model) {
		this.model_data=model;
		this.material=mat;
	}
	StaticItem(Material mat,int model,String nm) {
		this.model_data=model;
		this.material=mat;
		this.name=nm;
	}
	
	public ItemStack toItem(int id) {
		return toItem();
	};
	
	public ItemStack toItem() {
		if(material!=null) {
			ItemStack item = new ItemStack(material);
			item.setAmount(amount);
			if(model_data>0) {
				ItemMeta meta = item.getItemMeta();
				meta.setCustomModelData(model_data);
				meta.setDisplayName(name);
				item.setItemMeta(meta);
			}
			return item;
		}
		
		return null;
		
	}
	
}
