package main.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import main.Settings;
import main.hezar;
import main.Futh.ZItem;
import main.Futh.leveler.LevelMechanic;
import net.Indyuce.mmoitems.MMOItems;

public class BlockZarj{
	
	public String name;
	public String mmoid;
	public Integer myid;
	public int power_to_break=0;
	public boolean will_self_drop = true;
	public boolean only_regen_drop = false;
	public boolean need_power = false;
	public boolean can_be_placed = true;
	public boolean need_pickaxe = false;
	public String place_error = "You can't place block!";
	public List<Location> locs_placed = new ArrayList<>();
	List<LevelMechanic> drops = new ArrayList<>();
	public List<ZItem> droplist = new ArrayList<>();;
	public List<BlockSubTag> taglist = new ArrayList<>();;
	public List<BlockRegen> regens = new ArrayList<>();;
	public List<BukkitTask> regen_task = new ArrayList<>();
	public ItemStack item_block;
	public boolean vanilla_drop = true;
	public BlockData bd; 
	public ConfigurationSection regen_sec = null;
	
	public FixedMetadataValue meta_value;
	
	
	
	
	public BlockZarj(ConfigurationSection c) {
		name = c.getName();
		droplist = new ArrayList<>();
		taglist = new ArrayList<>();
		regens = new ArrayList<>();
		drops = new ArrayList<>();
		mmoid = c.getString("Block");
		myid = c.getInt("MyId");
		
		meta_value = new FixedMetadataValue(hezar.inst, this.myid);
		
		item_block = MMOItems.plugin.getItem("BLOCK", mmoid);
		if(c.contains("SubTags")) {
			ConfigurationSection sub_tags = c.getConfigurationSection("SubTags");
			Set<String> setofkeys = sub_tags.getKeys(false);
			for(String s : setofkeys) {
				taglist.add(new BlockSubTag(sub_tags.getConfigurationSection(s)));
			}
			if(!this.taglist.isEmpty()) {
				String new_data = "minecraft:"+item_block.getType().toString().toLowerCase()+"[";
				for(BlockSubTag bst : taglist) {
					new_data+=bst.name+"="+bst.value+",";
				}
				new_data = new_data.substring(0, new_data.length() - 1);
				new_data+="]";
				this.setBlockTag(new_data);
			}
			
		}
		if(c.contains("VanillaDrop")&&c.isBoolean("VanillaDrop")) {
			this.vanilla_drop = c.getBoolean("VanillaDrop");
		}
		if(c.contains("CanPlace")&&c.isBoolean("CanPlace")) {
			this.can_be_placed=c.getBoolean("CanPlace");
		}
		if(c.contains("Drop")&&c.isList("Drop")) {
			List<String> items = c.getStringList("Drop");
			for(String s : items) {
				droplist.add(new ZItem(s));
			}
		}
		if(c.contains("level_drop")&&c.isConfigurationSection("level_drop")) {
			ConfigurationSection drop = c.getConfigurationSection("level_drop");
			Set<String> keys = drop.getKeys(false);
			for(String s : keys) {
				this.drops.add(new LevelMechanic(drop.getConfigurationSection(s)));
			}
		}
		if(c.contains("SelfDrop")&&c.isBoolean("SelfDrop")) {
			this.will_self_drop = c.getBoolean("SelfDrop");
		}
		if(c.contains("OnlyRegenDrop")&&c.isBoolean("OnlyRegenDrop")) {
			this.only_regen_drop = c.getBoolean("OnlyRegenDrop");
		}
		if(c.contains("NeedPower")&&c.isBoolean("NeedPower")) {
			this.need_power = c.getBoolean("NeedPower");
			if(c.contains("PowertoBreak")&&c.isInt("PowertoBreak")) {
				this.power_to_break = c.getInt("PowertoBreak");
			}
		}
		if(c.contains("Pickaxe")&&c.isBoolean("Pickaxe")) {
			this.need_pickaxe = c.getBoolean("Pickaxe");
		}
		if(c.contains("Regen")) {
			this.regen_sec = c.getConfigurationSection("Regen");

			
		}
		
	}
	public void startRegen() {
		if(!(regen_sec instanceof ConfigurationSection)) {
			return;
		}
		Set<String> setofkeys = regen_sec.getKeys(false);
		regens = new ArrayList<>();
		
		for(String s : setofkeys) {
			regens.add(new BlockRegen(regen_sec.getConfigurationSection(s)));
		}
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		Server serv = Bukkit.getServer();
		regen_task = new ArrayList<>();
		for(BlockRegen br : regens) {
			this.regen_task.add(this.runRegen(br,scheduler,serv,this));
		}
		
	}
	public BukkitTask runRegen(BlockRegen br, BukkitScheduler scheduler, Server server, BlockZarj bz) {
		return scheduler.runTaskTimer(hezar.inst, new Runnable() {
            @Override
            public void run() {
            	World world = server.getWorld(br.world);
            	if(!server.getWorlds().contains(world)) {
            		Settings.debug("����������� ��� "+br.world);
            		return;
            	}
            	List<BlockLocation> list_bl = br.blocks_locs;
            	int amloc = list_bl.size();
            	List<Location> locs = new ArrayList<>();
            	for(BlockLocation bl : list_bl) {
            		locs.add(new Location(world,bl.x,bl.y,bl.z));
            	}
            	int amount_have = 0;
            	for(Location l : locs) {
            		if(!l.getBlock().hasMetadata("ZARJ_BLOCK_ID")) {
            			continue;
            		}
            		if(l.getBlock().getMetadata("ZARJ_BLOCK_ID").get(0).asString().equals(myid.toString())) {
            			amount_have+=1;
            		}
            	}
            	Settings.debug("{BLOCK REGEN}Start regen ["+bz.mmoid+"]: "+amount_have+"/"+locs.size());
            	
            	//Settings.debug("{BLOCK REGEN}");
            	if(amount_have>=br.amount) {
            		return;
            	}else{
            		int need_new = br.amount-amount_have;
            		Settings.debug("{BLOCK REGEN} need: "+br.amount+" | �� ������� "+need_new);
            		//HashMap<Location,Boolean> map_loc = new HashMap<Location, Boolean>();
            		List<Location> loc_list = new ArrayList<>();
            		for(Location l : locs) {
            			if(!l.getBlock().hasMetadata("ZARJ_BLOCK_ID")) {
            				loc_list.add(l);
                			continue;
                		}else if(!l.getBlock().getMetadata("ZARJ_BLOCK_ID").get(0).asString().equals(myid.toString())) {
                			loc_list.add(l);
                		}
            		}
            		Random rand = new Random();
            		if(need_new>=loc_list.size()) {
            			for(Location loc : loc_list) {
            				loc.getBlock().setType(item_block.getType());
                			loc.getBlock().setMetadata("ZARJ_BLOCK_ID", bz.meta_value);
                			if(!bz.taglist.isEmpty()) {
                    			BlockData bd = bz.bd;
                    			loc.getBlock().setBlockData(bd);
                			}
                			bz.addLocation(loc);
            			}
            		}else {
            			for(int i = 0;i<need_new&&!loc_list.isEmpty();i++) {
                			int ind= rand.nextInt(0,loc_list.size());
                			Location temp_loc = loc_list.get(ind);
                			//Settings.debug("{BLOCK REGEN}��������� ������: "+ind);
                			
                			temp_loc.getBlock().setType(item_block.getType());
                			temp_loc.getBlock().setMetadata("ZARJ_BLOCK_ID", bz.meta_value);
                			
                			if(!bz.taglist.isEmpty()) {
                    			//Settings.debug("����� ������ ��� �������� : "+new_data);
                    			//Settings.debug("TTag ��= "+temp_loc.getBlock().getState().getBlockData().getAsString());
                    			BlockData bd = bz.bd;
                    			//Settings.debug("TTag �����= "+bd.getAsString());
                    			temp_loc.getBlock().setBlockData(bd);
                    			//Settings.debug("TTag ����� = "+temp_loc.getBlock().getState().getBlockData().getAsString());

                			}
                			bz.addLocation(temp_loc);
                			loc_list.remove(temp_loc);
                			Settings.debug("{BLOCK REGEN} regen block"+myid+" placed at "+temp_loc);
                			
                		}
            		}
            	}

            	
            	//for()
            	//Location loc = new Location(world,br.);
            	//world.getBlockAt(null)
            	
                
            }
        }, 0L, br.timer);
	}
	public void stopRegen() {
		for(BukkitTask bt : regen_task) {
			bt.cancel();
		}
	}
	public void reloadRegen() {
		stopRegen();
		startRegen();
	}
	public void placeId(Location loc) {
			if(!loc.getBlock().hasMetadata("ZARJ_BLOCK_ID")||loc.getBlock().getMetadata("ZARJ_BLOCK_ID").get(0).asString().equals(myid.toString())) {
				loc.getBlock().setMetadata("ZARJ_BLOCK_ID", meta_value);
			}
	}
	
