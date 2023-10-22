package tv.tirco.template.spigotplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin {
	
	// File Manager setup bulk
	File mainFile;
	static String mainDirectory;
	static String userFileDirectory;
	static String usersFile;
	
	public static Plugin plugin;
	public static SpigotPlugin main;
    
    @Override
    public void onEnable() {
	this.plugin = this;
	this.main = this;
        // Don't log enabling, Spigot does that for you automatically!
	loadConfig();

        // Commands enabled with following method must have entries in plugin.yml
        getCommand("example").setExecutor(new ExampleCommand(this));
    }
    
    @Override
    public void onDisable() {
        // Don't log disabling, Spigot does that for you automatically!
    }
    
        
	public InputStreamReader getResourceAsReader(String fileName) {
		InputStream in = getResource(fileName);
		return in == null ? null : new InputStreamReader(in, Charsets.UTF_8);
	}
	
	private void loadConfig() {
		Config.getInstance();
	}
	
	public static String getMainDirectory() {
		return mainDirectory;
	}

	public static String getFlatFileDirectory() {
		return userFileDirectory;
	}

	public static String getUsersFilePath() {
		return usersFile;
	}
	
	private void setupFilePaths() {
		mainFile = getFile();
		mainDirectory = getDataFolder().getPath() + File.separator;
		userFileDirectory = mainDirectory + "userFiles" + File.separator;
		usersFile = userFileDirectory + "Users";
		fixFilePaths();
	}
	
	private void fixFilePaths() {
		File currentFlatfilePath = new File(userFileDirectory);
		currentFlatfilePath.mkdirs();
	}
}
