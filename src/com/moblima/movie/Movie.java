package com.moblima.movie;

import java.util.ArrayList;

public class Movie 
{
    private String title;
    private String synopString;
    private String director;
    private String cast;
    private String status;
    private double rating;
    private String movieType;
    private double duration;
    private ArrayList<MovieShowing> showings;


    public Movie(String title, String synopString, String director, String cast, String status, double duration,ArrayList<MovieShowing> showings){

        this.title = title;
        this.synopString = synopString;
        this.director = director;
        this.cast = cast;
        this.status = status;
        this.rating = 0.0;  //to be retrieved from rating.txt
        this.duration = duration;
        this.showings = showings;
    }

    
    public String getMovieName(){
        return title;
    }
    public String getTitle(){
        return title;
    }
    public String getSynop(){
        return synopString;
    }
    public String getCast(){
        return cast;
    }
    public String getDirector(){
        return director;
    }
    public String getStatus(){
        return status;
    }
    public double getRating(){
        return rating;
    }

    public ArrayList<MovieShowing> getShowings() {return showings;}
    
    public void setShowings(ArrayList<MovieShowing> newShowings) {showings = newShowings;}
    

}
