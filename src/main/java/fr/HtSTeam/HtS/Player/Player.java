package fr.HtSTeam.HtS.Player;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Options.Structure.DeathTrigger;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.PRIORITY;

/**
 * Player is the class meant to replace the Bukkit Player while providing
 * secure ways of dealing with not connected players and backups.
 * 
 * A Player object encapsulates all the information existing in Bukkit Player
 * and data related to this Plugin. However, it may be split into different
 * child so as to a Player object can remain as general as possible.
 * <p>
 * A Player object is guaranteed to exists as long as the concerned player 
 * has logged on at least once. Although, it is possible to load any player
 * from its data file.
 * <p>
 * A Player object also guarantees access to all of its data at all time.
 * Nonetheless, if the player is not connected when writing or reading data,
 * this will not reflect any changes that affected this player. Indeed,
 * a Player data is updated when it is requested and the player is online.
 * <p>
 * A Player object includes a way to execute a given code when the player
 * reconnects. This should not be chosen as default but thought thoroughly
 * prior to implementing it. It should not be used for trivial information.
 * <p>
 * Finally, A Player object features a backup system thence it is serializable.
 * It can restore any given data.
 *
 * @author ikbrunel
 */
@SuppressWarnings("serial")
public class Player implements Serializable {
	
	private transient static ArrayList<Player> players = new ArrayList<Player>();
	private transient static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	private transient static HashMap<String, Player> names = new HashMap<String, Player>();
	
	private transient Stack<Runnable> asynctasks = new Stack<Runnable>();
	
	private transient org.bukkit.entity.Player player;
	
	private UUID uuid;
	private String name;
	private String display_name;
	
	private Team team;
	private Team fake_team;
	
	private boolean spectator;
	private boolean inGame;
	private List<ItemStack> deathLoot = new ArrayList<ItemStack>();
	
	private boolean op;
	
	private double health;
	
	private World world;
	private Location location;
	private Inventory inventory;
	
	/**
	 * Sole constructor. (For invocation by subclass 
	 * constructors, typically implicit.)
	 */
	protected Player() {  }
	
	/**
	 * Creates an independent player, highly modular and safe.
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player
	 */
	private Player(org.bukkit.entity.Player player) {
		if (player == null)
			return;
		
		players.add(this);
		uuids.put(player.getUniqueId(), this);
		names.put(player.getName(), this);
		
		this.player = player;
		
		uuid = player.getUniqueId();
		name = player.getName();
		display_name = player.getDisplayName();
		
		spectator = false;
		inGame = true;
		
		op = player.isOp();
		world = player.getWorld();
		location = player.getLocation();
		inventory = player.getInventory();
		health = player.getHealth();
	}
	
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param player 
	 * 			Bukkit Player
	 * @return Player (new or already existing instance)
	 */
	public static Player instance(org.bukkit.entity.Player player) {
		if (player == null)
			return null;
		else if (uuids.containsKey(player.getUniqueId()))
			return uuids.get(player.getUniqueId());
		return new Player(player);	
	}
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param uuid 
	 * 			UUID of Player
	 * @return Player (new or already existing instance) <strong>[Can be Null]</strong>
	 */
	public static Player instance(UUID uuid) {
		if (uuids.containsKey(uuid))
			return uuids.get(uuid);
		else if (Bukkit.getPlayer(uuid) != null)
			return instance(Bukkit.getPlayer(uuid));
		else 
			return null;
	}
	/**
	 * Creates an independent player, highly modular and safe. Cannot instantiate twice. 
	 * 
	 * @param name 
	 * 			Name of Player
	 * @return Player (new or already existing instance) <strong>[Can be Null]</strong>
	 */
	public static Player instance(String name) {
		if (names.containsKey(name))
			return names.get(name);
		else if (Bukkit.getPlayer(name) != null)
			return instance(Bukkit.getPlayer(name));
		else 
			return null;
	}
	
	
	/**
	 * Use this method to know whether you should run your method in case this player is offline. You can add your method to be executed when the player reconnects
	 * 	
	 * @param addasync should it add this method to run at the player reconnection
	 * @param args parameters' values of method (do not put addasync in args)
	 * @return boolean
	 */
	private boolean canExecute(final boolean addasync, final Object... args) {
		if (Bukkit.getPlayer(uuid) != null)
			return true;
		else {
			if (addasync)
				addAsyncTask(args);
			return false;
		}
	}
	/**
	 * Add a method to be run when the player reconnects  
	 * 
	 * @param args
	 * 		parameters' values of method to run (do not put addasync in args)
	 */
	private void addAsyncTask(final Object... args) {
		Method m = null;
		for (Method o : getClass().getMethods())
			if (o.getName().equals(Thread.currentThread().getStackTrace()[3].getMethodName()) && o.getParameterCount() == args.length)
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
		while (!asynctasks.empty())
			asynctasks.pop().run();
	}
	/**
	 * Event triggered when a player joined, executes the {@link #runAsyncTask() runAsyncTask} method.
	 * 
	 * @param e
	 * @param p
	 */
	@EventHandler(PRIORITY.PLAYER)
	public static void join(PlayerJoinEvent e, Player p) {
		if (p == null)
			return;
		p.player = e.getPlayer();
		p.runAsyncTask();
	}
	
	
	@EventHandler(PRIORITY.PLAYER)
	public static void leave(PlayerQuitEvent e, Player p) {
		if (p == null)
			return;
		org.bukkit.entity.Player bplayer = e.getPlayer();
		p.op = bplayer.isOp();
		p.world = bplayer.getWorld();
		p.location = bplayer.getLocation();
		p.inventory = bplayer.getInventory();
		p.health = bplayer.getHealth();
		
		p.save();
	}
	
