package fr.HtSTeam.HtS.Utils.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.FilenameUtils;

public class GZipFile {
	
	private String source_file;
	private String ext;
	private String output_gzip_file;
	
	/**
	 * Prepares to GZip the source file, will automaticaly increment if destination already exists.
	 * @param source_file
	 * @param output_gzip_file
	 */
	public GZipFile(String source_file, String output_gzip_file) {
		this.source_file = source_file;
		ext = "." + FilenameUtils.getExtension(source_file) + ".gz";
		int i = 0;
		while(new File(output_gzip_file + "-" + i + ext).exists())
			i++;
		this.output_gzip_file = output_gzip_file + "-" + i;
	}
	
	/**
	 * Creates the GZip file.
	 */
	public void gzipIt(){
		byte[] buffer = new byte[1024];
		try {
			GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(output_gzip_file + ext));
			FileInputStream in = new FileInputStream(source_file);
			int len;
			while ((len = in.read(buffer)) > 0)
				gzos.write(buffer, 0, len);
			
			in.close();
			gzos.finish();
			gzos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}