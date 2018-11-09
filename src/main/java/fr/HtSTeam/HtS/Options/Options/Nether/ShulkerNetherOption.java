package fr.HtSTeam.HtS.Options.Options.Nether;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class ShulkerNetherOption extends Option<Boolean> {

	public ShulkerNetherOption() {
		super(Material.PURPLE_SHULKER_BOX, "Shulker", "§4Désactivé", false, GUIRegister.nether);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}
	
	
	@Override
	public void setState(Boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		this.value = value;
		getParent().update(this);		
	}
	

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (value && e.getWorld().getEnvironment().equals(Environment.NETHER) && Randomizer.randRate(10)) {
			Chunk c = e.getChunk();
			int cx = c.getX() << 4;
			int cz = c.getZ() << 4;
			ArrayList<Block> blocks = new ArrayList<Block>();
			for (int y = 50; y < 100; y++) {
				for (int x = cx; x < cx + 16; x++) {
					for (int z = cz; z < cz + 16; z++) {
						if (c.getBlock(x, y, z).getType() == Material.AIR && ((c.getBlock(x, y - 1, z).getType() != Material.AIR && c.getBlock(x, y + 1, z).getType() == Material.AIR && c.getBlock(x - 1, y, z).getType() == Material.AIR && c.getBlock(x + 1, y, z).getType() == Material.AIR && c.getBlock(x, y, z - 1).getType() == Material.AIR && c.getBlock(x, y, z + 1).getType() == Material.AIR) || (c.getBlock(x, y - 1, z).getType() == Material.AIR && c.getBlock(x, y + 1, z).getType() != Material.AIR && c.getBlock(x - 1, y, z).getType() == Material.AIR && c.getBlock(x + 1, y, z).getType() == Material.AIR && c.getBlock(x, y, z - 1).getType() == Material.AIR && c.getBlock(x, y, z + 1).getType() == Material.AIR)))
							blocks.add(c.getBlock(x, y, z));
					}
				}
			}
			if(blocks.size() == 0)
				return;
			Block b = blocks.get(Randomizer.randI(0, blocks.size() - 1));
			Shulker sh = (Shulker) (c.getWorld().spawnEntity(new Location(c.getWorld(), b.getX(), b.getY(), b.getZ()), EntityType.SHULKER));
			sh.setColor(DyeColor.RED);
		}
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Les Shulkers peuvent spawn naturellement dans le Nether.";
	}
}
