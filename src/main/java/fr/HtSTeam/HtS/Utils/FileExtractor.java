package fr.HtSTeam.HtS.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileExtractor {
	
	public static String wdir;
	public static final String lt = "main/resources/loot_tables/";
	public static final String cr = "main/resources/crafts/";
	public static final String Edir = "/minecraft/entities/";
	public static final String Gdir = "/minecraft/gameplay/";
	public static final String Rdir = "/minecraft/recipes/";
	public static final String Cdir = "/customs/";
	
	public static void extractFile(String name, String folderPath) throws IOException, URISyntaxException
    {	
		String serverPath = FileExtractor.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().split("plugins")[0].split("/")[1];
		String path = serverPath + folderPath;
		if(!Files.exists(Paths.get(path)))
			new File(path).mkdirs();
		
        File target = new File(path + name.split("/")[1]);
        if (target.exists())
            return;

        FileOutputStream out = new FileOutputStream(target);
        ClassLoader cl = FileExtractor.class.getClassLoader();
        InputStream in = cl.getResourceAsStream(name);

        byte[] buf = new byte[8*1024];
        int len;
        while((len = in.read(buf)) != -1)
        {
            out.write(buf,0,len);
        }
        out.close();
        in.close();
    }
}
