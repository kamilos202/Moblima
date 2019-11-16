package com.moblima.user;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * MovieGoer is an entity class which extends from the generalized User entity.
 * Its main purpose is to differentiate between a admin in order to perform the standard MovieGoer functions.
 * Next to that the moviegoer class can in future expension of the program be used to store more information regarding to the moviegoer object, for
 * Example: user seat preferences could be stored or creditcard details can be saved to make payments faster.
 * @author Ivo Janssen
 *
 */
public class MovieGoer extends User {

	Date birthdate;
	MovieGoer current;
	
	UserBoundary boundary;

	/**
	 * Constructor of the MovieGoer
	 * @param username
	 * @param password
	 * @param id
	 * @param birthdate
	 * @throws ParseException
	 */
	public MovieGoer(String username, String password, int id, String birthdate) throws ParseException {
		super(username,password,id);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		this.birthdate = formatter.parse(birthdate);
		boundary = new MovieGoerBoundary(this);
	}

	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}
	/**
	 * Returns username
	 */
	@Override
	public String getUsername() {
		return this.username;
	}
	/**
	 * Returns Date of the birthday
	 */
	@Override
	public Date getBirthdate()
	{
		return this.birthdate;
	}
}
