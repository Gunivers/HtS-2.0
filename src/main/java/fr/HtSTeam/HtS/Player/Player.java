package fr.HtSTeam.HtS.Player;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
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
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Team.Team;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Nms;
import fr.HtSTeam.HtS.Utils.PRIORITY;
import fr.HtSTeam.HtS.Utils.ReflectionUtil;
import fr.HtSTeam.HtS.Utils.Tag;
import fr.HtSTeam.HtS.Utils.Files.XmlFile;

public class Player {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static HashMap<UUID, Player> uuids = new HashMap<UUID, Player>();
	private static HashMap<String, Player> names = new HashMap<String, Player>();
	
	private ArrayList<Runnable> asynctasks = new ArrayList<Runnable>();
	
	private org.bukkit.entity.Player player;
	
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
	 * Dull Constructor <strong>DO NOT USE</strong>
	 */
	public Player() {}
	
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
	 * Use this method to know whether you should run your method in case the player is offline. You can add your method to be executed when the player reconnects
	 * 	
	 * @param addasync should it add this method to run at the player reconnection
	 * @param args parameters' values of method (do not put addasync in args)
	 * @return
	 * 		true when the player is online <p></p>
	 * 		false when the player is offline
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
		if (asynctasks.isEmpty())
			return;
		asynctasks.forEach(task -> task.run());
		asynctasks.clear();
	}
	/**
	 * Event triggered when a player joined, executes the {@link #runAsyncTask() runAsyncTask} method.
	 * 
	 * @param e the event
	 * @param p the player
	 */
	@EventHandler(PRIORITY.PLAYER)
	public static void join(PlayerJoinEvent e, Player p) {
		p.player = e.getPlayer();
		p.runAsyncTask();
	}
	
	
	/**
	 * Event triggered when a player left.
	 * 
	 * @param e the event
	 * @param p the player
	 */
	@EventHandler(PRIORITY.PLAYER)
	public static void leave(PlayerQuitEvent e, Player p) {
		org.bukkit.entity.Player bplayer = e.getPlayer();
		p.op = bplayer.isOp();
		p.world = bplayer.getWorld();
		p.location = bplayer.getLocation();
		p.inventory = bplayer.getInventory();
		p.health = bplayer.getHealth();
	}
	
	
	@SuppressWarnings("serial")
	void save() {
		ArrayList<Field> fields = new ArrayList<Field>();
		for (int i = ReflectionUtil.fieldIndex(getClass().getDeclaredFields(), "uuid"); i < ReflectionUtil.fieldIndex(getClass().getDeclaredFields(), "inventory") + 1; i++)
			fields.add(getClass().getDeclaredFields()[i]);
		
		new File(Main.plugin.getDataFolder() + "/" + "Players" + "/", uuid.toString()  + ".xml").delete();
		
		XmlFile f = new XmlFile("Players", uuid.toString());
		fields.forEach(field -> { try {
			field.setAccessible(true);
			Object value = field.get(this);
			if(value != null)
				f.add(new Tag("field", new HashMap<String, String>() {{ put("name", field.getName()); }}, new ArrayList<Tag>(){{ add(new Tag(value.toString(), null, null)); }}));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		f.save();
	}
	
	
	
	
//	EVENTS --------------------------------------------------------------------------------------

	
	

	@EventHandler(PRIORITY.PLAYER)
	public static void onWorld(PlayerChangedWorldEvent e, Player p) {
		p.world = e.getPlayer().getWorld();
	}
	
	@EventHandler(PRIORITY.PLAYER)
	public void onPlayerJoin(PlayerJoinEvent e, Player p) {
	    if(!EnumState.getState().equals(EnumState.WAIT))
			if (p.isInGame())
				p.setSpectator();
	}


	@EventHandler(PRIORITY.PLAYER)
	public void onDeath(PlayerDeathEvent e, Player p) {
		IconBuilder.optionsList.keySet().stream().filter(key -> key instanceof DeathTrigger).forEach(key -> ((DeathTrigger) key).onDeath(Player.instance(e.getEntity())));
	}
	
	
	
	
//	METHOS --------------------------------------------------------------------------------------
	
	
	// RETURN
	
	/**
	 * Returns the list of all Player (not necessarily online).
	 * 
	 * @return ArrayList<Player>
	 */
	public static ArrayList<Player> getPlayers() { return players; }
	
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
	public String getDisplayName() { return display_name; }
	
	
	/**
	 * Returns the team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public Team getTeam() { return team; }
	
	
	/**
	 * Returns the fake team of this Player.
	 * 
	 * @return TeamBuilder
	 */
	public Team getFakeTeam() { return fake_team; }
	
	
	/**
	 * Returns true if this Player is alive.
	 * 
	 * @return boolean
	 */
	public boolean isInGame() { return inGame; }
	
	
	/**
	 * Returns true if this Player is a spectator.
	 * 
	 * @return boolean
	 */
	public boolean isSpectator() { return spectator; }
	
	/**
	 * Returns a list of all items taht will be droped on death
	 * @return List<ItemStack>
	 */
	public List<ItemStack> getDeathLoot() { return deathLoot; }
	
	
	
	
	/**
	 * Returns true if this Player is op.
	 * 
	 * @return boolean
	 */
	public boolean isOp() { return op; }
	
	
	/**
	 * Returns world.
	 * 
	 * @return World
	 */
	public World getWorld() { return world; }
	
	
	/**
	 * Returns last known location.
	 * 
	 * @return Location
	 */
	public Location getLocation() {
		if (canExecute(false))
			location = player.getLocation();
		return location;
	}
	

	/**
	 * Returns inventory.
	 * 
	 * @return Inventory
	 */
	public Inventory getInventory() {
		if (canExecute(false))
			inventory = player.getInventory();
		return inventory;
	}
	
	
	/**
	 * Returns health value.
	 * 
	 * @return double
	 */
	public double getHealth() {
		if (canExecute(false))
			health = player.getHealth();
		return health;
	}
	
	
	/**
	 * Returns true if the player has been granted this permission
	 * @param string
	 * @return
	 */
	public boolean hasPermission(String perm) {
		if (canExecute(false))
			return player.hasPermission(perm);
		return false;
	}
	
	// NO RETURN
	
	/**
	 * You know how this shit works
	 * 
	 * @param action lambda expression
	 */
	public static void forEach(Consumer<Player> action) {
		for (Player t : players)
			action.accept(t);
	}
	

	/**
	 * Sends a message to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param message the message to send to the player
	 */
	public void sendMessage(String message) { sendMessage(message, true); }
	/**
	 * Sends a message to the player
	 * 
	 * @param message the message to send to the player
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
	 */
	public void sendMessage(String message, boolean addasync) {
		if (message == null || message.isEmpty() || !canExecute(addasync, message))
			return;
		player.sendMessage(message);
	}
	
	
	/**
	 * Sends a JSON message to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param msg_cmd the message to send to the player
	 */
	public void sendJsonMessage(final LinkedHashMap<String, Entry<String, String>> message) { sendJsonMessage(message, true); }
	/**
	 * Sends a JSON message to the player
	 * 
	 * @param msg_cmd the message to send to the player
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
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
	 * Sends a title to the player if he is connected, else it will send it when he reconnects
	 * 
	 * @param title the title to send to the player
	 * @param subtitle the subtitle to send to the player
	 * @param duration the duration of the title
	 */
	public void sendTitle(String title, String subtitle, int duration) { sendTitle(title, subtitle, duration, true); }
	/**
	 * Sends a title to the player
	 * 
	 * @param title the title to send to the player
	 * @param subtitle the subtitle to send to the player
	 * @param duration the duration of the title
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
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
	 * Sends a message to the player in his Action Bar if he is connected, else it will send it when he reconnects
	 * 
	 * @param message the message to send to the player
	 * @param duration the duration (seconds) of the message to be displayed
	 */
	public void sendActionBar(String message, int duration) { sendActionBar(message, duration, true); }
	/**
	 * Sends a message to the player in his Action Bar
	 * 
	 * @param message the message to send to the player
	 * @param duration the duration (seconds) of the message to be displayed
	 * @param addasync whether it should send the message when the player reconnects in case he was offline
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
	 * Sends a message to the player in his Action Bar
	 * 
	 * @param message the message to send to the player
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
	 * <br><strong>DO NOT USE</strong> - use {@link Team#add(Player) add} of the Team</br>
	 * 
	 * @param Team
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	/**	
	 * Sets this player's fake team
	 * 
	 * <br><strong>DO NOT USE</strong> - use {@link Team#add(Player) add} of the Team</br>
	 * 
	 * @param Team
	 */
	public void setFakeTeam(Team fake_team) {
		this.fake_team = fake_team;
	}
	
	
	
	
	/**
	 * Makes the player open this inventory
	 * @param inventory
	 */
	public void openInventory(Inventory inventory) {
		if(canExecute(false))
			player.openInventory(inventory);
	}
	/**
	 * Closes the player inventory
	 * @param inventory
	 */
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
	
	
	
	/**
	 * Plays the selected sound to the player.
	 * @param location
	 * @param sound
	 * @param volume
	 * @param pitch
	 */
	public void playSound(Location location, Sound sound, int volume, int pitch) {
		if(canExecute(false))
			player.playSound(location, sound, volume, pitch);
	}
	
	
	
	/**
	 * Sets this potion effect to the player
	 * @param potionEffect
	 */
	public void addPotionEffect(PotionEffect potionEffect) {
		if(canExecute(false))
			player.addPotionEffect(potionEffect);
	}
	
	
	
	/**
	 * Adds item to the dropped items at this player's death.
	 * @param ism
	 */
	public void addDeathLootItem(ItemStackBuilder ism) {
		deathLoot.add(ism);
	}
	/**
	 * Adds item to the dropped items at this player's death.
	 * @param ism
	 */
	public void addDeathLootItem(Material material) {
		deathLoot.add(new ItemStack(material, 1));
	}
	/**
	 * Removes item to the dropped items at this player's death.
	 * @param ism
	 */
	public void removeDeathLootItem(Material material) {
		for (int i = 0; i < deathLoot.size(); i++)
			if (deathLoot.get(i).getType().equals(material))
				deathLoot.remove(deathLoot.get(i));
	}
	
	
	
	
	/**
	 * Puts the player in spectator for the game.
	 */
	private void setSpectator() {
		spectator = true;
		if (canExecute(true))
			player.setGameMode(GameMode.SPECTATOR);
	}
}