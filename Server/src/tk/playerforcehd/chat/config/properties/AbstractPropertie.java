package tk.playerforcehd.chat.config.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import tk.playerforcehd.chat.annotation.type.Getter;
import tk.playerforcehd.chat.exception.manager.PropertiesErrorManager;
import tk.playerforcehd.chat.start.Main;

/**
 * This class makes it easier to handle configurations with propertie files
 */
public abstract class AbstractPropertie implements IPropertie {

	private Properties properties;
	
	private File file;
	
	private String fileName;
	
	private FileInputStream fileInputStream;
	
	private FileOutputStream fileOutputStream;
	
	private PropertiesErrorManager propertiesErrorManager;
	
	public AbstractPropertie(String fileName) {
		this.propertiesErrorManager = new PropertiesErrorManager(Main.getMainInstance());
		this.fileName = fileName;
		this.properties = new Properties();
		this.file = new File(Main.getMainDataDir().getPath(), fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
				load();
				addDefaults();
			} catch (IOException e) {
				this.propertiesErrorManager.throwException(e, false);
			}
		}
		load();
	}
	
	@Override
	public void load() {
		try {
			this.fileInputStream = new FileInputStream(file);
			this.properties.load(this.fileInputStream);
			this.fileInputStream.close();
		} catch (IOException e) {
			propertiesErrorManager.throwException(e, false);
		}
	}

	@Override
	public void save(String comment) {
		try {
			this.fileOutputStream = new FileOutputStream(file);
			this.properties.store(this.fileOutputStream, comment);
			this.fileOutputStream.close();
		} catch (IOException e) {
			propertiesErrorManager.throwException(e, false);
		}
		load();
	}

	@Override
	public void write(String path, Object value) {
		this.properties.setProperty(path, value.toString());
	}

	@Override @Getter
	public Object get(String path) {
		return this.properties.get(path);
	}

	@Override @Getter
	public Properties getProperties() {
		return this.properties;
	}
	
	@Getter
	public String getFileName() {
		return fileName;
	}

	public abstract void addDefaults();
	
}
