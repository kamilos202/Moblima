package com.moblima.booking;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBaseCommunication;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieShowing;

/**
 * Represents booking history
 */
public class Booking {
    private int numOfTickets;
    private CinemaRoom cinemaRoom;
    private Cineplex cineplex;
    private double totalPrice;
    private double discount;
    private Movie movie;
    static private String username;
    private Date birthday;
    Date today = (Date) Calendar.getInstance().getTime();

    public Booking(MovieShowing showing, String username, Date birthday, int numOfTickets) {
        cinemaRoom = showing.getCinemaRoom();
        cineplex = showing.getCineplex();
        movie = showing.getMovie();
        this.username = username;
        this.birthday = birthday;

    }

	public void printAndSaveReceipt() throws IOException {
        if(!DataBaseCommunication.ifExists(username + "_bookingHistory.txt")){
			DataBaseCommunication.createEmptyTxtFile(username + "_bookingHistory.txt");
		}

        String [] toSave = new String[1];
        System.out.println("Date of booking: "+today);
        toSave[0] += "\n"+today.toString();
        System.out.println("\nCineplex: "+cineplex.getCineplexName()+", Cinema: "+cinemaRoom.getCinemaName());
        toSave[0] += "\nCineplex: "+cineplex.getCineplexName()+", Cinema: "+cinemaRoom.getCinemaName();
        if(cinemaRoom.getPremium()){
            System.out.println("Premium cinema: Yes");
            toSave[0] += "\nPremium room: Yes";
        }else{
            System.out.println("Premium cinema: No");
            toSave[0] += "\nPremium room: No";
        }
        System.out.println("Standard price for choosen cinema: "+cinemaRoom.getCinemaPrice());
        toSave[0] += "\nStandard price for choosen cinema: "+cinemaRoom.getCinemaPrice();


        toSave[0] += "\n";
        DataBaseCommunication.appendToDataBase(toSave, (username + "_bookingHistory.txt"));
    }

    public static void retrieveHistory(String user){
        if(!DataBaseCommunication.ifExists(user + "_bookingHistory.txt")){
            System.out.println(user + "_bookingHistory.txt");
			System.out.println("There is no booking history for this user.");
		}else{
            List<String> history = new ArrayList<String>();
            history = DataBaseCommunication.readFile(user + "_bookingHistory.txt");

            for(int i=0;i<history.size();i++){
                if(history.get(i)==null){
                    System.out.println("\n");
                    continue;
                }
                System.out.println(history.get(i));
            }
        }
    }

}
