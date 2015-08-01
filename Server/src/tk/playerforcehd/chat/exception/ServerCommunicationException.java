package tk.playerforcehd.chat.exception;

public class ServerCommunicationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServerCommunicationException() {}

	public ServerCommunicationException(String arg0) {
		super(arg0);
	}

	public ServerCommunicationException(Throwable arg0) {
		super(arg0);
	}

	public ServerCommunicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServerCommunicationException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
