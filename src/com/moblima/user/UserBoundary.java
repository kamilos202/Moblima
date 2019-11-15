package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieShowing;
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
		
		return DataBase.moviesPlaying.get(UserInputs.getValidIntegerInput(0,DataBase.moviesPlaying.size()+1)-1);
	}
	
	protected Cineplex chooseCinpexFromList()
	{
		System.out.println("Please select a Cineplex: ");
		for(int i =0;i<DataBase.cineplexes.size();i++)
		{
			System.out.println(i+1+":" + DataBase.cineplexes.get(i).getCineplexName());
		}
		
		return DataBase.cineplexes.get(UserInputs.getValidIntegerInput(0,DataBase.cineplexes.size()+1)-1);
	}
	
	protected CinemaRoom chooseRoomFromList(Cineplex cineplex)
	{
		System.out.println("Please select a room: ");
		for(int i =0;i<cineplex.getScreens().size();i++)
		{
			System.out.println(i+1+":" + cineplex.getScreens().get(i).getCinemaName());
		}
		
		return cineplex.getScreens().get(UserInputs.getValidIntegerInput(0,cineplex.getScreens().size()+1)-1);
	}
	
	protected MovieShowing chooseShowingFromList(Movie movie)
	{
		System.out.println("Please select a showing: ");
		for(int i = 0;i<movie.getShowings().size();i++)
		{
			System.out.println(i+1+": "+ movie.getShowings().get(i).toString());
		}
		
		return movie.getShowings().get(UserInputs.getValidIntegerInput(0,movie.getShowings().size()+1)-1);
	}
}
