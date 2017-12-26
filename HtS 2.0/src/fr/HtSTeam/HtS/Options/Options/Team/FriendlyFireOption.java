package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;

public class FriendlyFireOption extends OptionsManager {

	private boolean activate = true;
	
	public FriendlyFireOption() {
		super(Material.BLAZE_POWDER, "FriendlyFire", "§2Activé", "Activé", OptionsRegister.teams);
	}
	
	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamFriendlyFire(true);
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamFriendlyFire(false);
		}
		parent.update(this);
	}
}
