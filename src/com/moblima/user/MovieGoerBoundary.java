package com.moblima.user;

public class MovieGoerBoundary extends UserBoundary
{

	User userLogedIn;
	public MovieGoerBoundary(MovieGoer movieGoer)
	{
		userLogedIn = movieGoer;
	}
	
	@Override
	public void performActions()
	{
		
	}

}
