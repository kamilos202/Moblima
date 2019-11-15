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
import com.moblima.movie.MovieListing;
import com.moblima.util.UserInputs;

public class AdminBoundary extends UserBoundary
{
	private User userLogedIn;
	
	public AdminBoundary(Admin admin)
	{
		userLogedIn = admin;
	}
	
	@Override
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
			System.out.println("Please choose the status of the movie: ");
			String[] possibleStatus = MovieControl.getValidMovieStatus();
			String status = "";
			for(int i=0;i<possibleStatus.length;i++) System.out.println(i+1+": " + possibleStatus[i]);
			boolean isValidSelection = false;
			while(!isValidSelection)
			{
				int choice = UserInputs.getValidIntegerInput();
				if(choice>0&&choice<=possibleStatus.length)
				{
					if(choice == possibleStatus.length) 
					{
						System.out.println("Please enter the custom movie status: ");
						status = UserInputs.getValidLineInput(); 
					}
					else status = possibleStatus[choice-1];
					
					isValidSelection = true;
				}
				else System.out.println("Please select one of the aformentioned options");
			}
			movieInfo.add(status);
			movieInfo.add(director);
			movieInfo.add(cast);
			
			System.out.println("Please enter the duration of the movie: ");
			movieInfo.add(""+UserInputs.getValidIntegerInput());
			movieInfo.add("0");//Inititial ticket sale is 0
			movieInfo.add(""); //Initially there are no showings
			MovieControl.addMovie(movieInfo);
			
			System.out.println("Do you want to add a showing to this movie: ");
			if(UserInputs.getValidBooleanInput()) addShowing(DataBase.fullMovieArchive.get(DataBase.fullMovieArchive.size()-1));
		}
		else System.out.println("Error: another movie with the same title already exists, action cancelled");
		
	}
	
	private void addShowing(Movie movie) //provide with movie = null to choose a movie from list
	{
		boolean addShowings = true;
		while(addShowings)
		{
			if(movie == null)
			{
				movie = super.chooseMovieFromList();
			}
			
			System.out.println("At what cineplex will the movie take place: ");
			Cineplex cinema = super.chooseCinpexFromList();
			
			System.out.println("At which screen will the movie be shown: ");
			CinemaRoom room = super.chooseRoomFromList(cinema);
			
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
	
	private void removeMovie()
	{
		System.out.println("which movie do you want to remove: ");
		Movie movie = super.chooseMovieFromList();
		
			MovieControl.removeMovie(movie);
		
	}
	
	private void editMovie() throws IOException, ParseException
	{
		System.out.println("which movie do you want to edit: ");
		ArrayList<Movie> movies = MovieListing.getMovies();
		for(int i = 0;i<movies.size();i++)
		{
			System.out.println(i+1+ ": " + movies.get(i).getTitle());
		}
		int choice = UserInputs.getValidIntegerInput();
		if(choice>0&&choice<=movies.size())
		{
			MovieListing.editMovie(movies.get(choice-1));
		}
		else System.out.println("Error selected number is not in the list");
		
	}

	
	private void giveAdminPermissions()
	{
		System.out.println("Enter user to give admin permissions");
		userLogedIn.username = UserInputs.getValidStringInput();
		List<String> currentUsers = DataBase.readFile("users.txt");
		String[] updatedUsers = new String[currentUsers.size()];
		boolean userFound = false;
		boolean alreadyAdmin = false;
		for(int i = 0;i<currentUsers.size();i++)
		{
			if(currentUsers.get(i).split(";")[0].equals(userLogedIn.username))
			{
				if(Boolean.parseBoolean(currentUsers.get(i).split(";")[2]))
				{
					alreadyAdmin = true;
				}
				else currentUsers.set(i, currentUsers.get(i).replace(";false;", ";true;"));
				userFound = true;
			}
			updatedUsers[i] = currentUsers.get(i)+"\n";
		}
		if(!userFound)
		{
			System.out.println("Action failed: Could not find user with name: " + userLogedIn.username);
		}
		else
		{
			if(alreadyAdmin) System.out.println("User " + userLogedIn.username + " is already an admin");
			else 
			{
				System.out.println("Are you sure you want to give the user " + userLogedIn.username + " administrator permissions?");
				System.out.println("1: yes");
				System.out.println("2: no");
				boolean notConfirmed = true;
				while(notConfirmed)
				{
					switch(UserInputs.getValidIntegerInput())
					{
						case 1:
							notConfirmed = false;
							DataBase.writeToDataBase(updatedUsers, "users.txt");
							DataBase.users.remove(User.getUserByName(userLogedIn.username));
							String[] userInfo = DataBase.getUserDetails(userLogedIn.username).split(";");
							DataBase.users.add(new Admin(userLogedIn.username,userInfo[1],Integer.parseInt(userInfo[3])));
							System.out.println(userLogedIn.getUsername() + " succesfully promoted to administrator");
							break;
						case 2:
							notConfirmed = false;
							System.out.println("Operation cancelled");
							break;
						default:
							System.out.println("Please enter either 1 or 2");
							break;
					}
				}
			}
		}
	}
}
