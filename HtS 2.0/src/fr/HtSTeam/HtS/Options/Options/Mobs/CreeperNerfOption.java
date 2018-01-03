package fr.HtSTeam.HtS.Options.Options.Mobs;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class CreeperNerfOption extends OptionsManager {
	
	private boolean activate = false;
	
	public CreeperNerfOption() {
		super(Material.SULPHUR, "One shot des Creepers", "§4Désactivé", "Désactivé", OptionsRegister.mob);
	}
	
	
	@Override
	public void event(Player p) {
		activate = !activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);		
		
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCreeperOneShot(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		if (EnumState.getState().equals(EnumState.RUNNING) && !activate && ((p instanceof Player)) && e.getDamager() instanceof Creeper) {
			double damage = e.getDamage();
			if (damage >= ((Player) p).getHealth()) {	
				((Player) p).setHealth(2.0D);
				e.setCancelled(true);
			}
		}
	}

	

}
