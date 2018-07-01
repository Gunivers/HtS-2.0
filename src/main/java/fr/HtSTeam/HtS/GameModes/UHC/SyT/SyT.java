package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;

public class SyT extends UHC {

	static TargetCycle targetCycleOption = new TargetCycle();
	static RadarFrequencyOption radarF = new RadarFrequencyOption();
	static RadarOption radar = new RadarOption();
	
	public SyT() {
		setTeamVictoryDetection(false);
		//new SyTOptionRegister();
		new SyTEventManager();
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