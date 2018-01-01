package fr.HtSTeam.HtS.Options.Options.Nether;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NetherWartOption extends OptionsManager {
	
	private boolean activate = false, wart = false;
	private static int cx, cz;
	
	public NetherWartOption() {
		super(Material.NETHER_WARTS, "Nether Warts", "§4Désactivé", "Désactivé", OptionsRegister.nether);
	}

	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");			
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}		
		parent.update(this);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (activate && e.getWorld().getEnvironment().equals(Environment.NETHER) && Randomizer.RandRate(10)) {
			Chunk c = e.getChunk();
			cx = c.getX() << 4;
			cz = c.getZ() << 4;
			for (int i = 0; i < 51200; i++) {
				int[] coords = Randomizer.RandCoord(cx, cx + 16, cz, cz + 16, 50, 100);
				if (c.getBlock(coords[0], coords[1], coords[2]).getType() == Material.AIR && c.getBlock(coords[0], coords[1] + 1, coords[2]).getType() == Material.AIR && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.AIR && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.LAVA && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.NETHER_BRICK && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.NETHER_BRICK_STAIRS && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.NETHER_FENCE && c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.STEP) {
					Location lb = new Location(c.getWorld(), coords[0], coords[1] - 1, coords[2]);
					Location lt = new Location(c.getWorld(), coords[0], coords[1], coords[2]);
					lb.getBlock().setType(Material.SOUL_SAND);
					lt.getBlock().setType(Material.NETHER_WARTS);
					lt.getBlock().setData((byte) 3);
					wart =! wart;
					if(wart)
						break;
				}
			}
		}
	}
}
