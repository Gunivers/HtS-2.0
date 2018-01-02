package fr.HtSTeam.HtS.GameModes.UHC;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class UHC implements GameMode {

	@Override
	public void initialisation() {
		new EventManagerUHC();
		for (Player p : Main.playerInGame.getPlayerInGame())
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
		teleport();
	}

	
	
	
	
	private void teleport() {

		int border = Integer.parseInt(OptionsRegister.borderOption.getValue());

		if (TeamManager.teamList.size() == 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
						(border - 50) / 2);
				player.teleport(new Location(player.getWorld(), coords[0], coords[1], coords[2]));
			}

		} else {
			for (TeamManager tm : TeamManager.teamList) {
				int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
						(border - 50) / 2);
				for (Entry<Player, TeamManager> entry : TeamManager.playerTeam.entrySet()) {
					if (entry.getValue() == tm)
						entry.getKey()
								.teleport(new Location(entry.getKey().getWorld(), coords[0], coords[1], coords[2]));
				}
			}
		}
	}

}
