package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.TeamManager;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class FriendlyFireOption extends OptionsManager {

	public FriendlyFireOption() {
		super(Material.BLAZE_POWDER, "FriendlyFire", "Active ou désactive le friendlyfire", null, OptionsRegister.teams);
	}
	
	private boolean activated = false;
	
	@Override
	public void event(Player p) {
		if(activated) {
			activated = false;
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamFriendlyFire(false);
		} else {
			activated = true;
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamFriendlyFire(true);
		}
	}

}
