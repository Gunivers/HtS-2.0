package fr.HtSTeam.HtS.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.Options.Structure.DeathTrigger;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;

public class PlayerDeath implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDeath(PlayerDeathEvent e) {
		IconBuilder.optionsList.keySet().stream().filter(key -> key instanceof DeathTrigger).forEach(key -> ((DeathTrigger) key).onDeath(Player.instance(e.getEntity())));
	}
}
