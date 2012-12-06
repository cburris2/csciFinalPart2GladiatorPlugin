package edu.unca.schalvet.GladiatorPlugin;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class GladiatorPluginListener implements Listener {
	private final GladiatorPlugin plugin;


	public GladiatorPluginListener(GladiatorPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		this.plugin = plugin;
	}


	@EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().sendMessage(ChatColor.GREEN+"Welcome "+event.getPlayer().getDisplayName()+" to the Gladiactor server");
	}




	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onPlayerDeath(PlayerRespawnEvent event) {
		ArrayList<String> coordinance = plugin.arenaLocation.getValues();
		
		if(!coordinance.isEmpty()){
			
       		
       		
    		Random rand = new Random();
    		Iterator iter = coordinance.iterator();
    		
    		String phrase = iter.next().toString();
    		String delims = "[ ]";
    		String[] tokens = phrase.split(delims);
    		    
    		int 	x = Integer.parseInt(tokens[0]),
    				y = Integer.parseInt(tokens[1]),
    				z = Integer.parseInt(tokens[2]),
    				arenaSizeX = Integer.parseInt(tokens[3]),
    				arenaSizeZ = Integer.parseInt(tokens[4]);
    	
    	Location loc =event.getPlayer().getLocation();
    	
		loc.setX( x+(arenaSizeX/2));
		loc.setZ( z+(arenaSizeZ/2) );
		loc.setY( y+11 );
    	
		plugin.log.info("x= "+loc.getX()+"y= "+loc.getY()+"z= "+loc.getZ());
		event.getPlayer().setBedSpawnLocation(loc);
		event.getPlayer().sendMessage(ChatColor.GREEN+"You died, but the fight is never over!");
		event.getPlayer().teleport(loc);
		
		
		}
	}

}
