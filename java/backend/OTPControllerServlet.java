package backend;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backend.AuthSession;
import crypto.CryptoUtil;
import crypto.TimeBaseOTP;

/**
 * Servlet implementation class OTPControllerServlet
 */

public class OTPControllerServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(OTPControllerServlet.class.getName());


    /**
     * @see HttpServlet#HttpServlet()
     */
    public OTPControllerServlet()
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
       
        
        // Make sure it has a valid 2fa session from login page
        // userid2fa session attribute must be set

        //TODO check2FASession
        
        HttpSession session = request.getSession(false);
        String userid = (String) session.getAttribute("userid2fa");
        // get info page
        String infoPage = request.getParameter("loginpage"); // user login from loginpage
        // Remove the userid2fa attribute to prevent multiple submission attempts
        session.removeAttribute("userid2fa");

        String otpvalue = (String) request.getParameter("totp");

        if (otpvalue == null)
        {
        	//TODO Invalidate session
        	session.invalidate();
        	//TODO Redirect error.html
        	response.sendRedirect("error.html");
        }
        
        String otpsecret = null;
        //TODO Get otpsecret from Database using OTPDAO class 
        otpsecret = Database.getUser(userid).getSalt();//.getOTPSecret(userid, request.getRemoteAddr());
        //GenerateOTP using TimeBaseOTP class
        String otpresult = TimeBaseOTP.generateOTP(CryptoUtil.hexStringToByteArray(otpsecret));
        
        otpsecret = null;

        if (otpresult == null)
        {
        	//TODO Invalidate session
        	session.invalidate();
        	//TODO Redirect error.html 
        	response.sendRedirect("error.html");
        }
        else
        if (otpresult.equals(otpvalue))
        {// Correct OTP value
        	System.out.println("OTP is correct.");
        	//TODO Redirect error.html 
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("userid", userid);
            session.setAttribute("anticsrf_success", "AntiCSRF");

            String custsession = "JSESSIONID=" + session.getId() + ";Path=/;Secure;HttpOnly;SameSite=Strict";
            response.setHeader("Set-Cookie", custsession);
            
            //TODO reset fail login attempt
            OTPDAO.resetFailLogin(userid, request.getRemoteAddr());
            //TODO Redirect /success.jsp
          request.getRequestDispatcher("/success.jsp").forward(request, response);
            
        }
        else
        {// Incorrect OTP value
            System.out.println("error OTP");
            String remoteip = request.getRemoteAddr();
            log.warning("Error: Invalid otp value " + request.getRemoteAddr() + " " + userid);
        	session.invalidate();
            session = request.getSession(true);
            session.setAttribute("userid2fa", userid);
            session.setAttribute("otperror", "");
            // TODO Update fail login count.
           int faillogin = Database.getUser(userid).getFaillogin() + 1;
           if(faillogin > 4) {
	           	session.invalidate();
	           	// lock account
	           	Database.setStatusUser(userid, 1);
	           	// redirect error.html
	           	response.sendRedirect("locked.html");
           } else {
        	   OTPDAO.updateFaillogi(userid, faillogin);
        	   if(infoPage != null) {
           			request.getRequestDispatcher("otp.jsp").forward(request, response);
           		} else request.getRequestDispatcher("confirm.jsp").forward(request, response);
           }
        }

    }

}
