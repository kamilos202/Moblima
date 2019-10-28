package com.moblima.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataBaseCommunication implements IDataBase
{
	
	/**
	 * 
	 */
	public void writeToDataBase(String[] lines)
	{
		;
	}
	/**
	 * 
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
	 * @return
	 */
	public static String retrievePasswordFromDatabase(String username)
	{
		boolean userExists = false;
		String password = "";
		List<String> users = IDataBase.readFromDataBase("users.txt");
		for(int i = 0;i<users.size();i++)
		{
			if(users.get(i).split(";")[0]==username)
			{
				password = users.get(i).split(";")[1];
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

		List<String> singleMovie = new ArrayList<String>();
		//allMovies = IDataBase.readFromDataBase("movies.txt");
		
		for(int i=0;i<allMovies.size();i++){
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



			moviesMap.put(i, singleMovie);
		}

		return moviesMap;
	}
	
}
