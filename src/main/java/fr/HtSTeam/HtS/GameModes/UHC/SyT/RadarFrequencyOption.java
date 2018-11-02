package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.StartTrigger;
import fr.HtSTeam.HtS.Options.Structure.Annotation.AwaitingPlayer;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class RadarFrequencyOption extends Option<Integer> implements StartTrigger {

	private boolean request = false;
	private Player p;
	private int frequency = 20;
//	private int duration = 15;
	
	public static HashMap<UUID, Location> offlineLocation = new HashMap<UUID, Location>();
	
	public RadarFrequencyOption() {
		super(Material.COMPASS, "Fréquence du Radar", "§220 minutes", 20, GUIRegister.syt);
	}

	@Override
	public void event(fr.HtSTeam.HtS.Player.Player p) {
		this.p = Bukkit.getPlayer(p.getUUID());
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
					setState(value);
					p.sendMessage("§2Radar toutes les " + getValue() + " minutes.");
					getParent().update(this);
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
//		for (UUID uuid : SyT.targetCycleOption.targetCycle) {
//			if (PlayerManager.isConnected(uuid))
//				radar(uuid);
//			else
//				PlayerReconnection.add(uuid, this);
//		}
	}
	
	@AwaitingPlayer
	public void radar(UUID uuid) {
//		Player player = Bukkit.getPlayer(uuid);
//		UUID targetUUID = SyT.targetCycleOption.getTarget(player);
//		Location loc;	
//		if (!PlayerManager.isConnected(targetUUID))
//			loc = offlineLocation.get(targetUUID);
//		else
//			loc = Bukkit.getPlayer(targetUUID).getLocation();
//		if (loc.getBlockY() >= 36
//				&& loc.getWorld().getEnvironment() == Environment.NORMAL) {
//			ActionBar msg = new ActionBar(player, "§4§lCible repérée : " + loc.getBlockX() + " " + loc.getBlockY() + " " +loc.getBlockZ(), duration);
//			msg.send();
//		} else if (loc.getBlockY() < 36
//				&& loc.getWorld().getEnvironment() == Environment.NORMAL) {
//			ActionBar msg = new ActionBar(player, "§4§lTrop faible signal détecté : impossible de localiser la cible.", duration);
//			msg.send();
//		} else if (loc.getWorld().getEnvironment() == Environment.NETHER
//				|| player.getLocation().getWorld().getEnvironment() == Environment.NETHER) {
//			ActionBar msg = new ActionBar(player, "§4§lAucun signal détecté : impossible de localiser la cible.", duration);
//			msg.send();
//		}
	}

	@Override
	public void onPartyStart() {
		setValue(SyT.radar.getValue());
	}

	@Override
	public void setState(Integer value) {
		setValue(value);
		frequency = value;
		this.getItemStack().setLore("§2" + value + " minutes");
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r Le radar s'éxécutera, après ça première activation, toutes les " + getValue() + "minutes.";
	}
}
