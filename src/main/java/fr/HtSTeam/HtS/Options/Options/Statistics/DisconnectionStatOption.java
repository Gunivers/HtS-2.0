package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class DisconnectionStatOption extends OptionBuilder<Boolean> {
	
	public DisconnectionStatOption() {
		super(Material.RED_ROSE, "Déconnexion", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
		parent.update(this);
	}

	@Override
	public void setState(Boolean value) {
		setValue(value);
		if(getValue())
			getItemStack().setLore("§2Activé");
		 else 
			getItemStack().setLore("§4Désactivé");
	}

	@Override
	public String description() {
		return null;
	}
	
	@EventHandler
	public void onLogOut(PlayerQuitEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && getValue()) {
			
		}
	}
}