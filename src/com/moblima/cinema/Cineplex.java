package com.moblima.cinema;

import java.util.ArrayList;
/**
 * This class represents the building - the set od cinema rooms
 */
public class Cineplex {
    private String cineplexName;
    private double basicPrice;
    private int forwardScheduling;
    ArrayList<CinemaRoom> cinemas = new ArrayList<CinemaRoom>();
    static ArrayList<Cineplex> cineplexes = new ArrayList<Cineplex>();
    /**
     * This is constructor of Cineplex
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
    
    /**
     * 
     * @return ArrayList of Cineplexes
     */
    public static ArrayList<Cineplex> getCineplexList()	{return cineplexes;}
    
    /**
     * This method returns the CinemaRoom instance which is recognised by its name.
     * @param name the name of the Cinema name
     * @return CinemaRoom instance
     */
    public CinemaRoom getRoomByName(String name)
    {
    	for(int i =0;i<cinemas.size();i++)
    	{
    		if(cinemas.get(i).getCinemaName().equals(name)) return cinemas.get(i);
    	}
    	return null;
    }
    /**
     * Creates the CinemaRoom
     * @param cinemaName 
     * @param suite
     */
    public void createCinema(String cinemaName, Boolean suite){
        cinemas.add(new CinemaRoom(cineplexName, cinemaName, basicPrice, suite));
    }
    /**
     * This method is listing Cinema rooms
     */
    public void listCinemas(){
        for(int i = 1;i<=cinemas.size();i++){
            System.out.println(i+".\t"+cinemas.get(i-1).getCinemaName());
        }
    }
    /**
     * Initializes the premium cinema layout
     * @return integer array of 0
     */
    static int[][] suiteLayout(){
        int [][] suite = new int[10][10];
        for(int i=0;i<suite.length;i++){
            for(int j=0;j<suite[i].length;j++){
                suite[i][j]=0;
            }
        }
        return suite;
    }
    /**
     * Initializes the standard cinema layout
     * @return integer array of 0
     */
    static int[][] standardLayout(){
        int [][] standard = new int[10][20];
        for(int i=0;i<standard.length;i++){
            for(int j=0;j<standard[i].length;j++){
                standard[i][j]=0;
            }
        }
        return standard;
    }
    /**
     * 
     * @return string of CineplexName
     */
    public String getCineplexName(){
        return cineplexName;
    }
    /**
     * 
     * @return price
     */
    public double getBasicPrice(){
        return basicPrice;
    }
    /**
     * 
     * @return integer number which is the length of forward scheduling in days
     */
    public int getForwardScheduling() {return forwardScheduling;}
    

}