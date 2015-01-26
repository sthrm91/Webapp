package com.todo.users;


import java.util.regex.Pattern;
import javax.ws.rs.core.NewCookie;
import org.springframework.beans.factory.annotation.Autowired;
import com.todo.exceptions.AccountCreationException;


public class ValidationAndRegistrationService {
	
	public boolean isUserNameAvailable(String userName)
	{
		return userProfileDAO.getProfileByUserName(userName) == null;
	}	

	
	public NewCookie generateCookieForId(String username)
	{
		NewCookie cookie = new NewCookie("id", "id-1");
		return cookie;
	}
	
	public boolean isValid(String username, String password)
	{
		return true;
	}
	
	public boolean isPasswordValid(String password)
	{
		if(passwordRegex.matcher(password).matches())
		{
			return true;
		}		
		return false;
	}
	
	public boolean isPhoneNumberValid(String phoneNumber)
	{
		if(phoneNumberRegex.matcher(phoneNumber).matches())
		{
			return true;
		}
		
		return false;
	}
	
	public UserProfile register(String userName, String password, String phoneNumber) throws AccountCreationException
	{
		if(!isUserNameAvailable(userName))
		{
			throw new AccountCreationException("UserName taken");
		}
		if(!isPhoneNumberValid(phoneNumber))
		{
			throw new AccountCreationException("Invalid phoneNumber");
		}
		if(!isPasswordValid(password))
		{
			throw new AccountCreationException("Invalid Passowrd");
		}
		
		UserProfile newUser = new UserProfile();
		newUser.setPassword(password);
		newUser.setPhoneNumber(phoneNumber);
		newUser.setUsername(userName);
		userProfileDAO.createUserProfile(newUser);
		return newUser;					
	}
	
	public UserProfile login(String userName, String password)
	{
		UserProfile user = userProfileDAO.getProfileByUserName(userName);
		if(user.getPassword().equals(password))
		{
			return user;
		}
		return null;		
	}
	
	
	@Autowired
	private MongoDBUserProfileDAO userProfileDAO;
	private static Pattern passwordRegex = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
	private static Pattern phoneNumberRegex = Pattern.compile("\\d{10}");
}
