package fr.HtSTeam.HtS.Options.Structure;

import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public abstract class ChatOption<A> extends Option<A> {

	public ChatOption(ItemStackBuilder material, A defaultValue, Gui gui) {
		super(material, defaultValue, gui);
	}

	@Override
	public void event(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setState(A value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
