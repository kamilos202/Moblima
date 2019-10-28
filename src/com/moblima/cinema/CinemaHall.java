package com.moblima.cinema;

import java.util.ArrayList;

public class CinemaHall {
    private String cineplexName;
    private double basicPrice;
    ArrayList<Cinema> cinemas = new ArrayList<Cinema>();

    public CinemaHall(String cineplexName, double basicPrice)
    {
        this.cineplexName = cineplexName;
        this.basicPrice = basicPrice;
    }

    public void createCinema(String cinemaName, Boolean suite){
        cinemas.add(new Cinema(cineplexName, cinemaName, basicPrice, suite));
    }

    public void listCinemas(){
        for(int i = 1;i<=cinemas.size();i++){
            System.out.println(i+".\t"+cinemas.get(i-1).getCinemaName());
        }
    }


    static int[][] suiteLayout(){
        int [][] suite = new int[10][10];
        return suite;
    }

    static int[][] standardLayout(){
        int [][] standard = new int[10][20];
        return standard;
    }

    public String getCineplexName(){
        return cineplexName;
    }

    public double getBasicPrice(){
        return basicPrice;
    }
    

}
