package fr.HtSTeam.HtS.Options.Options.Mobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class SkeletonNerfOption extends OptionsManager {
	
	private boolean request = false;
	private Player p;
	private boolean activate = false;

	public SkeletonNerfOption() {
		super(Material.BONE, "Squelette", "§d40 minutes", "40", OptionsRegister.mob);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir la minute d'activation des squelettes.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setValue(Integer.toString(value));
					p.sendMessage("§2Squelette activé à " + getValue() + " minutes." );
					this.getItemStackManager().setLore("§d" + value + " minutes");
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
	
	@Timer
	public void activateSkeleton() {
		activate = true;
		if(!getValue().equals("0"))
			Bukkit.broadcastMessage("§4Les squelettes sont activés !");
	}
	
	
	@EventHandler
	public void onSkeletonTargetPlayer(EntityTargetLivingEntityEvent e) {
		if(!activate && e.getTarget() instanceof Player && e.getEntityType().equals(EntityType.SKELETON)) {
			e.setCancelled(true);
		}
	}
	
	

}