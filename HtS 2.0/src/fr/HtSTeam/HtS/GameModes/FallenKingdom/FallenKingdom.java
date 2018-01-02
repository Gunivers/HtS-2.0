package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import fr.HtSTeam.HtS.GameModes.GameMode;

public class FallenKingdom implements GameMode {


	@Override
	public void initialisation() {
		new EventManagerFK();
	}


}
