package fr.HtSTeam.HtS.Players;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.Options.Structure.DeathTrigger;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;

public class PlayerDeath implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent e) {
		IconBuilder.optionsList.keySet().forEach(key -> { if(Arrays.asList(key.getClass().getInterfaces()).contains(DeathTrigger.class)) ((DeathTrigger) key).onDeath((Player)e.getEntity()); });
	}
}
