package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import fr.HtSTeam.HtS.GameModes.GameMode;

public class FallenKingdom implements GameMode {

	public static Boolean instance = false;
	
	public FallenKingdom() {
		instance = true;
	}
	
	@Override
	public void initialisation() {
		new EventManagerFK();
		for(BaseBuilder bb : BaseBuilder.baseList)
			bb.addAllPlayers();
	}

	@Override
	public String gamemodeToString() {
		return "Fallen Kingdom";
	}
}
