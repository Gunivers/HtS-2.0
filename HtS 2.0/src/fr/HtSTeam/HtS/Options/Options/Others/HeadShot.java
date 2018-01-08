package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.Alterable;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;

public class HeadShot extends OptionBuilder implements Alterable {
	
private boolean activate = false;
	
	public HeadShot() {
		super(Material.BOW, "HeadShot", "§4Désactivé", "Désactivé", OptionRegister.other);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		setState(activate);
	}
	
	@Override
	public void setState(boolean value) {
		activate = value;
		if (value) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getCause() == DamageCause.PROJECTILE && e.getEntity() instanceof Player) {
			Projectile proj = (Projectile) e.getDamager();
			if (proj.getShooter() instanceof Player) {
				Entity shot = e.getEntity();

				double Y = proj.getLocation().getY();
				double shotY = shot.getLocation().getY();
				boolean headshot = Y - shotY > 1.35d;

				if (headshot) {
					if (activate) {
						Player p = (Player) e.getEntity();
						((Player) proj.getShooter()).playSound(p.getLocation(), Sound.ENTITY_SLIME_SQUISH, 10, 10);
						p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 10);
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 255));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 255));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 30, 255));
					}
				}
			}
		}
	}
}
