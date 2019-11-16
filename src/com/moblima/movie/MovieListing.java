package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.rating.Rating;
import com.moblima.user.Admin;
import com.moblima.user.User;
import com.moblima.util.UserInputs;

public class MovieListing{
    
    
    public void printMovieTitle(){
        int i = 1;
        for(Movie movie : DataBase.moviesPlaying){
            System.out.println(i+".\t"+movie.getTitle());
            i++;
        }
    }
    
    public static ArrayList<Movie> getMovies(){return DataBase.moviesPlaying;}
    
    public static Movie getMovieByName(String name)
    {
    	for(int i =0;i<DataBase.moviesPlaying.size();i++)
    	{
    		if(DataBase.moviesPlaying.get(i).getTitle().contentEquals(name)) return DataBase.moviesPlaying.get(i);
    	}
    	return null;
    }
    
   
    
    public static int getMovieId(String name)
    {
    	for(int i =0;i<DataBase.moviesPlaying.size();i++)
    	{
    		if(DataBase.moviesPlaying.get(i).getMovieName().equals(name)) return i;
    	}
    	return -1;
    }
    



}
