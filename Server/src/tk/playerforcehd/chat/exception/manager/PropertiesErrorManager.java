package tk.playerforcehd.chat.exception.manager;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.PropertieHandleException;
import tk.playerforcehd.chat.start.Main;

/**
 * When a error happens by handle anything with a properties file, this manager will handle the stacktrace
 */
public class PropertiesErrorManager implements IErrorManager {

	private Main mainInstance;
	
	public PropertiesErrorManager(Main mainInstance) {
		this.mainInstance = mainInstance;
	}

	@Override
	public void throwException(Exception exception, boolean isCritical) {
		mainInstance.getLogger().warning("A error has been throwed by do anything with a propertie configuration!");
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("Error from the Properties class:");
		mainInstance.getLogger().warning(Main.getMainInstance().convertExceptionToString(exception));
		mainInstance.getLogger().warning("------------------------------------------------------------");
		mainInstance.getLogger().warning("The thrown PropertieHandleException (Pointing to this)");
		try {
			throw new PropertieHandleException("Anything is has going wrong by handling anything in a propertie configuration...");
		} catch (PropertieHandleException e) {
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
