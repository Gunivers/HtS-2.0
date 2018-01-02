package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class FixDayOption extends OptionsManager implements StartTrigger {
	
	int moment = 0;
	int tick = 0;
	public FixDayOption() {
		super(Material.STAINED_CLAY, "Moment au lancement", "§dAube", "Aube", OptionsRegister.base);
		getItemStackManager().setItem(Material.STAINED_CLAY, (short) 2);
		parent.update(this);
		}

	@Override
	public void event(Player p) {
		moment = (moment + 1) % 4;
		switch(moment) {
			case 0 : tick = 0;
					 setValue("Aube");
					 getItemStackManager().setLore("§dAube");
					 getItemStackManager().setItem(Material.STAINED_CLAY, (short) 2);
					 break;
			case 1 : tick = 6000;
					 setValue("Journée");
					 getItemStackManager().setLore("§bMidi");
					 getItemStackManager().setItem(Material.STAINED_CLAY, (short) 3);
					 break;
			case 2 : tick = 11615;
					 setValue("Crépuscule");
					 getItemStackManager().setLore("§6Crépuscule");
					 getItemStackManager().setItem(Material.STAINED_CLAY, (short) 1);
				 	 break;
			case 3 : tick = 18000;
					 setValue("Nuit");
					 getItemStackManager().setLore("§1Nuit");
					 getItemStackManager().setItem(Material.STAINED_CLAY, (short) 11);
					 break;
		}
		parent.update(this);
	}

	@Override
	public void onPartyStart() {
		Main.world.setTime(tick);
	}
}
