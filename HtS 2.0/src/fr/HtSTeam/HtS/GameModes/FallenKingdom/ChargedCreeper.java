package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class ChargedCreeper implements Listener {
	
	
	@EventHandler
	public void onCreeperSpawn(CreatureSpawnEvent e) {
		if(e.getEntityType().equals(EntityType.CREEPER))
			if(Randomizer.RandRate(10)) {
				Creeper c = (Creeper) e.getEntity();
				c.setPowered(true);
			}
	}

}
