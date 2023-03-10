package backend;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crypto.CryptoUtil;


/**
 * Servlet implementation class RegisterServlet
 */

public class RegisterControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LoginControllerServlet.class.getName());

    public RegisterControllerServlet()
    {
        super();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store"); 
	 // session
        HttpSession session = request.getSession(false);

        String firstName = request.getParameter("firstname").trim();
        String lastName = request.getParameter("lastname").trim();
        String userid = request.getParameter("userid").trim();
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        //check password contain(A-a)
        String regex = "(\\w*[a-z]+\\w*[A-Z]+\\w*)|(\\w*[A-Z]+\\w*[a-z]+\\w*)";
        
        if (firstName.length() == 0 || lastName.length() == 0 || userid.length() == 0 || password == null || repassword == null)
        {
        	//TODO Redirect to index.jsp 
        	response.sendRedirect("error.html");
        }
        else
        if (password.length() < AppConstants.MIN_LENGTH_PASS || !password.matches(regex)) {
        	
        	//TODO Redirect to error.html
        	response.sendRedirect("error.html");
        }
        else
        if (password.compareTo(repassword) != 0)
        {
        	//TODO Redirect to error.html 
        	response.sendRedirect("error.html");
        }
        else
        if (RegisterDAO.findUser(userid, request.getRemoteAddr()))
        {
        	//TODO Redirect to error.html
        	response.sendRedirect("error.html");
        	
        }
        else
        {
            //TODO Generate salt by CryptoUtil
        	byte [] salt = CryptoUtil.generateRandomBytes(CryptoUtil.SALT_SIZE);
        	//TODO Encode Base32 salt
        	String baseString = CryptoUtil.byteArrayToHexString(salt);
        	//TODO	Generate hexadecimal OTP Secret by CryptoUtil
        	String base32Secret = CryptoUtil.base32OTPSecret(baseString);
        	//TODO Add new user to Database
        	if(RegisterDAO.addUser(firstName, lastName, userid, repassword, baseString, base32Secret)) {
        		System.out.println("add user success!");
        	}
        	
        	password = null;
        	repassword = null;
            //Prevent Session fixation, invalidate and assign a new session
        	session.invalidate();
            session = request.getSession(true);
            session.setAttribute("userid2fa", userid);
            //Set the session id cookie with HttpOnly, secure and samesite flags
            String custsession = "JSESSIONID=" + session.getId() + ";Path=/;Secure;HttpOnly;SameSite=Strict";
            response.setHeader("Set-Cookie", custsession);
            
            //Dispatch request to confirm.jsp
            request.getRequestDispatcher("confirm.jsp").forward(request, response);
                 
        }

    }
}
