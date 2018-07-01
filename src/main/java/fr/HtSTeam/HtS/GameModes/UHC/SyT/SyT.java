package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;

public class SyT extends UHC {

	static TargetCycle targetCycleOption = new TargetCycle();
	
	public SyT() {
		setTeamVictoryDetection(false);
		new SyTOptionRegister();
	}

	@Override
	public void initialisation() {
		super.initialisation();
	}

	@Override
	public String gamemodeToString() {
		return "SyT";
	}
}