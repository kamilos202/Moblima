package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBaseCommunication;
import com.moblima.rating.Rating;
import com.moblima.user.Admin;
import com.moblima.user.User;
import com.moblima.util.UserInputs;

public class MovieListing
{
    static ArrayList<Movie> moviesPlaying= new ArrayList<Movie>();
    static ArrayList<Movie> fullMovieArchive = new ArrayList<Movie>();
    static String ended = "No Longer Running";

	public static void createMovies() throws IOException, ParseException {
    	System.out.println("createMovies");
        //DataBaseCommunication data = new DataBaseCommunication();
        Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        map = DataBaseCommunication.getMovies();

        //System.out.print(map);

        for(int i=0;i<map.size();i++)
        {
        	System.out.println("Current iteration: " + i + " and movie title is equal to: " + map.get(i).get(0));
        	String[] showingInfo = map.get(i).get(7).split("/");
        	boolean hasShowings = !showingInfo[0].equals("");
        	System.out.println("SHOWINGINFO: "+showingInfo[0]);
        	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
        	for(int j =0;j<showingInfo.length;j++)
        	{
        		if(hasShowings) showings.add(new MovieShowing(showingInfo[j].split("\\|")));
        	}
        	fullMovieArchive.add(new Movie(map.get(i).get(0), map.get(i).get(1), map.get(i).get(3),
        			map.get(i).get(4), map.get(i).get(2),Integer.parseInt(map.get(i).get(5)),showings,Integer.parseInt(map.get(i).get(6))));
			if(!map.get(i).get(2).equals(ended)) moviesPlaying.add(fullMovieArchive.get(fullMovieArchive.size()-1));
			
			System.out.println("this is what we are looking for: " + fullMovieArchive.get(fullMovieArchive.size()-1).toDataBaseString());
        	for(int j = 0;j<moviesPlaying.size();j++)
        	{
        		System.out.println("Movies added in playing: " + moviesPlaying.get(i).toDataBaseString());
        	}
        	for(int j = 0;j<fullMovieArchive.size();j++)
        	{
        		System.out.println("Movies added in archive: " + fullMovieArchive.get(i).toDataBaseString());
        	}
            if(hasShowings)
            {
            	for(int a=0;a<showingInfo.length;a++){
					showings.get(a).setMovie(moviesPlaying.get(i));
				}
            }
				
        }
        for(int i = 0;i<moviesPlaying.size();i++) createWeeklyShowings(i);
    }

    
    @SuppressWarnings("deprecation")
	public static void createWeeklyShowings(int i) throws IOException, ParseException
    {
    		ArrayList<MovieShowing> currentShowings = moviesPlaying.get(i).getShowings();
			@SuppressWarnings("unchecked")
			ArrayList<MovieShowing>	allShowings = (ArrayList<MovieShowing>) currentShowings.clone(); 
    		for(int j=0;j<currentShowings.size();j++)
    		{
    			if((!currentShowings.get(j).isCopy())&&(!currentShowings.get(j).hasCreatedWeeklyShowings()))
    			{
    				if(moviesPlaying.get(i).getShowings().get(j).isWeekly() && moviesPlaying.get(i).getShowings().get(j).getCineplex().getForwardScheduling() >=7)
        			{
        				Date currentTime = new Date();
        				Date finalSchedule = new Date(currentTime.getTime() - 1000 * (currentTime.getHours()*3600+currentTime.getMinutes()*60+
        						currentTime.getSeconds()) + (long) ((moviesPlaying.get(i).getShowings().get(j).getCineplex().getForwardScheduling()+1)*24*3600*1000));
        				Date nextDate = new Date(moviesPlaying.get(i).getShowings().get(j).getDate().getTime() + (long) 7*24*3600*1000);
        				
        				while(nextDate.before(finalSchedule))
        				{
        					allShowings.add(new MovieShowing(currentShowings.get(j).getCineplex(),currentShowings.get(j).getCinemaRoom(),nextDate,
        							currentShowings.get(j).isWeekly(),currentShowings.get(j).getScheduleDuration(),true));
        					Date dummy = new Date(nextDate.getTime() + (long) 7*24*3600*1000);
        					nextDate = dummy;
        				}
        			}
        			moviesPlaying.get(i).setShowings(allShowings);
        			moviesPlaying.get(i).getShowings().get(j).weeklyShowingsInitiated(true);
    			}
    			
    		}
    		
    }
    
    
    public void printMovieTitle(){
        int i = 1;
        for(Movie movie : moviesPlaying){
            System.out.println(i+".\t"+movie.getTitle());
            i++;
        }
    }
    
