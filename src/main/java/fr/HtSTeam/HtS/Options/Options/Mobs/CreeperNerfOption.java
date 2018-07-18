package fr.HtSTeam.HtS.Options.Options.Mobs;

import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class CreeperNerfOption extends OptionBuilder<Boolean>{
		
	public CreeperNerfOption() {
		super(Material.SULPHUR, "One shot des Creepers", "§4Désactivé", false, GUIRegister.mob);
	}
	
	
	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@EventHandler()
	public void onCreeperOneShot(EntityDamageByEntityEvent e) {
		Entity p = e.getEntity();
		if (EnumState.getState().equals(EnumState.RUNNING) && !getValue() && ((p instanceof Player)) && e.getDamager() instanceof Creeper) {
			double damage = e.getFinalDamage();
			if (damage >= ((Player) p).getHealth()) {	
				((Player) p).setHealth(2.0D);
				e.setCancelled(true);
			}
		}
	}


	@Override
	public void setState(Boolean value) {
		if(value) 
			getItemStack().setLore("§2Activé");
		else 
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);		
	}


	@Override
	public String description() {
		return "§2[Aide]§r Les creepers ne peuvent pas porter le coup final au joueur, le laissant à 2 points de vie.";
	}
}
