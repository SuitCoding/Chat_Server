package tk.playerforcehd.chat.exception.manager;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.GZipCompressionException;
import tk.playerforcehd.chat.start.Main;

/**
 * When a error happens by Zipping a file, this manager will handle the stacktrace
 */
public class GZIPCompressUtilErrorManager implements IErrorManager {

	private Main mainInstance;
	
	public GZIPCompressUtilErrorManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by compressing a file!");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the method call:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown GZipCompressionException (Pointing to this)");
		try {
			throw new GZipCompressionException("Anything is has going wrong by compressing a file");
		} catch (GZipCompressionException e) {
			mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(e));
		}
		mainInstance.getLogger().warning("------------------------------------------------------------");
		if(isCritical){
			Main.getMainInstance().shutdown();
		}
	}

	@Getter
	private Main getMainInstance() {
		return mainInstance;
	}
	
}
