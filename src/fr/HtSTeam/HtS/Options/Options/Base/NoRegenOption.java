package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class NoRegenOption extends OptionBuilder implements Alterable {
	
	private boolean activate = false;

	public NoRegenOption() {
		super(Material.INK_SACK, "Régération naturelle", "§4Désactivé", "Désactivé", OptionRegister.base);
		getItemStack().setItem(Material.INK_SACK, (short) 8);
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
			getItemStack().setLore("§2Activé");
			getItemStack().setItem(Material.INK_SACK, (short) 9);
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
			getItemStack().setItem(Material.INK_SACK, (short) 8);
		}
		parent.update(this);
		
		
	}
}

