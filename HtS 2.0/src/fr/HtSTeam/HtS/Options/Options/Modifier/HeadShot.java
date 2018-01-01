package fr.HtSTeam.HtS.Options.Options.Modifier;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class HeadShot extends OptionsManager {
	
private boolean activate = false;
	
	public HeadShot() {
		super(Material.BOW, "HeadShot", "§4Désactivé", "Désactivé", OptionsRegister.modifiers);
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if (activate) {
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
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 255));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 255));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 30, 255));
					}
				}
			}
		}
	}
}
