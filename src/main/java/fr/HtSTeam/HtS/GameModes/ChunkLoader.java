package fr.HtSTeam.HtS.GameModes;

import org.bukkit.Bukkit;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

public class ChunkLoader implements StartTrigger {

	@Override
	public void onPartyStart() {
		Main.LOGGER.logInfo("Loading chunks...");
		int size = ((OptionRegister.borderOption.getValue() / 2) / 16) + 1;
		for (int i = -size; i <= size; i++)
			for(int j = -size; j <= size; j++)
				Bukkit.getServer().dispatchCommand(null, "forceload add " + i + " " + j);
		Main.LOGGER.logInfo("Chunks loaded!");
	}
}