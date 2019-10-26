package com.moblima.database;



public interface IDataBase 
{
	public String[] readFromDataBase(String path);
	public void writeToDataBase(String[] lines);
}
