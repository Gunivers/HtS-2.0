package fr.HtSTeam.HtS.GameModes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class TimerTask {
	
	private static TimerTask instance;
	
	private int time = 0;
	private int step = 0;
	private boolean pause = false;
	private boolean isActivate = false;
	private boolean stop = false;
	    
	private TimerTask(int start, int step) {
		time = start;
		this.step = step;
		initMethods();
	}
	
	/**
	 * Returns the instance of TimerTask (singleton)
	 * @param start in second
	 * @param step in second
	 * @return
	 */
	public static TimerTask getInstance(int start, int step) {
		if (instance == null) {
			instance = new TimerTask(start, step);
		}
		return instance;
	}
	
	/**
	 * Starts the timer to count up
	 */
	public void run() {
		if (!isActivate) {
			isActivate = true;
			executeMethods();
			new BukkitRunnable() {
				public void run() {
					if(!pause) {
						time += step;
						executeMethods();
					}
					if (EnumState.getState().equals(EnumState.FINISHING) || time < 0 || stop)
						this.cancel();
				}
			}.runTaskTimer(Main.plugin, 20, 20);
		}
	}
	
	/**
	 * Returns the value of the Timer in second.
	 * @return int
	 */
	public int getTimerInSeconds() {
		return time;
	}
	
	/**
	 * Returns the approximate value of the Timer in minutes.
	 * @return int
	 */
	public int getTimerInMinute() {
		return time / 60;
	}
	
	/**
	 * Returns the value of the Timer in HH:mm:ss format.
	 * @return String
	 */
	public String getTimeFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		Date dt = null;
		try {
			dt = sdf.parse(Integer.toString(time));
			sdf = new SimpleDateFormat("HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(dt);
	}
	
	/**
	 * Retunrs true if the Timer is paused
	 * @param pause
	 */
	public void inPause(boolean pause) {
		this.pause = pause;
	}
	
	/**
	 * Stops the timer
	 */
	public void stop() {
		stop = true;
	}
	
	private HashMap<Option<?>, ArrayList<Method>> methods = new HashMap<Option<?>, ArrayList<Method>>();
	
	/**
	 * Fetches all annoted methods and orders them by priority.
	 */
	private void initMethods() {
		for (Option<?> ib : Option.optionsList.keySet()) {
			if (!(ib.getValue() instanceof Integer))
				continue;
			ArrayList<Method> meths = new ArrayList<Method>(new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forClass(ib.getClass())).setScanners(new MethodAnnotationsScanner())).getMethodsAnnotatedWith(Timer.class));
			meths.sort((m1, m2) -> m1.getAnnotation(Timer.class).value().compareTo(m2.getAnnotation(Timer.class).value()));
			methods.put(ib, meths);
		}
	}
	
	/**
	 * Executes every seconds method with that input time.
	 */
	private void executeMethods() {
		for (Entry<Option<?>, ArrayList<Method>> set : methods.entrySet())
			for (Method m : set.getValue())
				try {
					if (this.getTimerInSeconds() == (Integer) set.getKey().getValue())
						m.invoke(set.getKey());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					Main.LOGGER.logError(e);
					continue;
				}
	}
}
