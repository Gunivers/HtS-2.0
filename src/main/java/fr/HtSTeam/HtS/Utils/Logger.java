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
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class Logger implements Closeable
{
	private final static GregorianCalendar date = new GregorianCalendar();
	
	private final static String INIT_MESSAGE = "Starting Logger";
	private final static String CLOSING_MESSAGE = "Closing Logger";
	
	private final static char SEPARATOR = '>';
	
	private static enum LogType
	{
		SYSTEM("SYSTEM"),
		
		INFO("INFO"),
		WARN("WARN"),
		ERROR("ERROR"),
		
		FATAL_ERROR("FATAL");
		
		public final String prefix;
		
		private LogType(String prefix) { this.prefix = " "+ prefix +"\t";}
	}
	
	private final JavaPlugin plugin;
	
	private final File log;
	private final DataOutputStream writer;

	private boolean open = true;
	
	
	public Logger(File log, JavaPlugin plugin)
	{
		this.plugin = plugin;
		this.log = log;
		
		DataOutputStream writer = null;
		
		try
		{
			if (!log.exists())
				log.createNewFile();
			
			writer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(log)));
		}
		catch (IOException e)
		{
			this.open = false;
		}
		
		this.writer = writer;
		this.log(LogType.SYSTEM, INIT_MESSAGE);
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
		
		switch (type)
		{
			case ERROR: plugin.getLogger().log(Level.FINE, message);
				break;
				
			case FATAL_ERROR: plugin.getLogger().log(Level.FINER, message);
				break;
				
			case INFO: plugin.getLogger().info(message);
				break;
				
			case SYSTEM: plugin.getLogger().log(Level.CONFIG, message);
				break;
				
			case WARN: plugin.getLogger().warning(message);
				break;
		}
	}
	
	@Deprecated
	private void log(LogType type, String[] message) throws IOException
	{
		if(!this.open)
			throw new WriteAbortedException("This logger is already closed", new IOException());
		
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
		Logger log = new Logger(this.log, this.plugin);
		log.log(LogType.FATAL_ERROR, exception.getMessage());
		log.close();
	}

	@Override
	public void close()
	{
		this.log(LogType.SYSTEM, CLOSING_MESSAGE);
		
		try
		{
			this.writer.close();
		} catch (IOException e)
		{
			this.forceLog(e);
		}
		
		this.open = false;
	}
	
	public void logInfo(String message)		  { this.log(LogType.INFO, message); }
	public void logWarning(String message)	  { this.log(LogType.WARN, message); }
	public void logError(String message)	  { this.log(LogType.ERROR, message); }
	public void logError(Exception exception) { this.log(LogType.ERROR, exception.getMessage()); }
}
