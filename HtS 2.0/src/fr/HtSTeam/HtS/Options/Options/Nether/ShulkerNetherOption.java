package fr.HtSTeam.HtS.Options.Options.Nether;

import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class ShulkerNetherOption extends OptionsManager {

	private boolean activate = false;

	public ShulkerNetherOption() {
		super(Material.PURPLE_SHULKER_BOX, "Shulker", "§4Désactivé", "Désactivé", OptionsRegister.nether);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if (activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		if (activate && e.getWorld().getEnvironment().equals(Environment.NETHER) && Randomizer.RandRate(10)) {
			Chunk c = e.getChunk();
			int cx = c.getX() << 4;
			int cz = c.getZ() << 4;
			for (int i = 0; i < 102400; i++) {
				int[] coords = Randomizer.RandCoord(cx, cx + 16, 50, 100, cz, cz + 16);
				if (c.getBlock(coords[0], coords[1], coords[2]).getType() == Material.AIR && ((c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() != Material.AIR && c.getBlock(coords[0], coords[1] + 1, coords[2]).getType() == Material.AIR && c.getBlock(coords[0] - 1, coords[1], coords[2]).getType() == Material.AIR && c.getBlock(coords[0] + 1, coords[1], coords[2]).getType() == Material.AIR && c.getBlock(coords[0], coords[1], coords[2] - 1).getType() == Material.AIR && c.getBlock(coords[0], coords[1], coords[2] + 1).getType() == Material.AIR) || (c.getBlock(coords[0], coords[1] - 1, coords[2]).getType() == Material.AIR && c.getBlock(coords[0], coords[1] + 1, coords[2]).getType() != Material.AIR && c.getBlock(coords[0] - 1, coords[1], coords[2]).getType() == Material.AIR && c.getBlock(coords[0] + 1, coords[1], coords[2]).getType() == Material.AIR && c.getBlock(coords[0], coords[1], coords[2] - 1).getType() == Material.AIR && c.getBlock(coords[0], coords[1], coords[2] + 1).getType() == Material.AIR))) {
					Shulker sh = (Shulker) (c.getWorld().spawnEntity(new Location(c.getWorld(), coords[0], coords[1], coords[2]), EntityType.SHULKER));
					sh.setColor(DyeColor.RED);
					break;
				}
			}
		}
	}
}
