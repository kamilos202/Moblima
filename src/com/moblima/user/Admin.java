package com.moblima.user;

import java.util.List;

import com.moblima.database.DataBaseCommunication;
import com.moblima.util.UserInputs;

public class Admin extends User 
{
	String username,password;
	int id;

	public Admin(String username, String password,int id)
	{
		this.username = username;
		this.password = password;
		this.id = id;
		System.out.println("I am an admin");
	}
	
	@Override
	public String getUsername() {return username;}
	
	@Override
	public void performActions()
	{
		boolean loggedIn = true;
		while(loggedIn)
		{
			System.out.println("What action do you want to perform: ");
			System.out.println("1: Add a movie to the movie listing");
			System.out.println("2: remove a movie from the movie listing");
			System.out.println("3: Edit a movie from the movie listing");
			System.out.println("4: Change system settings");
			System.out.println("5: Show top 5 movies by rating");
			System.out.println("6: Show top 5 movies by ticketsale");
			System.out.println("7: Give a user administrator permissions");
			System.out.println("8: logout");
			
			int actionChoice = UserInputs.getValidIntegerInput();
			switch(actionChoice)
			{
				case 1:
					addMovie();
					break;
				case 2:
					removeMovie();
					break;
				case 3:
					editMovie();
					break;
				case 4:
					changeSystemSettings();
					break;
				case 5:
					showBestByRating();
					break;
				case 6:
					showBestByTicketSale();
					break;
				case 7:
					giveAdminPermissions();
					break;
				case 8:
					loggedIn = false;
					break;
				default:
					System.out.println("Please enter one of the valid options");
					break;
			}
		}
		
	}
	
	private void addMovie()
	{
		System.out.println("add");
	}
	
	private void removeMovie()
	{
		System.out.println("remove");
	}
	
	private void editMovie()
	{
		System.out.println("edit");
	}
	
	private void changeSystemSettings()
	{
		System.out.println("change");
	}
	
	private void showBestByRating()
	{
		System.out.println("rating");
	}
	
	private void showBestByTicketSale()
	{
		System.out.println("ticketsale");
	}
	
	private void giveAdminPermissions()
	{
		System.out.println("Enter user to give admin permissions");
		username = UserInputs.getValidStringInput();
		List<String> currentUsers = DataBaseCommunication.readFile("users.txt");
		String[] updatedUsers = new String[currentUsers.size()];
		boolean userFound = false;
		boolean alreadyAdmin = false;
		for(int i = 0;i<currentUsers.size();i++)
		{
			if(currentUsers.get(i).split(";")[0].equals(username))
			{
				if(Boolean.parseBoolean(currentUsers.get(i).split(";")[2]))
				{
					alreadyAdmin = true;
				}
				else currentUsers.set(i, currentUsers.get(i).replace(";false;", ";true;"));
				userFound = true;
			}
			updatedUsers[i] = currentUsers.get(i)+"\n";
		}
		if(!userFound)
		{
			System.out.println("Action failed: Could not find user with name: " + username);
		}
		else
		{
			if(alreadyAdmin) System.out.println("User " + username + " is already an admin");
			else 
			{
				System.out.println("Are you sure you want to give the user " + username + " administrator permissions?");
				System.out.println("1: yes");
				System.out.println("2: no");
				boolean notConfirmed = true;
				while(notConfirmed)
				{
					switch(UserInputs.getValidIntegerInput())
					{
						case 1:
							notConfirmed = false;
							DataBaseCommunication.writeToDataBase(updatedUsers, "users.txt");
							User.users.remove(User.getUserByName(username));
							String[] userInfo = DataBaseCommunication.getUserDetails(username).split(";");
							User.users.add(new Admin(username,userInfo[1],Integer.parseInt(userInfo[3])));
							System.out.println(username + " succesfully promoted to administrator");
							break;
						case 2:
							notConfirmed = false;
							System.out.println("Operation cancelled");
							break;
						default:
							System.out.println("Please enter either 1 or 2");
							break;
					}
				}
			}
		}
	}

}
