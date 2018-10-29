package fr.HtSTeam.HtS.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ColorUtil {
	
	/**
	 * Returns a list containing all possible colors
	 * @return ArrayList<String> ChatColor format
	 */
	public static ArrayList<String> getChatColors() {
		ArrayList<String> colors = new ArrayList<String>();
		Arrays.asList(ChatColor.values()).forEach(c -> colors.add(c.name().toLowerCase())); 
		return colors;
	}
	
	
	/**
	 * Returns the material form (color) of this color
	 * 
	 * @param color
	 * @return material (wool)
	 */
	public static Material chatColorToWoolMaterial(String color) {
		switch(color) {
		case "white":
			return Material.WHITE_WOOL;
		case "gold":
			return Material.ORANGE_WOOL;
		case "dark_red":
			return Material.MAGENTA_WOOL;
		case "aqua":
			return Material.LIGHT_BLUE_WOOL;
		case "yellow":
			return Material.YELLOW_WOOL;
		case "green":
			return Material.LIME_WOOL;
		case "light_purple":
			return Material.PINK_WOOL;
		case "dark_gray":
			return Material.GRAY_WOOL;
		case "gray":
			return Material.LIGHT_GRAY_WOOL;
		case "dark_aqua":
			return Material.CYAN_WOOL;
		case "dark_purple":
			return Material.PURPLE_WOOL;
		case "dark_blue":
			return Material.BLUE_WOOL;
		case "brown":
			return Material.BROWN_WOOL;
		case "dark_green":
			return Material.GREEN_WOOL;
		case "red":
			return Material.RED_WOOL;
		case "black":
			return Material.BLACK_WOOL;
		}
		return Material.WHITE_WOOL;
	}
	
}
