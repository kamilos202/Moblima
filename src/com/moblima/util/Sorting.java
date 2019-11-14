package com.moblima.util;

import java.util.Comparator;

import com.moblima.movie.Movie;

/**
 * 
 */
public class Sorting 
{

    	//-----------------------------------------------------------------
	// Sorts the specified array of objects using the selection
	// sort algorithm.
	//-----------------------------------------------------------------
	public static Movie [] selectionSort (Movie[] list)
	{
		int min;
		Movie temp;
		for (int index = 0; index < list.length-1; index++)
		{
			min = index;
			for (int scan = index+1; scan < list.length; scan++)
				if (list[scan].compareToByTicketsSold(list[min]) == false)
					min = scan;
			// Swap the values
			temp = list[min];
			list[min] = list[index];
			list[index] = temp;
        }
        
        return list;
    }
    
    public static Movie [] selectionSortByRatings (Movie[] list)
	{
		int min;
		Movie temp;
		for (int index = 0; index < list.length-1; index++)
		{
			min = index;
			for (int scan = index+1; scan < list.length; scan++)
				if (list[scan].compareToByRatings(list[min]) == false)
					min = scan;
			// Swap the values
			temp = list[min];
			list[min] = list[index];
			list[index] = temp;
        }
        
        return list;
	}
	//-----------------------------------------------------------------
	// Sorts the specified array of objects using the insertion
	// sort algorithm.
	//-----------------------------------------------------------------
	public static Movie [] insertionSort (Movie[] list)
	{
		for (int index = 1; index < list.length; index++)
		{
			Movie key = list[index];
			int position = index;
			// Shift larger values to the right
			while (position > 0 && key.compareToByRatings(list[position-1]) == true)
			{
				list[position] = list[position-1];
				position--;
			}
				list[position] = key;
        }
        
        return list;
	}
        // Used for sorting in ascending order of 
    // roll number 
/*
	public int compareRating(Movie o1, Movie o2) {
		// TODO Auto-generated method stub
		if (o1.getRating() < o2.getRating()) 
			return -1;
        if (o1.getRating() > o2.getRating())
        	return 1;
        return 0;
	}
*/	
//	public int compareTicketSale(Movie o1, Movie o2) {
//		// TODO Auto-generated method stub
//		return o1.getTicketSale - o2.getTicketSale;
//	}
   
/*
    public int compareDate(MovieShowing o1, MovieShowing o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
    */

//	@Override
//	public int compare(Movie o1, Movie o2) {
//		// TODO Auto-generated method stub
//		return 0;
//	} 
}
