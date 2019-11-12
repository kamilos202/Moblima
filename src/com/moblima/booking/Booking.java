package com.moblima.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private double totalPrice=0;
    private double discount;
    private Movie movie;
    private String username;
    private Date birthday;
    private double ageDiscount = 3;
    private Date showingTime;
    Date today = (Date) Calendar.getInstance().getTime();

    public Booking(MovieShowing showing, String username, Date birthday, int numOfTickets) {
        cinemaRoom = showing.getCinemaRoom();
        cineplex = showing.getCineplex();
        movie = showing.getMovie();
        showingTime = showing.getDate();
        this.username = username;
        this.birthday = birthday;
        this.numOfTickets = numOfTickets;

    }

	public void printAndSaveReceipt() throws IOException {
        if(!DataBaseCommunication.ifExists(username + "_bookingHistory.txt")){
			DataBaseCommunication.createEmptyTxtFile(username + "_bookingHistory.txt");
		}

        String [] toSave = new String[1];
        System.out.println("Date of booking: "+today);
        toSave[0] += "\n"+"Date of booking: "+today.toString();
        System.out.println("\nCineplex: "+cineplex.getCineplexName()+", Cinema: "+cinemaRoom.getCinemaName());
        toSave[0] += "\nCineplex: "+cineplex.getCineplexName()+", Cinema: "+cinemaRoom.getCinemaName();
        System.out.println("Showing time: "+showingTime.toString());
        toSave[0] += "\nShowing time: "+showingTime.toString();

        if(cinemaRoom.getPremium()){
            System.out.println("Premium cinema: Yes");
            toSave[0] += "\nPremium room: Yes";
        }else{
            System.out.println("Premium cinema: No");
            toSave[0] += "\nPremium room: No";
        }
        System.out.println("Price for choosen cinema: "+cinemaRoom.getCinemaPrice());
        toSave[0] += "\nPrice for choosen cinema: "+cinemaRoom.getCinemaPrice();
        totalPrice+=cinemaRoom.getCinemaPrice()*numOfTickets;

        System.out.println("Number of tickets: "+numOfTickets);
        toSave[0] += "\nNumber of tickets: "+numOfTickets;

        LocalDate currentDate = LocalDate.now();
        // People younger than 18yrs old and those older than 65 years old getting discount
        Date d1 = Date.from(currentDate.minusYears(18).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date d2 = Date.from(currentDate.minusYears(65).atStartOfDay(ZoneId.systemDefault()).toInstant());

       
        if(birthday.after(d1) || birthday.after(d2)){
            System.out.println("Discount regarding user's age: "+ageDiscount*numOfTickets);
            toSave[0] += "\nDiscount regarding user's age: "+ageDiscount*numOfTickets;
            totalPrice-=ageDiscount*numOfTickets;
        }else{
            System.out.println("Discount regarding user's age: "+ 0);
            toSave[0] += "\nDiscount regarding user's age: "+ 0;
        }

        //  check if there is a weekend
        if(today.getDay() == 5 || today.getDay() == 6 || today.getDay() == 0){
            System.out.println("Type of sale: Weekend purchase: +$"+2*numOfTickets);
            toSave[0] += "\nType of sale: Weekend purchase: +$"+2*numOfTickets;
            totalPrice += 2*numOfTickets;
        }else{
            System.out.println("Type of sale: Cheap Weekday (Mon-Thu) purchase  ");
            toSave[0] += "\nType of sale: Cheap Weekday (Mon-Thu) purchase";
        }
        
        System.out.println("\t\tTotal price of purchase: "+totalPrice);
        toSave[0] += "\n\t\tTotal price of purchase: "+totalPrice;

        toSave[0] += "\n";
        DataBaseCommunication.appendToDataBase(toSave, (username + "_bookingHistory.txt"));
    }

    public static void retrieveHistory(String user){
        System.out.println("===============Booking history for "+user+"===============\n\n");
        if(!DataBaseCommunication.ifExists(user + "_bookingHistory.txt")){
			System.out.println("There is no booking history for this user.");
		}else{
            List<String> history = new ArrayList<String>();
            history = DataBaseCommunication.readFile(user + "_bookingHistory.txt");

            for(int i=0;i<history.size();i++){
                if(history.get(i)==null){
                    System.out.println("\n");
                    continue;
                }
                String hist = history.get(i);
                hist = hist.replaceAll("null", "--------------------------------");
                System.out.println(hist);
                System.out.println("--------------------------------");
            }
        }
        System.out.println("=========================================================\n\n");

    }

}
