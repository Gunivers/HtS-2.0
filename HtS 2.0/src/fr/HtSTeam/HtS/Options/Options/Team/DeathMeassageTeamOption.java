package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team.OptionStatus;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.TeamManager;

public class DeathMeassageTeamOption extends OptionsManager {
	
	private int deathmsg = 0;
	
	public DeathMeassageTeamOption() {
		super(Material.SKULL_ITEM, "Message de mort", "§2Activé", "Activé", OptionsRegister.teams);
	}

	@Override
	public void event(Player p) {
		deathmsg = (deathmsg + 1) % 4;
		switch(deathmsg) {
			case 0 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamDeathMessage(OptionStatus.ALWAYS);
					 setValue("Activé");
					 getItemStackManager().setLore("§2Activé");
					 break;
			case 1 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamDeathMessage(OptionStatus.NEVER);
					 setValue("Désactivé");
					 getItemStackManager().setLore("§4Désactivé");
					 break;
			case 2 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamDeathMessage(OptionStatus.FOR_OTHER_TEAMS);
					 setValue("OtherTeam");
					 getItemStackManager().setLore("§6Activé pour les autres équipes");
				 	 break;
			case 3 : for (TeamManager teamManager : TeamManager.teamList)
						teamManager.setTeamDeathMessage(OptionStatus.FOR_OWN_TEAM);
					 setValue("OwnTeam");
					 getItemStackManager().setLore("§eActivé pour son équipe");
					 break;
		}
		parent.update(this);
	}
	
}
