package cn.smbms.tools;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.smbms.pojo.User;

public class sysinterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		if ((User) session.getAttribute(Constants.USER_SESSION) == null) {
			try {
				response.sendRedirect(request.getContextPath() + "/401.jsp");
				System.out
						.println(request.getContextPath()
								+ "/401.jsp------------------------------------------------------------------");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
}
