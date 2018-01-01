package fr.HtSTeam.HtS.Utils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class Timer {

	private int time = 0;
	private int step = 0;
	private boolean pause = false;
	private boolean isActivate = false;
	private boolean stop = false;
	
	public Timer(int start, int step) {
		time = start;
		this.step = step; 
	}

	public void run() {
		if (!isActivate) {
			isActivate = true;
			@SuppressWarnings("unused")
			BukkitTask task = new BukkitRunnable() {
				public void run() {
					if(!pause) {
						time += step;
						executeTimer();
					}
					if (EnumState.getState().equals(EnumState.FINISHING) || time <= 0 || stop)
						this.cancel();
				}
			}.runTaskTimer(Main.plugin, 0, 1200);
		}
	}

	public int getTimerInMinute() {
		return time;
	}

	public String getTimeFormat() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		Date dt = null;

		try {
		    dt = sdf.parse(Integer.toString(time));
		    sdf = new SimpleDateFormat("HH:mm");
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
		
		for (OptionsManager om : OptionsManager.optionsList.keySet()) {
			for (Method m : om.getClass().getMethods()) {
				try {
					if (m.isAnnotationPresent(fr.HtSTeam.HtS.Options.Structure.Timer.class)) {
						for (Entry<OptionsManager, Object> entry : OptionsManager.optionsList.entrySet()) {
							if (entry.getValue().equals(om.getClass()))
								if (this.getTimerInMinute() == Integer.parseInt(entry.getKey().getValue()))
									m.invoke(om);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
