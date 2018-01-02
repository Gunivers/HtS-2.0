package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team.OptionStatus;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Teams.TeamManager;

public class CollisionTeamOption extends OptionsManager {
	
	private int collision = 0;
	
	public CollisionTeamOption() {
		super(Material.ARMOR_STAND, "Collision", "§2Activé", "Activé", OptionsRegister.teams);
	}

	@Override
	public void event(Player p) {
		collision = (collision + 1) % 4;
		switch(collision) {
			case 0 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamCollision(OptionStatus.ALWAYS);
					 setValue("Activé");
					 getItemStackManager().setLore("§2Activé");
					 break;
			case 1 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamCollision(OptionStatus.NEVER);
					 setValue("Désactivé");
					 getItemStackManager().setLore("§4Désactivé");
					 break;
			case 2 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamCollision(OptionStatus.FOR_OTHER_TEAMS);
					 setValue("OtherTeam");
					 getItemStackManager().setLore("§6Activé pour les autres équipes");
				 	 break;
			case 3 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamCollision(OptionStatus.FOR_OWN_TEAM);
					 setValue("OwnTeam");
					 getItemStackManager().setLore("§eActivé pour son équipe");
					 break;
		}
		parent.update(this);
	}
	
}
