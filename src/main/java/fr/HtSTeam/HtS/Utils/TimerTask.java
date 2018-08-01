package fr.HtSTeam.HtS.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.IconBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class TimerTask {

	private int time = 0;
	private int step = 0;
	private boolean pause = false;
	private boolean isActivate = false;
	private boolean stop = false;
	
	public TimerTask(int start, int step) {
		time = start;
		this.step = step; 
	}

	public void run() {
		if (!isActivate) {
			isActivate = true;
			executeTimer();
			@SuppressWarnings("unused")
			BukkitTask task = new BukkitRunnable() {
				public void run() {
					if(!pause) {
						time += step;
						if (time%60 == 0)
							executeTimer();
					}
					if (EnumState.getState().equals(EnumState.FINISHING) || time < 0 || stop)
						this.cancel();
				}
			}.runTaskTimer(Main.plugin, 20, 20);
		}
	}

	public int getTimerInMinute() {
		return time / 60;
	}

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
	
	public void inPause(boolean pause) {
		this.pause = pause;
	}
	
	public void stop() {
		stop = true;
	}
	
	private void executeTimer() {
		if(this != Main.timer)
			return;
		for (IconBuilder<?> om : IconBuilder.optionsList.keySet()) {
			System.out.println("1 : " + om.getName());
			if(!(om.getValue() instanceof Integer)) return;
			System.out.println(om.getName());
			ArrayList<Method> methods = new ArrayList<Method>();
			for (Method m : om.getClass().getMethods()) {
				if(m.isAnnotationPresent(Timer.class))
					methods.add(m);
			}
			methods.sort((o1, o2) -> o1.getAnnotation(Timer.class).value().compareTo(o2.getAnnotation(Timer.class).value()));
			for (Method m : methods) {
				try {
					if (this.getTimerInMinute() == (Integer)om.getValue())
						m.invoke(om);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
