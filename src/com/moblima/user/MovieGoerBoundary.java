package com.moblima.user;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import com.moblima.booking.Booking;
import com.moblima.booking.BookingPage;
import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieControl;
import com.moblima.movie.MovieShowing;
import com.moblima.util.UserInputs;
import sun.lwawt.macosx.CSystemTray;

/**
 * Plays the role of the User's (MovieGoer's) boundary class 
 */
public class MovieGoerBoundary extends UserBoundary
{

	BookingPage page;

	User userLogedIn;
	public MovieGoerBoundary(MovieGoer movieGoer)
	{
		userLogedIn = movieGoer;
		page = new BookingPage();
	}
	
	/**
	 * This function takes the imput 
	 */
	@Override
	public void performActions() throws IOException
	{
		System.out.println("these are the moviegoer actions");
		boolean loggedIn = true;
		while(loggedIn)
		{
			System.out.println("\n=========== MovieGoer MENU ===========");
			System.out.println("1: Rate a movie");
			System.out.println("2: Display available movies in all Cineplexes");
			System.out.println("3: Book a movie");
			System.out.println("4: Retrieve booking history");
			System.out.println("5: List movies ordered by sold tickets");
			System.out.println("6: List movies by ratings");
			System.out.println("8: Logout");
			System.out.println("\n=============================================");


			
			int actionChoice = UserInputs.getValidIntegerInput();
			
			switch(actionChoice)
			{
				case 1:
					giveRating();
					break;
				case 2:
					page.listMovies();
					break;
				case 3:
					this.bookMovie();
					break;
				case 4:
					Booking.retrieveHistory(userLogedIn.getUsername());
					break;
				case 5:
					page.listTicketOrderMovies();
					break;
				case 6:
					page.listRatingOrderMovies();
					break;
				case 8:
					loggedIn = false;
					break;
				default:
					System.out.println("Please enter one of the valid options");
					break;
			}
		}
	}
	
	public void giveRating()
	{
		System.out.println("Please enter the movie you want to give a rating for: ");
		Movie movie = chooseMovieFromList();
		
		
		if(MovieControl.canUserRate(movie, userLogedIn))
		{
			System.out.println("What score would you give this movie on a scale from 1 to 5 (decimals allowed): ");
			double score = UserInputs.getValidDoubleInput();
			System.out.println("Do you have any further comments for your rating: ");
			String description = UserInputs.getValidLineInput();
			if(description.equals("")) description = " ";
	    	if(!((score>=1.0)&&(score<=5.0)))
	    	{
	    		System.out.println("Error Invalid number for rating, ratings should be between 1.0 and 5.0, action cancelled "+ score);
	    	}
	    	else
	    	{
	    		MovieControl.addRating(movie, userLogedIn, score, description);
	    		System.out.println("Rating succesfully added to the system");
	    	}
			
		}
	}



