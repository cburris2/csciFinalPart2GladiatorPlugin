package edu.unca.schalvet.GladiatorPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Joiner;


public class GladiatorPluginCommandExecutor implements CommandExecutor {
    private final GladiatorPlugin plugin;


    public GladiatorPluginCommandExecutor(GladiatorPlugin plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	
    	if (args.length == 0) {
    		return false;
    		} 
     /*******************************************************************************************************
      *   
      *   	COMMAND: zombiearena OR arena
      *   	EFFECT: Creates an arena of various size
      *   
      *******************************************************************************************************/
    	 
    	else if ( (args[0].equalsIgnoreCase("arena") || args[0].equalsIgnoreCase("zombiearena"))  && sender.hasPermission("GlagiatorPlugin.arena")) {
    			
    		Player randomGuy = (Player) sender;
    		Location loc = randomGuy.getLocation();
    		
    		World w = loc.getWorld();
    		
    		int locX= (int) loc.getX();
    		int locY= (int) loc.getY();
    		int locZ= (int) loc.getZ();
    		
    		boolean zombies=false;
    		Random rand = new Random();
    		
    		if (args[0].equalsIgnoreCase("zombiearena"))
    			zombies=true;
    		
     		plugin.log.info("arg length"+args.length);
    		int arenaSizeX=30, arenaSizeZ=50;
    			
    		if ( args.length >1 && Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[2]) > 0 ){
    			arenaSizeX= Integer.parseInt(args[1].toString());
    			arenaSizeZ= Integer.parseInt(args[2].toString());
    		} 
    		
    		if( args.length >1 && (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[2]) <= 0) )  {
    			randomGuy.sendMessage(ChatColor.RED+"The size of the arena must be positive");
    		}
    		
    		randomGuy.sendMessage(ChatColor.DARK_PURPLE+"You used your powers to create an arena of size "+arenaSizeX+" by "+arenaSizeZ);
    		
    		plugin.arenaLocation.clear();
    		plugin.arenaLocation.add(locX+" "+locY+" "+locZ+" "+arenaSizeX+" "+arenaSizeZ);
    		plugin.arenaLocation.save();
    		
    		/*bedrock base of arena*/
    		for(int x=1; x<arenaSizeX; x++){
	    		for(int z=1; z<arenaSizeZ; z++){
	    			
	    			
		    		Block b = w.getBlockAt(locX+x, locY-1, locZ+z);
		    		b.setType(Material.BEDROCK);
		    		//plugin.log.info("x="+(locX+x)+", z="+(locZ+z)+" ");
	    		}
    		}
    		
						
					
    		/*glass wall around*/
    		for(int x=1; x<arenaSizeX; x++){
    			if(x%2 == 1)
	    		for(int z=1; z<arenaSizeZ; z++){
	    			if(((z%2 == 1) && (x==1 || x==arenaSizeX-1)) || ((z==1||z==arenaSizeZ-1)  && (x%2==1)) )
	    			for (int y=0; y<10; y++){
				    		Block b = w.getBlockAt(locX+x, locY+y, locZ+z);
				    		b.setType(Material.GLASS);
	    			}
	    		}
    		}
    		
    		/*glowstone wall around*/
    		/*for(int x=1; x<arenaSizeX; x++){
    			if(x%2 == 0)
	    		for(int z=1; z<arenaSizeZ; z++){
	    			if( (x==1 || x==arenaSizeX-1) || (z==1 ||z==arenaSizeZ-1) ) 
	    			for (int y=0; y<10; y++){
				    		Block b = w.getBlockAt(locX+x, locY+y, locZ+z);
				    		b.setType(Material.GLASS);
	    			}
	    		}
    		}*/
    		
    		/*glowstone wall around*/
    		for(int x=2; x<arenaSizeX; x++){
    			
    			if(x%2==0)
    			for (int y=0; y<10; y++){
			    		Block b = w.getBlockAt(locX+x, locY+y, locZ+1);
			    		b.setType(Material.GLOWSTONE);
    			}
    		}
    		
    		for(int z=2; z<arenaSizeZ; z++){
    			if(z%2==0)
    			for (int y=0; y<10; y++){
			    		Block b = w.getBlockAt(locX+1, locY+y, locZ+z);
			    		b.setType(Material.GLOWSTONE);
    			}
    		}
    		
    		for(int x=2; x<arenaSizeX; x++){
    			if(x%2==0)
    			for (int y=0; y<10; y++){
			    		Block b = w.getBlockAt(locX+x, locY+y, locZ+arenaSizeZ-1);
			    		b.setType(Material.GLOWSTONE);
    			}
    		}
    		
    		for(int z=2; z<arenaSizeZ; z++){
    			if(z%2==0)
    			for (int y=0; y<10; y++){
			    		Block b = w.getBlockAt(locX+arenaSizeX-1, locY+y, locZ+z);
			    		b.setType(Material.GLOWSTONE);
    			}
    		}
    		
    		
    		/*glass roof of arena*/
    		
    		int roofX=(arenaSizeX/6), 
    			roofZ=(arenaSizeZ/6);
    		
