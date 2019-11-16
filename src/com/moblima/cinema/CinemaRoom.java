package com.moblima.cinema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import com.moblima.database.DataBase;
import com.moblima.database.IDataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieShowing;

public class CinemaRoom {
    String cineplexName;
    String cinemaName;
    double basicPrice;
    double cinemaPrice;
    int[][] cinemaLayout;

    Boolean suite;

    public CinemaRoom(String cineplexName, String cinemaName, double basicPrice, Boolean suite) {
        this.cineplexName = cineplexName;
        this.cinemaName = cinemaName;
        this.suite = false;
        cinemaPrice = basicPrice;
        if (suite == true) {
            this.suite = true;
            cinemaPrice += 7;
            cinemaLayout = Cineplex.suiteLayout();
        } else {
            cinemaLayout = Cineplex.standardLayout();
        }

        // setLayouts();

    }

    public int[][] getLayout() {
        return cinemaLayout;
    }

    /*
    public void setLayouts() throws IOException {

        
            ArrayList<MovieShowing> showingList = new ArrayList<MovieShowing>();
            showingList = this.getHallOccupation();

            int movieNum =0;

            DataBase.createEmptyTxtFile(cineplexName+"_"+cinemaName+".txt");

            for(int j=0;j<showingList.size();j++)
            {
                //check if layout already exist

                if(showingList.get(j).getCineplex().getCineplexName() == cineplexName && showingList.get(j).getCinemaRoom().getCinemaName() == cinemaName){

                    movieNum++;
                }
            }

            //cinemaLayout = showingList.get(index)

            String [] layout = new String[movieNum];

            int movieField = 0; // counter for single movies

            
            for(int j=0;j<showingList.size();j++){
               // layout[movieField]="afteerrr loop\n";
               //layout[movieField] = new String("");

                //check if movie showing is for this particular screen
                if(showingList.get(j).getCineplex().getCineplexName() == cineplexName && showingList.get(j).getCinemaRoom().getCinemaName() == cinemaName){
                    layout[movieField] = new String("");
                    int [][] arrShowLayout = showingList.get(j).getMovieLayout();
                   // System.out.println("Layout creating:    Movie title:::::"+showingList.get(j).getMovie().getTitle());
                    //layout[movieField] += "\n"+showingList.get(j).getMovie().getTitle();
                    layout[movieField] += "\n"+showingList.get(j).getDate() + "\n";
////change here
                    //cinemaLayout = showingList.get(j).getMovieLayout();

                    //Creating cinema layout
                    // String [] layout = new String[cinemaLayout.length+1];//+1 for ----Screen---
                    char row = 'A';
                    layout[movieField] += "-------------------------SCREEN-------------------------\n";
                    for(int a=0;a<cinemaLayout.length;a++){
                        layout[movieField] += ""+row+" ";
                        for(int b=0;b<cinemaLayout[a].length;b++){
                            //cinemaLayout[a][b] = 0;
                            layout[movieField]+=(arrShowLayout[a][b]+" ");
                        }
                        layout[movieField]+=(row+"\n");
                        row++;
                    }

                    String [] toPass = new String[1];
                    toPass[0] = layout[movieField];
                    DataBase.appendToDataBase(toPass, (cineplexName+"_"+cinemaName+".txt"));

                    movieField++;

                }//end if
            }


            System.out.println("File created");


    }*/

  //  }

    public String [] getLayout(MovieShowing sh){

        List<String> allLayouts = new ArrayList<String>();
        //changes in name
        if(DataBase.ifExists(cineplexName+"_"+cinemaName+"_"+sh.getDate()+".txt"))
        {
            allLayouts = IDataBase.readFromDataBase(cineplexName+"_"+cinemaName+"_"+sh.getDate()+".txt");
        }else{
            String [] layout= new String[cinemaLayout.length+2];
            layout[0] = "";
            return layout;
        }
        
        String [] layout= new String[cinemaLayout.length+2];
        layout[0] = "";
        int bound = -1;
        for(String line : allLayouts)
        {
            if(line.contains(sh.getDate().toString())){

                bound=0;
            
            }
    
            if(bound>=0 && bound<=11)
            {   
                layout[bound]=line+"\n";
                bound++;
            }else if(bound>11)
                break;
            

        }

        return layout;
    }


    public double getCinemaPrice(){
        return cinemaPrice;
    }
    public String getCinemaName(){
        return cinemaName;
    }
    
    public ArrayList<MovieShowing> getHallOccupation()
    {
    	ArrayList<Movie> movies = MovieListing.getMovies();
    	ArrayList<MovieShowing> showings = new ArrayList<MovieShowing>();
    	for(int i =0;i<movies.size();i++)
    	{
    		for(int j =0;j<movies.get(i).getShowings().size();j++)
    		{
    			if(this.equals(movies.get(i).getShowings().get(j).getCinemaRoom())&&new Date().before(movies.get(i).getShowings().get(j).getDate())) 
    				showings.add(movies.get(i).getShowings().get(j));
    		}
    	}
    	return showings;
    }
    public boolean getPremium(){
        return suite;
    }
}