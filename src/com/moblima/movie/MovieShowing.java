package com.moblima.movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;
import com.moblima.database.DataBaseCommunication;
import com.moblima.database.IDataBase;

public class MovieShowing 
{
	private Cineplex cinema;
	private CinemaRoom room;
	private Movie movie;
	private Date date;
	private boolean isWeekly,isCopy;
	private boolean hasCreatedWeeklyShowings = false;
	private int scheduleDuration;
	private int [][] layoutArray;
	
	@SuppressWarnings("deprecation")
	public MovieShowing(String[] details)
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

		List<String> allLayouts = new ArrayList<String>();
        if(DataBaseCommunication.ifExists(cinema+"_"+room+".txt"))
        {
			allLayouts = IDataBase.readFromDataBase(cinema+"_"+room+".txt");
			
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
		//////finding layout

			if(layout[0] !=  ""){
				int [][] layoutToBePassedToMovieShowing = new int[room.getLayout().length][room.getLayout()[0].length];
				layoutToBePassedToMovieShowing = room.getLayout();
				//System.out.println(layt[0]);

				for(int o=2;o<room.getLayout().length+2;o++){

					String [] row = new String[12];
					row = layout[o].split(" ");

					for(int p=0;p<room.getLayout()[0].length;p++){
						int seatIndex = 1+p;

						//System.out.print(row[seatIndex]);


						//cinemaLayout[]
					// System.out.print(row[seatIndex]);

					if(row[seatIndex] == "1" || row[seatIndex] == "0"){
							layoutToBePassedToMovieShowing[o-2][p] = Integer.parseInt(row[seatIndex]);
					}
						System.out.print(layoutToBePassedToMovieShowing[o-2][p]);
					// String seat = layt[seatIndex];
						//cinemaLayout[o][p] = Integer.parseInt();
					}
					System.out.println("\n");
				}
				//showingList.get(j).setLayout(layoutToBePassedToMovieShowing);
				layoutArray = layoutToBePassedToMovieShowing;

        }else{
			layoutArray = room.getLayout();
        }
	}
	//System.out.println("''''''''"+layoutArray[2][2]);

}
	
	public MovieShowing(Cineplex cineplex, CinemaRoom cinemaroom, Date date, boolean weekly, int schedule, boolean isCopy)
	{
		this.cinema = cineplex;
		this.room = cinemaroom;
		this.date = date;
		this.isWeekly = weekly;
		this.scheduleDuration = schedule;
		this.isCopy = isCopy;

		layoutArray = new int[room.getLayout().length][room.getLayout()[0].length]; 

		List<String> allLayouts = new ArrayList<String>();
        if(DataBaseCommunication.ifExists(cinema+"_"+room+".txt"))
        {
			allLayouts = IDataBase.readFromDataBase(cinema+"_"+room+".txt");
			
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
		//////finding layout

			if(layout[0] !=  ""){
				int [][] layoutToBePassedToMovieShowing = new int[room.getLayout().length][room.getLayout()[0].length];
				layoutToBePassedToMovieShowing = room.getLayout();
				//System.out.println(layt[0]);

				for(int o=2;o<room.getLayout().length+2;o++){

					String [] row = new String[12];
					row = layout[o].split(" ");

					for(int p=0;p<room.getLayout()[0].length;p++){
						int seatIndex = 1+p;

					if(row[seatIndex] == "1" || row[seatIndex] == "0"){
							layoutToBePassedToMovieShowing[o-2][p] = Integer.parseInt(row[seatIndex]);
					}
						System.out.print(layoutToBePassedToMovieShowing[o-2][p]);
					// String seat = layt[seatIndex];
						//cinemaLayout[o][p] = Integer.parseInt();
					}
					System.out.println("\n");
				}
				//showingList.get(j).setLayout(layoutToBePassedToMovieShowing);
				layoutArray = layoutToBePassedToMovieShowing;

        }else{
            layoutArray = room.getLayout();
        }
        
	}		
	System.out.println("''''''''"+layoutArray[2][3]);


	}

	public void setOccupied(int row,int column){

		layoutArray[row][column] = 1;
	}
	
	
	public String toString()
	{
		return ""+cinema.getCineplexName()+"|"+room.getCinemaName()+"|"+date.getYear() + "|" + date.getMonth() + "|" + date.getDate()+ "|"+ date.getHours() + "|"+ date.getMinutes() + "|" +isWeekly+"|"+scheduleDuration;
	}
	
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
	
}