    		for(int x=roofX*2; x<roofX*4; x++){
	    		for(int z=roofZ*2; z<roofZ*4; z++){
		    		Block b = w.getBlockAt(locX+x, locY+10, locZ+z);
		    		b.setType(Material.GLASS);
	    		}
    		}
    		
    		for(int x=roofX*2; x<roofX*4; x++){
	    		for(int z=roofZ*2; z<roofZ*4; z++){
	    			if(  (x==roofX*2 || x==(roofX*4)-1) || (z==roofZ*2 ||z==(roofZ*4)-1)   ){
				    		Block b = w.getBlockAt(locX+x, locY+11, locZ+z);
				    		b.setType(Material.GLASS);
	    			}
	    		}
    		}
    		
    		
    		if(zombies){
    			
    			/*dirtbase of arena*/
        		for(int x=2; x<arenaSizeX-1; x++){
    	    		for(int z=2; z<arenaSizeZ-1; z++){
    	    			
    	    			
    	    			Block b = w.getBlockAt(locX+x, locY, locZ+z);
    		    		b.setType(Material.DIRT);
    		    		b = w.getBlockAt(locX+x, locY+1, locZ+z);
    		    		b.setType(Material.GRASS);
    		    		
    		    		
    		    		if(rand.nextInt(100)<=10){
    		    			b = w.getBlockAt(locX+x, locY+2, locZ+z);
    		    			b.setType(Material.LONG_GRASS);
    		    			b.setData((byte) 2);
    		    		}
    		    		    		
    		    		if(rand.nextInt(100)<=5){
    		    			b = w.getBlockAt(locX+x, locY+2, locZ+z);
    		    			b.setType(Material.YELLOW_FLOWER);
    		    		}
    		    		
    		    		if(rand.nextInt(100)<=1){
    		    			b = w.getBlockAt(locX+x, locY+2, locZ+z);
    		    			b.setType(Material.BROWN_MUSHROOM);
    		    		}
    		    		
    		    		if(rand.nextInt(100)<=1){
    		    			b = w.getBlockAt(locX+x, locY+2, locZ+z);
    		    			b.setType(Material.RED_MUSHROOM);
    		    		}
    	    		}
        		}
        		
        		for(int x=4; x<arenaSizeX-3; x++){
    	    		for(int z=4; z<arenaSizeZ-3; z++){	
    	    
    		    		if(rand.nextInt(1000)<=15){
    		    			w.generateTree(w.getBlockAt(locX+x, locY+2, locZ+z).getLocation(), TreeType.BIG_TREE);

    		    		}
    	    		}
        		}

    			
    		}//end if
    		else{
    			/* arena maze*/
    		
	    		
	    		for(int x=2; x<arenaSizeX-1; x++){
	    			for(int z=2; z<arenaSizeZ-1; z++){
		    			
	    				Block b = w.getBlockAt(locX+x, locY, locZ+z);
			    		b.setType(Material.NETHER_BRICK);
			    		b = w.getBlockAt(locX+x, locY+1, locZ+z);
			    		b.setType(Material.NETHER_BRICK);
			    		
	    				if(rand.nextInt(100)<=45){
	    					int height = rand.nextInt(5);
	    					if(height==4)height=5;
			    			for (int y=0; y<height+1; y++){
					    		b = w.getBlockAt(locX+x, locY+y+2, locZ+z);
					    		b.setType(Material.STONE);
					    	
			    			}
	    				}
		    		}
	    		}
    		}//end else
    		
