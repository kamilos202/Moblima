package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieControl;
import com.moblima.movie.MovieShowing;
import com.moblima.util.UserInputs;

/**
 * The Admin Boundary is a boundary class which defines all the functions which can be performed by the admin.
 * This class regulates all the inputs from the admin and passes it on to the control classes in order to manipulate all the objects
 * @author Ivo Janssen
 *
 */
public class AdminBoundary extends UserBoundary
{
	private User userLogedIn;
	
	/**
	 * Construct the boundary based on user
	 * @param admin the admin which is logged in
	 */
	public AdminBoundary(Admin admin)
	{
		userLogedIn = admin;
	}
	
	@Override
	/**
	 * This method is called by the MoblimaApp when an admin logs in to the program.
	 * By calling this method the Admin function sequence is initiated
	 */
	public void performActions() throws IOException, ParseException {
		boolean loggedIn = true;
		while (loggedIn) {
			System.out.println("What action do you want to perform: ");
			System.out.println("1: Add a movie to the movie listing");
			System.out.println("2: remove a movie from the movie listing");
			System.out.println("3: Edit a movie from the movie listing");
			System.out.println("4: Give a user administrator permissions");
			System.out.println("5: logout");

			int actionChoice = UserInputs.getValidIntegerInput();
			switch (actionChoice) {
			case 1:
				addMovie();
				break;
			case 2:
				removeMovie();
				break;
			case 3:
				editMovie();
				break;
			case 4:
				giveAdminPermissions();
				break;
			case 5:
				loggedIn = false;
				break;
			default:
				System.out.println("Please enter one of the valid options");
				break;
			}
		}

	}
	
	/**
	 * Creates a new movie status based on the list of valid movie statuses
	 * @return valid status String
	 */
	private String createStatus()
	{
		
		String[] possibleStatus = MovieControl.getValidMovieStatus();
		String status = "";
		for(int i=0;i<possibleStatus.length;i++) System.out.println(i+1+": " + possibleStatus[i]);
		int choice = UserInputs.getValidIntegerInput(0,possibleStatus.length+1);
		if(choice == possibleStatus.length) 
		{
			System.out.println("Please enter the custom movie status: ");
			status = UserInputs.getValidLineInput(); 
		}
		else status = possibleStatus[choice-1];
		
		return status;
	}
	
	/**
	 * Create a new movie object based on user inputs
	 * @throws IOException
	 * @throws ParseException
	 */
	private void addMovie() throws IOException, ParseException
	{
		List<String> movieInfo = new ArrayList<String>();
		System.out.println("Please enter the title of the movie");
		String title = UserInputs.getValidLineInput();
		if(MovieControl.isNewTitleValid(title))
		{
			movieInfo.add(title);
			System.out.println("Please enter the synopsis of the movie: ");
			movieInfo.add(UserInputs.getValidLineInput());
			System.out.println("Please enter the director of the movie: ");
			String director = (UserInputs.getValidLineInput());
			System.out.println("Please enter the cast of the movie: ");
			String cast = (UserInputs.getValidLineInput());
			System.out.println("Please enter the duration of the movie: ");
			String duration = ""+UserInputs.getValidIntegerInput();
			System.out.println("Please choose the status of the movie: ");
			String status = createStatus();
			movieInfo.add(status);
			movieInfo.add(director);
			movieInfo.add(cast);
			
			
			movieInfo.add(duration);
			movieInfo.add("0");//Inititial ticket sale is 0
			movieInfo.add(""); //Initially there are no showings
			MovieControl.addMovie(movieInfo);
			
			System.out.println("Do you want to add a showing to this movie: ");
			if(UserInputs.getValidBooleanInput()) addShowing(DataBase.fullMovieArchive.get(DataBase.fullMovieArchive.size()-1));
		}
		else System.out.println("Error: another movie with the same title already exists, action cancelled");
		
	}
	
	/**
	 * Add a showing to a movie based on user inputs
	 * @param movie to add the showing to
	 */
	private void addShowing(Movie movie) //provide with movie = null to choose a movie from list
	{
		boolean addShowings = true;
		while(addShowings)
		{
			if(movie == null)
			{
				movie = chooseMovieFromList();
			}
			
			System.out.println("At what cineplex will the movie take place: ");
			Cineplex cinema = chooseCinpexFromList();
			
			System.out.println("At which screen will the movie be shown: ");
			CinemaRoom room = chooseRoomFromList(cinema);
			
			Date date = UserInputs.getValidDate(true);
			
			System.out.println("Will this showing be the same each week: ");
			boolean isWeekly = UserInputs.getValidBooleanInput();
			
			System.out.println("What is the total duration of the movie including breaks and advertisements: ");
			int duration = UserInputs.getValidIntegerInput(0, 99999999);
			
			MovieControl.addShowing(movie, cinema, room, date, isWeekly, duration);
			
			System.out.println("Do you want to add another showing for this movie: ");
			addShowings = UserInputs.getValidBooleanInput();
		}
	}
	
