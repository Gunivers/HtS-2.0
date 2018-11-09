package fr.HtSTeam.HtS.Options.Structure;

import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Events.Structure.EventHandler;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;

public abstract class ChatOption<A> extends Option<A> {

	private boolean request;
	protected Player p;
	
	public ChatOption(ItemStackBuilder material, A defaultValue, Gui gui) {
		this(material, defaultValue, gui, -1);
	}
	
	public ChatOption(Material material, String name, String description, A defaultValue, Gui gui) {
		this(material, name, description, defaultValue, gui, -1);
	}
	
	public ChatOption(Material material, String name, String description, A defaultValue, Gui gui, int slot) {
		this(new ItemStackBuilder(material, 1, "§r" + name, description), defaultValue, gui, slot);
	}
	
	public ChatOption(ItemStackBuilder material, A defaultValue, Gui gui, int slot) {
		super(material, defaultValue, gui, slot);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
		this.p = p;
	}
	
	public abstract boolean isCorrectValue(String value);
	public abstract void dispRequestMessage();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e, Player p) {		
		if(request && e.getPlayer().getUniqueId().equals(p.getUUID())) {
			e.setCancelled(true);
			if(isCorrectValue(e.getMessage())) {
				request = false;
				p = null;
			}
		}
	}
}
