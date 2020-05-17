package cn.smbms.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smbms.tools.Constants;

public class LogoutServlet extends HttpServlet {

	public LogoutServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 清除session
		request.getSession().removeAttribute(Constants.USER_SESSION);
		response.sendRedirect(request.getContextPath() + "/user/login.html");
	}

	public void init() throws ServletException {
	}

}
