package fr.HtSTeam.HtS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class Test {

	public static void main(String[] args) {
		
		
		
		ItemStack is = new ItemStack(Material.SLIME_BALL, 10);
		//is.getItemMeta().setDisplayName("Test");
		
		 try {
				ByteArrayOutputStream str = new ByteArrayOutputStream();	
				BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
				data.writeObject(is);
				data.close();
				System.out.println(Base64.getEncoder().encodeToString(str.toByteArray()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	

}
