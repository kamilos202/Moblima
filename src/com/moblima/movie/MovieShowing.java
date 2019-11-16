package com.moblima.movie;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBase;
import com.moblima.database.IDataBase;

/**
 * MovieShowing is an Entity class.
 * It contains all the information about 1 specific showing of 1 specific movie at 1 specific cinema in 1 specific room at 1 specific time
 * @author Ivo Janssen
 *
 */
public class MovieShowing {
	private Cineplex cinema;
	private CinemaRoom room;
	private Movie movie;
	private Date date;
	private boolean isWeekly,isCopy;
	private boolean hasCreatedWeeklyShowings = false;
	private int scheduleDuration;
	private int [][] layoutArray;
	private String [] layToExport = new String[1];
//	private Date lastDayOfShowing;

	Date today = (Date) Calendar.getInstance().getTime();
	long forwardScheduling;
	Date lastDate;
	
	
	/**
	 * Construct a MovieShowing instance based on a Stringwise representation. This stringwise representation can be retrieved from the database.
	 * Please note that inputting a different format of string can lead to unintended behaviours.
	 * @param details A stringwise representation of the movieshowing: Entry 0 cntains the cineplex. Entry 1 contains the room of the given
	 * cineplex. Entry 2-6 contain the information about the date as given by a Java.Util.Date object: Entry 2 contains the year-1900. Entry 3
	 * contains the month in limits 0-11. Entry 4 contains the day in limits 0-31. Entry5 contains the hours of that day with limits 0-23. Entry
	 * 6 contains the minutes of that hour with limits 0-59. Entry 7 represents a boolean given as a String which represents if the showing will
	 * be held every week or just once. Entry 8 contains the duration of the movie in minutes, inclduding breaks and advertisements.   
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public MovieShowing(String[] details) throws IOException, ParseException
	{
		//System.out.println(details[0]);
		this.cinema = Cineplex.getCineplexByName(details[0]);
		this.room = cinema.getRoomByName(details[1]);
		this.date = new Date(Integer.parseInt(details[2]),Integer.parseInt(details[3]),Integer.parseInt(details[4]),
				Integer.parseInt(details[5]),Integer.parseInt(details[6]));
		this.isWeekly = Boolean.parseBoolean(details[7]);
		this.scheduleDuration = Integer.parseInt(details[8]);
		this.isCopy = false;

		layoutArray = new int[room.getLayout().length][room.getLayout()[0].length]; 

		forwardScheduling = (today.getTime()+cinema.getForwardScheduling())*24*60*60*1000;
		lastDate = new Date(forwardScheduling);
	
		if(date.before(lastDate) && date.after(today)){
			setShowingLayout();
		}
	}



	/**
	 * Construct a new MovieShowing instance during the runtime of the program, based on inputted information.
	 * These inputs can be constructed by AdminBoundary.addShowing()
	 * @param cineplex the cineplex the showing will be held in
	 * @param cinemaroom the room the showing will be held in
	 * @param date the date+time the showing will be held at
	 * @param weekly determines if the showing will be held each week or just once
	 * @param schedule the duration of the movie in minutes including breaks,advertisements etc.
	 * @throws IOException
	 * @throws ParseException
	 */

	public MovieShowing(Cineplex cineplex, CinemaRoom cinemaroom, Date date, boolean weekly, int schedule, boolean isCopy) throws IOException
	{
		this.cinema = cineplex;
		this.room = cinemaroom;
		this.date = date;
		this.isWeekly = weekly;
		this.scheduleDuration = schedule;
		this.isCopy = isCopy;

		forwardScheduling = (today.getTime()+cinema.getForwardScheduling())*24*60*60*1000;
		lastDate = new Date(forwardScheduling);

		if(date.before(lastDate) && date.after(today)){
			setShowingLayout();		
		}

	}

	/**
	 * This method assign seat as occupied.
	 * @param row
	 * @param column
	 */
	public void setOccupied(int row,int column){

		layoutArray[row][column] = 1;
	}

	/**
	 * Get whether a seat is occupied
	 * @param row the row of the seat
	 * @param column the column of the seat
	 * @return 0 is free, 1 is occupied
	 */
	public int getOccupied(int row,int column){

		return layoutArray[row][column];
	}

	/**
	 * Getters and setters for MovieShowing class
	**/
	public Cineplex getCineplex() {return cinema;}
	public CinemaRoom getCinemaRoom() {return room;}
	public Date getDate() {return date;}
	public boolean isWeekly() {return isWeekly;}
	public int getScheduleDuration() {return scheduleDuration;}
	public void setMovie(Movie movie){this.movie = movie;}
	public Movie getMovie(){return movie;}
	public boolean isCopy()	{return isCopy;}
	public boolean hasCreatedWeeklyShowings()	{return hasCreatedWeeklyShowings;}
	public void weeklyShowingsInitiated(boolean initiated) {hasCreatedWeeklyShowings = initiated;}

