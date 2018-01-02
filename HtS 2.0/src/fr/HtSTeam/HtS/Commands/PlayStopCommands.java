package fr.HtSTeam.HtS.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

import fr.HtSTeam.HtS.Main;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class PlayStopCommands implements CommandExecutor, Listener {

	private boolean pause = false;
	private String dayCycleState;

	public PlayStopCommands() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this, Main.plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {

			if (cmd.getName().equalsIgnoreCase("pause") && sender.hasPermission("pause.use")) {
				if(!pause) {
					Bukkit.broadcastMessage("§4Le jeu est en pause !");
					pause = true;
					dayCycleState = Main.world.getGameRuleValue("doDaylightCycle");
					Main.world.setGameRuleValue("doDaylightCycle", "false");
	
					for (World w : Bukkit.getWorlds())
						for (org.bukkit.entity.Entity en : w.getEntities())
							if (en instanceof Creature && !(en instanceof Player))
								freezeEntity(en);
				}
				return true;
				
			} else if (cmd.getName().equalsIgnoreCase("play") && sender.hasPermission("play.use")) {
				if(pause) {
					Bukkit.broadcastMessage("§2Le jeu est de nouveau lancé !");
					Main.world.setGameRuleValue("doDaylightCycle", dayCycleState);
					for (World w : Bukkit.getWorlds())
						for (org.bukkit.entity.Entity en : w.getEntities())
							if (en instanceof Creature && !(en instanceof Player))
								unfreezeEntity(en);
					pause = false;
				}
				return true;
			}

		}

		return false;
	}

	@EventHandler
	public void onPlayerMove(PlayerAnimationEvent e) {
		if (pause && (e.getPlayer().getGameMode().equals(GameMode.ADVENTURE) || e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerMove2(PlayerMoveEvent e) {
		if (pause && (e.getPlayer().getGameMode().equals(GameMode.ADVENTURE) || e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)))
			e.setCancelled(true);
	}

	@EventHandler
	public void feed(FoodLevelChangeEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (pause && p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if (pause && e.getPlayer().getGameMode().equals(GameMode.ADVENTURE)
				|| e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onHealLevelChange(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (pause && p.getGameMode().equals(GameMode.ADVENTURE) || p.getGameMode().equals(GameMode.SURVIVAL)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMobSpawn(EntitySpawnEvent e) {
		if (pause)
			e.setCancelled(true);
	}

	@EventHandler
	public void onItemDespawn(ItemDespawnEvent e) {
		if (pause)
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
		if (pause)
			e.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (pause)
			e.setCancelled(true);
	}

	public void freezeEntity(org.bukkit.entity.Entity en) {
		Entity nmsEn = ((CraftEntity) en).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 1);
		nmsEn.f(compound);
	}

	public void unfreezeEntity(org.bukkit.entity.Entity en) {
		Entity nmsEn = ((CraftEntity) en).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 0);
		nmsEn.f(compound);
	}

}