	public void placeBlocks(Collection<Location> locs) {
		for(Location loc : locs) {

			this.locs_placed.add(loc);
			Settings.debug("{BLOCK LOAD} load "+this.name, "a");
			if(loc.getBlock().getType()!=item_block.getType()) {
				loc.getBlock().setType(item_block.getType());
				if(bd!=null) {
					loc.getBlock().setBlockData(bd);
					
				}
			}
			loc.getBlock().setMetadata("ZARJ_BLOCK_ID", meta_value);
		}
	}
	
	public void placeBlock(Location loc) {
		Settings.debug("{BLOCK LOAD} load "+this.name, "a");
		if(loc.getBlock().getType()!=item_block.getType()) {
			loc.getBlock().setType(item_block.getType());
			if(bd!=null) {
				loc.getBlock().setBlockData(bd);
				
			}
		}
		loc.getBlock().setMetadata("ZARJ_BLOCK_ID", meta_value);
	}
	
	
	public void placeAllData() {
		for(Location loc : locs_placed) {
			placeData(loc);
		}
	}
	public void placeData(Location loc) {
		if(bd!=null) {
			loc.getBlock().setBlockData(bd);
			
		}
	}
	
	public void placeAllId() {
		for(Location loc : locs_placed) {
			placeId(loc);
		}
	}
	public boolean hasRegenConfig() {
		if(regen_sec!=null) {
			return true;
		}else {
			return false;
		}
	}
	public ItemStack createItem() {
		ItemStack justitem = MMOItems.plugin.getItem("BLOCK",mmoid);
		if(!taglist.isEmpty()) {
			net.minecraft.world.item.ItemStack nmsitem = CraftItemStack.asNMSCopy(justitem);
    		net.minecraft.nbt.NBTTagCompound itemNBTComp = nmsitem.u();
    		net.minecraft.nbt.NBTTagCompound newComp = new net.minecraft.nbt.NBTTagCompound();
    		for(BlockSubTag bst : taglist) {
    			newComp.a(bst.name,bst.value);
    		}
    		nmsitem.u().a("BlockStateTag",newComp);
    		justitem = CraftItemStack.asCraftMirror(nmsitem);
		}
		justitem.setAmount(1);
		return justitem;
	}
	
	
	public List<LevelMechanic> getDrops(){
		return this.drops;
	}
	
