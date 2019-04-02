package login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (name.isEmpty() && password.isEmpty()) {
			out.print("<font color=red>Please fill all the fields</font>");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
			requestDispatcher.include(request, response);
		} else {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
			try {

				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task1?useSSL=false",
						"root", "sevya");
				PreparedStatement statement = (PreparedStatement) connection
						.prepareStatement("select userName,password from user where userName=? and password=?");
				statement.setString(1, name);
				statement.setString(2, password);
				ResultSet resultset = statement.executeQuery();

				while (resultset.next()) {
					if (resultset.getString("userName").equals(name)) {
						out.print("<b><font color=green> Login Successful</font></b><br>");
						out.print("<br>");
						out.print("Welcome   " + resultset.getString("userName"));
						return;

					}
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
				requestDispatcher.include(request, response);
				statement.close();
				connection.close();
				return;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}
