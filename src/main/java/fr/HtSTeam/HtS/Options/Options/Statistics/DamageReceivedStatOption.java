package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DamageReceivedStatOption extends OptionBuilder<Boolean> {
	
	public DamageReceivedStatOption() {
		super(Material.SHIELD, "Damage Received", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
		parent.update(this);
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue()) {
			EnumStats.DAMAGE_RECEIVED.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.DAMAGE_RECEIVED.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
	}

	@Override
	public String description() {
		return null;
	}
	
//	@EventHandler
	public void onLogOut(PlayerQuitEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.DISCONNECTIONS.isTracked())
			StatisticHandler.update(e.getPlayer(), EnumStats.DISCONNECTIONS);
	}
}