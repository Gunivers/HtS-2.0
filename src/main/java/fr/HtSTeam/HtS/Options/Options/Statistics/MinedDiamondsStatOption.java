package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class MinedDiamondsStatOption extends OptionBuilder<Boolean> {
	
	public MinedDiamondsStatOption() {
		super(Material.DIAMOND_ORE, "Diamond Ore", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.MINED_DIAMONDS.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.MINED_DIAMONDS.setTracked(false);
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