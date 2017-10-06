package com.ibm.cof.controller.RsvController;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.ibm.cof.dao.AdminDAO;
import com.ibm.cof.dao.HistoryDAO;
import com.ibm.cof.dao.RsvDAO;
import com.ibm.cof.dto.HistoryDTO;
import com.ibm.cof.dto.RsvDTO;

/**
 * Servlet implementation class DeleteRsv
 * DenyRsv.java by Nam Ho Kang
 */
@WebServlet("/DenyRsv.do")
public class DenyRsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DenyRsv() {
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
		int seq = Integer.parseInt(request.getParameter("seq"));
		RsvDAO rdao = new RsvDAO();
		rdao.delete(seq);

		RequestDispatcher rd = request.getRequestDispatcher("SearchApprove.do");
		rd.forward(request, response);
	}	
	
}
