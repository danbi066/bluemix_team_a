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
import com.ibm.cof.dao.RsvDAO;
import com.ibm.cof.dto.RsvDTO;

/**
 * Servlet implementation class AdminLogin
 * SearchApprove.java by Nam Ho Kang
 */
@WebServlet("/SearchApprove.do")
public class SearchApprove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchApprove() {
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
		
		RsvDAO dao = new RsvDAO();
		ArrayList<RsvDTO> dtos = null;

		String site = (String)session.getAttribute("project");
		
		if(site != null){
			dtos = dao.selectnotapproved(site);
		}

		request.setAttribute("list", dtos);
		
        RequestDispatcher rd = request.getRequestDispatcher("AdminRsvApprove.jsp");
        rd.forward(request, response);
    }
}