	public void setLayout(int [][] lay){
		layoutArray = lay;
	}

	public int [][] getMovieLayout(){
		return layoutArray;
	}

	/**
	 * This function take care of setting layout for particular showing. It retrieves existing layout, creates new or creates empty .txt file if needed.
	 * @throws IOException
	 */
	public void setShowingLayout() throws IOException {
		layoutArray = new int[room.getLayout().length][room.getLayout()[0].length]; 
		layoutArray = room.getLayout();
		String [] layout= new String[room.getLayout().length+2];
		layout[0] = "";

		List<String> allLayouts = new ArrayList<String>();
		// if file not exist
        if(!DataBase.ifExists(cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt")){
			DataBase.createEmptyTxtFile(cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt");
		}else{//check if showing already exists
        
			allLayouts = IDataBase.readFromDataBase(cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt");
			
			int bound = -1;
			for(String line : allLayouts)
			{
				if(line.contains(date.toString())){
					bound=0;
				}
		
				if(bound>=0 && bound<=11)
				{   
					layout[bound]=line+"\n";
					bound++;
				}else if(bound>11)
					break;
			}
		}
			if(layout[0] == ""){// if layout not found
				layoutArray = room.getLayout();
				initNewLayout();
			}
			else if(layout[0] !=  ""){// layout found

				int seatFromString;
				for(int o=2;o<room.getLayout().length+2;o++){

					String [] row = new String[12];

					row = layout[o].split(" ");

					for(int p=0;p<room.getLayout()[0].length;p++){
						int seatIndex = 1+p;
						seatFromString = Integer.parseInt(row[seatIndex]);
	
						layoutArray[o-2][p] = seatFromString;
					}
				}
		}		
	}
	/**
	 * This function writes in file empty layout if there is no layout for this show.
	 * @throws IOException
	 */
	private void initNewLayout() throws IOException {

		String [] layoutTxt = new String[1];
		layoutTxt[0] += "\n"+ date + "\n";
		char row = 'A';
		layoutTxt[0] += "-------------------------SCREEN-------------------------\n";
		for(int a=0;a<layoutArray.length;a++){
			layoutTxt[0] += ""+row+" ";
			for(int b=0;b<layoutArray[a].length;b++){
				layoutTxt[0]+=(layoutArray[a][b]+" ");
			}
			layoutTxt[0]+=(row+"\n");
			row++;
		}
		layToExport[0] = layoutTxt[0];
		DataBase.writeToDataBase(layoutTxt, (cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt"));

	}
	/**
	 * This method writes layout of the showing (after changes)
	 */
	public void initLayout(){
		String [] layoutTxt = new String[1];
		layoutTxt[0] += "\n"+ date + "\n";
		char row = 'A';
		layoutTxt[0] += "-------------------------SCREEN-------------------------\n";
		for(int a=0;a<layoutArray.length;a++){
			layoutTxt[0] += ""+row+" ";
			for(int b=0;b<layoutArray[a].length;b++){
				layoutTxt[0]+=(layoutArray[a][b]+" ");
			}
			layoutTxt[0]+=(row+"\n");
			row++;
		}
		DataBase.writeToDataBase(layoutTxt, (cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt"));
		layToExport[0] = layoutTxt[0];
	}
	/**
	 * This method retrieves layouts from txt file
	 * @return layout of the showing in String format
	 */
	public String [] getLayout(){
        List<String> allLayouts = new ArrayList<String>();
        //changes in name
        if(DataBase.ifExists(cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt"))
        {
            allLayouts = IDataBase.readFromDataBase(cinema.getCineplexName()+"_"+room.getCinemaName()+"_"+date+".txt");
        }else{
            String [] layout= new String[room.getLayout().length+2];
            layout[0] = "";
            return layout;
        }
        
        String [] layout= new String[room.getLayout().length+2];
        layout[0] = "";
        int bound = -1;
        for(String line : allLayouts)
        {
            if(line.contains(date.toString())){
                bound=0;
            }
            if(bound>=0 && bound<=11)
            {   
                layout[bound]=line+"\n";
                bound++;
            }else if(bound>11)
                break;
            
        }
        return layout;
    }
	/**
	 * Returns the string of the showing details
	 */
	public String toString()
	{
		return ""+cinema.getCineplexName()+"|"+room.getCinemaName()+"|"+date.getYear() + "|" + date.getMonth() + "|" + date.getDate()+ "|"+ date.getHours() + "|"+ date.getMinutes() + "|" +isWeekly+"|"+scheduleDuration;
	}
}
	

