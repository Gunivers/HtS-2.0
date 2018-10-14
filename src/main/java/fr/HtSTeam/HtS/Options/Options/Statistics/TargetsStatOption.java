package fr.HtSTeam.HtS.Options.Options.Statistics;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Player.PlayerInGame;
import fr.HtSTeam.HtS.Player.PlayerRemove;

public class TargetsStatOption extends OptionBuilder<Boolean> implements PlayerRemove {
	
	public TargetsStatOption() {
		super(Material.PLAYER_HEAD, "TARGETS", "§2Activé", true, GUIRegister.stats);
		addToList();
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if (EnumState.getState().equals(EnumState.RUNNING))
			return;
		setValue(value);
		if(getValue()) {
			EnumStats.TARGETS.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.TARGETS.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		parent.update(this);
	}

	@Override
	public String description() {
		return null;
	}
	
	public static void init() {
		PlayerInGame.playerInGame.forEach(uuid -> { if (PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid)) != null) StatisticHandler.update(uuid, EnumStats.TARGETS, PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid))); });
	}
	
	@EventHandler
	public void on(PlayerDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.TARGETS.isTracked() && Main.gamemode.gamemodeToString().equals("SyT"))
			PlayerInGame.playerInGame.forEach(uuid -> { if (PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid)) != null) StatisticHandler.update(uuid, EnumStats.TARGETS, PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid))); });
	}

	@Override
	public void removePlayer(UUID a, String b) {
		PlayerInGame.playerInGame.forEach(uuid -> { if (PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid)) != null) StatisticHandler.update(uuid, EnumStats.TARGETS, PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid))); });		
	}

	@Override
	public void addToList() {
		PlayerRemove.super.addToList();
	}
}