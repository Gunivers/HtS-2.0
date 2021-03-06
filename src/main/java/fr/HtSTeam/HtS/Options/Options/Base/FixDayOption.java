package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Base.FixDayOption.DayPhase;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;

public class FixDayOption extends OptionBuilder<DayPhase> implements StartTrigger {
	
	enum DayPhase {
		AUBE,
		JOURNEE,
		CREPUSCULE,
		NUIT;
	}
	
	int moment = 0;
	int tick = 0;
	public FixDayOption() {
		super(Material.STAINED_CLAY, "Moment au lancement", "§dAube", DayPhase.AUBE, GUIRegister.base, false);
		getItemStack().setItem(Material.STAINED_CLAY, (short) 2);
		parent.update(this);
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
		parent.update(this);
	}

	@Override
	public void onPartyStart() {
		Main.world.setTime(tick);
	}
	
	private void setLore(DayPhase value) {
		switch(value) {
		case AUBE : tick = 0;
				 getItemStack().setLore("§dAube");
				 getItemStack().setItem(Material.STAINED_CLAY, (short) 2);
				 break;
		case JOURNEE : tick = 6000;
				 getItemStack().setLore("§bMidi");
				 getItemStack().setItem(Material.STAINED_CLAY, (short) 3);
				 break;
		case CREPUSCULE : tick = 13100;
				 getItemStack().setLore("§6Crépuscule");
				 getItemStack().setItem(Material.STAINED_CLAY, (short) 1);
			 	 break;
		case NUIT : tick = 18000;
				 getItemStack().setLore("§1Nuit");
				 getItemStack().setItem(Material.STAINED_CLAY, (short) 11);
				 break;
		}
		parent.update(this);
	}

	@Override
	public void setState(DayPhase value) {
		setLore(value);
		setValue(value);
	}

	@Override
	public String description() {
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
