package com.moblima.cinema;

import java.util.ArrayList;
/**
 * 
 */
public class Cineplex {
    private String cineplexName;
    private double basicPrice;
    private int forwardScheduling;
    ArrayList<CinemaRoom> cinemas = new ArrayList<CinemaRoom>();
    static ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();
    /**
     * 
     * @param cineplexName
     * @param basicPrice
     * @param schedule
     */
    public Cineplex(String cineplexName, double basicPrice, int schedule)
    {
        this.cineplexName = cineplexName;
        this.basicPrice = basicPrice;
        this.forwardScheduling = schedule;
        cineplexes.add(this);
    }
    /**
     * 
     * @return list of cinema rooms (screens) in this cineplex
     */
    public ArrayList<CinemaRoom> getScreens(){
        return cinemas;
    }
    /**
     * Static method for getting Cineplex instance by its name.
     * @param name
     * @return Cineplex instance
     */
    public static Cineplex getCineplexByName(String name)
    {
    	for(int i =0;i<cineplexes.size();i++)
    	{
    		if(cineplexes.get(i).getCineplexName().equals(name)) return cineplexes.get(i);
    	}
    	return null;
    }
    
    public CinemaRoom getRoomByName(String name)
    {
    	for(int i =0;i<cinemas.size();i++)
    	{
    		if(cinemas.get(i).getCinemaName().equals(name)) return cinemas.get(i);
    	}
    	return null;
    }
    

    public void createCinema(String cinemaName, Boolean suite){
        cinemas.add(new CinemaRoom(cineplexName, cinemaName, basicPrice, suite));
    }

    public void listCinemas(){
        for(int i = 1;i<=cinemas.size();i++){
            System.out.println(i+".\t"+cinemas.get(i-1).getCinemaName());
        }
    }


    static int[][] suiteLayout(){
        int [][] suite = new int[10][10];
        for(int i=0;i<suite.length;i++){
            for(int j=0;j<suite[i].length;j++){
                suite[i][j]=0;
            }
        }
        return suite;
    }

    static int[][] standardLayout(){
        int [][] standard = new int[10][20];
        for(int i=0;i<standard.length;i++){
            for(int j=0;j<standard[i].length;j++){
                standard[i][j]=0;
            }
        }
        return standard;
    }

    public String getCineplexName(){
        return cineplexName;
    }

    public double getBasicPrice(){
        return basicPrice;
    }
    
    public int getForwardScheduling() {return forwardScheduling;}
    

}