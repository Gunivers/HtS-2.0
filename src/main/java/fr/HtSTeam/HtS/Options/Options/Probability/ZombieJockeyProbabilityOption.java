package fr.HtSTeam.HtS.Options.Options.Probability;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

import fr.HtSTeam.HtS.Options.Options.Probability.Structure.Probability;

/**
 * 
 * @author A~Z
 *
 */
public class ZombieJockeyProbabilityOption extends Probability
{
	public ZombieJockeyProbabilityOption()
	{
		super(Material.BONE, "Spider Jockey", "§61.0§2%", 1.d);
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event)
	{
		if (event.getEntityType() == EntityType.ZOMBIE && random.nextDouble() < this.getValue())
		{	
			event.setCancelled(true);
			event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.CHICKEN).addPassenger(event.getEntity());
		}
	}
}
