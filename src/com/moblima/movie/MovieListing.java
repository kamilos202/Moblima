package com.moblima.movie;

import java.util.ArrayList;
import java.util.List;

import com.moblima.database.IDataBase;

public class MovieListing implements IDataBase
{
    public void showMovies(){

        System.out.println(IDataBase.readFromDataBase("movies.txt").get(0));
    }
    

    public void writeToDataBase(String[] lines){
        ;
    }


}
