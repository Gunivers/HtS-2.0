package fr.HtSTeam.HtS.Utils;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.bukkit.plugin.java.JavaPlugin;

public class Logger implements Closeable
{
	private final static GregorianCalendar date = new GregorianCalendar();
	
	private final static String INIT_MESSAGE = "Starting Logger";
	private final static String CLOSING_MESSAGE = "Closing Logger";
	
	private final static char SEPARATOR = '>';
	private final static String TRANSITION = "\n──────────────────────────────────────────────────\n";
	
	private static enum LogType
	{
		SYSTEM("SYSTEM"),
		
		INFO("INFO"),
		WARN("WARN"),
		ERROR("ERROR");
		
		public final String prefix;
		
		private LogType(String prefix) { this.prefix = " "+ prefix +"\t";}
	}
	
	private final JavaPlugin plugin;
	
	private final File log;
	private final DataOutputStream writer;

	private boolean open = true;
	
	
	public Logger(File log, JavaPlugin plugin) throws IOException
	{
		this.plugin = plugin;
		this.log = log;
		
		this.writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(log)));
		this.writer.writeChars(TRANSITION + INIT_MESSAGE);
	}
	
	
	private void log(LogType type, String message)
	{
		try
		{
			this.log(type, message.split("\n"));
		} catch (IOException e)
		{
			this.forceLog(e);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void log(LogType type, String[] message) throws IOException
	{
		if(!this.open)
			throw new WriteAbortedException("This logger is already closed", null);
		
		Date time = Logger.date.getTime();
		String date = '['+ time.getHours() +":"+ time.getMinutes() +":"+ time.getSeconds() +']';
		
		for (String s : message)
		{
			this.writer.writeChars(date + type.prefix + SEPARATOR + s);
		}
	}
	
	@Deprecated
	public void forceLog(Exception exception)
	{
		try (Logger logger = new Logger(this.log, this.plugin))
		{
			logger.log(LogType.SYSTEM, exception.getMessage());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException
	{
		this.writer.writeChars(TRANSITION + CLOSING_MESSAGE);
		this.writer.close();
	}
	
	public void logInfo(String message) { this.log(LogType.INFO, message); }
	public void logWarning(String message) { this.log(LogType.WARN, message); }
	public void logError(String message) { this.log(LogType.ERROR, message); }
	public void logError(Exception exception) { this.log(LogType.ERROR, exception.getMessage()); }
}
