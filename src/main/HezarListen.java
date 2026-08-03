package main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.potion.PotionEffectType;

import Configs.BlockConfig;
import Configs.Errors;
import Configs.OhotaConfig;
import Configs.TochConfig;
import io.lumine.mythic.api.adapters.AbstractPlayer;
import io.lumine.mythic.bukkit.events.MythicDamageEvent;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.item.NBTItem;
import io.lumine.mythic.lib.damage.AttackMetadata;
import io.lumine.mythic.lib.damage.DamageMetadata;
import io.lumine.mythic.lib.damage.DamageType;
import main.CommandBlock.CBConfig;
import main.CommandBlock.CMDBlock;
import main.Futh.ZItem;
import main.Futh.LootBoxMechanic.LootBox;
import main.Futh.LootBoxMechanic.LootManager;
import main.Futh.leveler.LevelMechanic;
import main.Futh.leveler.LevelPerLevelMech;
import main.Futh.leveler.LevelPodMech;
import main.Utils.ChangeableVar;
import main.Utils.DeathItemKeeper;
import main.blocks.BlockLocation;
import main.blocks.BlockRegen;
import main.blocks.BlockZarj;
import main.events.DaggerOffHandDamageEvent;
import main.ohota.OhotaDrop;
import main.player.PlayerManager;
import main.player.ZarjPlayer;
import main.toch.TochStat;
import main.toch.Tochilka;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.experience.EXPSource;
import net.Indyuce.mmocore.experience.PlayerProfessions;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.event.item.ApplyGemStoneEvent;
import net.Indyuce.mmoitems.api.event.item.ConsumableConsumedEvent;
import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem;
import net.Indyuce.mmoitems.stat.data.GemstoneData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutChat;




public class HezarListen implements Listener{
	

