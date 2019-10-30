package com.moblima.movie;

import java.util.Date;

import com.moblima.cinema.CinemaRoom;
import com.moblima.cinema.Cineplex;

public class MovieShowing 
{
	private Cineplex cinema;
	private CinemaRoom room;
	private Movie movie;
	private Date date;
	private boolean isWeekly;
	private double scheduleDuration;
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
		this.scheduleDuration = Double.parseDouble(details[8]);
	}
	
	public MovieShowing(Cineplex cineplex, CinemaRoom cinemaroom, Date date, boolean weekly, double schedule)
	{
		this.cinema = cineplex;
		this.room = cinemaroom;
		this.date = date;
		this.isWeekly = weekly;
		this.scheduleDuration = schedule;
		layoutArray = room.getLayout();
	}

	public void setOccupied(int row,int column){
		layoutArray[row][column] = 1;
	}
	
	
	public String toString()
	{
		return ""+cinema.getCineplexName()+","+room.getCinemaName()+","+date.toString()+","+isWeekly+","+scheduleDuration;
	}
	
	public Cineplex getCineplex() {return cinema;}
	public CinemaRoom getCinemaRoom() {return room;}
	public Date getDate() {return date;}
	public boolean isWeekly() {return isWeekly;}
	public double getScheduleDuration() {return scheduleDuration;}
	public void setMovie(Movie movie){this.movie = movie;}
	public Movie getMovie(){return movie;}
	
}
