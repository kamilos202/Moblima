package com.moblima.booking;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBaseCommunication;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieShowing;
import com.moblima.util.UserInputs;

public class BookingPage {
    private String movieGoer;
    private Date birthday;
    public BookingPage(String movieGoer,Date birthday){
        this.birthday = birthday;
        this.movieGoer = movieGoer;
    }
    static ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();

    public static void initiateCinemas() throws IOException {
        cineplexes.add(new Cineplex("The Cathay Cineplex",13.5,14));

        cineplexes.get(0).createCinema("Screen1", false);
        cineplexes.get(0).createCinema("Screen2", false);
        cineplexes.get(0).createCinema("Screen3", true);

        cineplexes.add(new Cineplex("Cathay Cineplex Causeway Point",13,14));

        cineplexes.get(1).createCinema("Screen1", false);
        cineplexes.get(1).createCinema("Screen2", false);
        cineplexes.get(1).createCinema("Screen3", false);
        cineplexes.get(1).createCinema("Screen4", false);
        
        //MovieListing.createMovies();

        //set layout
        
        //for(int a=0;a<cineplexes.size();a++){
        //    ArrayList<CinemaRoom> screens = new ArrayList<CinemaRoom>();
        //    screens = cineplexes.get(a).getScreens();
        //    for(int b=0;b<screens.size();b++){
        //        screens.get(b).setLayouts();
        //    }
       // }
        
        //ArrayList<MovieShowing> occupation = cineplexes.get(0).getRoomByName("Screen1").getHallOccupation();
        
    }
    