	/**
	 * Saves this player instance to a file.
	 */
	private void save() {
		try {
			FileOutputStream f = new FileOutputStream(new File(Main.plugin.getDataFolder() + "/players/" + this.uuid.toString() + ".player"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(this);

			o.close();
			f.close();
		} catch (IOException ex) {
			Main.LOGGER.logError(ex);
		}
	}
	
	/**
	 * Loads every saved player.
	 */
	public static void load() {
		File[] files = new File(Main.plugin.getDataFolder() + "/players").listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.isDirectory() || !file.getName().toLowerCase().endsWith("player"))
					return false;
				return true;
			}});
		for (int i = 0; i < files.length; i++)
			try {
				FileInputStream fi = new FileInputStream(files[i]);
				ObjectInputStream oi = new ObjectInputStream(fi);

				Player p = (Player) oi.readObject();

				Main.LOGGER.logInfo("Loaded: " + p.getUUID() + " ... " + p.getName());

				oi.close();
				fi.close();
			} catch (IOException | ClassNotFoundException ex) {
				Main.LOGGER.logError(ex);
			}
	}
	
//	EVENTS --------------------------------------------------------------------------------------

	
	

	@EventHandler(PRIORITY.PLAYER)
	public static void onWorld(PlayerChangedWorldEvent e, Player p) {
		if (p == null)
			return;
		p.world = e.getPlayer().getWorld();
	}
	
	@EventHandler(PRIORITY.PLAYER)
	public static void onPlayerJoin(PlayerJoinEvent e, Player p) {
		if (p == null)
			return;
		if(!EnumState.getState().equals(EnumState.WAIT))
			if (p.isInGame())
				p.setSpectator(true);
	}


	@EventHandler(PRIORITY.PLAYER)
	public void onDeath(PlayerDeathEvent e, Player p) {
		if (p == null)
			return;
		Option.optionsList.keySet().stream().filter(key -> key instanceof DeathTrigger).forEach(key -> ((DeathTrigger) key).onDeath(p));
	}
	
	
	
	
