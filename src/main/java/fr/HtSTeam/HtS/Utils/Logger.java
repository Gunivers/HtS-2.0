package fr.HtSTeam.HtS.Utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.WriteAbortedException;
import java.util.Calendar;
import java.util.logging.Level;
import fr.HtSTeam.HtS.Utils.Files.GZipFile;

public class Logger implements Closeable
{
	private final static Calendar calendar = Calendar.getInstance();

	private final static String INIT_MESSAGE = "Starting Logger";
	private final static String CLOSING_MESSAGE = "Closing Logger";

	private final static String SEPARATOR = "> ";

	private static enum LogType
	{
		SYSTEM("SYSTEM"), FATAL_ERROR("FATAL "),
		INFO("INFO  "), WARN("WARN  "), ERROR("ERROR ");

		public final String prefix;
		private LogType(String prefix) { this.prefix = ' '+ prefix +' '; }
	}

	private final File logFile;
	private final PrintWriter writer;
	private final java.util.logging.Logger base;

	private boolean open = true;

	/**
	 * If the logFile does not exist, it will be created in a zip corresponding to its parent. <br \>
	 * There should not be any thrown at this point, else reconsider the file you choosed and check if there's not already a Logger on this file.
	 * <p>
	 * The logs will also be sended to the base {@link java.util.logging.Logger}, if it is not null.
	 * 
	 * @param logFile
	 *               a {@link java.io.File}
	 * @param base
	 *            a {@link java.util.logging.Logger}
	 * @throws IOThrowable.
	 *                     If there's an thrown here, you should reconsider your file choice.
	 */
	public Logger(File logFile, java.util.logging.Logger base) throws IOException
	{
		if (!logFile.exists())
			logFile.createNewFile();
		
		this.logFile = logFile;
		this.base = base;
		
		if (logFile.getParentFile() != null && !logFile.getParentFile().exists())
		{
			logFile.getParentFile().mkdirs();
		} else
		{
			GZipFile gZip = new GZipFile(logFile.getAbsolutePath(), logFile.getParent() +"/"+ calendar.get(Calendar.YEAR) +"-"+ calendar.get(Calendar.MONTH) +"-"+ calendar.get(Calendar.DAY_OF_MONTH));
			gZip.gzipIt();
		}

		this.writer = new PrintWriter(new FileWriter(logFile, false));
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
		
		message = "[HTS] " + message;
		
		if (base != null)
		{
			switch (type)
			{
				case INFO: base.info(message);
					break;
				
				case WARN: base.warning(message);
					break;
					
				case ERROR: base.log(Level.FINER, message);	
					break;
					
				case FATAL_ERROR: base.log(Level.FINE, message);
					break;
					
				case SYSTEM: base.log(Level.CONFIG, message);
				
				default: break;
			}
		}
	}

	@Deprecated
	private void log(LogType type, String[] message) throws IOException
	{
		if (!this.open)
			throw new WriteAbortedException("This logger is already closed", new IOException());

		String date = "["+ calendar.get(Calendar.HOUR_OF_DAY) +":"+ calendar.get(Calendar.MINUTE) +":"+ calendar.get(Calendar.SECOND) + "]";

		for (String s : message)
		{
			if (this.base == null) System.out.println("[HtS]" + type.prefix + SEPARATOR + s);
			this.writer.println(date + type.prefix + SEPARATOR + s);
		}
		
		this.writer.flush();
	}

	/**
	 * This method is only to use in extreme cases, such as a {@link Logger} crash, or an {@link IOException} caught when logging. <br \>
	 * In that logic, the {@code thrown} is recognized as a fatal error, and logged with that name.
	 * <p>
	 * It creates a new temporary logger on the same file, hoping that the report won't fail.
	 * <p>
	 * <strong>FAILURE CASE</strong> <br \>
	 * The logger will automatically close itself in order to avoid further trouble
	 *                 
	 * @see fr.HtSTeam.HtS.Utils.Logger#log
	 */
	@Deprecated
	public boolean forceLog(Throwable thrown)
	{
		try (Logger logger = new Logger(this.logFile, this.base))
		{
			logger.log(LogType.FATAL_ERROR, thrown.getMessage().split("\n"));
			if (base != null) base.log(Level.FINER, "[HTS]", thrown);
			
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			this.close();
			
			return false;
		}
	}

	@Override
	public void close()
	{
		this.log(LogType.SYSTEM, CLOSING_MESSAGE);
		this.writer.close();
	}

	/**
	 * @see Logger#log
	 */
	public void logInfo(String message) { this.log(LogType.INFO, message); }
	/**
	 * @see Logger#log
	 */
	public void logWarning(String message) { this.log(LogType.WARN, message); }
	/**
	 * @see Logger#log
	 */
	public void logError(String message) { this.log(LogType.ERROR, message); }
	

	/**
	 * @see Logger#log
	 */
	public void logError(Throwable thrown)
	{
		try
		{
			this.log(LogType.ERROR, thrown.getMessage().split("\n"));
			if (base != null) this.base.log(Level.FINE, "[HTS]", thrown);
		} catch (IOException e)
		{
			this.forceLog(e);
		}
	}
	
	public boolean isOpen() { return open; }
}