    public void showShowings()
    {
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

    public void bookMovie() throws IOException {
        System.out.println("\nWelcome to booking system\n\tChoose Cineplex\n");

        while(true){
            int i=0;
            for(Cineplex cineplex : cineplexes){
                i++;
                System.out.println("\t\t"+i+". "+cineplex.getCineplexName());
            }

            System.out.print("\nI want to go to Cineplex:   ");
            
            int choice = UserInputs.getValidIntegerInput();
            if(choice<=cineplexes.size() && choice>=1){
                System.out.println("Your chosen Cineplex is "+cineplexes.get(choice-1).getCineplexName()+"\n");
            }else{
                System.out.println("Error while choosing the CIneplex. Try again!");
                break;
            }

            System.out.println("Cineplex you have already chosen displays following movies:\n");
            ArrayList<Movie> tempArrMoviesPerCineplex = new ArrayList<Movie>();
            ///     Display movies which are currently showing in chosen cinema (Cineplex)
            ArrayList<Movie> moviesArray= new ArrayList<Movie>();
            moviesArray = MovieListing.getMovies();
            int num = 1;
            for(Movie mov : moviesArray){
                ArrayList<MovieShowing> showingsOfMov = new ArrayList<MovieShowing>();
                showingsOfMov = mov.getShowings();
                // checking if movie is displaying in chosen cinema
                for(int h=0;h<showingsOfMov.size();h++){
                    if(showingsOfMov.get(h).getMovie().getTitle() == mov.getTitle() && showingsOfMov.get(h).getCineplex().getCineplexName() == cineplexes.get(choice-1).getCineplexName() && showingsOfMov!=null){
                        System.out.print("\t"+num+". "+mov.getTitle());
                        tempArrMoviesPerCineplex.add(mov);
                        num++;
                        break;
                    }
                }
            }

            System.out.print("\nI want to watch:   ");
            int choiceMov = UserInputs.getValidIntegerInput();
            if(choice<=cineplexes.size() && choice>=1){
                if(tempArrMoviesPerCineplex.get(choiceMov-1).getStatus().contains("Coming soon") || tempArrMoviesPerCineplex.get(choiceMov-1).getStatus().contains("End Of Showing")){
                    System.out.println("\nMovie does not have any showing. Sorry\nChoose different movie or Cineplex!\n");
                    continue;
                }else{
                    System.out.println("\nYour chosen movie is "+tempArrMoviesPerCineplex.get(choiceMov-1).getTitle()+"\n");
                }
            }else{
                System.out.println("Error while choosing the Movie. Try again!");
                break;
            }


            //CHECK FOR PREMIUM ROOMS
            String premiumRooms="| ";
            for(CinemaRoom cr : cineplexes.get(choice-1).getScreens()){
                if(cr.getPremium()){
                    premiumRooms+=cr.getCinemaName()+" |";
                }
            }
            if(premiumRooms!="| ")
            {
                System.out.println("For this Cineplex Premium Cinema Halls are available in following rooms: "+"\n"+premiumRooms);
            }

            System.out.println("\nYour movie showings:   ");

            int f=1;
            Date today = (Date) Calendar.getInstance().getTime();
            long ltime=(today.getTime()+cineplexes.get(choice-1).getForwardScheduling())*24*60*60*1000;
            Date tmrr=new Date(ltime);
            
            ArrayList<MovieShowing> showsForUser = new ArrayList<MovieShowing>();
            for(MovieShowing show :tempArrMoviesPerCineplex.get(choiceMov-1).getShowings()){
                if(  (show.getDate().after(today)) &&  (show.getDate().before(tmrr)) && show.getCineplex().getCineplexName() == cineplexes.get(choice-1).getCineplexName() ){
                    System.out.println(f+". "+"| "+show.getCinemaRoom().getCinemaName()+" |"+show.getDate());
                    showsForUser.add(show);
                    f++;
                }
            }

            System.out.print("\nType here the number of Movie show you want to choose: ");
            int choiceDate = UserInputs.getValidIntegerInput();
            if(choiceDate<=showsForUser.size() && choiceDate>=1){
                System.out.println("\nYour chosen movie slot is on "+showsForUser.get(choiceDate-1).getDate()+"\n");
            }else{
                System.out.println("Error while choosing the Movie. Try again!");
                break;
            }

            String [] layoutInArray = new String[showsForUser.get(choiceDate-1).getCinemaRoom().getLayout().length+2];
            layoutInArray = showsForUser.get(choiceDate-1).getCinemaRoom().getLayout(showsForUser.get(choiceDate-1));

            System.out.println("\nChose your seat:  \n1 - already occupied\n0 - free\n");

            for(int u=0;u<showsForUser.get(choiceDate-1).getCinemaRoom().getLayout().length+2;u++){
                System.out.println(layoutInArray[u]);

            }

            System.out.print("How many seats you want to book?");
            int seatsNum;
            while(true){
                seatsNum = UserInputs.getValidIntegerInput();

                if(seatsNum<1){
                    System.out.println("Please enter valid input. Try again!");
                }else if(seatsNum>10){
                    System.out.println("You can book maximum 10 seats per booking. Sorry :( Try again!");
                }
                else{
                    break;
                }
            }

            System.out.println("Type respectively letter of chosen row and number of column example: (A 1)");


            for(int seat=0;seat<seatsNum;seat++){

                char r = UserInputs.gatValidCharInput();
                int c = UserInputs.getValidIntegerInput();

                showsForUser.get(choiceDate-1).setOccupied((int)r-65, c-1);
                
            }
            showsForUser.get(choiceDate-1).getCinemaRoom().setLayouts();



            System.out.println("\n\n\t\tGreat! You have just book your seat/s.\n\tSee ya there!");

            Booking booking= new Booking(showsForUser.get(choiceDate-1),movieGoer, birthday, seatsNum);
            booking.printAndSaveReceipt();
/////////////////////////////////////////////////
            String oldMovieInfo = tempArrMoviesPerCineplex.get(choiceMov-1).toDataBaseString();
     
            tempArrMoviesPerCineplex.get(choiceMov-1).setSale(tempArrMoviesPerCineplex.get(choiceMov-1).getTicketsSold()+seatsNum);

            DataBaseCommunication.replaceInDataBase(oldMovieInfo, tempArrMoviesPerCineplex.get(choiceMov-1).toDataBaseString(), "movies.txt");
            //chosen time slot tempArrMoviesPerCineplex.get(choiceMov-1).getShowings().get(choiceDate-1).
            //System.out.println(tempArrMoviesPerCineplex.get(choiceMov-1).getShowings().get(choiceDate-1).getCinemaRoom().getLayout(tempArrMoviesPerCineplex.get(choiceMov-1).getShowings().get(choiceDate-1)));
            
            //chosen movie tempArrMoviesPerCineplex.get(choiceMov-1).getTitle()
            break;
        }

        System.out.println("\n");
    }



    public int cineplexesNum(){
        return cineplexes.size();
    }
}