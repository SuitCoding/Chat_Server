package tk.playerforcehd.chat.config.mysql.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.config.mysql.MySQL;
import tk.playerforcehd.chat.exception.manager.DatabaseErrorManager;
import tk.playerforcehd.chat.start.Main;

public class UserTable {

	/**
	 * The MySQL instance
	 */
	private MySQL mySQL;
	
	public UserTable() {
		this.mySQL = Main.getMainInstance().getMySQL();
	}

	/**
	 * Checks the connection, if it is connected it returns true
	 * @return the connection state
	 */
	private boolean isConnected(){
		return mySQL.isConnected();
	}
	
	/**
	 * Reconnects to the Database
	 */
	private void reconnect(){
		mySQL.reconnect();
	}
	
	private DatabaseErrorManager getDatabaseErrorManager(){
		return MySQL.getMysqlInstance().getDatabaseErrorManager();
	}
	
	@Getter
	private Connection getConnection(){
		return mySQL.getConnection();
	}
	
	private void createTable(){
		if(isConnected()){
			try {
			PreparedStatement ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS User (CID INT(255), UUID VARCHAR(100). CNAME VARCHAR(20), UNAME VARCHAR(20), BANNED INT(1))");
			} catch (Exception e){
				getDatabaseErrorManager().throwException(e, false);
			}
		} else {
			reconnect();
			createTable();
		}
	}
	
}
