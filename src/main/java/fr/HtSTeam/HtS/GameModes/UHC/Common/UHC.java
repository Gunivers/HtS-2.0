package fr.HtSTeam.HtS.GameModes.UHC.Common;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Player.PlayerInGame;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class UHC implements GameMode {

	public static Boolean instance = false;
	private boolean teamVictoryDetection = false;

	public UHC() {
		instance = true;
	}

	@Override
	public void initialisation() {
		for (UUID p : PlayerInGame.playerInGame)
			Bukkit.getPlayer(p)
					.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
		teleport();

		new EventManagerUHC(teamVictoryDetection);
	}

	public void setTeamVictoryDetection(boolean teamVictoryDetection) {
		this.teamVictoryDetection = teamVictoryDetection;
	}

	private void teleport() {

		int border = (int) OptionRegister.borderOption.getValue();

		if (Team.teamList.size() == 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				int[] coords = Randomizer.randCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
						(border - 50) / 2);
				player.teleport(new Location(player.getWorld(), coords[0], coords[1], coords[2]));
			}

		} else {
//			for (Team tm : Team.teamList) {
//				int[] coords = Randomizer.randCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
//						(border - 50) / 2);
//				for (Entry<UUID, Team> entry : Team.playerTeam.entrySet()) {
//					if (entry.getValue() == tm)
//						Bukkit.getPlayer(entry.getKey()).teleport(new Location(
//								Bukkit.getPlayer(entry.getKey()).getWorld(), coords[0], coords[1], coords[2]));
//				}
//			}
		}
	}

	@Override
	public String gamemodeToString() {
		return "UHC";
	}

}
