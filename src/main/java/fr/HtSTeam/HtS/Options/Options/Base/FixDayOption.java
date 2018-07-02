package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.StartTrigger;
import fr.HtSTeam.HtS.Options.Options.Base.FixDayOption.DayPhase;

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
		super(Material.STAINED_CLAY, "Moment au lancement", "§dAube", DayPhase.AUBE, OptionRegister.base);
		getItemStack().setItem(Material.STAINED_CLAY, (short) 2);
		parent.update(this);
		}

	@Override
	public void event(Player p) {
		moment = (moment + 1) % 4;
		switch(moment) {
			case 0 : tick = 0;
					 setValue(DayPhase.AUBE);
					 getItemStack().setLore("§dAube");
					 getItemStack().setItem(Material.STAINED_CLAY, (short) 2);
					 break;
			case 1 : tick = 6000;
					 setValue(DayPhase.JOURNEE);
					 getItemStack().setLore("§bMidi");
					 getItemStack().setItem(Material.STAINED_CLAY, (short) 3);
					 break;
			case 2 : tick = 13100;
					 setValue(DayPhase.CREPUSCULE);
					 getItemStack().setLore("§6Crépuscule");
					 getItemStack().setItem(Material.STAINED_CLAY, (short) 1);
				 	 break;
			case 3 : tick = 18000;
					 setValue(DayPhase.NUIT);
					 getItemStack().setLore("§1Nuit");
					 getItemStack().setItem(Material.STAINED_CLAY, (short) 11);
					 break;
		}
		parent.update(this);
	}

	@Override
	public void onPartyStart() {
		Main.world.setTime(tick);
	}
}
