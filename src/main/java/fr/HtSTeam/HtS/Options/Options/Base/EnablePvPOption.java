package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.ChatNumberOption;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class EnablePvPOption extends ChatNumberOption {
	
	public EnablePvPOption() {
		super(Material.DIAMOND_SWORD, "Activation du PvP", "§520§2 minutes", 20, GUIRegister.base, 0, 60);
		switchState(false);
	}
	
	@Override
	public void dispValidMessage() {
		p.sendMessage("§2Le PvP s'activera au bout de §5" + value + "§2 minutes." );
	}

	@Override
	public void dispRequestMessage() {
		p.sendMessage("§2Veuillez saisir le délais d'activation du PvP.");
	}
	
	private void switchState(boolean b) {
		for (World world : Bukkit.getWorlds())
			world.setPVP(b);
	}
	
	@Timer
	public void changeState() {
		Bukkit.broadcastMessage("§4Le PvP est maintenant activé.");
		switchState(true);
	}

	@Override
	public void setState(Integer value) {
		this.value = value;
		this.getItemStack().setLore("§2" + value + " minutes");
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Le PvP s'activera au bout de §5" + value + "§2 minutes.";
	}
}
