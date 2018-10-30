package fr.HtSTeam.HtS.Utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.WriteAbortedException;
import java.util.Calendar;

import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Utils.Files.GZipFile;

public class Logger implements Closeable {
	private final static Calendar calendar = Calendar.getInstance();

	private final static String INIT_MESSAGE = "Starting Logger";
	private final static String CLOSING_MESSAGE = "Closing Logger";

	private final static String SEPARATOR = "> ";

	private static enum LogType {
		SYSTEM("SYSTEM"),

		INFO("INFO"), WARN("WARN"), ERROR("ERROR");

		public final String prefix;

		private LogType(String prefix) {
			this.prefix = " " + prefix + " ";
		}
	}

	private final JavaPlugin plugin;

	private final File logFile;
	private final PrintWriter writer;

	private boolean open = true;

	public Logger(File logFile, JavaPlugin plugin) throws IOException {
		this.plugin = plugin;
		this.logFile = logFile;
		
		if (!logFile.getParentFile().exists()) {
			logFile.getParentFile().mkdirs();
		} else if (logFile.exists()) {
			GZipFile gZip = new GZipFile(logFile.getAbsolutePath(), logFile.getParent() + "/" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
			gZip.gzipIt();
		}

		this.writer = new PrintWriter(new FileWriter(logFile, false));
		this.writer.println(INIT_MESSAGE);
	}

	private void log(LogType type, String message) {
		try {
			this.log(type, message.split("\n"));
		} catch (IOException e) {
			this.forceLog(e);
		}
	}

	private void log(LogType type, String[] message) throws IOException {
		if (!this.open)
			throw new WriteAbortedException("This logger is already closed", null);

		String date = "[" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "]";

		for (String s : message) {
			System.out.println("[HtS] " + s);
			this.writer.println(date + type.prefix + SEPARATOR + s);
		}
		this.writer.flush();
	}

	@Deprecated
	public void forceLog(Exception exception) {
		try (Logger logger = new Logger(this.logFile, this.plugin)) {
			logger.log(LogType.SYSTEM, exception.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		this.writer.println(CLOSING_MESSAGE);
		this.writer.close();
	}

	public void logInfo(String message) {
		this.log(LogType.INFO, message);
	}

	public void logWarning(String message) {
		this.log(LogType.WARN, message);
	}

	public void logError(String message) {
		this.log(LogType.ERROR, message);
	}

	public void logError(Exception exception) {
		this.log(LogType.ERROR, exception.getMessage());
	}
}
