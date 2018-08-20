package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.SyT;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Players.PlayerInGame;

public class TargetsStatOption extends OptionBuilder<Boolean> {
	
	public TargetsStatOption() {
		super(Material.SKULL_ITEM, "TARGETS", "§2Activé", true, GUIRegister.stats);		
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
	
	public void init() {
		PlayerInGame.playerInGame.forEach(uuid -> { StatisticHandler.update(uuid, EnumStats.TARGETS, PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid))); });
	}
	
	@EventHandler
	public void on(PlayerDeathEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.TARGETS.isTracked() && Main.gamemode.gamemodeToString().equals("SyT"))
			PlayerInGame.playerInGame.forEach(uuid -> { StatisticHandler.update(uuid, EnumStats.TARGETS, PlayerInGame.uuidToName.get(SyT.targetCycleOption.getTarget(uuid))); });
	}
}