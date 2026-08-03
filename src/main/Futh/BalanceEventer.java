 package main.Futh;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.RegisteredListener;

import io.lumine.mythic.bukkit.events.MythicDamageEvent;
import io.lumine.mythic.bukkit.events.MythicProjectileHitEvent;
import main.Settings;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmoitems.api.event.item.SpecialWeaponAttackEvent;

public class BalanceEventer implements Listener{
	
	
	/*
	@EventHandler
	public void onMythicDamage(MythicDamageEvent e) {
	}
	*/
	/*
	@EventHandler
	public void onAttack(AttackEvent e) {
		//Settings.debug("{DAMAGE TEST} Mythic test 2 "+e.isCancelled()+" = "+e.getEventName()+" from "+e.getHandlers().getRegisteredListeners()[0].getPlugin().getName());
		
		
	}
	
	@EventHandler
	public void onPlayeAttack(PlayerAttackEvent e) {
		
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			for(DamageType dt :e.getDamage().collectTypes()) {
				Settings.debug("{DAMAGE TEST} Mythic test 3 DT  "+dt.name()+" = "+e.getDamage().getDamage(dt));
				
			}
		}
		for(RegisteredListener rl :e.getHandlers().getRegisteredListeners()) {
			//Settings.debug("{DAMAGE TEST} Mythic test |3| = "+rl.getPlugin().getName()+"  {"+rl.getPriority().name()+"}  "+rl.getListener().getClass().getName() );
		}
		Settings.debug("{DAMAGE TEST} Mythic test 3 |"+e.getEventName()+"| = "+e.getAttack().getDamage().getDamage());
	}
	@EventHandler
	public void onSWeaponAttack(SpecialWeaponAttackEvent e) {
		Settings.debug("{DAMAGE TEST} MI damage test 1");
		if(e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
		}
	}
	*/
	

}
