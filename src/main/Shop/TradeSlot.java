package main.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import main.hezar;
import main.Futh.ZItem;

public class TradeSlot {
	boolean is_multi= false;
	int uid = 0;
	int page_id = 0;
	int shop_id=0;
	boolean can_shift = true;
	List<ItemStack> from= new ArrayList<ItemStack>();
	double price = 0.0;
	double money = 0.0;
	String perm;
	boolean need_perm;
	String perm_desc;
	List<ItemStack> to = new ArrayList<ItemStack>();
	List<ZItem> zto = new ArrayList<ZItem>();
	
	public TradeSlot(ConfigurationSection c,int shop_id) {
		
		this.shop_id =shop_id;
		
		if(c.contains("from")&&c.isString("from")) {
			ZItem f_item = new ZItem(c.getString("from"));
			from.add(f_item.getItemStack());
		}else if(c.contains("from")&&c.isList("from")){
			List<String> frome = c.getStringList("from");
			if(frome.size()==1) {
				ZItem f_item = new ZItem(frome.get(0));
				from.add(f_item.getItemStack());
			}else if(frome.size()>1) {
				is_multi=true;
				for(String from_string : frome) {
					ZItem f_item = new ZItem(from_string);
					from.add(f_item.getItemStack());
					if(from.size()>=3) {
						break;
					}
				}
			}
		}
		if(c.contains("to")&&c.isString("to")) {
			ZItem t_item = new ZItem(c.getString("to"));
			to.add(t_item.getItemStack());
			zto.add(t_item);
		}else if(c.contains("to")&&c.isList("to")){
			List<String> frome = c.getStringList("to");
			if(frome.size()==1) {
				ZItem f_item = new ZItem(frome.get(0));
				to.add(f_item.getItemStack());

				zto.add(f_item);
			}else if(frome.size()>1) {
				is_multi=true;
				for(String from_string : frome) {
					ZItem f_item = new ZItem(from_string);
					to.add(f_item.getItemStack());

					zto.add(f_item);
					if(to.size()>=3) {
						break;
					}
				}
			}
		}
		if(c.contains("shift")&&c.isBoolean("shift")) {
			this.can_shift=c.getBoolean("shift");
		}
		if(c.contains("price")&&c.isDouble("price")) {
			this.price = c.getDouble("price");
		}
		if(c.contains("money")&&c.isDouble("money")) {
			this.money = c.getDouble("money");
		}
		if(c.contains("perm")&&c.isString("perm")) {
			this.perm = c.getString("perm");
			need_perm = true;
		}else if(c.contains("permission")&&c.isString("permission")) {
			this.perm = c.getString("permission");
			need_perm = true;
		}
		
		

		if(need_perm&&c.contains("perm_desc")&&c.isString("perm_desc")) {
			this.perm_desc = c.getString("perm_desc");
		}else if(need_perm&&c.contains("permission_description")&&c.isString("permission_description")) {
			this.perm_desc = c.getString("permission_description");
		}
	}
	public void setPageId(int id) {
		this.page_id = id;
	}
	public void setId(int id) {
		uid= id;
	}
	
}
