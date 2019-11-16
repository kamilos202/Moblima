package com.moblima.booking;
import java.io.IOException;
import java.util.ArrayList;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieShowing;
import com.moblima.rating.Rating;
import com.moblima.user.MovieGoer;
import com.moblima.user.User;
import com.moblima.util.Sorting;
import com.moblima.util.UserInputs;

public class BookingPage {


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
        
    }

    public ArrayList<Cineplex> getCineplexes(){
        return cineplexes;
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

	public void listTicketOrderMovies(){

		ArrayList<Movie> movies = new ArrayList<>();
		movies = DataBase.moviesPlaying;
		Movie [] moviesArr = new Movie[movies.size()];

		for(int i=0;i<movies.size();i++){
			moviesArr[i] = movies.get(i);
		}

		moviesArr = Sorting.selectionSort(moviesArr);

		for(int i=0;i<moviesArr.length;i++){
			System.out.println(i+1+". "+moviesArr[i].getTitle());
		}
	}

	public void listRatingOrderMovies(){
		ArrayList<Movie> movies = new ArrayList<>();
		movies = DataBase.moviesPlaying;
		Movie [] moviesArr = new Movie[movies.size()];

		for(int i=0;i<movies.size();i++){
			moviesArr[i] = movies.get(i);
        }

		moviesArr = Sorting.selectionSortByRatings(moviesArr);

		for(int i=0;i<moviesArr.length;i++){
			System.out.println(i+1+". "+moviesArr[i].getTitle());
		}
	}

    public int cineplexesNum(){
        return cineplexes.size();
    }

    /**
	 * Shows movies and all information about them.
	 */
	public void listMovies(){
		ArrayList<Movie> movies = new ArrayList<>();
		movies = DataBase.moviesPlaying;

		for(int i = 0; i<movies.size() ; i++){
			System.out.println((i+1)+". "+movies.get(i).getTitle());
		}
		System.out.println("Do you want to find out more about any movie? (y/n)");
		char ans = UserInputs.gatValidCharInputForAnswer();
		
		if(ans == 'y'){
			while(true){
				System.out.println("Type the number of the movie you want to get more information about\nor type any other number to get back to previous menu:");

				int movieChoice = UserInputs.getValidIntegerInput();

				if(movieChoice>=1 && movieChoice<=movies.size()){

					System.out.printf(UserInputs.format,"Title: ",movies.get(movieChoice-1).getTitle());
					System.out.printf(UserInputs.format,"Director: ",movies.get(movieChoice-1).getDirector());
					System.out.printf(UserInputs.format,"Cast: ",movies.get(movieChoice-1).getCast());
					System.out.printf(UserInputs.format,"Description: ",movies.get(movieChoice-1).getSynop());
					System.out.printf(UserInputs.format,"Rating: ",movies.get(movieChoice-1).getRating());
					System.out.printf(UserInputs.format,"Status: ",movies.get(movieChoice-1).getStatus());
					System.out.println("\n");

					while(true){
						System.out.println("Do you want to see previous ratings? y/n");
						char ansR = UserInputs.gatValidCharInputForAnswer();
						if(ansR == 'y'){
							ArrayList<Rating> ratings = new ArrayList<Rating>();
							ratings = movies.get(movieChoice-1).getRatings();
							for(int i = 0;i<ratings.size();i++){
								System.out.println("\n\n"+ratings.get(i).getUser().getUsername()+"\n"+ratings.get(i).getDescription()+"\n\n");
							}
							break;
						}else{
							break;
						}
					}
				}else{
					break;
				}
			}
		}

    }
    
    /**
     * Initiates new Booking
     * 
     * @param showing
     * @param user
     * @param seats
     * @throws IOException
     */
    public void createBookingAndPrintReceipt(MovieShowing showing, User user, int seats) throws IOException {
        Booking booking= new Booking(showing,user.getUsername(), user.getBirthdate(), seats);
        booking.printAndSaveReceipt();
    }
}