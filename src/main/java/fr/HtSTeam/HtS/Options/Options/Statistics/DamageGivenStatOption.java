package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DamageGivenStatOption extends OptionBuilder<Boolean> {
	
	public DamageGivenStatOption() {
		super(Material.GOLD_SWORD, "Damage Given", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.DAMAGE_GIVEN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.DAMAGE_GIVEN.setTracked(false);
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