package tk.playerforcehd.chat.config.properties;

import java.util.Properties;

public interface IPropertie {

	public abstract void load();
	
	public abstract void save(String comment);
	
	public abstract void write(String path, Object value);
	
	public abstract Object get(String path);
	
	public abstract Properties getProperties();
	
}
