package fr.HtSTeam.HtS.GameModes.UHC;

import fr.HtSTeam.HtS.GameModes.GameMode;

public class UHC implements GameMode {

	@Override
	public void initialisation() {
		new EventManagerUHC();
	}

}
