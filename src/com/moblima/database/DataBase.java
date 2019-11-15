package com.moblima.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.moblima.cinema.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieControl;
import com.moblima.movie.MovieListing;
import com.moblima.user.Admin;
import com.moblima.user.MovieGoer;
import com.moblima.user.User;
import com.moblima.user.UserControl;


/**
 * This class contains all the communication between other classes and the database
 */
public class DataBase implements IDataBase
{
	/**
	 * Check whether file already exists or not.
	 * @param file
	 * @return boolean value true - file exist, false - file do not exist
	 */
	
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();
	public static ArrayList<Movie> moviesPlaying = new ArrayList<Movie>();
	public static ArrayList<Movie> fullMovieArchive = new ArrayList<Movie>();
	
	public static void init()
	{
		initiateUsers();
		initiateCineplexes();
		initiateMovies();
	}
	
	private static void initiateUsers()
	{
		List<String> usersInDataBase = readFile("users.txt");
		for(int i =1;i<usersInDataBase.size();i++)
		{
			users.add(UserControl.getUserFromString(usersInDataBase.get(i)));
			System.out.println("IIIIIIIIIIIIIIINITTTT"+UserControl.getUserFromString(usersInDataBase.get(i)));
		}
		
	}
	
	private static void initiateCineplexes()
	{
		cineplexes.add(new Cineplex("The Cathay Cineplex",13.5,14));

        cineplexes.get(0).createCinema("Screen1", false);
        cineplexes.get(0).createCinema("Screen2", false);
        cineplexes.get(0).createCinema("Screen3", true);

        cineplexes.add(new Cineplex("Cathay Cineplex Causeway Point",13,14));

        cineplexes.get(1).createCinema("Screen1", false);
        cineplexes.get(1).createCinema("Screen2", false);
        cineplexes.get(1).createCinema("Screen3", false);
        cineplexes.get(1).createCinema("Screen4", false);
	}
	
	private static void initiateMovies()
	{
		Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        map = DataBase.getMovies();

        for(int i=0;i<map.size();i++)
        {
        	System.out.println("the map is equal to: " + map.get(i));
        	Movie movie = MovieControl.getMovieFromString(map.get(i));
        	fullMovieArchive.add(movie);
        	if(!movie.isEnded()) moviesPlaying.add(movie);
        	System.out.println("THE MOVIE RESULT: \n" + movie.toDataBaseString());
        }
        
        
	}
	
	public static Boolean ifExists(String file){
		return IDataBase.ifExists(file);
	}
	/**
	 * 
	 * @param lines
	 * @param path
	 * 
	 * Called by other Classes to write to data base
	 */
	public static void writeToDataBase(String[] lines, String path)
	{
		try {IDataBase.writeToDataBase(lines, path);} 
		catch (IOException e) {	e.printStackTrace();}
	}
	
	/**
	 * Called by other classes to append to database
	 * @param lines
	 * @param path
	 * @throws IOException
	 */
	public static void appendToDataBase(String[] lines, String path) throws IOException
	{
		IDataBase.appendToDataBase(lines, path);
	}
	

	public static void replaceInDataBase(String lineToReplace,String newLine,String path)
	{
		List<String> currentFile = IDataBase.readFromDataBase(path);
		String[] newLines = new String[currentFile.size()];
		System.out.println("New Line: " + newLine);
		
		for(int i =0;i<currentFile.size();i++)
		{
			System.out.println("Line to replace:" + lineToReplace);
			System.out.println("Line to compare:" + currentFile.get(i));
			if(i!=currentFile.size()-1){
				if(currentFile.get(i).replaceAll("\n","").equals(lineToReplace.replaceAll("\n", ""))) 
				{
					System.out.println("Replace content in file: " + path);
					newLines[i] = newLine.replaceAll("\n", "")+"\n";
				}
				else newLines[i] = currentFile.get(i).replaceAll("\n", "")+"\n";
			}
			else{
				if(currentFile.get(i).replaceAll("\n","").equals(lineToReplace.replaceAll("\n", ""))) 
				{
					System.out.println("Replace content in file: " + path);
					newLines[i] = newLine.replaceAll("\n", "");
				}
				else newLines[i] = currentFile.get(i).replaceAll("\n", "");
			}
			System.out.println("Line i will write to document: " + newLines[i]+ "; and the line i should write: " + newLine);
		}
		writeToDataBase(newLines, path);
	}
	
