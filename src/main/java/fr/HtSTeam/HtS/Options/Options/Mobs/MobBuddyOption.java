package fr.HtSTeam.HtS.Options.Options.Mobs;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.metadata.FixedMetadataValue;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class MobBuddyOption extends OptionBuilder {
	
	public MobBuddyOption() {
		super(Material.MONSTER_EGG, "Mob Buddy", "§4Désactivé", "Désactivé", OptionRegister.mob);
	}

	private boolean activate = false;
	
	@Override
	public void event(Player p) {
		activate = !activate;
		if(activate) {
			setValue("Activé");
			getItemStack().setLore("§2Activé");			
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMobTarget(EntityTargetLivingEntityEvent e){
		if (!activate) return;
		if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("buddy")) {
			if (((Player)e.getTarget()).getUniqueId().toString().equalsIgnoreCase(e.getEntity().getMetadata("buddy").get(0).asString())) {
				e.setCancelled(true);
			} else if (!TeamBuilder.teamList.isEmpty()) {
				if (TeamBuilder.playerTeam.get(UUID.fromString(e.getEntity().getMetadata("buddy").get(0).asString())).getTeamPlayers().contains(e.getTarget().getUniqueId()))
					e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMobSpawn(PlayerInteractEvent e) {
		if (!activate)
			return;
		if (e.getPlayer().getItemInHand().getType() == Material.MONSTER_EGG && e.getItem().getItemMeta().hasDisplayName() && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.setCancelled(true);
			Entity mob = e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), ((SpawnEggMeta) e.getPlayer().getItemInHand().getItemMeta()).getSpawnedType());
			mob.setMetadata("buddy", new FixedMetadataValue(Main.plugin, e.getPlayer().getUniqueId().toString()));
			e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
		}
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if (!activate)
			return;
		if (!e.getEntity().hasMetadata("buddy") && (e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME) && e.getEntity().getKiller() instanceof Player) {
			if (Randomizer.RandRate(17)) {
				ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
				drops.add(new ItemStackBuilder(e.getEntityType(), 1, ChatColor.GREEN + "Appel à un ami", "Fait apparaître un monstre qui combattra à vos côtés (il ne vous suivera pas)."));
				e.getDrops().addAll(drops);				
			}
		}
	}
}
