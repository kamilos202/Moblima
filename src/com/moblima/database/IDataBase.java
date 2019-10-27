package com.moblima.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface IDataBase 
{
	
	//Read in a specific file from database and return its content in the form of a list of Strings
	public static List<String> readFromDataBase(String path)
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("./bin/com/moblima/database/"+path));
			List<String> lines = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				//if(!sc.next().startsWith("#"))  //Implement comment function in database, WIP
					lines.add(sc.nextLine());
			}
			sc.close();
			System.out.println(lines);
			return lines;
		}
		catch(FileNotFoundException e) //When the file is not found an empty list will be returned (this should only happen in the case the user
		{							   //tries to open a file with manual inputs in the program)
			System.err.println(e);
			System.out.println("File not found");
			return new ArrayList<String>();
		}
		
	}
	
	//Writes an array of Strings back to the database. NOTE: old data will be overwritten
	public static void writeToDataBase(String[] lines, String path) throws IOException 
	{
		FileWriter writer = new FileWriter(new File("./bin/com/moblima/database/"+path),false); //the false indicates not appending --> overwriting
		for(int i =0;i<lines.length;i++)
		{
			writer.write(lines[i]);
		}
		writer.close();
	}
	
	//Append new data to the bottem of one of the database files
	public static void appendToDataBase(String[] lines,String path) throws IOException
	{
		FileWriter writer = new FileWriter(new File("./bin/com/moblima/database/"+path),true); //The true indicates appending
		for(int i =0;i<lines.length;i++)
		{
			writer.write(lines[i]);
		}
		writer.close();
	}
}
