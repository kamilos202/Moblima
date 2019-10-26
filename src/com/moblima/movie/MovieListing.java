package com.moblima.movie;

import java.util.ArrayList;
import java.util.List;

import com.moblima.database.IDataBase;

public class MovieListing implements IDataBase
{
    public void showMovies(){

        List<String> array = new List<String>();
        array = (newIDataBase.readFromDataBase("movies.txt"));
        System.out.println(a);
    }
    

    public void writeToDataBase(String[] lines){
        ;
    }


}
