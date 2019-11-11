package com.moblima.movie;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBaseCommunication;
import com.moblima.database.IDataBase;

public class MovieShowing {
	private Cineplex cinema;
	private CinemaRoom room;
	private Movie movie;
	private Date date;
	private boolean isWeekly,isCopy;
	private boolean hasCreatedWeeklyShowings = false;
	private int scheduleDuration;
	private int [][] layoutArray;
//	private Date lastDayOfShowing;

	Date today = (Date) Calendar.getInstance().getTime();
	long forwardScheduling;
	Date lastDate;
	/**
	 * 
	 * @param details
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
		//layoutArray = room.getLayout();

		//System.out.println(date.toString());

		layoutArray = new int[room.getLayout().length][room.getLayout()[0].length]; 

		forwardScheduling = (today.getTime()+cinema.getForwardScheduling())*24*60*60*1000;
		lastDate = new Date(forwardScheduling);
	
		if(date.before(lastDate) && date.after(today)){
			setShowingLayout();
		}
	}



	/**
	 * 
	 * @param cineplex
	 * @param cinemaroom
	 * @param date
	 * @param weekly
	 * @param schedule
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
	 * Getters and setters for MovieShowing class
	 */
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
        if(!DataBaseCommunication.ifExists(cinema.getCineplexName()+"_"+room.getCinemaName()+".txt")){
			DataBaseCommunication.createEmptyTxtFile(cinema.getCineplexName()+"_"+room.getCinemaName()+".txt");
		}else{//check if showing already exists
        
			allLayouts = IDataBase.readFromDataBase(cinema.getCineplexName()+"_"+room.getCinemaName()+".txt");
			
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
		DataBaseCommunication.appendToDataBase(layoutTxt, (cinema.getCineplexName()+"_"+room.getCinemaName()+".txt"));

	}
	/**
	 * 
	 */
	public String toString()
	{
		return ""+cinema.getCineplexName()+"|"+room.getCinemaName()+"|"+date.getYear() + "|" + date.getMonth() + "|" + date.getDate()+ "|"+ date.getHours() + "|"+ date.getMinutes() + "|" +isWeekly+"|"+scheduleDuration;
	}
	

}
	

