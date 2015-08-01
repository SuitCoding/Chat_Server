package tk.playerforcehd.chat.start;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.FileHandler;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.utils.CompressUtil;

public class FileLogger {

	/**
	 * The mainInstance of the Programm
	 */
	private Main mainInstance;
	
	/**
	 * This handler writes the log into a file
	 */
	private FileHandler fileHandler;
	
	/**
	 * Needed to handle the fileName
	 */
	protected final Date date = new Date();
	
	/**
	 * Needed to hanle the fileName
	 */
	protected final DateFormat dateformat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
	
	/**
	 * Creates a new instance of this
	 * @param mainInstance needed to get the registred logger
	 */
	@SuppressWarnings("static-access")
	public FileLogger(Main mainInstance) {
		this.mainInstance = mainInstance.getMainInstance();
		zipOldLogs();
		startHandling();
	}
	
	/**
	 * Opens the FileHandler
	 */
	private void startHandling(){
		try {
			date.setTime(System.currentTimeMillis());
			fileHandler = new FileHandler(mainInstance.getJarLocation() + "/logs/"+ dateformat.format(date).replace(':', '_') +".log");
			Main.getMainInstance().getLogger().addHandler(fileHandler);
			fileHandler.setFormatter(new LogFormat());
		} catch (Exception e){
			Main.getMainInstance().throwError(e, true);
		}
	}
	
	private void zipOldLogs(){
		try {
		CompressUtil compressUtil = new CompressUtil();
		File[] oldLogs = null;
		oldLogs = new File(Main.getMainInstance().getJarLocation() + "/logs").listFiles();
		for(File toZip : oldLogs){
			if(!toZip.getName().endsWith(".gz")){
				compressUtil.compress(toZip, true);
			}
		}
		} catch (Exception e){
			Main.getMainInstance().throwError(e, false);
		}
	}
	
	@Getter
	private Main getMainInstance() {
		return mainInstance;
	}

}
