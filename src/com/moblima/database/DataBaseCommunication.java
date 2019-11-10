package com.moblima.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class contains all the communication between other classes and the database
 */
public class DataBaseCommunication implements IDataBase
{
	
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
	
	//Called by other classes to append to database
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
			if(currentFile.get(i).replaceAll("\n","").equals(lineToReplace.replaceAll("\n", ""))) 
			{
				System.out.println("Replace content in file: " + path);
				newLines[i] = newLine.replaceAll("\n", "")+"\n";
			}
			else newLines[i] = currentFile.get(i).replaceAll("\n", "")+"\n";
		}
		writeToDataBase(newLines, path);
	}
	
	//Called by other classes to read in data from database
	public static List<String> readFile(String path)
	{
		return IDataBase.readFromDataBase(path);
	}
	/**
	 * 
	 * @param username
	 * @return
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
	 * 
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