//	METHOS --------------------------------------------------------------------------------------
	
	
	// RETURN
	

	public static ArrayList<Player> getPlayers() { return players; }
	

	public UUID getUUID() { return uuid; }
	
	
	public String getName() { return name; }
	

	public String getDisplayName() { return display_name; }
	
	
	/**
	 * Gets the team of this Player.
	 * 
	 * @return team can be null
	 */
	public Team getTeam() { return team; }
	
	
	/**
	 * Gets the fake team of this Player.
	 * 
	 * @return fake_team can be null
	 */
	public Team getFakeTeam() { return fake_team; }
	

	public boolean isInGame() { return inGame; }
	
	
	public boolean isSpectator() { return spectator; }
	
	
	/**
	 * Returns the list of all dropped items on this player's death.
	 * @return deathLoot
	 */
	public List<ItemStack> getDeathLoot() { return deathLoot; }
	
	
	
	
	public boolean isOp() { return op; }
	
	
	public World getWorld() { return world; }
	
	
	public Location getLocation() {
		if (canExecute(false))
			location = player.getLocation();
		return location;
	}
	

	public Inventory getInventory() {
		if (canExecute(false))
			inventory = player.getInventory();
		return inventory;
	}
	
	
	public double getHealth() {
		if (canExecute(false))
			health = player.getHealth();
		return health;
	}
	
	

	public boolean hasPermission(String perm) {
		if (canExecute(false))
			return player.hasPermission(perm);
		return false;
	}
	
	
	public AttributeInstance getAttribute(Attribute attribute) {
		if (canExecute(false))
			return player.getAttribute(attribute);
		return null;
	}
	
	// NO RETURN
	
	
	/**
	 * Loops through all registered players, connected or not.
	 * 
	 * @param action lambda expression
	 */
	public static void forEach(Consumer<Player> action) {
		for (Player t : players)
			action.accept(t);
	}
	

	/**
	 * Sends a message to this player if it is connected, else it will send the message when it reconnects
	 * 
	 * @param message
	 */
	public void sendMessage(String message) { sendMessage(message, true); }
	/**
	 * Sends a message to this player
	 * 
	 * @param message
	 * @param addasync whether it should send the message when this player reconnects in case it was offline
	 */
	public void sendMessage(String message, boolean addasync) {
		if (message == null || message.isEmpty() || !canExecute(addasync, message))
			return;
		player.sendMessage(message);
	}
	
	
	/**
	 * Sends a JSON message to this player if it is connected, else it will send the message when this player reconnects
	 * 
	 * @param msg_cmd
	 */
	public void sendJsonMessage(final LinkedHashMap<String, Entry<String, String>> message) { sendJsonMessage(message, true); }
	/**
	 * Sends a JSON message to this player, acts like a tellraw command.
	 * 
	 * @param msg_cmd
	 * @param addasync whether it should send the message when this player reconnects in case it was offline
	 */
	public void sendJsonMessage(final LinkedHashMap<String, Entry<String, String>> message, boolean addasync) {
		if (message == null || message.isEmpty() || !canExecute(addasync, message))
			return;
		
		String msg = null;
		for (Entry<String, Entry<String, String>> set : message.entrySet())
			if (msg == null)
				if (set.getValue() != null)
					msg = "[{\"text\":\"" + set.getKey() + "\",\"clickEvent\":{\"action\":\"" + set.getValue().getKey() + "\",\"value\":\"" + set.getValue().getValue() + "\"}}";
				else
					msg = "[{\"text\":\"" + set.getKey() + "\"}";
			else if (set.getValue() != null)
				msg = msg + ",{\"text\":\"" + set.getKey() + "\",\"clickEvent\":{\"action\":\"" + set.getValue().getKey() + "\",\"value\":\"" + set.getValue().getValue() + "\"}}";
			else
				msg = msg + ",{\"text\":\"" + set.getKey() + "\"}";
		msg += ']';
		
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet;

			Object[] chatMessageTypes = Nms.chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (Object obj : chatMessageTypes)
				if (obj.toString().equals("CHAT"))
					chatMessageType = obj;

			Object chatCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, message);
			packet = Nms.packetPlayOutChatClass.getConstructor(new Class<?>[] { Nms.iChatBaseComponentClass, Nms.chatMessageTypeClass }).newInstance(chatCompontentText, chatMessageType);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Sends a title to this player in its Action Bar if this player is connected,
	 * else it will send the title when it reconnects
	 *  
	 * @param title
	 * @param subtitle
	 * @param duration the duration (seconds) of the title to be displayed
	 */
	public void sendTitle(String title, String subtitle, int duration) { sendTitle(title, subtitle, duration, true); }
	/**
	 * Sends a title to this player 
	 * 
	 * @param title
	 * @param subtitle
	 * @param duration the duration (seconds) of the title to be displayed
	 * @param addasync whether it should send the message when this player reconnects in case it was offline
	 */
	public void sendTitle(String title, String subtitle, int duration, boolean addasync) {
		if (title == null || title.isEmpty() || duration < 1 || !canExecute(addasync, title, subtitle, duration))
			return;
		
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet_title = null;
			Object packet_subtitle = null;
			Object packet_duration = null;
			
			Object[] titleTypes = Nms.packetPlayOutTitleEnumTitleActionClass.getEnumConstants();
			Object titleType = null;
			Object subtitleType = null;
			for (Object obj : titleTypes)
				if (obj.toString().equals("TITLE"))
					titleType = obj;
				else if (obj.toString().equals("SUBTITLE"))
					subtitleType = obj;
			
			Object titleCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");
			packet_title = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { Nms.packetPlayOutTitleEnumTitleActionClass, Nms.iChatBaseComponentClass }).newInstance(titleType, titleCompontentText);
			if (subtitle != null && !subtitle.isEmpty()) {
				Object subtitleCompontentText = Nms.iChatBaseComponentChatSerializerClass.getDeclaredMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");
				packet_subtitle = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { Nms.packetPlayOutTitleEnumTitleActionClass, Nms.iChatBaseComponentClass }).newInstance(subtitleType, subtitleCompontentText);
			}
			packet_duration = Nms.packetPlayOutTitleClass.getConstructor(new Class<?>[] { int.class, int.class, int.class }).newInstance(5, duration * 20, 5);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet_title);
			if (subtitle != null && !subtitle.isEmpty())
				sendPacketMethod.invoke(playerConnection, packet_subtitle);
			sendPacketMethod.invoke(playerConnection, packet_duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Sends a message to this player in its Action Bar if this player is connected,
	 * else it will send the message when it reconnects
	 * 
	 * @param message
	 * @param duration the duration (seconds) of the message to be displayed
	 */
	public void sendActionBar(String message, int duration) { sendActionBar(message, duration, true); }
	/**
	 * Sends a message to this player in its Action Bar
	 * 
	 * @param message
	 * @param duration the duration (seconds) of the message to be displayed
	 * @param addasync whether it should send the message when this player reconnects in case it was offline
	 */
	public void sendActionBar(String message, int duration, boolean addasync) {
		if (message == null || message.isEmpty() || duration < 1 || !canExecute(addasync, message, duration))
			return;
		
		actionBar(message);
		duration = 20 * duration;
		
		if (duration >= 0) {
			new BukkitRunnable() {
				@Override
				public void run() {
					actionBar("");
				}
			}.runTaskLater(Main.plugin, duration + 1);
		}

		while (duration > 40) {
			duration -= 40;
			new BukkitRunnable() {
				@Override
				public void run() {
					actionBar(message);
				}
			}.runTaskLater(Main.plugin, (long) duration);
		}
	}
	/**
	 * Sends a message to this player in its Action Bar
	 * 
	 * @param message
	 */
	private void actionBar(String message) {
		try {
			Object craftPlayer = Nms.craftPlayerClass.cast(player);
			Object packet;

			Object[] chatMessageTypes = Nms.chatMessageTypeClass.getEnumConstants();
			Object chatMessageType = null;
			for (Object obj : chatMessageTypes)
				if (obj.toString().equals("GAME_INFO"))
					chatMessageType = obj;
			
			Object chatCompontentText = Nms.chatComponentTextClass.getConstructor(new Class<?>[] { String.class }).newInstance(message);
			packet = Nms.packetPlayOutChatClass.getConstructor(new Class<?>[] { Nms.iChatBaseComponentClass, Nms.chatMessageTypeClass }).newInstance(chatCompontentText, chatMessageType);
			
			Method craftPlayerHandleMethod = Nms.craftPlayerClass.getDeclaredMethod("getHandle");
			Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
			Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(craftPlayerHandle);
			Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", Nms.packetClass);
			sendPacketMethod.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**	
	 * Sets this player's team
	 * 
	 * <br><strong>DO NOT USE</strong> - use {@link Team#add(Player) add} from Team instead.</br>
	 * 
	 * @param team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	/**	
	 * Sets this player's fake team
	 * 
	 * <br><strong>DO NOT USE</strong> - use {@link Team#add(Player) add} from Team instead.</br>
	 * 
	 * @param fake_team
	 */
	public void setFakeTeam(Team fake_team) {
		this.fake_team = fake_team;
	}
	
	
	
	

	public void openInventory(Inventory inventory) {
		if(canExecute(false))
			player.openInventory(inventory);
	}

	public void closeInventory() {
		if(canExecute(false))
			player.closeInventory();
	}
	
	
	
	
	/**
	 * Sets the health of this player. Max is 20. (1 is half-heart)
	 * @param health
	 */
	public void setHealth(double health) {
		if(canExecute(false))
			player.setHealth(health);
	}
	
	
	

	public void playSound(Location location, Sound sound, int volume, int pitch) {
		if(canExecute(false))
			player.playSound(location, sound, volume, pitch);
	}
	
	
	
	
	public void addPotionEffect(PotionEffect potionEffect) {
		if(canExecute(false))
			player.addPotionEffect(potionEffect);
	}
	
	
	

	public void addDeathLootItem(ItemStackBuilder ism) {
		deathLoot.add(ism);
	}

	public void addDeathLootItem(Material material) {
		deathLoot.add(new ItemStack(material, 1));
	}

	public void removeDeathLootItem(Material material) {
		for (int i = 0; i < deathLoot.size(); i++)
			if (deathLoot.get(i).getType().equals(material))
				deathLoot.remove(deathLoot.get(i));
	}
	
	
	
	
	/**
	 * Puts this in spectator mode.
	 * 
	 * @param b false will put this player
	 * 			in adventure mode.
	 */
	public void setSpectator(boolean b) {
		spectator = b;
		if (canExecute(false) && b)
			player.setGameMode(GameMode.SPECTATOR);
		else if (canExecute(false))
			player.setGameMode(GameMode.ADVENTURE);
	}
}