package main.blocker;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.Vector;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import main.Settings;
import net.Indyuce.mmoitems.api.event.item.ConsumableConsumedEvent;



public class BansEventer implements Listener {
	
	
	
	static RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if(e.getPlayer().isOp()){
			return;
		}
		
		World world = e.getPlayer().getWorld();
		String world_name = world.getName();
		String cmd = e.getMessage().split(" ")[0];
		cmd=cmd.replace("/", "");
		//.debug(e.getPlayer().getName()+" написал в мире "+world_name+" |"+e.getMessage()+"|  конвертировано в "+cmd, "e");
		if(CmdConfig.hasBanWorld(world_name, cmd)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§4Эта комманда запрещена в данном мире!");
			Settings.debug("Заблокированна комманда |"+cmd+"| игроком "+e.getPlayer().getName()+" в мире "+world_name);
			return;
		}
		
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regionManager = container.get(BukkitAdapter.adapt(world));
		Location loc = e.getPlayer().getLocation();
		BlockVector3 vec= BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
		for(ProtectedRegion region :regionManager.getApplicableRegions(vec).getRegions()) {
			if(CmdConfig.hasBanReg(world_name, region.getId(), cmd)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage("§4Эта комманда запрещена в данном регионе!");
				Settings.debug("Заблокированна комманда |"+cmd+"| игроком "+e.getPlayer().getName()+" в мире "+world_name+" в регионе "+region.getId());
				return;
			}
		}
		//Settings.debug("Проверка комманды |"+cmd+"| игроком "+e.getPlayer().getName()+" в мире "+world_name+" в регионе "+reg);
		
		
		
		
		//BlockVector3 pos = new BlockVector3()
		
		
		
	}
	
	@EventHandler
	public void onConsumableConsumed(ConsumableConsumedEvent e) {
		if(e.getPlayer().isOp()){
			return;
		}
		String type = e.getMMOItem().getType().getId();
		String id = e.getMMOItem().getId();
		String name = e.getUseItem().getItem().getItemMeta().getDisplayName();
		World world = e.getPlayer().getWorld();
		String world_name = world.getName();
		//Settings.debug(e.getPlayer().getName()+" Использовал |"+name+"|  type|"+type+"| id|"+id+"|", "e");
		if(UseConfig.hasBanWorld(world_name, type,id)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("§4Использование этого предмета запрещено в данном мире!");
			Settings.debug("Заблокированно использование предмета |"+name+"| игроком "+e.getPlayer().getName()+" в мире "+world_name);
			return;
		}
		
		RegionManager regionManager = container.get(BukkitAdapter.adapt(world));
		Location loc = e.getPlayer().getLocation();
		BlockVector3 vec= BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
		for(ProtectedRegion region :regionManager.getApplicableRegions(vec).getRegions()) {
			if(UseConfig.hasBanReg(world_name, region.getId(), type, id)) {

				e.setCancelled(true);
				e.getPlayer().sendMessage("§4Использование этого предмета запрещено в данном регионе!");
				Settings.debug("Заблокированно использование прдмета |"+name+"| игроком "+e.getPlayer().getName()+" в мире "+world_name);
				return;
			}
		}
		
		
		
	}
	
}