    public static ArrayList<Movie> getMovies(){return moviesPlaying;}
    
    public static Movie getMovieByName(String name)
    {
    	for(int i =0;i<moviesPlaying.size();i++)
    	{
    		if(moviesPlaying.get(i).getTitle().contentEquals(name)) return moviesPlaying.get(i);
    	}
    	return null;
    }
    
    public static void addRatingToMovie(User user)
    {
		System.out.println("Please enter the movie you want to give a rating for: ");
		System.out.println("Movie.size::::::::::"+moviesPlaying.size());
		for(int i = 0;i<moviesPlaying.size();i++)
		{
			System.out.println(i+1+": " + moviesPlaying.get(i).getMovieName());
		}
		int choice = UserInputs.getValidIntegerInput()-1;
		if(choice<moviesPlaying.size())
		{

			ArrayList<Rating> currentRatings = moviesPlaying.get(choice).getRatings();
			System.out.println("currentRatingArr'''''''"+currentRatings.size());
			boolean canRate = true;
			for(int j = 0;j<currentRatings.size();j++)
			{
				System.out.println("currratings"+currentRatings.get(j).toString());
				if(currentRatings.get(j).getUser().equals(user))
				{
					System.out.println("Error: You have already rated this movie");
					canRate = false;
				}
			}
			if(canRate)
			{
				System.out.println("What score would you give this movie on a scale from 1 to 5 (decimals allowed): ");
				double score = UserInputs.getValidDoubleInput();
				System.out.println("Do you have any further comments for your rating: ");
				//String dummy = UserInputs.getValidStringInput();
				String description = UserInputs.getValidLineInput();
				//System.out.println("Dummy: " + dummy + " + description" + description);
				if(description.equals("")) description = " ";
		    	if(!((score>=1.0)&&(score<=5.0)))
		    	{
		    		System.out.println("Error Invalid number for rating, ratings should be between 1.0 and 5.0, action cancelled "+ score);
		    	}
		    	else
		    	{
		    		moviesPlaying.get(choice).addRating(user, score, description);
		    		System.out.println("Rating succesfully added to the system");
		    	}
				
			}
		}
		else System.out.println("Error: select a valid movie");
    	
    }
    
