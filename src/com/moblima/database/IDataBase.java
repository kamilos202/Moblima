package com.moblima.database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * Interface for txt files manipulation.
 */
public interface IDataBase 
{
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> readFromDataBase(String path)
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("./bin/com/moblima/database/"+path));
			List<String> lines = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				//if(!sc.nextLine().startsWith("#")) 
					lines.add(sc.nextLine());
			}
			sc.close();
			//System.out.println(lines);
			return lines;
		}
		//When the file is not found an empty list will be returned (this should only happen in the case the user
		catch(FileNotFoundException e) 
		{							   
			System.err.println(e);
			System.out.println("File not found");
			

		}
		catch(Exception e)
		{
			e.printStackTrace();;
		}
		return null;

		
	}
	
	/**
	 * 
	 * @param lines
	 * @param path
	 * @throws IOException
	 * Writes an array of Strings back to the database. NOTE: old data will be overwritten
	 * 
	 */
	public static void writeToDataBase(String[] lines, String path) throws IOException 
	{
		FileWriter writer = new FileWriter(new File("./bin/com/moblima/database/"+path),false); //the false indicates not appending --> overwriting
		for(int i =0;i<lines.length;i++)
		{
			writer.write(lines[i]);
		}
		writer.close();
	}
	
	/**
	 * 
	 * @param lines
	 * @param path
	 * @throws IOException
	 * Append new data to the bottom of one of the database files
	 */
	public static void appendToDataBase(String[] lines,String path) throws IOException
	{
		
		FileWriter writer = new FileWriter(new File("./bin/com/moblima/database/"+path),true); //The true indicates appending
		for(int i =0;i<lines.length;i++)
		{
				writer.write(lines[i]);
		}
		writer.close();
	}
	/**
	 * 
	 * @param fileName name of the file which is checking
	 * @return boolean variable which indicates if file exists (true) or not (false)
	 */
	public static Boolean ifExists(String fileName)
	{
		return (new File("./bin/com/moblima/database/"+fileName).isFile());
	}
	/**
	 * This function creates empty txt file 
	 * @param fileName name of the file which then is created
	 * @throws IOException
	 */
	public static void createEmptyTxtFile(String fileName) throws IOException {
		File file = new File("./bin/com/moblima/database/"+fileName);
		file.createNewFile();
	}
}
