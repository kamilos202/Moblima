package com.moblima.booking;

import java.util.ArrayList;
import java.util.Scanner;

import com.moblima.cinema.Cineplex;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieShowing;

public class BookingPage 
{
    ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();

    public void initiateCinemas(){
        cineplexes.add(new Cineplex("The Cathay Cineplex",13.5));

        cineplexes.get(0).createCinema("Screen1", false);
        cineplexes.get(0).createCinema("Screen2", false);
        cineplexes.get(0).createCinema("Screen3", true);

        cineplexes.add(new Cineplex("Cathay Cineplex Causeway Point",13));

        cineplexes.get(1).createCinema("Screen1", false);
        cineplexes.get(1).createCinema("Screen2", false);
        cineplexes.get(1).createCinema("Screen3", false);
        cineplexes.get(1).createCinema("Screen4", false);
        
        MovieListing.createMovies();
        
        ArrayList<MovieShowing> occupation = cineplexes.get(0).getRoomByName("Screen1").getHallOccupation();
        System.out.println("cinema 1");
        for(int i =0;i<occupation.size();i++)
        {
        	System.out.println(occupation.get(i).toString());
        }
        
        ArrayList<MovieShowing> occupation2 = cineplexes.get(1).getRoomByName("Screen2").getHallOccupation();
        System.out.println("cinema 2");
        for(int j = 0;j<occupation2.size();j++)
        {
        	System.out.println(occupation2.get(j).toString());
        }
        
        


    }

    //CinemaHall cathy = new CinemaHall("Cathy", 13.5);



    public void bookMovie(){
        System.out.println("Welcome to booking system\nChoose Cineplex\n");

        //while(true){
            int i=0;
            for(Cineplex cineplex : cineplexes){
                i++;
                System.out.println(i+". "+cineplex.getCineplexName());
            }
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
        //}


        System.out.println("\n");


    }
    
    

}