	/**
	 * remove a movie specified by inputs of the user
	 */
	private void removeMovie()
	{
		System.out.println("which movie do you want to remove: ");
		Movie movie = chooseMovieFromList();
		
			MovieControl.removeMovie(movie);
		
	}
	
	/**
	 * Edit a movie based on the inputs of the user
	 * @throws IOException
	 * @throws ParseException
	 */
	private void editMovie() throws IOException, ParseException
	{
		boolean editing = true;
		Movie movie = chooseMovieFromList();
		
		while(editing)
    	{
			String oldInfo = MovieControl.toDateBaseString(movie);
    		System.out.println("What movie information do you want to edit?");
    		System.out.println("1: Movie title");
    		System.out.println("2: synopsis");
    		System.out.println("3: director");
    		System.out.println("4: cast");
    		System.out.println("5: status");
    		System.out.println("6: movie type");
    		System.out.println("7: duration");
    		System.out.println("8: Add a movieShowing");
    		System.out.println("9: Remove a movieShowing");
    		System.out.println("0: Save changes");
    		int choice = UserInputs.getValidIntegerInput();
    		switch(choice)
    		{
    			case 1:
    				System.out.println("Please enter the new movie title: ");
    				movie.setTitle(UserInputs.getValidLineInput());
    				break;
    			case 2:
    				System.out.println("Please enter the new movie synopsis: ");
    				movie.setSynopsis(UserInputs.getValidLineInput());
    				break;
    			case 3:
    				System.out.println("Please enter the new director: ");
    				movie.setDirector(UserInputs.getValidLineInput());
    				break;
    			case 4:
    				System.out.println("Please enter the new cast: ");
    				movie.setCast(UserInputs.getValidLineInput());
    				break;
    			case 5:
    				System.out.println("Please select the new status");
    				movie.setStatus(createStatus());
    				break;
    			case 6:
    				System.out.println("Please enter the new movie type: ");
    				movie.setType(UserInputs.getValidLineInput());
    				break;
    			case 7:
    				System.out.println("Please enter the new movie duration");
    				movie.setDuration(UserInputs.getValidIntegerInput());
    				break;
    			case 8:
    				addShowing(movie);
    				break;
    			case 9:
    				removeShowing(movie);
    				break;
    			case 0:
    				editing = false;
    				System.out.println("Changes saved!");
    				break;
    			default:
    				System.out.println("Please enter one of the options provided");
    				break;
    		}
    		System.out.println("Writing to database: ");
    		DataBase.replaceInDataBase(oldInfo, MovieControl.toDateBaseString(movie), "movies.txt");
    	}
	}
	
	/**
	 * Select a showing which is not created based on weekly showings (an original one saved in the database)
	 * @param movie movie to get orinal showings from
	 * @return all showings which are not a copy from the given movie.
	 */
	private MovieShowing getShowingNoCopy(Movie movie)
	{
		ArrayList<MovieShowing> originalShowings = new ArrayList<MovieShowing>();
		for(int i = 0;i<movie.getShowings().size();i++)
		{
			if(!movie.getShowings().get(i).isCopy()) originalShowings.add(movie.getShowings().get(i));
		}
		
		System.out.println("Please select a showing: ");
		for(int j = 0;j<originalShowings.size();j++)
		{
			System.out.println(j+1+": " + originalShowings.get(j).toString());
		}
		
		return originalShowings.get(UserInputs.getValidIntegerInput(0,originalShowings.size()+1)-1);
	}
	
	/**
	 * Remove a showing based on inputs from the user
	 * @param movie to remove a showing from
	 */
	private void removeShowing(Movie movie)
	{
		System.out.println("Which showing do you want to remove: ");
    	MovieShowing showing = getShowingNoCopy(movie);
    	MovieControl.removeShowing(movie, showing);
	}

	/**
	 * Give a moviegoer admin permissions based on user inputs
	 */
	private void giveAdminPermissions()
	{
		System.out.println("Enter username to give admin permission: ");
		String promotionName = UserInputs.getValidLineInput();
		if(UserControl.doesUserExist(promotionName))
		{
			User user = UserControl.getUserByName(promotionName);
			if(user instanceof MovieGoer)
			{
				System.out.println("Are you sure you want to promote " + promotionName + " to admin: ");
				if(UserInputs.getValidBooleanInput())
				{
					UserControl.makeUserAdmin(user.getUsername());
					System.out.println(promotionName + " succesfully promoted to admin");
				}
				else System.out.println("Action cancelled");
			}
			else System.out.println("Error: User is already an admin, action cancelled");
		}
		else System.out.println("Error: User doesn't exist in system, action cancelled");
	}
	
	
	
}
