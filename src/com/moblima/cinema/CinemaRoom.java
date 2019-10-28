package com.moblima.cinema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.database.DataBaseCommunication;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieShowing;

public class CinemaRoom
{
    String cineplexName;
    String cinemaName;
    double basicPrice;
    double cinemaPrice;
    int [][] cinemaLayout;

    Boolean suite;

    public CinemaRoom(String cineplexName, String cinemaName, double basicPrice, Boolean suite){
        this.cineplexName = cineplexName;
        this.cinemaName = cinemaName;
        this.suite = false;
        cinemaPrice=basicPrice;
        if(suite == true){
            this.suite = true;
            cinemaPrice+=7;
            cinemaLayout = Cineplex.suiteLayout();
        }else{
            cinemaLayout = Cineplex.standardLayout();
        }

        if(DataBaseCommunication.ifExists(cineplexName+"_"+cinemaName+".txt"))
        {
            System.out.println("File already exist");
        }
        else
        {
            //Creating cinema layout
            String [] layout = new String[cinemaLayout.length+1];//+1 for ----Screen---
            char row = 'A';
            layout[0] = "-------------------------SCREEN-------------------------\n";
            for(int i=0;i<cinemaLayout.length;i++){
                layout[i+1] = ""+row;
                for(int j=0;j<cinemaLayout[i].length;j++){
                    cinemaLayout[i][j] = 0;
                    layout[i+1]+=(" "+cinemaLayout[i][j]+" ");
                }
                layout[i+1]+=(row+"\n");
                row++;
            }
            //layout[0] = "";
            DataBaseCommunication.writeToDataBase(layout, (cineplexName+"_"+cinemaName+".txt"));
            System.out.println("File created");


        }


    }



    public double getCinemaPrice(){
        return cinemaPrice;
    }
    public String getCinemaName(){
        return cinemaName;
    }
    
    public ArrayList<MovieShowing> getHallOccupation()
    {
    	ArrayList<Movie> movies = MovieListing.getMovies();
    	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
    	for(int i =0;i<movies.size();i++)
    	{
    		for(int j =0;j<movies.get(i).getShowings().size();j++)
    		{
    			if(this.equals(movies.get(i).getShowings().get(j).getCinemaRoom())&&new Date().before(movies.get(i).getShowings().get(j).getDate())) 
    				showings.add(movies.get(i).getShowings().get(j));
    		}
    	}
    	
    	return showings;
    }
}
