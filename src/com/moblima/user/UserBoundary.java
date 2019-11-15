package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.util.UserInputs;

public class UserBoundary 
{
	
	public void performActions() throws IOException, ParseException
	{
		
	}
	
	
	protected Movie chooseMovieFromList()
	{
		System.out.println("Please select a movie: ");
		for(int i =0;i<DataBase.moviesPlaying.size();i++)
		{
			System.out.println(i+1+":" + DataBase.moviesPlaying.get(i).getTitle());
		}
		
		boolean isValid = false;
		Movie movie = null;
		while(!isValid)
		{
			int choice = UserInputs.getValidIntegerInput();
			if(choice>0&&choice<=DataBase.moviesPlaying.size())
			{
				movie = DataBase.moviesPlaying.get(choice-1);
				isValid = true;
			}
			else System.out.println("Please enter one of the mentioned options");
		}
		
		return movie;
	}
	
	protected Cineplex chooseCinpexFromList()
	{
		System.out.println("Please select a Cineplex: ");
		for(int i =0;i<DataBase.cineplexes.size();i++)
		{
			System.out.println(i+1+":" + DataBase.cineplexes.get(i).getCineplexName());
		}
		
		boolean isValid = false;
		Cineplex cinema = null;
		while(!isValid)
		{
			int choice = UserInputs.getValidIntegerInput();
			if(choice>0&&choice<=DataBase.moviesPlaying.size())
			{
				cinema = DataBase.cineplexes.get(choice-1);
				isValid = true;
			}
			else System.out.println("Please enter one of the mentioned options");
		}
		
		return cinema;
	}
	
	protected CinemaRoom chooseRoomFromList(Cineplex cineplex)
	{
		System.out.println("Please select a room: ");
		for(int i =0;i<cineplex.getScreens().size();i++)
		{
			System.out.println(i+1+":" + cineplex.getScreens().get(i).getCinemaName());
		}
		
		boolean isValid = false;
		CinemaRoom room = null;
		while(!isValid)
		{
			int choice = UserInputs.getValidIntegerInput();
			if(choice>0&&choice<=DataBase.moviesPlaying.size())
			{
				room = cineplex.getScreens().get(choice-1);
				isValid = true;
			}
			else System.out.println("Please enter one of the mentioned options");
		}
		
		return room;
	}
}
