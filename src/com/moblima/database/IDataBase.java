package com.moblima.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface IDataBase 
{
	public static List<String> readFromDataBase(String path)
	{
		try
		{
			System.out.println(new FileReader("./bin/com/moblima/database/users.txt"));
			Scanner sc = new Scanner(new FileReader("./bin/com/moblima/database/users.txt"));
			List<String> lines = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				//if(!sc.nextLine().startsWith("#")) 
					lines.add(sc.next());
			}
			sc.close();
			System.out.println(lines);
			return lines;
		}
		catch(FileNotFoundException e)
		{
			System.err.println(e);
			System.out.println("File not found");
			return new ArrayList<String>();
		}
		
	}
	public void writeToDataBase(String[] lines);
}
