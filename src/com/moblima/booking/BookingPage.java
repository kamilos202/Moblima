package com.moblima.booking;

import java.util.ArrayList;
import java.util.Scanner;

import com.moblima.cinema.Cinema;
import com.moblima.cinema.CinemaHall;
import com.moblima.database.DataBaseCommunication;

public class BookingPage 
{
    ArrayList<CinemaHall> cineplexes = new ArrayList<CinemaHall>();

    public void initiateCinemas(){
        cineplexes.add(new CinemaHall("The Cathay Cineplex",13.5));

        cineplexes.get(0).createCinema("Screen1", false);
        cineplexes.get(0).createCinema("Screen2", false);
        cineplexes.get(0).createCinema("Screen3", true);

        cineplexes.add(new CinemaHall("Cathay Cineplex Causeway Point",13));

        cineplexes.get(1).createCinema("Screen1", false);
        cineplexes.get(1).createCinema("Screen2", false);
        cineplexes.get(1).createCinema("Screen3", false);
        cineplexes.get(1).createCinema("Screen4", false);


    }

    //CinemaHall cathy = new CinemaHall("Cathy", 13.5);



    public void bookMovie(){
        System.out.println("Welcome to booking system\nChoose Cineplex\n");

        //while(true){
            int i=0;
            for(CinemaHall cineplex : cineplexes){
                i++;
                System.out.println(i+". "+cineplex.getCineplexName());
            }
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            

        //}


        System.out.println("\n");


    }

}
