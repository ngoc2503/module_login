package backend;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.crypto.params.KeyParameter;

import crypto.HMacUtil;

public class RegisterDAO {
	
	 /**
     * Check user existence
     * 
     * @param userid 
     * @param remoteip client ip address
     * @return true if user is existent, false otherwise
     */
	public static boolean findUser(String userid, String remoteip)
	{
		 boolean isFound = false;
		 
		 User user = Database.getUser(userid);
		 //TODO Pseudo code
		 if (user != null) {
			isFound = true;
		} 
		 return isFound;
	}
	
	public static boolean addUser(String firstName, String lastName, String userid, String password, String salt, String otpSecret)
	{
		boolean isSuccess = false;
		
		//TODO Hash password with salt
		HMacUtil util = new HMacUtil();
		KeyParameter parameter = util.getSecretKey(password, salt);
		try {
			String saltString = util.generateHMacString(parameter, password);
			
			User user = new User();
			user.setFistname(firstName);
			user.setLastname(lastName);
			user.setUserid(userid);
			user.setPassword(saltString);
			user.setSalt(salt);
			user.setOptsecret(otpSecret);
			// insert into database
			int row = Database.addCustomer(user);
			if(row > 0) isSuccess = true;
	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}
		
}
