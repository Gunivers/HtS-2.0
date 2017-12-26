package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team.OptionStatus;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;

public class NameTagTeamOption extends OptionsManager{

	private int visible = 0;
	
	public NameTagTeamOption() {
		super(Material.NAME_TAG, "NameTag", "§fVisible", "Visible", OptionsRegister.teams);
	}

	@Override
	public void event(Player p) {
		visible = (visible + 1) % 4;
		switch(visible) {
			case 0 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamNameTag(OptionStatus.ALWAYS);
					 setValue("Visible");
					 getItemStackManager().setLore("§fVisible");
					 break;
			case 1 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamNameTag(OptionStatus.NEVER);
					 setValue("Invisible");
					 getItemStackManager().setLore("§8Invisible");
					 break;
			case 2 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamNameTag(OptionStatus.FOR_OTHER_TEAMS);
					 setValue("OtherTeam");
					 getItemStackManager().setLore("§7Invisible pour les autres équipes");
				 	 break;
			case 3 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamNameTag(OptionStatus.FOR_OWN_TEAM);
					 setValue("OwnTeam");
					 getItemStackManager().setLore("§0Invisible pour son équipe");
					 break;
		}
		parent.update(this);
	}

}