	public void onMythicDamage(MythicDamageEvent e) {
		
		double armor = e.getTarget().getArmor();
		double base_armor = e.getTarget().getArmorBase();
		Settings.debug("§6{ATTACK} get damage "+e.getTarget().getName()+" \ndamage = "+e.getDamage()+"\narmor = "+armor+"\nbase armor = "+base_armor);
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(PlayerDeathEvent e) {
		if(e.getKeepInventory()) {
			return;
		}
		Player p = e.getEntity();
		
		PlayerInventory inv = p.getInventory();
		
		if(inv.getItemInOffHand()!=null &&NBTItem.get(inv.getItemInOffHand()).hasTag(ZarjTags.ZARJ_KEEP_INVENTORY.toString())) {
			e.setKeepInventory(true);
			e.getDrops().clear();
			inv.getItemInOffHand().setAmount(inv.getItemInOffHand().getAmount()-1);
			
			
		}else {
			Settings.debug("{KEEPER} debug 3");
			DeathItemKeeper keeper = new DeathItemKeeper(p);
			for(ItemStack item : e.getDrops()) {
				if(item== null) continue;
				Settings.debug("{KEEPER} debug 4 "+item.getItemMeta().getDisplayName());
				
				final NBTItem nbt = NBTItem.get(item);
				
				if(nbt.hasTag(ZarjTags.ZARJ_KEEP_ITEM.toString())) {
					Settings.debug("{KEEPER} Tag found ");
					keeper.addItem(item.clone());
					item.setAmount(0);
				}
				
				
			}
			if(keeper.hasItems()) {
				Settings.debug("{KEEPER} debug 2");
				DeathItemKeeper.registerKeeper(keeper);
			}
		}
		
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent e)
	{
		DeathItemKeeper.giveItem(e.getPlayer().getUniqueId());
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAttack(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {/*
			for(RegisteredListener rl: e.getHandlers().getRegisteredListeners()) {
				if(rl.getPriority()==EventPriority.HIGHEST) {
					
					
					//Settings.debug("§5{DAMAGE TEST} [HIGHEST]  LISTENER: "+rl.getPlugin().getName()+"     |"+rl.getListener().getClass().getName()+"|","a");
					
				}
				
			}*/
			Settings.debug("§b{ATTACK} get damage = "+e.getEntity().getName()+" at "+e.getEntity().getLocation()+" \n damage = "+e.getDamage()+"\nFinal damage = "+e.getFinalDamage());
			Player player = (Player) e.getEntity();
		}
		
		
	}
	
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent e) {
		if(e.getResult()!=Result.ALLOWED) {
			return;
		}
		Player p = e.getPlayer();
		PlayerManager.addPlayer(p);
	}
	
	/*
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsumableConsumed(ConsumableConsumedEvent e) {
		if(e.isCancelled()) {
			return;
		}
		
		NBTItem nbt_item = e.getUseItem().getNBTItem();
		if(!nbt_item.hasTag(ZarjTags.ZARJ_COOLDOWN_GROUP.name())||!nbt_item.hasTag(ZarjTags.ZARJ_COOLDOWN_TIME.name())) {
			return;
		}
		String group = nbt_item.getString(ZarjTags.ZARJ_COOLDOWN_GROUP.name());
		Player p = e.getPlayer();
		if(!PlayerManager.hasPlayer(p)) {
			Errors.error("Использование расходника игроком, не имеющим свою копию в ZarjPlayer!");
			return;
		}
		ZarjPlayer zp = PlayerManager.getPlayer(p);
		if(zp.checkCooldown(group)<=0) {
			int cd = nbt_item.getInteger(ZarjTags.ZARJ_COOLDOWN_TIME.name());
			zp.setCooldown(group, cd*20);
		}else {
			e.setCancelled(true);
			TextComponent tc = new TextComponent("Кд типа "+zp.getCooldown(group));
			//ChatMessageType cmt = ChatMessageType.ACTION_BAR;
			//p.spigot().sendMessage(tc);;
			
			tc.setColor(ChatColor.RED);
			Player.Spigot ps = p.spigot();
			//ps.sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,tc);
			IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "Кд типа "+zp.getCooldown(group) + "\"}");
			
			PacketPlayOutChat actionbarPacket = new PacketPlayOutChat(iChatBaseComponent, ChatMessageType.a((byte) 2), UUID.randomUUID());
			((CraftPlayer)p).getHandle().b.a(actionbarPacket);
			//p.spigot().sendMessage(ChatMessageType.ACTION_BAR,tc);
			return;
		}
		
		
		
	}
	
	
	*/
	
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerAttackHIGH(EntityDamageByEntityEvent e)
	{
		if(e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)){
			Double raw_damage = e.getDamage();
	    	Player p = (Player) e.getDamager();
			
			//Settings.debug("{DAMAGE TEST} "+"  TYPE="+e.getCause().name()+"  Damager cd "+p.getAttackCooldown());
			Float cd = p.getAttackCooldown();
			
			ItemStack itemstk =p.getInventory().getItemInOffHand();
			  if(itemstk!=null&&!itemstk.getType().equals(Material.AIR)) {
				  net.minecraft.world.item.ItemStack tempitmsk = CraftItemStack.asNMSCopy(itemstk);
				  if(tempitmsk.u().e("MMOITEMS_ATTACK_DAMAGE")) {
					  if(tempitmsk.u().e("MMOITEMS_ITEM_TYPE") && tempitmsk.u().c("MMOITEMS_ITEM_TYPE").e_().equals(Settings.mmotype)) {
						//String stringdamage = tempitmsk.u().c("MMOITEMS_ATTACK_DAMAGE").e_();
						
						ChangeableVar<Double> dmg = new ChangeableVar<Double>(raw_damage);
						//Settings.debug("{DAMAGE}{DAGGER} raw_damage before = §6"+raw_damage, "c");
						DaggerOffHandDamageEvent dagger_event = new DaggerOffHandDamageEvent(itemstk,dmg,cd,p,e.getCause());
						Bukkit.getPluginManager().callEvent(dagger_event);
						
						
						
						net.minecraft.world.item.ItemStack nbt_item = CraftItemStack.asNMSCopy(itemstk);

						if(nbt_item.u().e("MMOITEMS_DURABILITY")) {
							  String string_damage = nbt_item.u().c("MMOITEMS_DURABILITY").e_();
							  //Settings.debug("{DAMAGE}{DAGGER} Durability final_final = "+string_damage, "c");
						}
						// Double puredamage = Double.parseDouble(stringdamage);
						 //Double halfdamage = (puredamage/Settings.divisor)*cd;
						raw_damage = dmg.getVar();
						//Settings.debug("{DAMAGE}{DAGGER} raw_damage after = §4"+raw_damage, "c");
						  
						
						  
					  }
				  }
				  e.setDamage(raw_damage);
			  }
			  
	    }
		
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerAttackFINAL(EntityDamageByEntityEvent e)
	{
		//Settings.debug("{DAMAGE TEST} [FINAL]  урон: "+e.getDamage(),"\nentity "+e.getEntity().getName());
		
		//Settings.debug("{DAMAGE TEST} [FINAL]--------------------------------","e");
		for(RegisteredListener rl: e.getHandlers().getRegisteredListeners()) {
			if(rl.getPriority()==EventPriority.MONITOR) {
				
				
				//Settings.debug("{DAMAGE TEST} [FINAL]  LISTENER: "+rl.getPlugin().getName()+"     |"+rl.getListener().getClass().getName()+"|","a");
				
			}
			
		}
		
	}
	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerAttack(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) {
			return;
		}
		DamageCause dc = e.getCause();
		
		Double raw_damage = e.getDamage();
		if(raw_damage==0.0) {
			return;
		}
		//for(RegisteredListener rl : e.getHandlers().getRegisteredListeners()) {
		//	Settings.debug("{DAMAGE TEST} �������� �������� 	 |"+rl.getPlugin().getName()+"|		 ��������� {"+rl.getPriority().name()+"} 		 Listener: "+rl.getListener().getClass().getName());
		//}
		
		if(e.getEntity() instanceof Player ) {
			if(e.getDamager() instanceof Player) {
				
				Player p = (Player) e.getDamager();
				//if(p.getFallDistance()!=0.0&&!p.isOnGround()&&!p.getLocation().getBlock().isLiquid()&&!p.hasPotionEffect(PotionEffectType.BLINDNESS)&&p.getVehicle()==null&&!p.isSprinting()) {
				//	raw_damage/=1.5;}
				//Settings.debug("{DAMAGE TEST} Damager cd "+p.getAttackCooldown());
				
			}
			Player player=(Player) e.getEntity();
			Double mmo_armor = getAllArmor(player.getInventory());
			//if(MMOCore.plugin.dataProvider.getDataManager().isLoaded(player.getUniqueId())) {
				//mmo_armor = MMOCore.plugin.dataProvider.getDataManager().get(player).getStats().getStat("ARMOR");;
			//}
			
			Settings.debug("{DAMAGE TEST} MMO Armor ("+player.getName()+") =  "+mmo_armor);
			Settings.debug("{DAMAGE TEST} damage =  "+raw_damage);
			
			if(mmo_armor!=0.0&&e.getFinalDamage()!=0.0) {
				Double armor = mmo_armor/5;
				if(armor>=1.0) {
					e.setDamage(0.0);
					final AttackMetadata attack = MythicLib.plugin.getDamage().findAttack(e);
					if(attack.getDamage().getDamage()>0.01) {
						double modif = 1/armor;
						attack.getDamage().multiplicativeModifier(modif);
						player.damage(raw_damage);
						
						
					
						
						
						Settings.debug("{DAMAGE} damage meta = "+attack.getDamage().getDamage()+" \nmodif = "+modif, "d");
						Settings.debug("{DAMAGE TEST} FINAL CHECK \n"+"�eARMOR = "+armor+" ("+mmo_armor+")\n"+"�cDAMAGE = ("+raw_damage+")");
						
					}else {
						double d = raw_damage/armor;
						DamageMetadata damage = new DamageMetadata(d);
						attack.getDamage().add(d, DamageType.PHYSICAL);
						player.damage(d);
						Settings.debug("{DAMAGE TEST} FINAL CHECK \n"+"�eARMOR = "+armor+" ("+mmo_armor+")\n"+"�cDAMAGE = "+d+" ("+raw_damage+")");
					}
					for(ItemStack armor_item : player.getInventory().getArmorContents()) {
						if(armor_item==null) {
							continue;
						}
						final String BREAK = "MMOITEMS_WILL_BREAK";
						NBTItem nbt_armor = NBTItem.get(armor_item);
						if(nbt_armor.hasTag("MMOITEMS_MAX_DURABILITY")) {

							  DurabilityItem dur_item = new DurabilityItem(player,armor_item);
							  
							  Settings.debug("{DAMAGE}{ARMOR} Durability before = "+dur_item.getDurability(), "c");
							  int decr = 1;
							  if(raw_damage>4) {
								  decr = (int) (raw_damage/4.0);
							  }
							  Settings.debug("{DAMAGE}{ARMOR} Durability decr by = "+decr +"  getted damage сука "+raw_damage, "c");
							  dur_item.decreaseDurability(decr);
							  if(!dur_item.isBroken())
							  {
								  armor_item.setItemMeta(dur_item.toItem().getItemMeta());

								  Settings.debug("{DAMAGE}{ARMOR} NOT BROKE ", "c");
								  
							  }else {
								  ItemMeta meta =armor_item.getItemMeta();
								  if(meta instanceof Damageable) {
									  Damageable m_item = (Damageable) meta;
									  Settings.debug("{DAMAGE}{DAGGER} before = "+m_item.getDamage(), "c");
									  m_item.setDamage(armor_item.getType().getMaxDurability());
									  Settings.debug("{DAMAGE}{DAGGER} after = "+m_item.getDamage(), "c");
									  
									  armor_item.setItemMeta(m_item);
									  if(armor_item.getAmount()>0) {

										  Settings.debug("{DAMAGE}{DAGGER} Too bad(", "c");
										  armor_item.setAmount(0);
									  }
								  }
							  }
							  Settings.debug("{DAMAGE}{ARMOR} Durability after = "+dur_item.getDurability(), "c");
						}else {
							ItemMeta meta =armor_item.getItemMeta();
							  if(meta instanceof Damageable) {
								  //Settings.debug("{DAMAGE}{DAGGER} ������� ���������!", "c");
								  Damageable m_item = (Damageable) meta;
								  m_item.setDamage(m_item.getDamage()+1);
								  //Settings.debug("{DAMAGE}{DAGGER} ����� ��������� ������� = "+m_item.getDamage(), "c");
								  
								  armor_item.setItemMeta(m_item);
								  
							  }
						}
					}
					
					
				}
				
				//Settings.debug("{DAMAGE TEST} New Damage =  "+damage);
				
			
				
				
			}
			
			
			/*
			Double armor_coef = 1.0;
			if(mmo_armor>30.0) {
				  armor_coef = mmo_armor/30;
			}
			Double new_armor = mmo_armor*armor_coef;
			Double new_damage = raw_damage *(1-(new_armor/(raw_damage*5+new_armor)));
			
			*/
			
			//Settings.debug("{DAMAGE TEST} Damage cause "+dc.name());
			
			
			//Settings.debug("{DAMAGE TEST} New damage "+new_damage+" old damage = "+raw_damage);
			//e.setDamage(new_damage);
			//Settings.debug("{DAMAGE TEST} Final damage "+e.getFinalDamage());
	    }
		
		/*
	    if(e.getDamager() instanceof Player){
	    	for(RegisteredListener rl :e.getHandlers().getRegisteredListeners()) {
				Settings.debug("{DAMAGE TEST} Mythic test |3| = "+rl.getPlugin().getName()+" ||"+rl.getPriority().name()+"||  "+rl.getListener().getClass().getName() );
			}
	    	
		  Player player=(Player) e.getDamager();
		  ItemStack itemstk =player.getInventory().getItemInOffHand();
			  
			  if(e.getEntity() instanceof Player ) {
				  player = (Player) e.getEntity();
				  if(!itemstk.getType().equals(Material.AIR)) {
					  net.minecraft.world.item.ItemStack tempitmsk = CraftItemStack.asNMSCopy(itemstk);
					  if(tempitmsk.u().e("MMOITEMS_ATTACK_DAMAGE")) {
						  if(tempitmsk.u().e("MMOITEMS_ITEM_TYPE") && tempitmsk.u().c("MMOITEMS_ITEM_TYPE").e_().equals(Settings.mmotype)) {
							  String stringdamage = tempitmsk.u().c("MMOITEMS_ATTACK_DAMAGE").e_();
							  Double puredamage = Double.parseDouble(stringdamage);
							  Double halfdamage = puredamage/Settings.divisor;
							  raw_damage += halfdamage;
						  }
					  }
				  }
				  
				  Double mmo_armor = MMOCore.plugin.dataProvider.getDataManager().get(player).getStats().getStat("ARMOR");
				  Double armor_coef = 1.0;
				  if(mmo_armor>30.0) {
					  armor_coef = mmo_armor/30;
				  }
				  Double new_armor = mmo_armor*armor_coef;
				  
				  
				  Double new_damage = raw_damage *(1-(new_armor/(raw_damage*5+new_armor)));
				  Settings.debug("{DAMAGE TEST} New damage "+new_damage+" old damage = "+raw_damage);
				  e.setDamage(new_damage);
				  Settings.debug("{DAMAGE TEST} Final damage "+e.getFinalDamage());
				  
				  
				  return;
			  }else {
					  Settings.debug("{DAMAGE TEST} RAW DAMAGE"+raw_damage);
					  if(!itemstk.getType().equals(Material.AIR)) {
						  net.minecraft.world.item.ItemStack tempitmsk = CraftItemStack.asNMSCopy(itemstk);
						  if(tempitmsk.u().e("MMOITEMS_ATTACK_DAMAGE")) {
							  if(tempitmsk.u().e("MMOITEMS_ITEM_TYPE") && tempitmsk.u().c("MMOITEMS_ITEM_TYPE").e_().equals(Settings.mmotype)) {
								  String stringdamage = tempitmsk.u().c("MMOITEMS_ATTACK_DAMAGE").e_();
								  Double puredamage = Double.parseDouble(stringdamage);
								  Double halfdamage = puredamage/Settings.divisor;
								  raw_damage += halfdamage;
							  }
						  }
					  }
					  e.setDamage(raw_damage);
					  Double mmo_armor = MMOCore.plugin.dataProvider.getDataManager().get(player).getStats().getStat("ARMOR");;
					  Settings.debug("{DAMAGE TEST} ARMOR "+mmo_armor);
					  
					  //Double new_damage = raw_damage *(1-(Math.min(249.0,mmo_armor*2))/250);
					  Double new_damage = raw_damage *(1-(mmo_armor/(raw_damage*5+mmo_armor)));
					  
					  Settings.debug("{DAMAGE TEST} New damage "+new_damage);
					  //e.setDamage(new_damage);
				  
			  }
		  
		  
		  
		  
	    }*/
	}
	
	
	
