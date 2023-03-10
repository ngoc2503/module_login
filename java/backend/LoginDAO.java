
package backend;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import javax.servlet.ServletException;

import org.bouncycastle.crypto.params.KeyParameter;

import crypto.HMacUtil;



public class LoginDAO
{

    private static final Logger log = Logger.getLogger(LoginDAO.class.getName());

    /**
     * Validates user credential
     * 
     * @param userid 
     * @param password
     * @param remoteip client ip address
     * @return true if user is valid, false otherwise
     */
    public static boolean validateUser(String userid, String password, String remoteip)
    {

    	boolean isValid = false;
    	
    	User user = Database.getUser(userid);
    	HMacUtil hMacUtil = new HMacUtil();
    	KeyParameter parameter = hMacUtil.getSecretKey(password, user.getSalt());
    	try {
			String salString = hMacUtil.generateHMacString(parameter, password);
			if(salString.equals(user.getPassword())) isValid = true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return isValid;  
    }

    /**
     * Check if a user account is locked
     * 
     * @param userid
     * @param remoteip client ip address
     * @return true if account is locked or false otherwise
     * @throws ServletException
     */
    public static boolean isAccountLocked(String userid, String remoteip) throws ServletException
    {
    	boolean isLocked = false;
    	// check user status
    	User user = Database.getUser(userid);
    	if (user != null) {
			isLocked = user.isIslooked();
		}

    	return isLocked;
    }
    
    /**
     * Increments the failed login count for a user
     * Locked the user account if fail logins exceed threshold.
     * 
     * @param userid
     * @param remoteip 
     * @throws ServletException
     */
    public static void incrementFailLogin(String userid, String remoteip)
            throws ServletException
    {
        if (userid == null || remoteip == null)
        {
        	//TODO throw exception;
        }
        User user = Database.getUser(userid);
        int failLogin = user.getFaillogin();
        // user login fail
        failLogin = failLogin + 1;
        // failLogin > Max lock account
        if(failLogin > 4) Database.setStatusUser(userid, 1);
        else Database.updateUser(userid, failLogin);
  
    }
}
