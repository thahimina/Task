package login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String address = request.getParameter("address");

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		if (firstName.isEmpty() && lastName.isEmpty() && userName.isEmpty() && password.isEmpty()
				&& address.isEmpty()) {
			out.print("<font color=red>Please fill all the fields</font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		} else if (firstName.isEmpty() || firstName == null) {
			out.print("<font color=red>Please fill the firstname </font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		} else if (lastName.isEmpty() || lastName == null) {
			out.print("<font color=red>Please fill the lastname </font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);
		} else if (userName.isEmpty() || userName == null) {
			out.print("<font color=red>Please fill the username</font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		}

		else if (password.isEmpty() || password == null) {
			out.print("<font color=red>Please fill the password </font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		}

		else if (address.isEmpty() || address == null) {

			out.print("<font color=red>Please fill the address </font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("registration.jsp");
			requestDispatcher.include(request, response);

		}

		else {
			try {

				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/task1",
						"root", "sevya");

				String query = "insert into user(firstName,lastName,userName,password,address) values(?,?,?,?,?)";

				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);

				preparedStatement.setString(1, firstName);
				preparedStatement.setString(2, lastName);
				preparedStatement.setString(3, userName);
				preparedStatement.setString(4, password);
				preparedStatement.setString(5, address);
				preparedStatement.executeUpdate();
				out.println("<b><font color=green >Successfully Registered</font></b>");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
				requestDispatcher.include(request, response);
				preparedStatement.close();
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
