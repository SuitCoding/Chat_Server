package tk.playerforcehd.chat.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.config.mysql.MySQL;
import tk.playerforcehd.chat.connection.client.ClientConnection;
import tk.playerforcehd.chat.exception.manager.ServerCommErrManager;
import tk.playerforcehd.chat.start.Main;

public class MainServerSocket {

	/**
	 * The mainInstance of the programme
	 */
	private Main mainInstance;
	
	/**
	 * The instance of the MySQL connection
	 */
	private MySQL mySQL;
	
	/**
	 * The ServerSocket which recieves every client
	 */
	protected ServerSocket serverSocket;
	
	/**
	 * Listening to new client requests
	 */
	protected Thread serverThread;
	
	/**
	 * A Exception Manager to print stacktraces
	 */
	protected static ServerCommErrManager serverCommErrManager;
	
	public MainServerSocket() {
		mainInstance = Main.getMainInstance();
		mySQL = mainInstance.getMySQL();
		startConnectionListener();
	}

	/**
	 * Starts the ServerSocket which communicates with the clients
	 */
	private void startConnectionListener(){
		try {
			mainInstance.getLogger().info("Starting ServerSocket...");
			this.serverSocket = new ServerSocket(Integer.parseInt(Main.getMainInstance().getServer_properties().get("server_port").toString()));
			this.serverThread = new Thread(new ServerThread(this.serverSocket));
			this.serverThread.setName("MainServerThread");
			this.serverThread.start();
			mainInstance.getLogger().info("ServerSocket has started and is now listening to: " + Main.getMainInstance().getServer_properties().get("server_port").toString());
		} catch (Exception e) {
			serverCommErrManager.throwException(e, true);
		}
	}
	
	public void shutdownServerThread(){
		try {
			this.serverThread.join(1);
		} catch (InterruptedException e) {
		}
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			serverCommErrManager.throwException(e, true);
		}
	}
	
	@Getter
	private Main getMainInstance() {
		return mainInstance;
	}

	@Getter
	private MySQL getMySQL() {
		return mySQL;
	}

	private class ServerThread implements Runnable {

		/**
		 * The ServerSocket which get listened
		 */
		private ServerSocket serverSocket;
		
		public ServerThread(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					Socket clientConnection = serverSocket.accept();
					ClientConnection cliConnection = new ClientConnection(clientConnection);
					Main.getMainInstance().addClientConnection(cliConnection);
				} catch (Exception e) {
					MainServerSocket.serverCommErrManager.throwException(e, false);
				}
				
			}
		}

		@Getter
		protected ServerSocket getServerSocket() {
			return serverSocket;
		}
		
	}
	
}
