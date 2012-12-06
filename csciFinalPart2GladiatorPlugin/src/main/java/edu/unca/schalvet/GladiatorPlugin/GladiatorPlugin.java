package edu.unca.schalvet.GladiatorPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import edu.unca.schalvet.GladiatorPlugin.util.*;

public class GladiatorPlugin extends JavaPlugin {

	GladiatorPluginLogger log;
	
	public ArenaLocation arenaLocation;
	
	@Override
	public void onEnable() {
		
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		
		this.arenaLocation = new ArenaLocation (new File(pluginFolder + File.separator +"GladiatorArenaLocations.txt"));

		this.arenaLocation.load();


		
		// save the configuration file
		saveDefaultConfig();

		log = new GladiatorPluginLogger(this);
		log.info("gladiator plugin enabled");

		// Create the SampleListener
		new GladiatorPluginListener(this);

		// set the command executor for sample
		this.getCommand("gladiator").setExecutor(new GladiatorPluginCommandExecutor(this));
	}


	@Override
	public void onDisable() {
		log.info("gladiator plugin disabled");
		this.arenaLocation.save();

	}

}
