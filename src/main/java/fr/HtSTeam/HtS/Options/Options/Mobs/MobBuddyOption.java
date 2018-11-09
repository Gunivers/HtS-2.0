package fr.HtSTeam.HtS.Options.Options.Mobs;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.FixedMetadataValue;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.MobManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class MobBuddyOption extends Option<Boolean> {
	
	public MobBuddyOption() {
		super(Material.ZOMBIE_SPAWN_EGG, "Mob Buddy", "§4Désactivé", false, GUIRegister.mob);
	}
	
	@Override
	public void event(Player p) {
		setState(!value);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMobTarget(EntityTargetLivingEntityEvent e){
		if (!value) return;
		if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("buddy")) {
			if (((Player)e.getTarget()).getUUID().toString().equalsIgnoreCase(e.getEntity().getMetadata("buddy").get(0).asString())) {
				e.setCancelled(true);
			} else if (!Team.teamList.isEmpty()) {
//				if (Team.playerTeam.get(UUID.fromString(e.getEntity().getMetadata("buddy").get(0).asString())).getTeamPlayers().contains(e.getTarget().getUniqueId()))
//					e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onMobSpawn(PlayerInteractEvent e) {
		if (!value || e.getItem() == null)
			return;
		if (e.getItem().getType() == Material.ZOMBIE_SPAWN_EGG && e.getItem().hasItemMeta() && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)) {
			e.setCancelled(true);
			e.getItem().setAmount(e.getItem().getAmount() - 1);
			Entity mob = e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), MobManager.eggToMob(e.getItem().getType()));
			mob.setMetadata("buddy", new FixedMetadataValue(Main.plugin, e.getPlayer().getUniqueId().toString()));
			if (mob instanceof Zombie) {
				Zombie m = (Zombie) mob;
				ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
				if (Team.teamList.size() != 0) {
					LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
					meta.setColor(getColor(e.getPlayer().getUniqueId()));
					helmet.setItemMeta(meta);
				}
				m.getEquipment().setHelmet(helmet);
			} else if (mob instanceof Skeleton) {
				Skeleton m = (Skeleton) mob;
				ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
				if (Team.teamList.size() != 0) {
					LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
					meta.setColor(getColor(e.getPlayer().getUniqueId()));
					helmet.setItemMeta(meta);
				}
				m.getEquipment().setHelmet(helmet);
			}
		}
	}

	private Color getColor(UUID uniqueId) {
//		switch (Team.playerTeam.get(uniqueId).getTeamColor()) {
//			case "white":
//				return Color.WHITE;
//			case "gold":
//				return Color.ORANGE;
//			case "dark_red":
//				return Color.MAROON;
//			case "aqua":
//				return Color.AQUA;
//			case "yellow":
//				return Color.YELLOW;
//			case "green":
//				return Color.LIME;
//			case "light_purple":
//				return Color.FUCHSIA;
//			case "dark_gray":
//				return Color.GRAY;
//			case "gray":
//				return Color.SILVER;
//			case "dark_aqua":
//				return Color.TEAL;
//			case "dark_purple":
//				return Color.PURPLE;
//			case "dark_blue":
//				return Color.NAVY;
//			case "dark_green":
//				return Color.OLIVE;
//			case "red":
//				return Color.RED;
//			case "black":
//				return Color.BLACK;
//		}
		return null;
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if (!value)
			return;
		if (!e.getEntity().hasMetadata("buddy") && (e.getEntity() instanceof Monster || e.getEntityType() == EntityType.SLIME) && e.getEntity().getKiller() instanceof Player) {
			if (Randomizer.randRate(17)) {
				ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
				drops.add(new ItemStackBuilder(e.getEntityType(), 1, ChatColor.GREEN + "Appel à un ami", "Fait apparaître un monstre qui combattra à vos côtés (il ne vous suivera pas)."));
				e.getDrops().addAll(drops);				
			}
		}
	}

	@Override
	public void setState(Boolean value) {
		if(value)
			getItemStack().setLore("§2Activé");			
		else
			getItemStack().setLore("§4Désactivé");
		this.value = value;
		getParent().update(this);		
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r À leur mort, les mobs hostiles ont 17% de chances de drop un oeuf.\r Ce dernier une fois invoqué combattra à vos côtés.";
	}
}
