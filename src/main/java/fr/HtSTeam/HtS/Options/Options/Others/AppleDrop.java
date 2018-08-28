package fr.HtSTeam.HtS.Options.Options.Others;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class AppleDrop extends OptionBuilder<Double>{
	
	private boolean request;
	private Player p;
	
	public AppleDrop() {
		super(Material.APPLE, "Loot des pommes", "§2Activé", 0.5, GUIRegister.other);
	}
	
	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir le pourcentage de drop des pommes.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				double value = Double.parseDouble(e.getMessage());
				if(value >= 0 && value <= 100) {
					setState(value);
					p.sendMessage("§2Taux de loot de pommes à " + getValue() + "%." );
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 100.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	
	@Override
	public void setState(Double value) {
		setValue(value);
		this.getItemStack().setLore("§2" + value + "%");
		parent.update(this);
	}

	

	@EventHandler
	public void breakBlock(BlockBreakEvent e){
		if((e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES)) && !e.getPlayer().getItemInHand().getType().equals(Material.SHEARS)){
			e.setCancelled(true);
			e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
			dropApple(e.getBlock().getLocation(), e.getBlock());	
		}
	}
	
	@EventHandler
	public void explodeBlock(BlockExplodeEvent e){
		if((e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))){
			e.setCancelled(true);
			e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
			dropApple(e.getBlock().getLocation(), e.getBlock());
		}
	}
	
	@EventHandler
	public void leavesBlock(LeavesDecayEvent e){
		if((e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))){
			e.setCancelled(true);
			e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
			dropApple(e.getBlock().getLocation(), e.getBlock());
		}
	}
	
	@EventHandler
	public void burnBlock(BlockBurnEvent e){
		if((e.getBlock().getType().equals(Material.OAK_LEAVES) || e.getBlock().getType().equals(Material.DARK_OAK_LEAVES))){
			e.setCancelled(true);
			e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
			dropApple(e.getBlock().getLocation(), e.getBlock());
		}
	}
	
	public void dropApple(Location loc, Block block){
		new Randomizer();
		int randomValue = Randomizer.rand(100);

		if(randomValue <= getValue()){
			ItemStack item = new ItemStack(Material.APPLE, 1);
			loc.getWorld().dropItemNaturally(loc, item).setVelocity(new Vector(0,0,0));
		}else if(randomValue <= getValue()+5.0){
			if(block.getType().equals(Material.OAK_WOOD)) {
				ItemStack item = new ItemStack(Material.OAK_SAPLING, 1);
				loc.getWorld().dropItemNaturally(loc, item).setVelocity(new Vector(0,0,0));
			}else {
				ItemStack item = new ItemStack(Material.DARK_OAK_SAPLING, 1);
				loc.getWorld().dropItemNaturally(loc, item).setVelocity(new Vector(0,0,0));
			}
		}		
	}

	@Override
	public String description() {
		return "§2[Aide]§r Lors de la décomposition d'une feuille de chêne et de chêne noir, il y a 0.5% de chance qu'une pomme loot.";
	}
}
