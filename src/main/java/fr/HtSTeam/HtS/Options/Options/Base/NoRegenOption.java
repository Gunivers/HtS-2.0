package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class NoRegenOption extends OptionBuilder<Boolean> {
	
	public NoRegenOption() {
		super(Material.INK_SACK, "Régération naturelle", "§4Désactivé", false, GUIRegister.base);
		getItemStack().setItem(Material.INK_SACK, (short) 8);
		parent.update(this);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}
	
	@EventHandler
	public void onFoodHealEvent(EntityRegainHealthEvent e) {
		if(!getValue()) {
			Entity p = e.getEntity();
			if(p instanceof Player && e.getRegainReason() == RegainReason.SATIATED)
				e.setCancelled(true);
		}
	}

	@Override
	public void setState(Boolean value) {
		if(value) {
			getItemStack().setLore("§2Activé");
			getItemStack().setItem(Material.INK_SACK, (short) 9);
		} else {
			getItemStack().setLore("§4Désactivé");
			getItemStack().setItem(Material.INK_SACK, (short) 8);
		}
		setValue(value);
		parent.update(this);		
	}
}