    public static void addMovie() throws IOException, ParseException
    {
    	System.out.println("Please enter the title of the movie: ");
    	String newTitle = UserInputs.getValidLineInput();
    	System.out.println("Please enter the synopsis of the movie: ");
    	String newSynopsis = UserInputs.getValidLineInput();
    	System.out.println("Please enter the director: ");
    	String newDirector = UserInputs.getValidLineInput();
    	System.out.println("Please enter the cast: ");
    	String newCast = UserInputs.getValidLineInput();
    	System.out.println("Please enter the movie status: ");
    	String newStatus = UserInputs.getValidLineInput();
    	System.out.println("Please enter the duration of the movie: ");
    	int newDuration = UserInputs.getValidIntegerInput();
    	moviesPlaying.add(new Movie(newTitle,newSynopsis,newDirector,newCast,newStatus, newDuration,new ArrayList<MovieShowing>(),0));
    	fullMovieArchive.add(new Movie(newTitle,newSynopsis,newDirector,newCast,newStatus, newDuration,new ArrayList<MovieShowing>(),0));
    	for(int i =0;i<moviesPlaying.size();i++) System.out.println("all movies currently available: " + moviesPlaying.get(i).toDataBaseString());
    	String[] appendInfo = {moviesPlaying.get(moviesPlaying.size()-1).toDataBaseString()};
    	try {
			DataBaseCommunication.appendToDataBase(appendInfo, "movies.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	boolean addShowings = true;
    	while(addShowings)
    	{
    		System.out.println("Do you want to add a showing to this movie: ");
    		System.out.println("1: Yes");
    		System.out.println("2: No");
    		switch(UserInputs.getValidIntegerInput())
    		{
    			case 1:
    				addShowing(moviesPlaying.get(moviesPlaying.size()-1));
    				break;
    			case 2:
    				addShowings = false;
    				break;
    			default:
    				System.out.println("Please enter either 1 or 2");
    				break;
    		}
    		
    	}
    }
    
    public static int getMovieId(String name)
    {
    	for(int i =0;i<moviesPlaying.size();i++)
    	{
    		if(moviesPlaying.get(i).getMovieName().equals(name)) return i;
    	}
    	return -1;
    }
    
    public static void addShowing(Movie movie) throws IOException, ParseException
    {
    	System.out.println("At what Cineplex will the movie take place: ");
    	ArrayList<Cineplex> cinemas = Cineplex.getCineplexList();
    	for(int i= 0 ;i<cinemas.size();i++)
    	{
    		System.out.println(i+1+": "+cinemas.get(i).getCineplexName());
    	}
    	int choice = UserInputs.getValidIntegerInput();
    	if(choice>0&&choice<=cinemas.size())
    	{
    		Cineplex cinemaSelected = cinemas.get(choice-1);
    		ArrayList<CinemaRoom> rooms = cinemaSelected.getScreens();
    		System.out.println("In what hall will the movie be played: ");
    		for(int i = 0;i<rooms.size();i++)
    		{
    			System.out.println(i+1+": "+rooms.get(i).getCinemaName());
    		}
    		int roomChoice = UserInputs.getValidIntegerInput();
    		if(roomChoice>0&&roomChoice<=rooms.size())
    		{
    			CinemaRoom roomSelected = cinemaSelected.getRoomByName(rooms.get(roomChoice-1).getCinemaName());
    			System.out.println("Please enter the date at which the movie will be played (dd): ");
    			int day = UserInputs.getValidIntegerInput();
    			System.out.println("Please enter the date at which the movie will be played (mm): ");
    			int month = UserInputs.getValidIntegerInput();
    			System.out.println("Please enter the date at which the movie will be played (yyyy): ");
    			int year = UserInputs.getValidIntegerInput();
    			if(UserInputs.isDateValid((day+"/"+month+"/"+year)))
    			{
    				System.out.println("Please enter at which time the movie will be played (hours 0-23): ");
    				int hour = UserInputs.getValidIntegerInput();
    				if(hour>0&&hour<24)
    				{
    					System.out.println("Please enter at which time the movie will be played (minutes 0-60: ");
    					int minutes = UserInputs.getValidIntegerInput();
    					Date date = new Date(year-1900,month-1,day,hour,minutes);
    					System.out.println("Will the movie be displayed each week at the same time: ");
    					System.out.println("1: yes");
    					System.out.println("2: no");
    					int weeklyChoice = UserInputs.getValidIntegerInput();
    					if(weeklyChoice == 1 || weeklyChoice == 2)
    					{
    						boolean weekly;
    						if(weeklyChoice == 1) weekly = true;
    						else weekly = false;
    						System.out.println("What is the total movie duration including breaks and advertisements: ");
    						int showingDuration = UserInputs.getValidIntegerInput();
    						ArrayList<MovieShowing> currentShowings = movie.getShowings();
    						String oldInfo = movie.toDataBaseString();
    						currentShowings.add(new MovieShowing(cinemaSelected,roomSelected,date,weekly,showingDuration,false));
    						movie.setShowings(currentShowings);
    						System.out.println("new showing: " + currentShowings.get(currentShowings.size()-1).toString());
    						DataBaseCommunication.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    						createWeeklyShowings(getMovieId(movie.getMovieName()));
    					}
    					else System.out.println("Invalid input, action cancelled");
    				}
    				else System.out.println("Invalid hour");
    			}
    			else System.out.println("Invalid date");
    		}
    		else System.out.println("Invalid Cinemaroom selection");
    	}
    	else System.out.println("Invalid cinema selection");
    }
    
    public static void removeShowing(Movie movie)
    {
    	System.out.println("Which showing do you want to remove: ");
    	int count = 0;
    	for(int i = 0;i<movie.getShowings().size();i++)
    	{
    		if(!movie.getShowings().get(i).isCopy())
    		{
    			System.out.println(count+1+": "+movie.getShowings().get(count));
    			count++;
    		}
    		
    	}
    	int choice = UserInputs.getValidIntegerInput();
    	if(choice>0&&choice<=count)
    	{
    		ArrayList<MovieShowing> currentShowings = movie.getShowings();
    		currentShowings.remove(choice-1);
    		movie.setShowings(currentShowings);
    	}
    	else System.out.println("Invalid choice, no showing selected");
    }
    
    public static void removeMovie(Movie movie)
    {
    	String oldInfo = movie.toDataBaseString();
    	moviesPlaying.remove(movie);
    	movie.setStatus(ended);
    	for(int i =0;i<fullMovieArchive.size();i++)
    	{
    		System.out.println(i+ ":  contains: " + fullMovieArchive.get(i).toDataBaseString());
    		if(fullMovieArchive.get(i).getTitle().equals(movie.getTitle()))
    		{
    			System.out.println("Updated");
    			fullMovieArchive.get(i).setStatus(ended);
    		}
    	}
    	DataBaseCommunication.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    	//moviesPlaying.remove(movie);
    	//DataBaseCommunication.replaceInDataBase(oldInfo, movie.toDataBaseString(), path);
    }

    public static void editMovie(Movie movie) throws IOException, ParseException
    {
    	boolean editing = true;
    	String oldInfo = movie.toDataBaseString();
    	int index1 = moviesPlaying.indexOf(movie);
    	int index2 = fullMovieArchive.indexOf(movie);
    	System.out.println(index1+" and "+index2);
    	while(editing)
    	{
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
    				System.out.println("Please enter new movie title: ");
    				movie.setTitle(UserInputs.getValidLineInput());
    				break;
    			case 2:
    				System.out.println("Please enter new movie synopsis: ");
    				movie.setSynopsis(UserInputs.getValidLineInput());
    				break;
    			case 3:
    				System.out.println("Please enter new movie director: ");
    				movie.setDirector(UserInputs.getValidLineInput());
    				break;
    			case 4:
    				System.out.println("Please enter new movie cast: ");
    				movie.setCast(UserInputs.getValidLineInput());
    				break;
    			case 5:
    				System.out.println("Please enter new movie status: ");
    				movie.setStatus(UserInputs.getValidLineInput());
    				break;
    			case 6:
    				System.out.println("Please enter new movie type: ");
    				movie.setType(UserInputs.getValidLineInput());
    				break;
    			case 7:
    				System.out.println("Please enter new movie duration: ");
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
    				break;
    			default:
    				System.out.println("Please enter a number between 0-9");
    				break;
    		}
    	}
    	
    	moviesPlaying.set(index1, movie);
    	fullMovieArchive.set(index2, movie);
    	DataBaseCommunication.replaceInDataBase(oldInfo, movie.toDataBaseString(), "movies.txt");
    	
    }

}
