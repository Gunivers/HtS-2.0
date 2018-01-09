package fr.HtSTeam.HtS.GameModes.UHC;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class UHC implements GameMode {

	@Override
	public void initialisation() {
		new EventManagerUHC();
		for (UUID p : PlayerInGame.playerInGame)
			Bukkit.getPlayer(p).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 255, false, false));
		teleport();	
		
		
		OptionRegister.noRegen.setState(false);
		OptionRegister.goldenApple.setState(true);
	}
	
	
	private void teleport() {

		int border = Integer.parseInt(OptionRegister.borderOption.getValue());

		if (TeamBuilder.teamList.size() == 0) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
						(border - 50) / 2);
				player.teleport(new Location(player.getWorld(), coords[0], coords[1], coords[2]));
			}

		} else {
			for (TeamBuilder tm : TeamBuilder.teamList) {
				int[] coords = Randomizer.RandCoord(-(border - 50) / 2, (border - 50) / 2, 255, 255, -(border - 50) / 2,
						(border - 50) / 2);
				for (Entry<UUID, TeamBuilder> entry : TeamBuilder.playerTeam.entrySet()) {
					if (entry.getValue() == tm)
						Bukkit.getPlayer(entry.getKey()).teleport(new Location(Bukkit.getPlayer(entry.getKey()).getWorld(), coords[0], coords[1], coords[2]));
				}
			}
		}
	}


	@Override
	public String gamemodeToString() {
		return "UHC";
	}

}
