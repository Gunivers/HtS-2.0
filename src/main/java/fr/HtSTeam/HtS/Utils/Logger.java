package fr.HtSTeam.HtS.Utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.WriteAbortedException;
import java.util.Calendar;

import fr.HtSTeam.HtS.Utils.Files.GZipFile;

public class Logger implements Closeable
{	
	private final static Calendar calendar = Calendar.getInstance();

	private final static String INIT_MESSAGE = "Starting Logger";
	private final static String CLOSING_MESSAGE = "Closing Logger";

	private final static String SEPARATOR = "> ";

	private static enum LogType
	{
		SYSTEM("SYSTEM"), FATAL_ERROR("FATAL"),
		INFO("INFO"), WARN("WARN"), ERROR("ERROR");

		public final String prefix;
		private LogType(String prefix) { this.prefix = " " + prefix + " "; }
	}

	private final File logFile;
	private PrintWriter writer = null;

	private boolean open = true;

	public Logger(File logFile)
	{
		if (!logFile.getParentFile().exists() && !logFile.getParentFile().mkdirs()) throw new FileNotCreatedException("Could not create the log directory");
		
		try { if (!logFile.exists() && !logFile.createNewFile()) throw new FileNotCreatedException("Could not create the log file"); }
		catch (IOException e) { throw new FileNotCreatedException("Could not create the log file"); }
		
		this.logFile = logFile;
		
		GZipFile gZip = new GZipFile(logFile.getAbsolutePath(), logFile.getParent() + "/" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
		gZip.gzipIt();

		try 
		{
			this.writer = new PrintWriter(new FileWriter(logFile, false));
			this.writer.println(INIT_MESSAGE);
		} catch (IOException e)
		{
			e.printStackTrace();
			this.open = false;
		}
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

	private void log(LogType type, String[] message) throws IOException
	{
		if (!this.open)
			throw new WriteAbortedException("This logger is already closed!", null);

		String date = "["+ calendar.get(Calendar.HOUR_OF_DAY) +':'+ calendar.get(Calendar.MINUTE) +':'+ calendar.get(Calendar.SECOND) +"]";

		for (String s : message)
		{
			System.out.println("§6[HtS] §4" + type.prefix + SEPARATOR + "§2" + s);
			this.writer.println(date + type.prefix + SEPARATOR + s);
		}
		
		this.writer.flush();
	}

	@Deprecated
	public void forceLog(Exception exception)
	{
		try (Logger logger = new Logger(this.logFile);)
		{
			logger.log(LogType.SYSTEM, exception.getMessage().split("\n"));
		} catch (IOException e)
		{
			e.printStackTrace();
			this.close();
		}
	}

	@Override
	public void close()
	{
		this.writer.println(CLOSING_MESSAGE);
		this.writer.close();
	}

	public void logInfo(String message)
	{
		this.log(LogType.INFO, message);
	}

	public void logWarning(String message)
	{
		this.log(LogType.WARN, message);
	}

	public void logError(String message)
	{
		this.log(LogType.ERROR, message);
	}

	public void logError(Exception exception)
	{
		this.log(LogType.ERROR, exception.getMessage());
	}
	
	public boolean isOpen()
	{
		return open;
	}
}

class FileNotCreatedException extends RuntimeException
{
	private static final long serialVersionUID = 2349246904028378357L;
	
	public FileNotCreatedException(String msg) { super(msg); }
}
