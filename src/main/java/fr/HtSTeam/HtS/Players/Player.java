package fr.HtSTeam.HtS.Players;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.player.PlayerJoinEvent;

import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Teams.TeamBuilder;

public class Player {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	
	/**
	 * Returns the list of all Player (not neceseraly online).
	 * 
	 * @return List of Player
	 */
	public static ArrayList<Player> getPlayers() { return players; }
	
	/**
	 * Returns the instance of Player from a UniqueId.
	 * Should not return null.
	 * 
	 * @param uuid
	 * 		UniqueId of Player
	 * @return Player
	 */
	public static Player getPlayerFromUUID(UUID uuid) { if (uuids.containsKey(uuid)) return uuids.get(uuid); return null; }
	
	private ArrayList<Runnable> asynctasks = new ArrayList<Runnable>();
	
	private UUID uuid;
	private String name;
	private String display_name;

	private TeamBuilder team;
	private TeamBuilder fake_team;
	
	/**
	 * Creates an independent player, highly modular and safe.
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player
	 */
	private Player(org.bukkit.entity.Player player) {
		players.add(this);
		uuids.put(player.getUniqueId(), this);
		
		uuid = player.getUniqueId();
		name = player.getName();
		display_name = player.getDisplayName();
	}
	
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player (new or already existing instance)
	 */
	public static Player instance(org.bukkit.entity.Player player) {
		if (uuids.containsKey(player.getUniqueId()))
			return uuids.get(player.getUniqueId());
		return new Player(player);	
	}
	
	/**
	 * Returns the UUID of this Player.
	 * 
	 * @return UUID
	 */
	public UUID getUUID() { return uuid; }
	
	/**
	 * Returns the UUID of this Player.
	 * 
	 * @return String
	 */
	public String getName() { return name; }
	
	/**
	 * Returns the displayed name of this Player.
	 * 
	 * @return String
	 */
	public String getDisplay() { return display_name; }
	
	/**
	 * Returns the team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public TeamBuilder getTeam() { return team; }
	
	/**
	 * Returns the fake team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public TeamBuilder getFakeTeam() { return fake_team; }
	
	
	/**
	 * Add a method to be run when the player reconnects  
	 * 
	 * @param args
	 * 		parameters' values of method to run
	 */
	private void addAsyncTask(final Object... args) {
		Method m = null;
		for (Method o : getClass().getMethods())
			if (o.getName().equals(Thread.currentThread().getStackTrace()[2].getMethodName()))
				m = o;
		
		if (m == null)
			return;
		
		final Method method = m;
		Runnable runnable = () -> {
			try {
				method.invoke(this, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		};
		asynctasks.add(runnable);
	}
	
	/**
	 * Runs every method added via {@link #addAsyncTask(Object...) addAsyncTask}
	 */
	private void runAsyncTask() {
		if (asynctasks.isEmpty())
			return;
		asynctasks.forEach(task -> task.run());
		asynctasks.clear();
	}
	
	/**
	 * Event triggered when a player joined, executes the {@link #runAsyncTask() runAsyncTask} method
	 * 
	 * @param e the event
	 */
	@EventHandler
	public static void onPlayerjoin(PlayerJoinEvent e) {
		getPlayerFromUUID(e.getPlayer().getUniqueId()).runAsyncTask();
	}
}