	/**
	 * Called by other classes to read in data from database
	 * @param path
	 * @return
	 */
	public static List<String> readFile(String path)
	{
		return IDataBase.readFromDataBase(path);
	}
	/**
	 * 
	 * @param username
	 * @return <String> user details
	 * 
	 * Called by user to get the data from 1 specific user
	 */
	public static String getUserDetails(String username)
	{
		List<String> users = readFile("users.txt");
		for(int i = 0;i<users.size();i++) 
		{
			if(users.get(i).split(";")[0].equals(username)) return users.get(i);
		}
		
		return null;
	}
	/**
	 * Creates empty txt file for given name.
	 * @param fileName
	 * @throws IOException
	 */
	public static void createEmptyTxtFile(String fileName) throws IOException {
		IDataBase.createEmptyTxtFile(fileName);
	}

	/**
	 * 
	 * @param username
	 * @return
	 * 
	 * Called by User to retrieve the actual users password to verify login 
	 */
	public static String retrievePasswordFromDatabase(String username)
	{
		boolean userExists = false;
		String password = "";
		List<String> users = IDataBase.readFromDataBase("users.txt");
		for(int i = 0;i<users.size();i++)
		{
			if(users.get(i).split(";")[0].equals(username))
			{
				userExists = true;
				password = users.get(i).split(";")[1];
				userExists = true;
				break;
			}
		}
		return userExists? password : null;
	}
	/**
	 * Retrieves all the movie details from txt file
	 */
	public static Map<Integer,List<String>> getMovies(){
		Map<Integer,List<String>> moviesMap = new HashMap<Integer,List<String>>();

		List<String> allMovies = new ArrayList<String>();
		allMovies = IDataBase.readFromDataBase("movies.txt");

		
		//allMovies = IDataBase.readFromDataBase("movies.txt");
		
		for(int i=0;i<allMovies.size();i++){
			
			List<String> singleMovie = new ArrayList<String>();
			//singleMovie.add(allMovies.get(i));

			Pattern p = Pattern.compile("TITLE:(.*?);");
			Matcher m = p.matcher(allMovies.get(i));
		
			String title="";
			while (m.find()) {
				title = m.group(1);
			}
			if(title == ""){
				title = "NO TITLE";
			}
			singleMovie.add(title);

			p = Pattern.compile("SYNOPSIS:(.*?);");
			m = p.matcher(allMovies.get(i));
			
			String synopsis="";
			while (m.find()) {
				synopsis = m.group(1);
			}
			if(synopsis == ""){
				synopsis = "NO SYNOPSIS";
			}
			singleMovie.add(synopsis);
		
			p = Pattern.compile("STATUS:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			String status="";
			while (m.find()) {
				status = m.group(1);
			}
			if(status == ""){
				status = "NO STATUS";
			}
			singleMovie.add(status);

			String director="";;
			p = Pattern.compile("DIRECTOR:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			while (m.find()) {
				director=m.group(1);
			}
			if(director == ""){
				director = "NO DIRECTOR";
			}
			singleMovie.add(director);

			p = Pattern.compile("CAST:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			String cast="";
			while (m.find()) {
				cast = m.group(1);
			}
			if(cast == ""){
				cast = "NO CAST";
			}
			singleMovie.add(cast);

			p = Pattern.compile("DURATION:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			String duration="";
			while (m.find()) {
				duration = m.group(1);
			}
			if(duration == ""){
				duration = "0";
			}
			singleMovie.add(duration);
			
			p = Pattern.compile("TICKETS:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			String tickets="";
			while (m.find()) {
				tickets = m.group(1);
			}
			if(tickets == ""){
				tickets = "";
			}
			singleMovie.add(tickets);
			
			p = Pattern.compile("SHOWINGS:(.*?);");
			m = p.matcher(allMovies.get(i));
		
			String showings="";
			while (m.find()) {
				showings = m.group(1);
			}
			if(showings == ""){
				showings = "";
			}
			singleMovie.add(showings);
			moviesMap.put(i, singleMovie);
		}

		return moviesMap;
	}



	
}
