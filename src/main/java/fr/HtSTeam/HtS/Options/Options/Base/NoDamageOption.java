package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class NoDamageOption extends OptionBuilder<Integer> {
	
	private boolean request;
	private boolean damageOn;
	private Player p;
	
	public NoDamageOption() {
		super(Material.SHIELD, "Activation des dégâts", "§21 minutes", 1, GUIRegister.base);
		switchState(false);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir le délais de non dégâts.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setState(value);
					p.sendMessage("§2Dégâts activé à " + getValue() + " minutes." );
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 60.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	private void switchState(boolean b) {
		damageOn = b;
	}
	
	@Timer
	public void changeState() {
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
		return "§2[Aide]§r Les dégâts s'activeront au bout de " + getValue() + " minutes.";
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (EnumState.getState() == EnumState.RUNNING && !damageOn && e.getEntity() instanceof Player)
			e.setCancelled(true);
	}
}