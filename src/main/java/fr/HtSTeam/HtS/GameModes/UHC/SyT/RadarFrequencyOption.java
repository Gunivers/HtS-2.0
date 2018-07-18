package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;
import fr.HtSTeam.HtS.Utils.ActionBar;
import fr.HtSTeam.HtS.Utils.StartTrigger;

public class RadarFrequencyOption extends OptionBuilder<Integer> implements StartTrigger {

	private boolean request = false;
	private Player p;
	private int frequency = 20;

	public RadarFrequencyOption() {
		super(Material.COMPASS, "Fréquence du Radar", "§220 minutes", 20, GUIRegister.syt);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir la fréquence du Radar.");
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if (request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if (value >= 0 && value <= 60) {
					frequency = value;
					setState(value);
					p.sendMessage("§2Radar toutes les " + getValue() + " minutes.");
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 60.");
			} catch (NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}

	@Timer
	public void radar() {
		setValue(getValue() + frequency);
		for (UUID uuid : SyT.targetCycleOption.targetCycle) {
			Player player = Bukkit.getPlayer(uuid);
			Player victim = Bukkit.getPlayer(SyT.targetCycleOption.getTarget(player));
			if (victim.getLocation().getBlockY() >= 36
					&& victim.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
				ActionBar msg = new ActionBar(player, "§4§lCible repérée : " + victim.getLocation().getBlockX() + " " + victim.getLocation().getBlockY() + " " + victim.getLocation().getBlockZ(), 30);
				msg.send();
			} else if (victim.getLocation().getBlockY() < 36
					&& victim.getLocation().getWorld().getEnvironment() == Environment.NORMAL) {
				ActionBar msg = new ActionBar(player, "§4§lTrop faible signal détecté : impossible de localiser la cible.", 30);
				msg.send();
			} else if (victim.getLocation().getWorld().getEnvironment() == Environment.NETHER
					|| player.getLocation().getWorld().getEnvironment() == Environment.NETHER) {
				ActionBar msg = new ActionBar(player, "§4§lAucun signal détecté : impossible de localiser la cible.", 30);
				msg.send();
			}
		}
	}

	@Override
	public void onPartyStart() {
		setValue(SyT.radar.getValue());
	}

	@Override
	public void setState(Integer value) {
		setValue(value);
		this.getItemStack().setLore("§2" + value + " minutes");
	}

	@Override
	public String description() {
		return "§2[Aide]§r Le radar s'éxécutera, après ça première activation, toutes les " + getValue() + "minutes.";
	}
}
