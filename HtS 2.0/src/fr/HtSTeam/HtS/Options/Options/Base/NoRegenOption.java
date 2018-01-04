package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class NoRegenOption extends OptionsManager implements Alterable {
	
	private boolean activate = false;

	public NoRegenOption() {
		super(Material.INK_SACK, "Régération naturelle", "§4Désactivé", "Désactivé", OptionsRegister.base);
		getItemStackManager().setItem(Material.INK_SACK, (short) 8);
		parent.update(this);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}
	
	@EventHandler
	public void onFoodHealEvent(EntityRegainHealthEvent e) {
		if(!activate) {
			Entity p = e.getEntity();
			if(p instanceof Player && e.getRegainReason() == RegainReason.SATIATED)
				e.setCancelled(true);
		}
	}

	@Override
	public void setState(boolean value) {
		
		activate = value;
		if(value) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			getItemStackManager().setItem(Material.INK_SACK, (short) 9);
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			getItemStackManager().setItem(Material.INK_SACK, (short) 8);
		}
		parent.update(this);
		
		
	}
}

