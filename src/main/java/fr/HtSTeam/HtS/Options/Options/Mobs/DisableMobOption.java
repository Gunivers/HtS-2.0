package fr.HtSTeam.HtS.Options.Options.Mobs;

import fr.HtSTeam.HtS.Options.Structure.GUIBuilder;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public class DisableMobOption extends OptionBuilder<String> {

	public DisableMobOption(ItemStackBuilder material, String defaultValue, GUIBuilder gui) {
		super(material, defaultValue, gui);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void event(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setState(String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

}
