package fr.HtSTeam.HtS.Utils.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.HtSTeam.HtS.Main;

public class FileExtractor {
	
	public static String wdir;
	public static final String lt = "/loot_tables/";
	public static final String cr = "/crafts/";
	public static final String Edir = "/minecraft/entities/";
	public static final String Gdir = "/minecraft/gameplay/";
	public static final String Rdir = "/minecraft/recipes/";
	public static final String Cdir = "/customs/";
	
	/**
	 * Extracts the file from resources folder
	 * @param name path to extract file
	 * @param folderPath path to resources file
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void extractFile(String name, String folderPath) throws IOException, URISyntaxException {	
		String path = Main.plugin.getDataFolder() + folderPath;
		if(!Files.exists(Paths.get(path)))
			new File(path).mkdirs();
		
        File target = new File(path + name.split("/")[1]);
        if (target.exists())
			target.delete();

        FileOutputStream out = new FileOutputStream(target);
        ClassLoader cl = FileExtractor.class.getClassLoader();
        InputStream in = cl.getResourceAsStream(name);

        byte[] buf = new byte[8*1024];
        int len;
        while((len = in.read(buf)) != -1)
            out.write(buf,0,len);
        out.close();
        in.close();
    }
}
