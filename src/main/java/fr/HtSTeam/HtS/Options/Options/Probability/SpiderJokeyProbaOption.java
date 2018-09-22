package fr.HtSTeam.HtS.Options.Options.Probability;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

import fr.HtSTeam.HtS.Options.Options.Probability.Structure.Probability;

/**
 * 
 * @author A~Z
 *
 */
public class SpiderJokeyProbaOption extends Probability
{
	public SpiderJokeyProbaOption()
	{
		super(Material.BONE, "Spider Jockey", "§62.0§2%", 2.d);
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent event)
	{
		if (event.getEntityType() == EntityType.SPIDER && random.nextDouble() < this.getValue())
		{
			event.setCancelled(true);
			Entity spider = event.getEntity();
			
			for (Entity e : spider.getPassengers())
			{
				spider.removePassenger(e);
			}
			
			spider.addPassenger(event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.SKELETON));
		}
	}
}
