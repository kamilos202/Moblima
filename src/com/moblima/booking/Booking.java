package com.moblima.booking;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieShowing;
import com.moblima.user.MovieGoer;
/**
 * Represents booking history
 */
public class Booking 
{
    private int numOfTickets;
    private CinemaRoom cinemaRoom;
    private Cineplex cineplex;
    private double totalPrice;
    private double discount;
    private Movie movie;
    private MovieGoer someone;

    public Booking(MovieShowing showing, MovieGoer someone, int numOfTickets){
        cinemaRoom = showing.getCinemaRoom();
        cineplex = showing.getCineplex();
        movie = showing.getMovie();
        this.someone = someone;

    }

    public void printAndSaveReceipt(){
        
    }

}
