package fr.HtSTeam.HtS.Options.Options.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Teams.TeamManager;

public class SeeInvisibleOption extends OptionsManager {
		
	private boolean activate = false;
	
	public SeeInvisibleOption() {
		super(Material.GLASS_BOTTLE, "Invisibilité visible par son équipe", "§4Désactivé", "Désactivé", OptionsRegister.teams);
	}
	
	@Override
	public void event(Player p) {
		activate =! activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
			getItemStackManager().setItem(Material.POTION, (byte) 0);
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamSeeInvisible(true);
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
			getItemStackManager().setItem(Material.GLASS_BOTTLE, (byte) 0);
			for (TeamManager teamManager : TeamManager.teamList)
				teamManager.setTeamSeeInvisible(false);
		}
		parent.update(this);
	}
	
}
