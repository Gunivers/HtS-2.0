package fr.HtSTeam.test.Utils;

import java.io.File;
import java.io.IOException;

import fr.HtSTeam.HtS.Utils.Logger;

public class LoggerTest
{
	public static void main(String[] args)
	{
		File file = new File("src/test/resources/");
		
		try (Logger log = new Logger(file, null /*Main.plugin.getLogger()*/);)
		{
			log.logInfo("Info test");
			log.logWarning("Warning test");
			log.logError("Exception test");
			
			log.logError(new Exception("Throwable test"));
			//log.forceLog(new Exception("forceLog test"));
			
			log.logInfo("State after forceLog: " + (log.isOpen() ? "open" : "closed"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}