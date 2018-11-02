package fr.HtSTeam.HtS.Events.Structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.BatToggleSleepEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PigZombieAngerEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRecipeDiscoverEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.Player;

public class Event implements Listener {
	
	private HashMap<Class<?>, ArrayList<Method>> eventMethods = new HashMap<Class<?>, ArrayList<Method>>();
	
	/**
	 * Registers every annoted methods into Events.
	 */
	public Event() {
		Main.LOGGER.logInfo("[Events] Registering events...");
		ArrayList<Method> annoted_methods = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("fr.HtSTeam.HtS")).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(EventHandler.class));
		annoted_methods.removeIf(m -> m.getParameterCount() != 2);
		HashSet<Class<?>> clazzes = new HashSet<Class<?>>();
		annoted_methods.forEach(m -> { clazzes.add(m.getParameterTypes()[0]); Main.LOGGER.logInfo("[Events] > " + m.getParameterTypes()[0].getName().substring(m.getParameterTypes()[0].getName().lastIndexOf('.') + 1) + "    ...    " + m.getDeclaringClass().getName() + "." + m.getName()); });
		clazzes.forEach(clazz -> {
			ArrayList<Method> methods = new ArrayList<Method>();
			annoted_methods.forEach(m -> {
				if (m.getParameterTypes()[0].equals(clazz))
					methods.add(m);
			});
			methods.sort((o1, o2) -> o1.getAnnotation(EventHandler.class).value()
					.compareTo(o2.getAnnotation(EventHandler.class).value()));
			eventMethods.put(clazz, methods);
		});
		Main.LOGGER.logInfo("[Events] " + annoted_methods.size() + " events registered!");
	}
	
	/**
	 * Dispatches the event to concerned methods.
	 * @param clazz
	 * @param event
	 * @param player
	 */
	private void invoke(Class<?> clazz, Object event, Player player) {
		if (!eventMethods.containsKey(clazz))
			return;
		eventMethods.get(clazz).forEach(m -> { try {
			m.invoke(null, event, player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} });
	}
	
	/**
	 * Returns the {@link Player Player(HtS)} if the entity is a {@link org.bukkit.entity.Player Player(Bukkit)} else null.
	 * @param e
	 * @return
	 */
	private Player getPlayer(Entity e) {
		if (e instanceof org.bukkit.entity.Player)
			return Player.instance((org.bukkit.entity.Player) e);
		return null;
	}
	
	// Events -----
	
	// Block
	
	@org.bukkit.event.EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockBurnEvent(BlockBurnEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockCanBuildEvent(BlockCanBuildEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockDispenseArmorEvent(BlockDispenseArmorEvent event) { invoke(event.getClass(), event, getPlayer(event.getTargetEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockDispenseEvent(BlockDispenseEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockExpEvent(BlockExpEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockExplodeEvent(BlockExplodeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockFadeEvent(BlockFadeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockFertilizeEvent(BlockFertilizeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockFormEvent(BlockFormEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockFromToEvent(BlockFromToEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockGrowEvent(BlockGrowEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockIgniteEvent(BlockIgniteEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockMultiPlaceEvent(BlockMultiPlaceEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPhysicsEvent(BlockPhysicsEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPistonRetractEvent(BlockPistonRetractEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onBlockRedstoneEvent(BlockRedstoneEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBlockSpreadEvent(BlockSpreadEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onCauldronLevelChangeEvent(CauldronLevelChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityBlockFormEvent(EntityBlockFormEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onLeavesDecayEvent(LeavesDecayEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onMoistureChangeEvent(MoistureChangeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onNotePlayEvent(NotePlayEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onSignChangeEvent(SignChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onSpongeAbsorbEvent(SpongeAbsorbEvent event) { invoke(event.getClass(), event, null); }
	
	// Enchantment
	
	@org.bukkit.event.EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event) { invoke(event.getClass(), event, getPlayer(event.getEnchanter())); }
	
	@org.bukkit.event.EventHandler
	public void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) { invoke(event.getClass(), event, getPlayer(event.getEnchanter())); }
	
	// Entity
	
	@org.bukkit.event.EventHandler
	public void onAreaEffectCloudApplyEvent(AreaEffectCloudApplyEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onBatToggleSleepEvent(BatToggleSleepEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onCreeperPowerEvent(CreeperPowerEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEnderDragonChangePhaseEvent(EnderDragonChangePhaseEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityAirChangeEvent(EntityAirChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityBreakDoorEvent(EntityBreakDoorEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityBreedEvent(EntityBreedEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityCombustByBlockEvent(EntityCombustByBlockEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityCombustEvent(EntityCombustEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityDropItemEvent(EntityDropItemEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityExplodeEvent(EntityExplodeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityInteractEvent(EntityInteractEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityPickupItemEvent(EntityPickupItemEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityPortalEvent(EntityPortalEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityPortalExitEvent(EntityPortalExitEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityPotionEffectEvent(EntityPotionEffectEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityResurrectEvent(EntityResurrectEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityTameEvent(EntityTameEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityTargetEvent(EntityTargetEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityTeleportEvent(EntityTeleportEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityToggleSwimEvent(EntityToggleSwimEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onEntityUnleashEvent(EntityUnleashEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onExpBottleEvent(ExpBottleEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onExplosionPrimeEvent(ExplosionPrimeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onFireworkExplodeEvent(FireworkExplodeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onHorseJumpEvent(HorseJumpEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onItemDespawnEvent(ItemDespawnEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onItemMergeEvent(ItemMergeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onItemSpawnEvent(ItemSpawnEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onLingeringPotionSplashEvent(LingeringPotionSplashEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onPigZapEvent(PigZapEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onPigZombieAngerEvent(PigZombieAngerEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerLeashEntityEvent(PlayerLeashEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onPotionSplashEvent(PotionSplashEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onProjectileHitEvent(ProjectileHitEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onProjectileLaunchEvent(ProjectileLaunchEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onSheepDyeWoolEvent(SheepDyeWoolEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onSheepRegrowWoolEvent(SheepRegrowWoolEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onSlimeSplitEvent(SlimeSplitEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onVillagerAcquireTradeEvent(VillagerAcquireTradeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onVillagerReplenishTradeEvent(VillagerReplenishTradeEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	// Hanging
	
	// Inventory
	
	@org.bukkit.event.EventHandler
	public void onBrewEvent(BrewEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onBrewingStandFuelEvent(BrewingStandFuelEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onCraftItemEvent(CraftItemEvent event) { invoke(event.getClass(), event, getPlayer(event.getWhoClicked())); }
	
	@org.bukkit.event.EventHandler
	public void onFurnaceBurnEvent(FurnaceBurnEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onFurnaceExtractEvent(FurnaceExtractEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) { invoke(event.getClass(), event, getPlayer(event.getWhoClicked())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryCreativeEvent(InventoryCreativeEvent event) { invoke(event.getClass(), event, getPlayer(event.getWhoClicked())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryDragEvent(InventoryDragEvent event) { invoke(event.getClass(), event, getPlayer(event.getWhoClicked())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryEvent(InventoryEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryInteractEvent(InventoryInteractEvent event) { invoke(event.getClass(), event, getPlayer(event.getWhoClicked())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryOpenEvent(InventoryOpenEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onInventoryPickupItemEvent(InventoryPickupItemEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onPrepareAnvilEvent(PrepareAnvilEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onPrepareItemCraftEventt(PrepareItemCraftEvent event) { invoke(event.getClass(), event, null); }
	
	// Player
	
	@org.bukkit.event.EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerAnimationEvent(PlayerAnimationEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerBucketEmptyEvent(PlayerBucketEmptyEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerBucketEvent(PlayerBucketEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerBucketFillEvent(PlayerBucketFillEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerChangedMainHandEvent(PlayerChangedMainHandEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerChannelEvent(PlayerChannelEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerEditBookEvent(PlayerEditBookEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerEggThrowEvent(PlayerEggThrowEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerEvent(PlayerEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerFishEvent(PlayerFishEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerItemMendEvent(PlayerItemMendEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) { Bukkit.broadcastMessage(event.getJoinMessage()); event.setJoinMessage(null);	invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerKickEvent(PlayerKickEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerLevelChangeEvent(PlayerLevelChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerLocaleChangeEvent(PlayerLocaleChangeEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerPickupArrowEvent(PlayerPickupArrowEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerPortalEvent(PlayerPortalEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) { String msg = event.getQuitMessage(); event.setQuitMessage(null);	invoke(event.getClass(), event, getPlayer(event.getPlayer())); Bukkit.broadcastMessage(msg); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerRecipeDiscoverEvent(PlayerRecipeDiscoverEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerRegisterChannelEvent(PlayerRegisterChannelEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerResourcePackStatusEvent(PlayerResourcePackStatusEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerRiptideEvent(PlayerRiptideEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerShearEntityEvent(PlayerShearEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerToggleFlightEvent(PlayerToggleFlightEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerToggleSprintEvent(PlayerToggleSprintEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerUnleashEntityEvent(PlayerUnleashEntityEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerUnregisterChannelEvent(PlayerUnregisterChannelEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	@org.bukkit.event.EventHandler
	public void onPlayerVelocityEvent(PlayerVelocityEvent event) { invoke(event.getClass(), event, getPlayer(event.getPlayer())); }
	
	// Server
	
	// Vehicle
	
	@org.bukkit.event.EventHandler
	public void onVehicleBlockCollisionEvent(VehicleBlockCollisionEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleCreateEvent(VehicleCreateEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleDamageEvent(VehicleDamageEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleDestroyEvent(VehicleDestroyEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleEnterEvent(VehicleEnterEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleEntityCollisionEvent(VehicleEntityCollisionEvent event) { invoke(event.getClass(), event, getPlayer(event.getEntity())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleExitEvent(VehicleExitEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	@org.bukkit.event.EventHandler
	public void onVehicleMoveEvent(VehicleMoveEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }

	@org.bukkit.event.EventHandler
	public void onVehicleMoveEvent(VehicleUpdateEvent event) { invoke(event.getClass(), event, getPlayer(event.getVehicle())); }
	
	// Weather
	
	@org.bukkit.event.EventHandler
	public void onLightningStrikeEvent(LightningStrikeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onThunderChangeEvent(ThunderChangeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onWeatherChangeEvent(WeatherChangeEvent event) { invoke(event.getClass(), event, null); }
	
	// World
	
	@org.bukkit.event.EventHandler
	public void onChunkLoadEvent(ChunkLoadEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onChunkPopulateEvent(ChunkPopulateEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onChunkUnloadEvent(ChunkUnloadEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onPortalCreateEvent(PortalCreateEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onSpawnChangeEvent(SpawnChangeEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onStructureGrowEvent(StructureGrowEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onWorldInitEvent(WorldInitEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onWorldLoadEvent(WorldLoadEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onWorldSaveEvent(WorldSaveEvent event) { invoke(event.getClass(), event, null); }
	
	@org.bukkit.event.EventHandler
	public void onWorldUnloadEvent(WorldUnloadEvent event) { invoke(event.getClass(), event, null); }
	
	
}