	public void loadBlocks(Collection<Location> locs) {
		Settings.debug("load blocks "+this.name, "c");
		this.locs_placed.addAll(locs);
		for(Location loc : locs) {
			//Settings.debug("{BLOCK LOAD} load "+this.name, "a");
			if(loc.getBlock().getType()!=item_block.getType()) {
				loc.getBlock().setType(item_block.getType());
				if(bd!=null) {
					loc.getBlock().setBlockData(bd);
					
				}
			}
			loc.getBlock().setMetadata("ZARJ_BLOCK_ID", meta_value);
		}
		//addAllLocations(locs);
	}
	
	
	
	public void setBlockTag(String s) {
		this.bd = Bukkit.createBlockData(s);
	}
	
	
	public void addAllLocations(Collection<Location> locs) {
		this.locs_placed.addAll(locs);
	}
	public void addLocation(Location loc) {
		this.locs_placed.add(loc);
	}
	public int getMyId() {
		return myid;
	}
	public boolean equalMyId(int id) {
		if(myid==id) {
			return true;
		}else {
			return false;
		}
	}
	public boolean equalByName(String id) {
		if(name.equalsIgnoreCase(id)) {
			return true;
		}else {
			return false;
		}
	}
	public ItemStack getItemBlock() {
		
		return item_block;
	}
	public BlockData getBD() {
		return bd;
	}
	
}