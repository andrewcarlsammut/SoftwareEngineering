package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game
{
	private static Scanner keyboard = new Scanner(System.in);
	private static Map map;
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	/**
	 * Default Constructor which initialises the class variables
	 */
	public Game()
	{
		map = null;
		players = null;
	}
	
	public static void main(String args[])
	{
		startGame();
	}
	
	public static Map getMap()
	{
		return map;
	}

	/**
	 * Sets the number of players that will be playing the game
	 */
	private static void setNumPlayers()
	{
		System.out.print("How many players are going to play? : ");
		int numOfPlayers = keyboard.nextInt();
		
		for(int i = 0; i < numOfPlayers; i++)
		{
			players.add(new Player(i + 1));
		}
		
		handlingPlayerEvents();
	}

	private static int getPlayers()
	{
		return players.size();
	}
	
	/**
	 * Handles all the player events
	 * 	1. Generation of HTML Files
	 * 	2. Movement of players
	 * 	3. Updates corresponding HTML Files
	 */
	private static void handlingPlayerEvents()
	{
		//Generating random position
		Random ran = new Random();
		Misc.deleteFiles("external/maps");
		
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			
			//Get random position for each particular player
			while(player.setPosition(new Position(ran.nextInt(map.getSize()), ran.nextInt(map.getSize()))) != Map.TILE_GRASS)
			{
				//Setting a valid position
			}
			
			generateHTMLFiles(true, player);
		}
		
		while(getPlayers() > 0)
		{
			for(int i = 0; i < players.size(); i++)
			{
				Player player = players.get(i);
				int playerNumber = player.getNumber();
				
				//Get input from each user
				System.out.print("Player " + playerNumber + ": ");
				char direction = keyboard.next().charAt(0);
				int nextTile = player.move(direction);
				
				if(nextTile == Map.TILE_WATER)
				{
					System.out.println("Game Over Player " + playerNumber);
					players.remove(i);
				}
				
				else if(nextTile == Map.TILE_TREASURE)
				{
					System.out.println("Congratulations " + playerNumber + ", you have found the Treasure");
					players.removeAll(players);
				}
				
				else if(nextTile == Map.TILE_INVALID)
				{
					System.out.println("Invalid Direction");
				}
				
				//Modify the HTML map for this particular Player
				generateHTMLFiles(false, player);
				
			}
		}
	}

	/**
	 * Generates HTML files
	 * @param init Whether HTML files are created for initialisation or for updating
	 * @param player Player to which HTML files are to be delivered
	 */
	private static void generateHTMLFiles(boolean init, Player player)
	{
		if(init)
			Misc.writeToFile("external/maps/map_player_" + player.getNumber() +".html", init, player);
		
		else
			Misc.writeToFile("external/maps/map_player_" + player.getNumber() +".html", false, player);
	}
	
	/**
	 * Generates HTML Code for a particular player
	 * @param player The player to which the HTML rendered code is delivered
	 * @return Initialisation HTML code
	 */
	public static String generateHTMLCode(Player player)
	{
		StringBuffer buff = new StringBuffer();
		buff.append("<!DOCTYPE html>\n")
			.append("<html>\n")
			.append("\t<head>\n")
			.append("\t\t<title>MinGame Map</title>\n")
			.append("\t</head>\n")
			.append("\t<body>\n")
			.append("\t\t<table style='width:40%; margin-left:auto; margin-right:auto'>\n")
			.append("\t\t\t<tr>\n")
			.append("\t\t\t\t<td colspan='" + map.getSize())
			.append("' style='font-size:20px; text-align:center; font-style:italic'> miniGame - Player ")
			.append(player.getNumber() + " </td>\n")
			.append("\t\t\t</tr>\n");
		
		for(int row = 0; row < map.getSize(); row++ )
		{
			buff.append("\t\t\t<tr>\n");
			for(int col = 0; col < map.getSize(); col++)
			{
				buff.append("\t\t\t\t<td id='")
				.append(row)
				.append("-")
				.append(col)
				.append("' style='text-align:center; width:45px; height:45px; background-color:");
				
				
				if(player.getPosition().equals(new Position(row, col)))
				{
					buff.append(map.getTileType(Map.TILE_GRASS));
				}
				
				else
				{
					buff.append(map.getTileType(Map.TILE_HIDDEN));
				}
				
				if(player.getPosition().equals(new Position(row, col)))
				{
					buff.append("'>\n")
						.append("\t\t\t\t\t<img id='currentPosition' src='../images/currentPosition.png' alt='CurrentPositon' height='42' width='42'>\n\t\t\t\t");
				}
				else
				{
					buff.append("'>");
				}
				
				buff.append("</td>\n");
			}
			
			buff.append("\t\t\t</tr>\n");
		}
		
		buff.append("\t\t</table>\n")
			.append("\t\t<script>\n")
			.append("\t\t\tvar timer;\n")
			.append("\n\t\t\tfunction refreshmypage()\n")
			.append("\t\t\t{\n")
			.append("\t\t\t\tdocument.location=document.location.href;\n")
			.append("\t\t\t}\n")
			.append("\n\t\t\ttimer=setTimeout(refreshmypage,1000);\n")
			.append("\n\t\t\tfunction updatePosition(x, y, color)\n")
			.append("\t\t\t{\n")
			.append("\t\t\t\tdocument.getElementById(x + \'-\' + y).style.backgroundColor = color;\n")
			.append("\t\t\t\tvar currentPos = document.getElementById('currentPosition');\n")
			.append("\t\t\t\tcurrentPos.parentNode.removeChild(currentPos);\n")
			.append("\t\t\t\tvar newPos = document.getElementById(x + \'-\' + y);\n")
			.append("\t\t\t\tnewPos.appendChild(currentPos);\n")
			.append("\t\t\t}\n\n")
			.append("\t\t\t//Player Directions\n")
			.append("\t\t</script>\n")
			.append("\t</body>\n")
			.append("</html>");
		
		return buff.toString();
	}

	
	static void startGame()
	{
		System.out.print("Enter size of map : ");
		int size = keyboard.nextInt();
		map = new Map();
		map.setSize(size);
		map.generateMap();
		map.printMap();
		setNumPlayers();
	}
	
	/**
	 * Gets a particular player based on the player number
	 * @param playerNumber the number of the player
	 * @return Player
	 */
	public static Player getPlayer(int playerNumber)
	{
		Player player = null;
		for(int i = 0; i < getPlayers(); i++)
		{
			if(players.get(i).getNumber() == playerNumber)
			{
				player =  players.get(i);
			}
		}
		
		return player;
	}
	
	/**
	 * Updates the map for each player according to each particular
	 * step made by the player
	 * @param player Player that wants his map to be updated
	 * @return updated HTML
	 */
	public static String updateHTML(Player player)
	{
		BufferedReader reader = null;
		StringBuilder buff = new StringBuilder();
		
		try
		{
			File file = new File("external/maps/map_player_" + player.getNumber() +".html");
			reader = new BufferedReader(new FileReader(file));
		} 
		catch (FileNotFoundException e)
		{
			LogFile.logError("Game.updateHTML::" + e.getMessage());
		}
		
		String line = "";
		
		try
		{
			//Reading the current file until the point to which the file will be modified
			while ((line = reader.readLine()) !=null && !line.contains("//Player Directions"))
				buff.append(line + "\n");
			
			int x = player.getPosition().getX();
			int y = player.getPosition().getY();
			
			//Updating the HTML File with the new Position of the player
			buff.append("\t\t\tupdatePosition(")
				.append(x)
				.append(", ")
				.append(y)
				.append(",'")
				.append(map.getTileType(x, y))
				.append("');\n")
				.append("\t\t\t//Player Directions\n");


			//Continue reading the rest of the file after appending the direction of the 
			//player at the appropriate line number
			while ((line = reader.readLine()) != null)
				buff.append(line + "\n");
			
			reader.close();
		}
		catch (IOException e)
		{
			LogFile.logError("Game.updateHTML:: " + e.getMessage());
		}
		
		return buff.toString();
	}
}
