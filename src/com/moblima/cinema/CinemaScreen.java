package com.moblima.cinema;

import com.moblima.database.DataBaseCommunication;

public class CinemaScreen
{
    String cineplexName;
    String cinemaName;
    double basicPrice;
    double cinemaPrice;
    int [][] cinemaLayout;

    Boolean suite;

    public CinemaScreen(String cineplexName, String cinemaName, double basicPrice, Boolean suite){
        this.cineplexName = cineplexName;
        this.cinemaName = cinemaName;
        this.suite = false;
        cinemaPrice=basicPrice;
        if(suite == true){
            this.suite = true;
            cinemaPrice+=7;
            cinemaLayout = Cineplex.suiteLayout();
        }else{
            cinemaLayout = Cineplex.standardLayout();
        }

        if(DataBaseCommunication.ifExists(cineplexName+"_"+cinemaName+".txt"))
        {
            System.out.println("File already exist");
        }
        else
        {
            //Creating cinema layout
            String [] layout = new String[cinemaLayout.length+1];
            char row = 'A';
            layout[0] = "-------------------------SCREEN-------------------------\n";
            for(int i=0;i<cinemaLayout.length;i++){
                layout[i+1] = ""+row;
                for(int j=0;j<cinemaLayout[i].length;j++){
                    cinemaLayout[i][j] = 0;
                    layout[i+1]+=(" "+cinemaLayout[i][j]+" ");
                }
                layout[i+1]+=(row+"\n");
                row++;
            }
            //layout[0] = "";
            DataBaseCommunication.writeToDataBase(layout, (cineplexName+"_"+cinemaName+".txt"));
            System.out.println("File created");


        }


    }



    public double getCinemaPrice(){
        return cinemaPrice;
    }
    public String getCinemaName(){
        return cinemaName;
    }
}
