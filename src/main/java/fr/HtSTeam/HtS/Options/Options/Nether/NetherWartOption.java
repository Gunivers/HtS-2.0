package fr.HtSTeam.HtS.Options.Options.Nether;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.material.NetherWarts;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NetherWartOption extends OptionBuilder<Boolean>{
		
	public NetherWartOption() {
		super(Material.NETHER_WART_BLOCK, "Nether Warts", "§4Désactivé", false, GUIRegister.nether);
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}
	
	
	@Override
	public void setState(Boolean value) {
		if(value)
			getItemStack().setLore("§2Activé");			
		else
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);
	}
	
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (getValue() && e.getWorld().getEnvironment().equals(Environment.NETHER) && Randomizer.randRate(10)) {
			Chunk c = e.getChunk();
			int cx = c.getX() << 4;
			int cz = c.getZ() << 4;
			ArrayList<Block> blocks = new ArrayList<Block>();
			for (int y = 50; y < 100; y++) {
				for (int x = cx; x < cx + 16; x++) {
					for (int z = cz; z < cz + 16; z++) {
						if (c.getBlock(x, y, z).getType() == Material.AIR && c.getBlock(x, y + 1, z).getType() == Material.AIR && c.getBlock(x, y - 1, z).getType() != Material.AIR && c.getBlock(x, y - 1, z).getType() != Material.LAVA && c.getBlock(x, y - 1, z).getType() != Material.NETHER_BRICK && c.getBlock(x, y - 1, z).getType() != Material.NETHER_BRICK_STAIRS && c.getBlock(x, y - 1, z).getType() != Material.NETHER_BRICK_FENCE && c.getBlock(x, y - 1, z).getType() != Material.NETHER_BRICK_SLAB)
							blocks.add(c.getBlock(x, y, z));
					}
				}
			}
			if(blocks.size() == 0)
				return;
			Block b = blocks.get(Randomizer.randI(0, blocks.size() - 1));
			Location lb = new Location(c.getWorld(), b.getX(), b.getY() - 1, b.getZ());
			Location lt = new Location(c.getWorld(), b.getX(), b.getY(), b.getZ());
			lb.getBlock().setType(Material.SOUL_SAND);
			NetherWarts m = new NetherWarts();
			m.setState(NetherWartsState.STAGE_TWO);
			lt.getBlock().getState().setData(m);
		}
	}

	@Override
	public String description() {
		return "§2[Aide]§r La nether wart peut naturellement apparaître dans le Nether.";
	}
}