package com.moblima.cinema;

import java.util.ArrayList;

public class Cineplex {
    private String cineplexName;
    private double basicPrice;
    ArrayList<CinemaScreen> cinemas = new ArrayList<CinemaScreen>();

    public Cineplex(String cineplexName, double basicPrice)
    {
        this.cineplexName = cineplexName;
        this.basicPrice = basicPrice;
    }

    public void createCinema(String cinemaName, Boolean suite){
        cinemas.add(new CinemaScreen(cineplexName, cinemaName, basicPrice, suite));
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
