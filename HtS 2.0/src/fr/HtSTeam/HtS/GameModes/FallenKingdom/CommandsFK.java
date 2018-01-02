package fr.HtSTeam.HtS.GameModes.FallenKingdom;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Teams.TeamManager;
import fr.HtSTeam.HtS.Utils.ItemStackManager;
import net.md_5.bungee.api.ChatColor;

public class CommandsFK implements CommandExecutor, Listener {
	
	private boolean inCreation = false;
	private ItemStackManager ism;
	private Player p;
	private String name;
	private int angleDo = 0;
	private Block firstAngle;
	private Block secondAngle;
	private TeamManager team;


	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			
			if (cmd.getName().equalsIgnoreCase("base") && sender.hasPermission("base.use")) {
				
				
				
				if(args.length == 3 && args[0].equalsIgnoreCase("add")) {
					if(!TeamManager.nameTeam.containsKey(args[2])) {
						p.sendMessage("§4La team saisie n'existe pas !");
						return true;
					}
					team = TeamManager.nameTeam.get(args[2]);
					if(inCreation) { 
						p.sendMessage("§4 Une base est déjà en création !");
						return true;
					}
					if(!p.getEquipment().getItemInMainHand().getType().equals(Material.AIR)) {
						this.p = p;
						name = args[1];
						ItemStack im = p.getEquipment().getItemInMainHand();
						ism = new ItemStackManager(im.getType(), im.getData().getData(), 1, "Définir le premier angle de la base " + name, null, true);
						p.getEquipment().setItemInMainHand(ism.getItemStack());
						inCreation = true;
					} else {
						p.sendMessage("§4Veuillez prendre un item en main.");
					}
					return true;
				
				
				
				} if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
					if(BaseManager.nameBase.containsKey(args[1])) {
						BaseManager.nameBase.get(args[1]).deleteBase();
						p.sendMessage("§2Base supprimée.");
					}
					else
						p.sendMessage("§4Base inexistante.");
					return true;
				
				
				} if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
					p.sendMessage("- Bases crées : ");
					for(BaseManager bm : BaseManager.baseList) {
						p.sendMessage(" - " + ChatColor.valueOf(bm.getTeam().getTeamColor().toUpperCase()) + bm.getBaseName());
						p.sendMessage("  - §2Team :§r " + ChatColor.valueOf(bm.getTeam().getTeamColor().toUpperCase()) + bm.getTeam().getTeamName());
						p.sendMessage("  - §2Premier angle :§r " + bm.getPos()[0][0] + " " + bm.getPos()[1][0]);
						p.sendMessage("  - §2Second angle :§r " + bm.getPos()[0][1] + " " + bm.getPos()[1][1]);
					}
					return true;
				}
				
				
			}
		}
		return false;
	}
	
	
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		ItemStack is = e.getPlayer().getEquipment().getItemInMainHand();
		if(inCreation && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().equals(p) && ism.getMaterial().equals(is.getType()) && ism.getName().equals(is.getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			Block b = e.getClickedBlock();
			angleDo++;
			if(angleDo == 1) {
				firstAngle = b;
				ism = new ItemStackManager(ism.getMaterial(), ism.getData(), 1, "Définir le second angle de la base " + name, "Premier angle : " + firstAngle.getX() + " " + firstAngle.getY() + " " + firstAngle.getZ(), true);
				p.getEquipment().setItemInMainHand(ism.getItemStack());
			} else if(angleDo == 2) {
				secondAngle = b;
				p.sendMessage("§2Base " + name + " de l'équipe §r" + ChatColor.valueOf(team.getTeamColor().toUpperCase()) + team.getTeamName() + " §2créée avec succès !");
				p.sendMessage("§2Premier angle : §5" + firstAngle.getX() + " " + firstAngle.getY() + " " + firstAngle.getZ());
				p.sendMessage("§2Second angle : §5" + secondAngle.getX() + " " + secondAngle.getY() + " " + secondAngle.getZ());
				BaseManager bm = new BaseManager(name, firstAngle, secondAngle);
				bm.addTeam(team);
				reset();
			}
		} else if(e.getPlayer().equals(p) && inCreation) {
			p.sendMessage("§4Création de la base annulée.");
			reset();
			e.setCancelled(true);
		}
	}

	
	private void reset() {
		secondAngle = null;
		firstAngle = null;
		name = null;
		ism = null;
		inCreation = false;
		angleDo = 0;
		p = null;
	}
	
	
}
