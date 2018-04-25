package fr.HtSTeam.HtS.Options.Options.Nether;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class ShulkerNetherOption extends OptionBuilder implements Alterable {

	private boolean activate = false;

	public ShulkerNetherOption() {
		super(Material.PURPLE_SHULKER_BOX, "Shulker", "§4Désactivé", "Désactivé", OptionRegister.nether);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}
	
	
	@Override
	public void setState(boolean value) {
		activate = value;
		if (value) {
			setValue("Activé");
			getItemStack().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);		
	}
	

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (activate && e.getWorld().getEnvironment().equals(Environment.NETHER) && Randomizer.RandRate(10)) {
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
			Block b = blocks.get(Randomizer.RandI(0, blocks.size() - 1));
			Shulker sh = (Shulker) (c.getWorld().spawnEntity(new Location(c.getWorld(), b.getX(), b.getY(), b.getZ()), EntityType.SHULKER));
			sh.setColor(DyeColor.RED);
		}
	}
}
