package fr.HtSTeam.HtS.Commands.Structure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Player.Player;

public class Command implements TabExecutor {
		
	private HashMap<String,Method> commandMethods = new HashMap<String,Method>();
	private HashMap<String,Method> completeMethods = new HashMap<String,Method>();

	public Command() {
		System.out.println("[HtS] Registering commands...");
		ArrayList<Method> annoted_methods = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("fr.HtSTeam.HtS")).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(CommandHandler.class));
		annoted_methods.forEach(m -> { if(m.getAnnotation(CommandHandler.class).value().equals(EnumCommand.EXECUTE) && m.getParameterCount() == 4) commandMethods.put(m.getName(), m); else if(m.getAnnotation(CommandHandler.class).value().equals(EnumCommand.COMPLETE) && m.getParameterCount() == 0) completeMethods.put(m.getName(), m);});
		commandMethods.keySet().forEach(name -> Main.plugin.getCommand(name).setExecutor(this));
		System.out.println("[HtS] " + commandMethods.keySet().size() + " commands registered!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(sender instanceof org.bukkit.entity.Player && commandMethods.containsKey(cmd.getName()))
			try { return (boolean) commandMethods.get(cmd.getName()).invoke(null, Player.instance((org.bukkit.entity.Player) sender), cmd, label, args); } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {	e.printStackTrace(); }
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] args) {
		if(sender instanceof org.bukkit.entity.Player && completeMethods.containsKey(cmd.getName())) {
			try {
				HashMap<String,ArrayList<String>> prop = (HashMap<String,ArrayList<String>>) completeMethods.get(cmd.getName()).invoke(null);
				if (prop.isEmpty())
					return null;
				List<String> list = new ArrayList<String>();
				if (args.length > 1) {
					String key = "";
					for (int i = 0; i < args.length - 1; i++) key += args[i];
					for (String regex : prop.keySet()) if (regex != null && key.matches(regex)) { key = regex; break; }
					
					if (key == null || !prop.containsKey(key) || key.isEmpty())
						return null;
					else if (!args[args.length - 1].isEmpty())
						prop.get(key).forEach(string -> { if (string.startsWith(args[args.length - 1])) list.add(string); });
					else
						list.addAll(prop.get(key));
				} else if (args.length == 1)
					if (!args[0].isEmpty())
						prop.get(null).forEach(string -> { if (string.startsWith(args[0])) list.add(string); });
					else
						list.addAll(prop.get(null));
				else
					return null;
				Collections.sort(list);
				return list;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}