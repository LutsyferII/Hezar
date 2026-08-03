package main.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import main.Settings;
import main.Utils.ChangeableVar;
import net.Indyuce.mmoitems.api.event.item.CustomDurabilityDamage;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;

public class DaggerOffHandDamageEvent  extends Event implements Cancellable {
	boolean cancel = false;
	ItemStack dagger;
	ChangeableVar<Double> damage;
	Float cd;
	Player p;
	private static final HandlerList handlers = new HandlerList();
	
	
	public DaggerOffHandDamageEvent(ItemStack dagger, ChangeableVar<Double> damage,Float cd,Player p,DamageCause cause) {
		this.dagger = dagger;
		this.damage = damage;
		this.cd = cd;
		this.p = p;
		net.minecraft.world.item.ItemStack nbt_item = CraftItemStack.asNMSCopy(dagger);

		String stringdamage = nbt_item .u().c("MMOITEMS_ATTACK_DAMAGE").e_();
		Double dagger_damage = Double.parseDouble(stringdamage);
		Double halfdamage = (dagger_damage/Settings.divisor)*cd;
		if(cause==DamageCause.ENTITY_SWEEP_ATTACK) {
			halfdamage = halfdamage/2;
		}
		damage.setVar(damage.getVar()+halfdamage);
		
		if(p.getGameMode() != GameMode.CREATIVE&&cause!=DamageCause.ENTITY_SWEEP_ATTACK&&cd!=0.032500003) {
			  //Settings.debug("{DAMAGE}{DAGGER} GameMode is not Creative!", "c");
			  if(nbt_item.u().e("MMOITEMS_MAX_DURABILITY")) {
				  //LiveMMOItem mmoitem = new LiveMMOItem(dagger);
				  //MMOItems.plugin.getStats().get("DurabilityBar").
				  //mmoitem.getNBT().
				  DurabilityItem dur_item = new DurabilityItem(p,dagger);
				  
				  Settings.debug("{DAMAGE}{DAGGER} Durability before = "+dur_item.getDurability(), "c");
				  dur_item.decreaseDurability(1);
				  Settings.debug("{DAMAGE}{DAGGER} Durability after = "+dur_item.getDurability(), "c");
				  final ItemStack new_dagger = dur_item.toItem();
				  dagger.setItemMeta(new_dagger.getItemMeta());
				  //p.getInventory().setItemInOffHand(dagger);
				  nbt_item = CraftItemStack.asNMSCopy(dagger);

				  if(nbt_item.u().e("MMOITEMS_DURABILITY")) {
					  stringdamage = nbt_item.u().c("MMOITEMS_DURABILITY").e_();
					  Settings.debug("{DAMAGE}{DAGGER} Durability final = "+stringdamage, "c");
				  }
				  
				  //int durab = nbt_item.u().h("MMOITEMS_DURABILITY");
				  //durab--;
				  //if(durab<1) {
					  //p.getInventory().setItemInOffHand(null);  
				  //}else {
					 //nbt_item.u().a("MMOITEMS_DURABILITY", durab);
					  //dagger= CraftItemStack.asBukkitCopy(nbt_item);
					  //p.getInventory().setItemInOffHand(dagger);
				  //}
				  
			  }else {

				  ItemMeta meta = dagger.getItemMeta();
				  if(meta instanceof Damageable) {
					  Settings.debug("{DAMAGE}{DAGGER} Предмет дамажемый!", "c");
					  Damageable m_item = (Damageable) meta;
					  m_item.setDamage(m_item.getDamage()+1);
					  Settings.debug("{DAMAGE}{DAGGER} Новая прочность даггера = "+m_item.getDamage(), "c");
					  
					  dagger.setItemMeta(m_item);
					  
				  }
			  }
		}
		
		
		//Settings.debug("{DAMAGE_DAGGER} DAGGER_EVENT new_damage = "+damage.getVar(), "a");
	}
	
	
	
	public ChangeableVar<Double> getVarDamage(){
		return damage;
	}
	
	public Double getDamage() {
		return damage.getVar();
	}
	public Float getCooldown() {
		return cd;
	}
	
	
	
	
	public void setDamage(Double dmg) {
		damage.setVar(dmg);
	}
	public void setVarDamage(ChangeableVar<Double> dmg) {
		damage = dmg;
	}
	
	
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancel;
	}

	@Override
	public void setCancelled(boolean arg) {
		cancel = arg;
		
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		 return handlers;
	}
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