	@EventHandler
	public void onMythicMobDeath(MythicMobDeathEvent e) {
			
			//Settings.debug("[huy] §2Такс, хуякс.=|"+e.getMobType().getInternalName()+"|");
			if(e.getKiller()==null) {
				return;
			}
			
			if(e.getKiller().getType().equals(EntityType.PLAYER)) {
				PlayerData pd = PlayerData.get(e.getKiller().getUniqueId());
				PlayerProfessions pp = pd.getCollectionSkills();
				//Settings.debug("§1[huy]--------------Убийство Охота-----------------");
				for(OhotaDrop od : OhotaConfig.getListOhoty()) {
					if(e.getMobType().getInternalName().equals(od.mobname)) {
						//Settings.debug("[huy] §2 Ваш level охоты: "+pp.getLevel("ohota")+" "+pd.getLevel());
						for(LevelMechanic lm : od.listmechan) {
							int prof;
							if(lm.profession.equals("main")) {
								prof = pd.getLevel();
							}else {
								prof = pp.getLevel(lm.profession);
							}
							List<ItemStack> newlist = e.getDrops();
							
							if(lm.haveEnouchLevel(prof)) {
								LevelPodMech opm = lm.getPodMech(prof);
								for(ZItem zitem : opm.itemlist) {
									if(zitem.willdrop()) {
										ItemStack item;
										if(!zitem.is_vanilla) {
											item =  zitem.getMMOItem();
										}else {
											if(Material.valueOf(zitem.iid) == null) {
												//Settings.debug("Несуществует такого ванильного предмета ("+zitem.iid+")","4");
												continue;
											};
											item = zitem.getVanillaItem();
										}
										if(zitem.amount>1) {
											item.setAmount(zitem.howMatchDrop());
											//Settings.debug("[huy] §2Предмет: Тип ="+zitem.type+"| Id = "+zitem.iid+"  Количество"+item.getAmount());
										}
										newlist.add(item);
									}
								}
								//Settings.debug("[huy] §2 Тест на уровень пройден. Необходимый: "+opm.level+" | Ваш: "+prof);
								//Settings.debug("[huy] §2 Тест предмета: "+opm.itemlist.get(0).type+" "+opm.itemlist.get(0).iid+" "+opm.itemlist.get(0).chance);
								
							}
							if(!lm.onlm.isEmpty()) {
								for(LevelPerLevelMech oolm : lm.onlm){
									for(ZItem zitem : oolm.itemlist) {
										if(oolm.willnewdrop(zitem, prof)) {
											ItemStack item;
											if(!zitem.is_vanilla) {
												item =  zitem.getMMOItem();
											}else {
												if(Material.valueOf(zitem.iid) == null) {
													//Settings.debug("Несуществует такого ванильного предмета ("+zitem.iid+")","4");
													continue;
												};
												item =  zitem.getVanillaItem();
											}
											if(zitem.amount>1) {
												item.setAmount(oolm.howMuchNewDrop(zitem,prof));
												//Settings.debug("[huy] §2Предмет: Тип ="+zitem.type+"| Id = "+zitem.iid+"  Количество"+item.getAmount());
											}
											
											newlist.add(item);
										}
									}
									if(oolm.hasExp()) {
										if(lm.profession.equals("main")) {
											prof = pd.getLevel();
										}else {
											prof = pp.getLevel(lm.profession);
										}
									}
								}
								
							}
							if(!newlist.equals(e.getDrops())) {
								e.setDrops(newlist);
							}
							
						}
						
					}
				}
					
					
			}
			
		}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		String w = e.getBlock().getWorld().getName();
		if(!e.getBlock().hasMetadata("ZARJ_BLOCK_ID")) {
			return;
		}
		if(e.isCancelled()) {
			if(!Settings.isWhiteBlockWorld(w)) {
				return;
			}
		}
		Player player = (Player) e.getPlayer();
		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			for(BlockZarj bz : BlockConfig.blocks) {
				if(e.getBlock().getMetadata("ZARJ_BLOCK_ID").get(0).asString().equals(bz.myid.toString())) {
					if(bz.locs_placed.contains(e.getBlock().getLocation())) {
						bz.locs_placed.remove(e.getBlock().getLocation());
						break;
					}
				}
			}
			e.getBlock().removeMetadata("ZARJ_BLOCK_ID", hezar.inst);
			return;
		}
		
		for(BlockZarj bz : BlockConfig.blocks) {
			if(e.getBlock().getMetadata("ZARJ_BLOCK_ID").get(0).asString().equals(bz.myid.toString())) {
				if(bz.need_pickaxe) {
					ItemStack hand = player.getInventory().getItemInMainHand();
					Material material = hand.getType();
					if(!material.equals(Material.GOLDEN_PICKAXE)&&!material.equals(Material.IRON_PICKAXE)&&!material.equals(Material.DIAMOND_PICKAXE)&&!material.equals(Material.NETHERITE_PICKAXE)) {
						player.sendMessage("§4Необходима, как минимум, железная кирка!");
						e.setCancelled(true);
						
						return;
					}
				}
				
	            
	            
	            if(bz.need_power) {
	            	if(player.getInventory().getItemInMainHand()==null || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
	            		e.setCancelled(true);
	        			return;
	            	}
	            	net.minecraft.world.item.ItemStack nmsitem_used = CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand());
	        		net.minecraft.nbt.NBTTagCompound item_usedNBTComp = nmsitem_used.u();
	        		if(!item_usedNBTComp.e("ZARJ_PICKAXE_POWER")) {
	        			Settings.debug("{Поломка} Не найден тег. Используемый предмет = "+player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()+" ");
	        			player.sendMessage("§4Необходим более мощный инструмент!");
	        			e.setCancelled(true);
	        			return;
	        		}
	        		
	        		//String str_power = item_usedNBTComp.c("ZARJ_PICKAXE_POWER").e_();
		            Integer int_power = item_usedNBTComp.h("ZARJ_PICKAXE_POWER");
		            Settings.debug("{Поломка} Необходимая моща = "+bz.power_to_break+" | Моща кирки = "+int_power);
		            if(int_power<bz.power_to_break) {
		            	player.sendMessage("Инструмент слишком слабый!");
		            	e.setCancelled(true);
		            	return;
		            }
	            }
	            
	            
	            
	            if(!bz.vanilla_drop) {
	            	e.setDropItems(false);
	            }
	            World world = player.getWorld();
				if(bz.will_self_drop) {
					
		            //ItemManager itemManager = MMOItems.plugin.getItems();
		            //MMOItem mmoitem = itemManager.getMMOItem(MMOItems.plugin.getTypes().get("BLOCK"), bz.mmoid);
					ItemStack item = bz.createItem();
		            e.setDropItems(false);
		            e.getBlock().getDrops(item).clear();
		            world.dropItemNaturally(e.getBlock().getLocation(), item);
				}
				 {
						if(bz.only_regen_drop&&!bz.regens.isEmpty()) {
							Server serv = Bukkit.getServer();
							boolean will_break = false;
							for(BlockRegen br : bz.regens) {
								World world_temp = serv.getWorld(br.world);
								for(BlockLocation bl : br.blocks_locs) {
									if(e.getBlock().getLocation().equals(new Location(world_temp,bl.x,bl.y,bl.z))) {
										for(ZItem zitem : bz.droplist) {
							            	if(zitem.willdrop()) {
							            		ItemStack item;
												if(!zitem.is_vanilla) {
													item = zitem.getMMOItem();
												}else {
													if(Material.valueOf(zitem.iid) == null) {
														Settings.debug("Несуществует такого ванильного предмета ("+zitem.iid+")","4");
														continue;
													};
													item = zitem.getVanillaItem();
												}
												
							            		item.setAmount(zitem.howMatchDrop());
							            		world.dropItemNaturally(e.getBlock().getLocation(), item);
							            	}
							            }
										if(!bz.getDrops().isEmpty()) {
											PlayerData pd = PlayerData.get(player.getUniqueId());
								            doDrop(bz,pd,world,e.getBlock().getLocation());
										}
										
										
										
										
										will_break = true;
										break;
									}
								}
								if(will_break) {
									break;
								}
							}
						}else {
							for(ZItem zitem : bz.droplist) {
				            	if(zitem.willdrop()) {
				            		ItemStack item;
									if(!zitem.is_vanilla) {
										item = zitem.getMMOItem();
									}else {
										if(Material.valueOf(zitem.iid) == null) {
											Settings.debug("Несуществует такого ванильного предмета ("+zitem.iid+")","4");
											continue;
										};
										item =  zitem.getVanillaItem();
									}
				            		//ItemStack item = MMOItems.plugin.getItem(zitem.type, zitem.iid);
				            		item.setAmount(zitem.howMatchDrop());
				            		world.dropItemNaturally(e.getBlock().getLocation(), item);
				            	}
				            }
							if(!bz.getDrops().isEmpty()) {
								PlayerData pd = PlayerData.get(player.getUniqueId());
					            Settings.debug("{БЛОКИ} Тест 1. Дроп не пуст!");
					            doDrop(bz,pd,world,e.getBlock().getLocation());
							}
						}
		    			
		    		
				}
				bz.locs_placed.remove(e.getBlock().getLocation());
				e.getBlock().removeMetadata("ZARJ_BLOCK_ID", hezar.inst);
				e.setCancelled(false);
				return;
	            
			}
		}
		e.getBlock().removeMetadata("ZARJ_BLOCK_ID", hezar.inst);
		
		
	}
	private void doDrop(BlockZarj bz,PlayerData pd,World world,Location loc) {
		for(LevelMechanic lm : bz.getDrops()) {
			int level = 0;
			if(lm.profession.equalsIgnoreCase("main")) {
				level = pd.getLevel(); 
			
			}else{
				PlayerProfessions pp = pd.getCollectionSkills();
				level = pp.getLevel(lm.profession);
			}
			if(!lm.onlm.isEmpty()) {
				for(LevelPerLevelMech level_mech: lm.onlm) {
					for(ZItem zitem : level_mech.itemlist) {
						if(level_mech.willnewdrop(zitem, level)) {
							ItemStack item;
							if(!zitem.is_vanilla) {
								item =  zitem.getMMOItem();
							}else {
								if(Material.valueOf(zitem.iid) == null) {
									Settings.debug("Несуществует такого ванильного предмета ("+zitem.iid+")","4");
									continue;
								};
								item =  zitem.getVanillaItem();
							}
							if(zitem.amount>1) {
								item.setAmount(level_mech.howMuchNewDrop(zitem,level));
								Settings.debug("[huy] §2Предмет: Тип ="+zitem.type+"| Id = "+zitem.iid+"  Количество"+item.getAmount());
							}

		            		world.dropItemNaturally(loc, item);
						}
					}
				}
			}
			if(lm.haveEnouchLevel(level)) {
	            Settings.debug("{БЛОКИ} Тест 2. Уровня хватает на один из под мехов! "+level);
				LevelPodMech lpm= lm.getPodMech(level);
				if(lpm==null) {
		            Settings.debug("{БЛОКИ} Проблемка, возвращенная подмеханика ровна null level="+level,4);
					
				}
				int exp = lpm.getExp();
				if(exp>0) {
					if(lm.profession.equalsIgnoreCase("main")) {
						pd.giveExperience(exp, null);
					
					}else{
						PlayerProfessions pp = pd.getCollectionSkills();
						pp.giveExperience( LevelMechanic.getProfManager().get(lm.profession), exp, EXPSource.OTHER);
					}
				}
				if(lpm.hasLootBoxes()) {
					for(LootBox loot: lpm.getLootBoxes()) {
						ZItem zitem = loot.rollItems();
						ItemStack item = zitem.getItemStack();
						item.setAmount(zitem.howMatchDrop());
	            		world.dropItemNaturally(loc, item);
					}
				}
				
				
				for(ZItem zitem : lpm.getItems()) {
	            	if(zitem.willdrop()) {
			            Settings.debug("{БЛОКИ} Тест 3. Должно выдать предмет! "+zitem.iid);
	            		ItemStack item = zitem.getItemStack();
						
	            		item.setAmount(zitem.howMatchDrop());
	            		world.dropItemNaturally(loc, item);
	            	}
				}
			}
		}
		
	}
	
	@EventHandler
    public void LootBoxCheck(PlayerInteractEvent e) {
		if(e.getAction()!=Action.RIGHT_CLICK_AIR) {
			return;
		}
		if(!e.hasItem()) {
			return;
		}
		if(e.getHand()!=EquipmentSlot.HAND) {
			return;
		}
		ItemStack item = e.getItem();
		NBTItem nbt_item =  NBTItem.get(item);
		if(nbt_item.hasTag(ZarjTags.ZARJ_LOOTBOX_ID.name())) {
			Player p = e.getPlayer();
			
			if(p.getInventory().firstEmpty()==-1) {
				p.sendMessage("§4Освободите как минимум одну ячейку инвентаря!");
				return;
			}
			String loot_id = nbt_item.getString(ZarjTags.ZARJ_LOOTBOX_ID.name());
			if(!LootManager.hasLoot(loot_id)){
				p.sendMessage("§4Ваш лутбокс имеет неправильный тег или устарел, если вы уверены, что он должен быть рабочим, обратитесь к администрации сервера!");
				return;
			}
			LootBox loot_box = LootManager.getLoot(loot_id);
			if(loot_box.needPerm()&&!p.hasPermission(loot_box.getPermission())) {
				p.sendMessage("§4У вас недостаточно прав для использования данного предмета!");
				return;
			}
			if(e.getPlayer().getGameMode()!=GameMode.CREATIVE) {
				if(item.getAmount()>1) {
					item.setAmount(item.getAmount()-1);
				}else {
					item.setAmount(0);
					
				}
			}

			ZItem zitem = loot_box.rollItems();
			ItemStack new_item = zitem.getItemStack();
			new_item.setAmount(zitem.howMatchDrop());
			p.getInventory().addItem(new_item);
			
			
			
			
			
			
			
			
			
					
		}
	}
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {

		if(e.isCancelled()) {
			return;
		}
		
		ItemStack item_hand = e.getItemInHand();
		item_hand.getItemMeta().getPersistentDataContainer().getKeys();
		net.minecraft.world.item.ItemStack nmsitem = CraftItemStack.asNMSCopy(item_hand);
		net.minecraft.nbt.NBTTagCompound itemNBTComp = nmsitem.u();
		if(!itemNBTComp.e("ZARJ_BLOCK_ID")) {
			return;
		}
		for(BlockZarj bz : BlockConfig.blocks) {
			Settings.debug("Хуета = "+itemNBTComp.h("ZARJ_BLOCK_ID"));  
			Settings.debug("Предмет в руке ="+e.getItemInHand().getType()+" | CMD ="+itemNBTComp.c("ZARJ_BLOCK_ID").e_()+"   |   bz = "+bz.getMyId());
			if(itemNBTComp.c("ZARJ_BLOCK_ID").e_().equalsIgnoreCase(bz.myid.toString())) {
				if(bz.can_be_placed||e.getPlayer().isOp()) {
					e.getBlock().setMetadata("ZARJ_BLOCK_ID", new FixedMetadataValue(hezar.inst, bz.myid));
					Settings.debug("Установлен верный блок!");
					bz.addLocation(e.getBlock().getLocation());
					
				}else {
					e.setCancelled(true);
					e.getPlayer().sendMessage(bz.place_error);
				}
				return;
			}
		}
		
	}
	
	@EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	Block block = e.getClickedBlock();
        	for(CMDBlock cmdb : CBConfig.list_cb) {
        		if(cmdb.loc.equals(block.getLocation())) {
        			if(!cmdb.asconsole) {
        				e.getPlayer().performCommand(cmdb.cmd);
        			}else {
        				Settings.debug("{CMDBlocks} Использован блок. Выполняю "+cmdb.cmd.replaceAll("%player%", e.getPlayer().getName()));
        				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmdb.cmd.replaceAll("%player%", e.getPlayer().getName()));
        			}
        			break;
        			
        		}
        	}
        	
        	
        }
	}
	
	@EventHandler
    public void onUpgradeItem(/*InventoryDragEvent*/InventoryClickEvent e) {
		if(!e.isRightClick()) {
			return;
		}
		net.minecraft.world.item.ItemStack tempitmsk_temp = CraftItemStack.asNMSCopy(e.getCursor());
		NBTItem nbt_toch = NBTItem.get(e.getCursor());
		if(!tempitmsk_temp.u().e("ZARJ_TOCH_ID")) {
			return;
		}
		net.minecraft.world.item.ItemStack tempitmsk = CraftItemStack.asNMSCopy(e.getCurrentItem());
		NBTItem nbt_item = NBTItem.get(e.getCurrentItem());
		if(!tempitmsk.u().e("MMOITEMS_ITEM_ID")) {
			return;
		}
		//int id = Integer.parseInt(tempitmsk_temp.u().c("ZARJ_TOCH_ID").e_());
		int id = tempitmsk_temp.u().h("ZARJ_TOCH_ID");
		if(!TochConfig.hasTochilka(id)) {
			Settings.debug("{ТОЧИЛКА} Нет такой точилки.");
			return;
		}
		Tochilka toch = TochConfig.getTochilka(id);
		String mmotype = tempitmsk.u().c("MMOITEMS_ITEM_TYPE").e_();
		String mmoid = tempitmsk.u().c("MMOITEMS_ITEM_ID").e_();
		if(!toch.hasItem(mmotype, mmoid)) {
			e.getView().getPlayer().sendMessage("{ТОЧИЛКА} Этой точилкой нельзя улучшить этот предмет.");
			e.setCancelled(true);
			return;
		}
		int level;
		if(!tempitmsk.u().e("ZARJ_TOCH_LEVEL")) {
			level=0;
		}else {
			level = tempitmsk.u().h("ZARJ_TOCH_LEVEL");//Integer.parseInt(tempitmsk.u().c("ZARJ_TOCH_LEVEL").e_());
		}
		if(!toch.isUpdatable(level)) {
			e.getView().getPlayer().sendMessage("Этот предмет нельзя прокачать данной точкой.");
			e.setCancelled(true);
			return;
		}
		if(!toch.willUpgrade(level)) {
			e.getCursor().setAmount(e.getCursor().getAmount()-1);
			e.getWhoClicked().sendMessage("§4Неудача!");
			e.setCancelled(true);
			
			if(toch.broke_in_fall) {
				if(e.getWhoClicked().getInventory().getItemInOffHand()!=null) {
					ItemStack rune = e.getWhoClicked().getInventory().getItemInOffHand();
					NBTItem nbt_rune = NBTItem.get(rune);
					if(nbt_rune.hasTag(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString())) {
						int rune_level = nbt_rune.getInteger(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString());
						if(rune_level>=toch.rune_level) {
							rune.setAmount(rune.getAmount()-1);
							
							e.getWhoClicked().sendMessage("§4Была израсходованна руна "+rune_level+" уровня, для предотвращения обнуления предмета.");
							
							
							
							
							return;
						}else {
							e.getWhoClicked().sendMessage("§4Установленная руна слишком слаба ("+rune_level+"), необходима руна с силой "+toch.rune_level+" или выше!");
						}
					}
				}
				e.getCurrentItem().setItemMeta(Tochilka.toZero(e.getCurrentItem().getItemMeta(),e.getCurrentItem().getType().getMaxDurability()));
			}else if(toch.down) {
				if(level==0) {
					return;
				}
				if(e.getWhoClicked().getInventory().getItemInOffHand()!=null) {
					ItemStack rune = e.getWhoClicked().getInventory().getItemInOffHand();
					NBTItem nbt_rune = NBTItem.get(rune);
					if(nbt_rune.hasTag(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString())) {
						int rune_level = nbt_rune.getInteger(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString());
						if(rune_level>=toch.rune_level) {
							rune.setAmount(rune.getAmount()-1);
							
							e.getWhoClicked().sendMessage("§4Была израсходованна руна "+rune_level+" уровня, для предотвращения уменьшения уровня точки.");
							
							return;
						}else {
							e.getWhoClicked().sendMessage("§4Установленная руна слишком слаба ("+rune_level+"), необходима руна с силой "+toch.rune_level+" или выше!");
						}
					}
				}
				boolean old = toch.isOld(level);
				tempitmsk.u().a("ZARJ_TOCH_LEVEL",(level-1));
				ItemStack new_item = CraftItemStack.asCraftMirror(tempitmsk);
				List<String> lor = new_item.getItemMeta().getLore();
				for(TochStat ts : toch.list_stats) {
					if(tempitmsk.u().e("MMOITEMS_"+ts.type)) {
						double value = Double.parseDouble(tempitmsk.u().c("MMOITEMS_"+ts.type).e_());
						double final_val= 0.0;
						
						if(!old) {
							if(ts.percent) {
								final_val=ts.getPercentFinalValue(value);
							}else {
								final_val= ts.getFinalValue(level);
							}
							
						}else {
							if(ts.percent) {
								ts.getPercentPreValue(value);
							}else {
								final_val= ts.getPreValue(level);
							}
						}
						double end_val = value-final_val;
						BigDecimal bd_val = new BigDecimal(Double.toString(end_val));
						bd_val = bd_val.setScale(2, RoundingMode.HALF_UP);
						end_val=bd_val.doubleValue();
						Settings.debug("TOCHKA FAIL "+ value+" | "+(value-final_val)+" | Вычитаемое значение: "+ts.getFinalValue(level)+" || Новое значение: "+end_val);
						
						tempitmsk.u().a("MMOITEMS_"+ts.type,  end_val);
						
						double value_z=0.0;
						if(tempitmsk.u().e("ZARJ_"+ts.type)) {
							value_z = Double.parseDouble(tempitmsk.u().c("ZARJ_"+ts.type).e_());
							value_z-=final_val;
							BigDecimal bd_val_z = new BigDecimal(Double.toString(value_z));
							bd_val_z = bd_val_z.setScale(2, RoundingMode.HALF_UP);
							value_z=bd_val_z.doubleValue();
							
							tempitmsk.u().a("ZARJ_"+ts.type,  value_z);
						}
						lor = toch.removeLevelLore(lor, level, value_z, ts,end_val);
						
					}
				}
				ItemMeta m = new_item.getItemMeta();
				
				String key_regex="(.*)(\\+.*\\d)(.*)";
				
				if(new_item.getItemMeta().getDisplayName().matches(key_regex)) {
					Pattern pattern = Pattern.compile("\\+.*\\d");
					
					Matcher matcher = pattern.matcher(new_item.getItemMeta().getDisplayName());
					if (matcher.find()) {
						//Settings.debug("{TOCHKA} Не найдено ");
					}
					String plus = matcher.group();
					String s_temp = new_item.getItemMeta().getDisplayName();
					if(level!=1) {
						
						s_temp = s_temp.replaceAll(plus.replaceAll("\\+", "\\\\+"), "+"+(level-1));
					}else {
						s_temp = s_temp.replaceAll(plus.replaceAll("\\+", "\\\\+"), "");
					}
					m.setDisplayName(s_temp);
					
					
				}
				
				m.setLore(lor);
				new_item.setItemMeta(m);
				e.setCurrentItem(new_item);
			}
			
			
			
			return;
		}
		level++;
		
		if(e.getWhoClicked().getInventory().getItemInOffHand()!=null) {
			ItemStack rune = e.getWhoClicked().getInventory().getItemInOffHand();
			NBTItem nbt_rune = NBTItem.get(rune);
			if(nbt_rune.hasTag(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString())) {
				int rune_level = nbt_rune.getInteger(ZarjTags.ZARJ_TOCH_RUNE_LEVEL.toString());
				if(rune_level>=toch.rune_level) {
					rune.setAmount(rune.getAmount()-1);
					e.getWhoClicked().sendMessage("§4Была израсходованна руна "+rune_level+" уровня.");
				}else {
					e.getWhoClicked().sendMessage("§4Установленная руна слишком слаба ("+rune_level+"), необходима руна с силой "+toch.rune_level+" или выше!");
				}
			}
		}
		
		
		//MMOItem mmoitem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(mmotype), mmoid);
		
		tempitmsk.u().a("ZARJ_TOCH_LEVEL",level);
		List<String> lor = e.getCurrentItem().getItemMeta().getLore();
		for(TochStat ts : toch.list_stats) {
			if(tempitmsk.u().e("MMOITEMS_"+ts.type)) {
				
				double value = Double.parseDouble(tempitmsk.u().c("MMOITEMS_"+ts.type).e_());
				double final_val = 0.0;
				if(ts.percent) {
					final_val = ts.getPercentFinalValue(value);
				}else {
					final_val = ts.getFinalValue(level);
				}
				
				double end_val = value+final_val;
				BigDecimal bd_val = new BigDecimal(Double.toString(end_val));
				bd_val = bd_val.setScale(2, RoundingMode.HALF_UP);
				end_val=bd_val.doubleValue();
				Settings.debug("§6{ТОЧИЛКА УЛУЧШЕНИЕ} Имеемое знрачение="+value+" | Вносимое="+(value + final_val)+" | Прибавляемое="+final_val+" | Новое носимое ="+end_val);
				
				Double value_z;
				if(tempitmsk.u().e("ZARJ_"+ts.type)) {
					value_z = Double.parseDouble(tempitmsk.u().c("ZARJ_"+ts.type).e_());
					value_z+=final_val;
					BigDecimal bd_val_z = new BigDecimal(Double.toString(value_z));
					bd_val_z = bd_val_z.setScale(2, RoundingMode.HALF_UP);
					value_z = bd_val_z.doubleValue();
					tempitmsk.u().a("ZARJ_"+ts.type,  value_z);
				}else {
					value_z=final_val;
					tempitmsk.u().a("ZARJ_"+ts.type,  value_z);
				}
				tempitmsk.u().a("MMOITEMS_"+ts.type, end_val);
				lor = toch.addLore(lor, level, end_val, ts,value_z);
			}
		}
		
		
		
		
		
		
		
		ItemStack new_item = CraftItemStack.asCraftMirror(tempitmsk);
		String key_regex="(.*)(\\+.*\\d)(.*)";
		Settings.debug("{ТОЧИЛКА} Имя до изменения" + new_item.getItemMeta().getDisplayName());
		if(new_item.getItemMeta().getDisplayName().matches(key_regex)) {
			Pattern pattern = Pattern.compile("\\+.*\\d");
			
			Matcher matcher = pattern.matcher(new_item.getItemMeta().getDisplayName());
			if (matcher.find()) {
				Settings.debug("{TOCHKA} Не найдено ");
			}
			String plus = matcher.group();
			Settings.debug("{TOCHKA} ПЛЮС "+plus);
			String s_temp = new_item.getItemMeta().getDisplayName();
			Settings.debug("{TOCHKA} substr |"+plus.substring(1)+"| Замен на "+level+"");
			s_temp = s_temp.replaceAll("\\"+plus, "+"+level+"");
			
			ItemMeta m = new_item.getItemMeta();
			Settings.debug("{TOCHKA} Имя должно быть "+s_temp);
			m.setDisplayName(s_temp);
			new_item.setItemMeta(m);
			//new_item.getItemMeta().getDisplayName().replaceAll(key_regex, "+"+level);
			Settings.debug("{TOCHKA} Новое имя "+new_item.getItemMeta().getDisplayName());
		}else {
			String s_temp = new_item.getItemMeta().getDisplayName();
			s_temp +=" +"+level;
			ItemMeta m = new_item.getItemMeta();
			m.setDisplayName(s_temp);
			new_item.setItemMeta(m);
			Settings.debug("{TOCHKA} Новое имя "+new_item.getItemMeta().getDisplayName());
		}
		net.minecraft.world.item.ItemStack fnew_name_item = CraftItemStack.asNMSCopy(new_item);
		if(fnew_name_item.u().e("MMOITEMS_NAME")) {
			fnew_name_item.u().a("MMOITEMS_NAME",new_item.getItemMeta().getDisplayName());
		}
		new_item = CraftItemStack.asCraftMirror(fnew_name_item);
		
		//lor = toch.addLore(new_item.getItemMeta().getLore(), level);
		ItemMeta m = new_item.getItemMeta();
		m.setLore(lor);
		new_item.setItemMeta(m);
		e.setCurrentItem(new_item);
		e.getCursor().setAmount(e.getCursor().getAmount()-1);
		//e.setCursor(null);
		
		
		
		
		if(!tempitmsk.u().e("ZARJ_TOCH_LEVEL")) {
			tempitmsk.u().a("ZARJ_TOCH_LEVEL",0);
		}
		e.setCancelled(true);
		Settings.debug("{TOOOOOOCHKA} Супер, дупер тест "+e.getCurrentItem().getItemMeta().getDisplayName());
		//e.setCurrentItem();;
		
		//Settings.debug("{TOCHKA} Старый курсор = "+e.getCurrentItem().getType());
		//Settings.debug("{TOCHKA} Актуальный курсор = "+e.getCursor().getType());
		//Settings.debug("{TOCHILKA} Заебок! id = "+tempitmsk.u().c("ZARJ_TOCH_ID").e_());
		if(tempitmsk_temp.u().e("ZARJ_TOCH_ID")) {
			//Settings.debug("{TOCHILKA} Актуальное id = "+tempitmsk_temp.u().c("ZARJ_TOCH_ID").e_());
		}
		
		
	}

	
	@EventHandler
	public void onAddGemToUp(ApplyGemStoneEvent e) {
		ItemStack is = e.getTargetItem().newBuilder().build();
		net.minecraft.world.item.ItemStack tempitmsk_temp = CraftItemStack.asNMSCopy(is);
		if(!tempitmsk_temp.u().e("ZARJ_TOCH_LEVEL")) {
			Settings.debug("Ну пиздец, крч, нет в этом прдмете точки, но вроде робит. Ах да, "+is.getItemMeta().getLore());
			return;
		}
		Settings.debug("Взлом жопы, всё ок, едем дальше. "+is.getItemMeta().getLore());
		
	}
	
	
	@EventHandler 
	public void onPlayerItemBreak(PlayerItemBreakEvent e) {
		ItemStack item = e.getBrokenItem();
		net.minecraft.world.item.ItemStack tempitmsk = CraftItemStack.asNMSCopy(item);
		if(!tempitmsk.u().e("MMOITEMS_ITEM_ID")) {
			return;
		}
		LiveMMOItem live_item = new LiveMMOItem(item);
		Set<GemstoneData> gem_set = live_item.getGemStones();
		if(gem_set.isEmpty()) {
			Settings.debug("Сет пустой, гемов нет.");
			return;
		}
		//List<ItemStack> items_list = new ArrayList<>();
		Player p = e.getPlayer();
		for(GemstoneData gem : gem_set) {
			ItemStack item_gem = MMOItems.plugin.getItem("GEM_STONE", gem.getMMOItemID());
			int first_empty = p.getInventory().firstEmpty();
			Settings.debug("Такс, куета, номер пустого слота = "+first_empty);
			if(first_empty==-1) {
				p.getWorld().dropItem(p.getLocation(), item_gem);
			}else {
				p.getInventory().addItem(item_gem);
			}
			
			//items_list.add(MMOItems.plugin.getItem("GEM_STONE", gem.getMMOItemID()));
		}
	}
	@EventHandler
	public void onBlowBlock(BlockExplodeEvent e) {
		if(!Settings.blow_blocks) {
			e.setCancelled(true);
		}
	}



	private double getAllArmor(PlayerInventory inv) {
		Double armor = 0.0;
		final int mnozh = 4;
		for(ItemStack item : inv.getArmorContents()) {
			if(item==null) {
				continue;
			}
			NBTItem nbt_item = NBTItem.get(item);
			if(nbt_item.hasTag("MMOITEMS_ARMOR")) {
				armor+=nbt_item.getDouble("MMOITEMS_ARMOR");
			}else {
				
				switch(item.getType()) {
					
						
					case LEATHER_BOOTS:
					case LEATHER_HELMET:
					case GOLDEN_BOOTS:
					case CHAINMAIL_BOOTS:
						armor+=1*mnozh;
						break;
					case GOLDEN_HELMET:
					case CHAINMAIL_HELMET:
					case IRON_HELMET:
					case IRON_BOOTS:
					case LEATHER_LEGGINGS:
						armor+=2*mnozh;
						break;
					case DIAMOND_HELMET:
					case DIAMOND_BOOTS:
						armor+=5*mnozh;
						break;
					case GOLDEN_LEGGINGS:
					case LEATHER_CHESTPLATE:
						armor+=3*mnozh;
						break;
					case CHAINMAIL_LEGGINGS:
						armor+=4*mnozh;
						break;
					case GOLDEN_CHESTPLATE:
					case IRON_LEGGINGS:
					case CHAINMAIL_CHESTPLATE:
						armor+=5*mnozh;
						break;
					case DIAMOND_LEGGINGS:
						armor+=8*mnozh;
						break;
					case IRON_CHESTPLATE:
					case NETHERITE_HELMET:
					case NETHERITE_BOOTS:
						armor+=6*mnozh;
						break;
					case DIAMOND_CHESTPLATE:
						armor+=10*mnozh;
						break;
					case NETHERITE_CHESTPLATE:
						armor+=11*mnozh;
						break;
					case NETHERITE_LEGGINGS:
						armor+=9*mnozh;
						break;
					default: continue;
					
					
				
				
				}
			}
		}
		
		
		
		return armor;
	}


	







}