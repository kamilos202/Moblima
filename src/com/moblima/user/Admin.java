package com.moblima.user;

/**
 * Admin is an entity class which extends from the generalized User entity.
 * Its main purpose is to differentiate between a moviegoer in order to perform authorized admin functions.
 * Next to that the admin class can in future expension of the program be used to store more information regarding to the admin object, for
 * Example: information about which cineplexes an admin can influence can be saved in the admin entity
 * @author Ivo Janssen
 *
 */
public class Admin extends User {

	private UserBoundary boundary;
	
	
	/**
	 * 
	 * @param username the username of the admin
	 * @param password his password
	 * @param id his userID
	 */
	public Admin(String username, String password, int id) {
		super(username,password,id);
		boundary = new AdminBoundary(this);
	}
	
	/**
	 * This boundary defines the functions the admin can perform, by initiating this boundary as type AdminBoundary, when LoggedIn this user will
	 * have access to all Admin functions
	 */
	@Override
	public UserBoundary getBoundary()
	{
		return boundary;
	}


}
