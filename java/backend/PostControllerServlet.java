package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import options.LabelDAO;

/**
 * Servlet implementation class PostControllerServlet
 */
@WebServlet("/PostControllerServlet")
public class PostControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		// check user login
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			response.sendRedirect("error.html");
			return;
		}
		// check user in database
		User user = Database.getUser(userid);
		if(user == null) {
			response.sendRedirect("error.html");
			return;
		}
		// get level secret
		int lvUser = user.getLabel();
	
		String message = request.getParameter("mes");
		// input is blank
		if(message.trim().length() == 0) {
			response.sendRedirect("error.html");
			return;
		}
		String stLevel = request.getParameter("stt");
		int lvPost = LabelDAO.parIntLabel(stLevel);
		
		// compare lvUser with lvPost
		if(lvPost < lvUser) {
			request.setAttribute("postErr", "Error! Message's level is not lower your level");
			request.getRequestDispatcher("post.jsp").forward(request, response);
			// error post
		}else {
			// save into database
			// gettime
			LocalDate localDate = LocalDate.now();
			Date date = Date.valueOf(localDate);
			
			// create object news
			News news = new News();
			news.setUserid(userid);
			news.setText(message);
			news.setDate(date);
			news.setLabel(lvPost);
			// save into database
			Database.addNews(news);
			
			request.setAttribute("postErr", "Successful!");
			request.getRequestDispatcher("post.jsp").forward(request, response);
		}
	
	}

}
