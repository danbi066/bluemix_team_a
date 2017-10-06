package com.ibm.cof.controller.AdminController;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.cof.dao.AdminDAO;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin.do")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String message = "";
		
		AdminDAO adao = new AdminDAO();
		int result = adao.checkLogin(id, pw);
		String nextPage = "RsvView.jsp";
		String project = adao.selectProjectName(id);
		
		if(result == 1) {//success
			session.setMaxInactiveInterval(10*60*60);
			session.setAttribute("admin", "yes");
			session.setAttribute("project", project);
			nextPage = "home.do";

		} else if(result == 0){
			message = "��й�ȣ�� ��ġ���� �ʽ��ϴ�";
			nextPage = "AdminLogin.jsp";
			
		} else if(result == -1){
			message = "ID�� ��ġ���� �ʽ��ϴ�";
			nextPage = "AdminLogin.jsp";
		}


		request.setAttribute("loginMessage", message);
        RequestDispatcher rd = request.getRequestDispatcher(nextPage);
        rd.forward(request, response);
    }
}