    	return true;
     	}
    		
    	  /*******************************************************************************************************
    	  *   
    	  *   	COMMAND: tpspec or tpArena
    	  *   	EFFECT: teleports player to the arena
    	  *   
    	  *******************************************************************************************************/
    		   
    	 else if ( (args[0].equalsIgnoreCase("tpspec")|| (args[0].equalsIgnoreCase("tparena")) && sender.hasPermission("GlagiatorPlugin.tparena"))) {
    		
    		ArrayList<String> coordinance = plugin.arenaLocation.getValues();
    		
    		if(!coordinance.isEmpty()){
    			
	    		Player randomGuy = (Player) sender;
	    		Location loc = randomGuy.getLocation();
	       		World w = loc.getWorld();
	    		Random rand = new Random();
	    		
	       		
	       		if(args.length <2){
	    			randomGuy.sendMessage(ChatColor.RED+"The command is: \"tpspec <playerName>\" OR \"tparena <playerName>\" playername can = \"me\"");
	    			return true;
	       		}
	    		
	       		Iterator iter = coordinance.iterator();
	    		
	    		String phrase = iter.next().toString();
	    		String delims = "[ ]";
	    		String[] tokens = phrase.split(delims);    
	    		int 	x = Integer.parseInt(tokens[0]),
	    				y = Integer.parseInt(tokens[1]),
	    				z = Integer.parseInt(tokens[2]),
	    				arenaSizeX = Integer.parseInt(tokens[3]),
	    				arenaSizeZ = Integer.parseInt(tokens[4]);
	    		
	    		if(args[0].equalsIgnoreCase("tpspec")){
		    		loc.setX( x+(arenaSizeX/2));
		    		loc.setZ( z+(arenaSizeZ/2) );
		    		loc.setY( y+11 );
	    		} else /*args[0].equalsIgnoreCase("tparena")*/ { 
	    			
	    			loc.setX( x+rand.nextInt(arenaSizeX-4)+2);
		    		loc.setZ( z+rand.nextInt(arenaSizeZ-4)+2 );
		    		loc.setY( y+2 );
	    			
	    		}
	    		
       			if(loc.getBlock().getType()!= Material.AIR)
       				onCommand( sender, command, label, args);
	    		
	    		/*Teleport your self*/
	       		if(args[1].equalsIgnoreCase("me")){
	       			
	       			randomGuy.teleport(loc);
		    		randomGuy.sendMessage(ChatColor.DARK_PURPLE+"You used your powers to teleport to the arena");		
	       		/*teleport others*/
	       		} else {
	       			
	       			List<Player> playerList = w.getPlayers();
		       		Iterator pList = playerList.iterator();
		       		
		       		int listLength=playerList.size();
		       		
		       		int index=-1, length=0, count=0;
		       		String listName="", teleportee="null";

		       		String playerName = args[1].toString();
		       		
		       		while(pList.hasNext()){
		       			
		       			listName = pList.next().toString();
		       			length = listName.length();
		       			teleportee = listName.substring(17, length-1);
		       			
			    		plugin.log.info("name= "+teleportee+".");
		       			
			    		if(playerName.equalsIgnoreCase(teleportee))
		       				index=count;
			    		
		       			count++;
		       		}
		   		
		    		plugin.log.info(randomGuy.getDisplayName()+" teleported "+teleportee+" to the arena.");
		    		//plugin.log.info("your displayname="+randomGuy.getDisplayName());
		    		//plugin.log.info("your name="+randomGuy.getName());
		    		
		    		if(index<listLength && index >= 0){//to make sure that the List is not out of bounds
		    			playerList.get(index).teleport(loc);
		    			randomGuy.sendMessage(ChatColor.DARK_PURPLE+"You used your powers to teleport "+teleportee+" to the arena.");
		    			playerList.get(index).sendMessage(ChatColor.DARK_GREEN+"You were teleported by "+randomGuy.getDisplayName()+" to the arena.");
		    		} else{
		    			randomGuy.sendMessage(ChatColor.RED+"The player: "+playerName+" does not seem to be on this server.");
		    		}
	       			
	       		}
	       		
	       		
	    	
	    		
    		}

    		return true;
    		
    	  /*******************************************************************************************************
    	  *   
    	  *   	COMMAND: zombies
    	  *   	EFFECT: Spawns zombies in the arena
    	  *   
    	  *******************************************************************************************************/
    		   
    		}else if (args[0].equalsIgnoreCase("zombies") && sender.hasPermission("GlagiatorPlugin.zombies")) {
		
    		ArrayList<String> coordinance = plugin.arenaLocation.getValues();
    		
    		if(!coordinance.isEmpty()){
    			
	    		Player randomGuy = (Player) sender;
	    		Location loc = randomGuy.getLocation();
	       		World w = loc.getWorld();
	       		
	       		if(args.length <2){
	    			randomGuy.sendMessage(ChatColor.RED+"The command is: \"zombies <number>\"");
	    			return true;
	    		}
	       		
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
	    		
	    		
	    		int num = Integer.parseInt(args[1].toString());


	    		for (int i=0; i<=num; i++){
	    			loc.setX( x+rand.nextInt(arenaSizeX-2)+1);
		    		loc.setZ( z+rand.nextInt(arenaSizeZ-2)+1 );
		    		loc.setY( y+3 );
		    		
		    		if(rand.nextInt(10)<=1)
		    			w.spawnEntity(loc, EntityType.SKELETON);
		    		else if(rand.nextInt(10)<=5)
		    			w.spawnEntity(loc, EntityType.PIG_ZOMBIE);
		    		else
		    			w.spawnEntity(loc, EntityType.ZOMBIE);
	    		}
	    				
	    		randomGuy.sendMessage(ChatColor.RED+"Look out for Zombies!!! ");
    		}

    		return true;

    		}
        		
        	  /*******************************************************************************************************
        	  *   
        	  *   	COMMAND: invincible
        	  *   	EFFECT: Gives player full diamond
        	  *   
        	  *******************************************************************************************************/
        		   
        		/*}*/else if (args[0].equalsIgnoreCase("invincible")&& sender.hasPermission("GlagiatorPlugin.invincible")) {

				Player randomGuy = (Player) sender;
				Location loc = randomGuy.getLocation();
				World w = loc.getWorld();
				
				
				randomGuy.getInventory().clear();
				randomGuy.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
				randomGuy.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
				randomGuy.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
				randomGuy.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
				randomGuy.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_SWORD, 1));
				randomGuy.sendMessage(ChatColor.GREEN+"Fight Well Brave Warrior");

				
				return true;

    			}else {
 
    	return false;
    		
    	}
    }
}
