package tk.playerforcehd.chat.exception.manager;

public interface IErrorManager {

	abstract public void throwException(Exception exception, boolean isCritical);
	
}
