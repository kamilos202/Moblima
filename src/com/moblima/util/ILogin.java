package com.moblima.util;

/**
 * Interface needed for login.
 * Now almost empty. Can be useful in the future 
 */
public interface ILogin 
{
	/**
	 * Function verifies the login
	 * @param username username of the user
	 * @param password password of the user
	 * @return boolean value
	 */
	public static boolean verifyLogin(String username,String password) {
		return false;
	}
}
