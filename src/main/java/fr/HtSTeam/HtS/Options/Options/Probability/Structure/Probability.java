package fr.HtSTeam.HtS.Options.Options.Probability.Structure;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Player.Player;

/**
 * 
 * @author A~Z
 *
 */
public class Probability extends OptionBuilder<Double>
{
	
	private static final int min = 1;
	private static final int max = 100;
	
	private final String name;
	protected static final Random random = new Random(max);

	private boolean request = false;
	private Player p = null;
	
	public Probability(Material material, String name, String description, Double defaultValue)
	{
		super(material, name, description, defaultValue, GUIRegister.probability, false);
		
		this.name = name.toLowerCase();
	}

	@Override
	public void event(Player p)
	{
		this.request = true;
		this.p = p;
		this.p.closeInventory();
		this.p.sendMessage("§2Veuillez saisir la probabilité de " + name.toLowerCase() + ":");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		if (request && this.p.getUUID().equals(e.getPlayer().getUniqueId()))
		{
			e.setCancelled(true);
			
			try
			{
				double value = Double.parseDouble(e.getMessage());
				this.setState(value);
				
				if (value >= min && value <= max)
				{
					p.sendMessage("§2La probabilité de "+ name +" est de §6" + value + "§2%.");
					
					parent.update(this);
					request = false;
					
					return;
				}

				p.sendMessage("La probabilité de "+ name +" est nulle.");
				
			} catch (NumberFormatException ex)
			{
				p.sendMessage("§4La valeur saisie n'est pas un nombre.");
				request = false;
			}
		}
	}
	
	@Override
	public void setState(Double value)
	{
		this.setValue(value >= min && value <= max ? value : -1);
		this.getItemStack().setLore(value != -1 ? "§4Désactivé" : "§6"+ value +"§2%");
	}

	@Override
	public String description()
	{
		return "§1§o[Aide]§2 La probabilité de "+ name +" est "+ (this.getValue() != -1 ? " de §6"+ this.getValue() +"§2%" : "§4nulle");
	}
}
