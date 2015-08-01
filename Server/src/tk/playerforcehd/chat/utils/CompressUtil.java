package tk.playerforcehd.chat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import tk.playerforcehd.chat.exception.manager.GZIPCompressUtilErrorManager;
import tk.playerforcehd.chat.start.Main;

public class CompressUtil {

	/**
	 * Throws the errors if anything went wrong
	 */
	private GZIPCompressUtilErrorManager gzipCompressUtilErrorManager;
	
	public CompressUtil() {
		this.gzipCompressUtilErrorManager = new GZIPCompressUtilErrorManager(Main.getMainInstance());
	}

	/**
	 * Compress a file into a gz file
	 * @param file this file get zipped
	 * @param delete chooses fi delete the file after zipping
	 */
	public void compress(File file, boolean delete){
		try{
		int read = 0;
	    byte[] data = new byte[1024];
	    FileInputStream fileInputStream = null;
		fileInputStream = new FileInputStream(file);
	    GZIPOutputStream gzipOutputStream = null; 
		gzipOutputStream = new GZIPOutputStream(new FileOutputStream(file.getPath()+".gz"));
		while((read = fileInputStream.read(data, 0, 1024)) != -1) gzipOutputStream.write(data, 0, read);
		fileInputStream.close();
	    gzipOutputStream.close();
        if(delete) file.delete();
		} catch (Exception e){
			gzipCompressUtilErrorManager.throwException(e, false);
		}
	}
	
	/**
	 * Unzipping a specified file
	 * @param file the file which get unzipped
	 * @param delete choose if you like to delete the old file
	 */
	public void uncompress(File file, boolean delete){
		try {
		    int read = 0;
		    byte[] data = new byte[1024];
			GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(file));
			String name;
			if (file.getName().endsWith(".gz")) name = file.getName().substring(0, file.getName().length()-3);
			else name = file.getName();
			File unzippedFile = new File(file.getPath(), name);
			FileOutputStream outputStream = new FileOutputStream(unzippedFile);
			while((read = gzipInputStream.read(data, 0, 1024)) != -1) outputStream.write(data, 0, read);
			gzipInputStream.close();
			outputStream.close();
			if(delete){
				file.delete();
			}
		} catch (Exception e){
			gzipCompressUtilErrorManager.throwException(e, false);
		}
	}
}
