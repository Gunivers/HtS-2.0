package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;

public class SyT extends UHC {
	
	public static Boolean instance = false;
	
	static TargetCycle targetCycleOption;
	static RadarFrequencyOption radarF;
	static RadarOption radar;
	
	public SyT() {
		instance = true;
		targetCycleOption = new TargetCycle();
		radarF = new RadarFrequencyOption();
		radar = new RadarOption();
		setTeamVictoryDetection(false);
				
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