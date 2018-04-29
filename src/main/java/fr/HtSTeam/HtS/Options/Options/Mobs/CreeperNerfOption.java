package fr.HtSTeam.HtS.Options.Options.Mobs;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class CreeperNerfOption extends OptionBuilder implements Alterable {
	
	private boolean activate = false;
	
	public CreeperNerfOption() {
		super(Material.SULPHUR, "One shot des Creepers", "§4Désactivé", "Désactivé", OptionRegister.mob);
	}
	
	
	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}

	@EventHandler()
	public void onCreeperOneShot(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		if (EnumState.getState().equals(EnumState.RUNNING) && !activate && ((p instanceof Player)) && e.getDamager() instanceof Creeper) {
			double damage = e.getFinalDamage();
			if (damage >= ((Player) p).getHealth()) {	
				((Player) p).setHealth(2.0D);
				e.setCancelled(true);
			}
		}
	}


	@Override
	public void setState(boolean value) {
		activate = value;
		if(value) {
			setValue("Activé");
			getItemStack().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);		
	}

	

}
