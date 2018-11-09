package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class NoRegenOption extends Option<Boolean> {
	
	public NoRegenOption() {
		super(Material.INK_SAC, "Régération naturelle", "§4Désactivé", false, GUIRegister.base);
		getItemStack().setItem(Material.GRAY_DYE);
		getParent().update(this);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}
	
	@EventHandler
	public void onFoodHealEvent(EntityRegainHealthEvent e) {
		if(!value) {
			Entity p = e.getEntity();
			if(p instanceof Player && e.getRegainReason() == RegainReason.SATIATED)
				e.setCancelled(true);
		}
	}

	@Override
	public void setState(Boolean value) {
		if(value) {
			getItemStack().setLore("§2Activé");
			getItemStack().setItem(Material.PINK_DYE);
		} else {
			getItemStack().setLore("§4Désactivé");
			getItemStack().setItem(Material.GRAY_DYE);
		}
		this.value = value;
		getParent().update(this);		
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r La régénération naturelle est activée.";
	}
}

