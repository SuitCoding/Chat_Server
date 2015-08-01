package tk.playerforcehd.chat.connection.client;

import java.net.Socket;

import tk.playerforcehd.chat.annotation.type.Getter;

public class ClientConnection implements IClientConnection {

	/**
	 * The client socket which is needed for the communication
	 */
	private Socket clientConnection;
	
	/**
	 * The thread which is listen to the client - server communication
	 */
	private Thread thread;
	
	/**
	 * This instance
	 */
	private ClientConnection clientConnectionInstance;
	
	public ClientConnection(Socket clientSocket) {
		this.clientConnectionInstance = this;
		this.clientConnection = clientSocket;
		this.thread = new Thread(new ClientConnectionRunnable(this.clientConnectionInstance));
		this.thread.start();
	}

	@Override
	public void kick(String reason) {
		
	}

	@Getter
	private Socket getClientConnection() {
		return clientConnection;
	}
	
	private class ClientConnectionRunnable implements Runnable {

		public ClientConnection clientConnection;
		
		public ClientConnectionRunnable(ClientConnection clientConnection) {
			this.clientConnection = clientConnection;
		}
		
		@Override
		public void run() {
			
		}

		@Getter
		private ClientConnection getClientConnection() {
			return clientConnection;
		}
		
	}
	
}