	public void bookMovie() throws IOException {
        System.out.println("\nWelcome to booking system\n\tChoose Cineplex\n");

        while(true){
            int i=0;
            for(Cineplex cineplex : page.getCineplexes()){
                i++;
                System.out.println("\t\t"+i+". "+cineplex.getCineplexName());
            }

            System.out.print("\nI want to go to Cineplex:   ");
            
            int choice = UserInputs.getValidIntegerInput();
            if(choice<=page.getCineplexes().size() && choice>=1){
                System.out.println("Your chosen Cineplex is "+page.getCineplexes().get(choice-1).getCineplexName()+"\n");
            }else{
                System.out.println("Error while choosing the CIneplex. Try again!");
                break;
            }

            System.out.println("Cineplex you have already chosen displays following movies:\n");
            ArrayList<Movie> tempArrMoviesPerCineplex = new ArrayList<Movie>();
            ///     Display movies which are currently showing in chosen cinema (Cineplex)
            ArrayList<Movie> moviesArray= new ArrayList<Movie>();
            moviesArray = DataBase.moviesPlaying;
            int num = 1;
            for(Movie mov : moviesArray){
                ArrayList<MovieShowing> showingsOfMov = new ArrayList<MovieShowing>();
                showingsOfMov = mov.getShowings();
                // checking if movie is displaying in chosen cinema
                for(int h=0;h<showingsOfMov.size();h++){

                    if(showingsOfMov.get(h).getMovie().getTitle() == mov.getTitle() && showingsOfMov.get(h).getCineplex().getCineplexName() == page.getCineplexes().get(choice-1).getCineplexName() && showingsOfMov!=null){
//                        System.out.println("Test: "+ mov==null);
                        System.out.print("\t"+num+". "+mov.getTitle());
                        tempArrMoviesPerCineplex.add(mov);
                        num++;
                        break;
                    }
                }
            }

            System.out.print("\nI want to watch:   ");
            int choiceMov = UserInputs.getValidIntegerInput();
            if(choice<=page.getCineplexes().size() && choice>=1){
                if(tempArrMoviesPerCineplex.get(choiceMov-1).getStatus() == "Coming Soon" || tempArrMoviesPerCineplex.get(choiceMov-1).getStatus() == "End Of Showing"){
                    System.out.println("Movie does not have any showing. Sorry");
                }else{
                    System.out.println("\nYour chosen movie is "+tempArrMoviesPerCineplex.get(choiceMov-1).getTitle()+"\n");
                }
            }else{
                System.out.println("Error while choosing the Movie. Try again!");
                break;
            }

            //CHECK FOR PREMIUM ROOMS
            String premiumRooms="| ";
            for(CinemaRoom cr : page.getCineplexes().get(choice-1).getScreens()){
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
            long ltime=(today.getTime()+page.getCineplexes().get(choice-1).getForwardScheduling())*24*60*60*1000;
            Date tmrr=new Date(ltime);
            
            ArrayList<MovieShowing> showsForUser = new ArrayList<MovieShowing>();
            for(MovieShowing show :tempArrMoviesPerCineplex.get(choiceMov-1).getShowings()){
                if(  (show.getDate().after(today)) &&  (show.getDate().before(tmrr)) && show.getCineplex().getCineplexName() == page.getCineplexes().get(choice-1).getCineplexName() ){
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

            String [] layoutInArray = new String[showsForUser.get(choiceDate-1).getLayout().length+2];
            layoutInArray = showsForUser.get(choiceDate-1).getLayout();

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

            Boolean validBooking = true;
            for(int seat=0;seat<seatsNum;seat++){

                char r = UserInputs.gatValidCharInput();
                int c = UserInputs.getValidIntegerInput();

                if(showsForUser.get(choiceDate-1).getOccupied((int)r-65, c-1)!=1){
                    showsForUser.get(choiceDate-1).setOccupied((int)r-65, c-1);
                }else{
                    validBooking=false;
                }
                
            }
            if(validBooking){
                showsForUser.get(choiceDate-1).initLayout();;
            }else{
                System.out.println("Sorry! Seat you have already chosen is occupied.");
                break;
            }
            



            System.out.println("\n\n\t\tGreat! You have just book your seat/s.\n\tSee ya there!");

			page.createBookingAndPrintReceipt(showsForUser.get(choiceDate-1),userLogedIn,seatsNum);

            String oldMovieInfo = MovieControl.toDateBaseString(tempArrMoviesPerCineplex.get(choiceMov-1));
     
            tempArrMoviesPerCineplex.get(choiceMov-1).setSale(tempArrMoviesPerCineplex.get(choiceMov-1).getTicketsSold()+seatsNum);

            DataBase.replaceInDataBase(oldMovieInfo, MovieControl.toDateBaseString(tempArrMoviesPerCineplex.get(choiceMov-1)), "movies.txt");

            break;
        }
        System.out.println("\n");
    }
}
