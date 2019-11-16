package com.moblima.util;
import com.moblima.movie.Movie;
/**
 * Class which takes care of sorting
 */
public class Sorting 
{

    //-----------------------------------------------------------------
	// Sorts the specified array of objects using the selection
	// sort algorithm.
	//-----------------------------------------------------------------
	/**
	 * Sorts movies by number of tickets sold
	 * @param list
	 * @return
	 */
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
    /**
	 * Sorts movies by average ratings
	 * @param list
	 * @return
	 */
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
	// not used in the program
	// might be useful in the future
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
}