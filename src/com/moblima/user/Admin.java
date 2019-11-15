package com.moblima.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.moblima.database.DataBase;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.util.UserInputs;

public class Admin extends User {

	private UserBoundary boundary;
	
	
	public Admin(String username, String password, int id) {
		super(username,password,id);
		boundary = new AdminBoundary(this);
		System.out.println("I am an admin");
	}


	
	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}


}
