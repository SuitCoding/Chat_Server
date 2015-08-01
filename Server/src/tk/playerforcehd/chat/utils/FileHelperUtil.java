package tk.playerforcehd.chat.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.manager.IErrorManager;
import tk.playerforcehd.chat.start.Main;

public class FileHelperUtil {

	private FileHelperManager fileHelperManager;
	
	public FileHelperUtil(){
		this.fileHelperManager = new FileHelperManager(Main.getMainInstance());
	}
	
	/**
	 * Copy a folder to another location
	 * @param from The folder to copy
	 * @param to Where the folder/files get copied
	 * @throws FileNotFoundException 
	 * @throws IOException
	 */
    public void copyDir(File from, File to) {
    	try {
    	File[] files = from.listFiles();
        File newFile = null;
        to.mkdirs();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                newFile = new File(to.getAbsolutePath() + System.getProperty("file.separator") + files[i].getName());
                if (files[i].isDirectory()) {
                    copyDir(files[i], newFile);
                }
                else {
                    copyFile(files[i], newFile);
                }
            }
        }
    	} catch (Exception e){
    		fileHelperManager.throwException(e, false);
    	}
    }
    
    public void copyFile(File file, File to) {
        try {
    	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(to, true));
        int bytes = 0;
        while ((bytes = in.read()) != -1) {
            out.write(bytes);
        }
        in.close();
        out.close();
        } catch (Exception e){
        	fileHelperManager.throwException(e, false);
        }
    }

    public void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDir(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
            dir.delete();
        }
    }
    
    public ArrayList<File> searchFile(File dir, String find) {
        File[] files = dir.listFiles();
        ArrayList<File> matches = new ArrayList<File> ();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().equalsIgnoreCase(find)) { 
                    matches.add(files[i]);
                }
                if (files[i].isDirectory()) {
                    matches.addAll(searchFile(files[i], find)); 
                }
            }
        }
        return matches;
    }
    
    public File[] listDir(File dir) {
    	File[] files = dir.listFiles();
        if (files != null) return files; else return null;
    }
    
    public long getDirSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    size += getDirSize(files[i]);
                }
                else {
                    size += files[i].length();
                }
            }
        }
        return size;
    }
}

class FileHelperException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FileHelperException() {}

	public FileHelperException(String arg0) {
		super(arg0);
	}

	public FileHelperException(Throwable arg0) {
		super(arg0);
	}

	public FileHelperException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileHelperException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
	
}

class FileHelperManager implements IErrorManager {

private Main mainInstance;
	
	public FileHelperManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by using the FileHelper");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the FileHelper:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown FileHelperException (Pointing to this)");
		try {
			throw new FileHelperException("Anything is has going wrong by using the FileHandler");
		} catch (FileHelperException e) {
			mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(e));
		}
		mainInstance.getLogger().warning("------------------------------------------------------------");
		if(isCritical){
			mainInstance.shutdown();
		}
	}

	@Getter
	private Main getMainInstance() {
		return mainInstance;
	}
	
}