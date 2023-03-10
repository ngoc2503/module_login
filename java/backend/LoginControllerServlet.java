package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import crypto.CryptoUtil;

public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoginControllerServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginControllerServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("hello world");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// @WebServlet("/login")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store");
		
		// get initparameter
		String adminName = getServletContext().getInitParameter("adminname"); // admin
		String passwordAdmin = getServletContext().getInitParameter("adminpassword"); // 123456
		
		// getparameter by form
		String userid = request.getParameter("userid").trim();
		String password = request.getParameter("password");
		
		// check admin
		if(userid.equals(adminName) && password.equals(passwordAdmin)) {
			// redirect label.jsp
			request.getRequestDispatcher("label.jsp").forward(request, response);
		}
		else
		if(userid.length() == 0) {
			// username is blank
			request.setAttribute("errLogin", "Username or Password is invalid");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		} else 
		if(!RegisterDAO.findUser(userid, request.getRemoteAddr())) {
			// user is not register yet.
			request.setAttribute("errLogin", "Username or Password is invalid");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else {

			if(LoginDAO.isAccountLocked(userid, request.getRemoteAddr())) {
				// user isLocked redirect locked.html
				response.sendRedirect("locked.html");
				
			} else {
				// check password user
				if(LoginDAO.validateUser(userid, password, request.getRemoteAddr())) {
					// user login success.
					System.out.println(CryptoUtil.byteArrayToHexString(CryptoUtil.generateRandomBytes(CryptoUtil.SALT_SIZE)));
					HttpSession session = request.getSession();
					session.setAttribute("userid2fa", userid);
					request.getRequestDispatcher("otp.jsp").forward(request, response);
				}
			    else {
			    	// user login fail
					LoginDAO.incrementFailLogin(userid, request.getRemoteAddr());
					request.setAttribute("errLogin", "Username or Password is invalid");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
				
			}
		}
	}
}
