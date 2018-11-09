package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Base.FixDayOption.DayPhase;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Player.Player;

public class FixDayOption extends Option<DayPhase> implements StartTrigger {
	
	enum DayPhase {
		AUBE,
		JOURNEE,
		CREPUSCULE,
		NUIT;
	}
	
	int moment = 0;
	int tick = 0;
	public FixDayOption() {
		super(Material.TERRACOTTA, "Moment au lancement", "§dAube", DayPhase.AUBE, GUIRegister.base);
		getItemStack().setItem(Material.MAGENTA_TERRACOTTA);
		getParent().update(this);
		}

	@Override
	public void event(Player p) {
		moment = (moment + 1) % 4;
		switch(moment) {
			case 0 : tick = 0;
					 setState(DayPhase.AUBE);
					 break;
			case 1 : tick = 6000;
					 setState(DayPhase.JOURNEE);
					 break;
			case 2 : tick = 13100;
					 setState(DayPhase.CREPUSCULE);
				 	 break;
			case 3 : tick = 18000;
					 setState(DayPhase.NUIT);
					 break;
		}
		getParent().update(this);
	}

	@Override
	public void onPartyStart() {
		Main.world.setTime(tick);
	}
	
	private void setLore(DayPhase value) {
		switch(value) {
		case AUBE : tick = 0;
				 getItemStack().setLore("§dAube");
				 getItemStack().setItem(Material.MAGENTA_TERRACOTTA);
				 break;
		case JOURNEE : tick = 6000;
				 getItemStack().setLore("§bMidi");
				 getItemStack().setItem(Material.LIGHT_BLUE_TERRACOTTA);
				 break;
		case CREPUSCULE : tick = 13100;
				 getItemStack().setLore("§6Crépuscule");
				 getItemStack().setItem(Material.ORANGE_TERRACOTTA);
			 	 break;
		case NUIT : tick = 18000;
				 getItemStack().setLore("§1Nuit");
				 getItemStack().setItem(Material.BLUE_TERRACOTTA);
				 break;
		}
		getParent().update(this);
	}

	@Override
	public void setState(DayPhase value) {
		setLore(value);
		this.value = value;
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@Override
	public void load(Object o) {
		try {
			setState(DayPhase.valueOf(o.toString()));
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
