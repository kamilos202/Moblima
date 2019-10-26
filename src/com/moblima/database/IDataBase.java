package com.moblima.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface IDataBase 
{
	public static List<String> readFromDataBase(String path)
	{
		try
		{
			Scanner sc = new Scanner(new File("com.moblima.database"+path));
			List<String> lines = new ArrayList<String>();
			while(sc.hasNextLine())
			{
				if(!sc.nextLine().startsWith("#")) lines.add(sc.nextLine());
			}
			sc.close();
			return lines;
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch(Exception e)
		{
			e.printStackTrace();;
		}
		return null;

		
	}
	public void writeToDataBase(String[] lines);
}
