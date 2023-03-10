package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.Database;
import backend.RegisterDAO;
import backend.User;
import options.LabelDAO;

/**
 * Servlet implementation class LabelController
 */
@WebServlet("/LabelController")
public class LabelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LabelController() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get data
		String sttLevel = request.getParameter("stt");
		String userid = request.getParameter("userid");
		// check user in database
		if(RegisterDAO.findUser(userid, request.getRemoteAddr())) {
			// covert by int
			int lv = LabelDAO.parIntLabel(sttLevel);
			// save into database
			Database.setLabelSecurity(userid, lv);
			
			request.setAttribute("laberr", "Successful!");
			request.getRequestDispatcher("label.jsp").forward(request, response);
		}  else {
			request.setAttribute("laberr", "Not found user");
			request.getRequestDispatcher("label.jsp").forward(request, response);
		}
	}

}
