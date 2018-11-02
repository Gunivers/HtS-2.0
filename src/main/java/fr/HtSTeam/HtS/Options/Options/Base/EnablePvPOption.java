package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Player.Player;

public class EnablePvPOption extends Option<Integer> {
	

	private boolean request;
	private Player p;
	
	public EnablePvPOption() {
		super(Material.DIAMOND_SWORD, "Activation du PvP", "§520§2 minutes", 20, GUIRegister.base);
		switchState(false);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir le délais d'activation du PvP.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().getUniqueId().equals(p.getUUID())) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setState(value);
					p.sendMessage("§2Le PvP s'activera au bout de §5" + getValue() + "§2 minutes." );
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre§5 0§2 et§5 60§2.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide, veuillez réessayer.");
			}
		}
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
		setValue(value);
		this.getItemStack().setLore("§2" + value + " minutes");
		parent.update(this);
	}

	@Override
	public String description() {
		return "§2[Aide]§r Le PvP s'activera au bout de §5" + getValue() + "§2 minutes.";
	}

}
