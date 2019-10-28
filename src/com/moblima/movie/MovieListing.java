package com.moblima.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moblima.database.DataBaseCommunication;

public class MovieListing
{
    ArrayList<Movie> movies= new ArrayList<Movie>();

    public void createMovies(){
        DataBaseCommunication data = new DataBaseCommunication();
        Map<Integer,List<String>> map = new HashMap<Integer,List<String>>();
        map = data.getMovies();

        //System.out.print(map);

        for(int i=0;i<map.size();i++)
        {
            movies.add(new Movie(map.get(i).get(0), map.get(i).get(1), map.get(i).get(2), map.get(i).get(3), map.get(i).get(4)));
        }
        
    }

    public void printMovieTitle(){
        int i = 1;
        for(Movie movie : movies){
            System.out.println(i+".\t"+movie.getTitle());
            i++;
        }
    }
    



}
