package com.ibm.cof.controller.RsvController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.ibm.cof.dao.MemberDAO;
import com.ibm.cof.dao.ReqDAO;
import com.ibm.cof.dto.MemberDTO;
import com.ibm.cof.dto.ReqDTO;

/**
 * Servlet implementation class Reservation
 */
@WebServlet("/ReqRepRsv.do")
public class ReqRepRsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReqRepRsv() {
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

		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String site = request.getParameter("site");
		String confer_nm = request.getParameter("confer_nm");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");		
		String title = request.getParameter("title");
		String mon = request.getParameter("mon");
		String tues = request.getParameter("tues");
		String wed = request.getParameter("wed");
		String thur = request.getParameter("thur");
		String fri = request.getParameter("fri");
		String weekly = request.getParameter("weekly");
		String monthly = request.getParameter("monthly");
		String details = request.getParameter("details");
		String message = "ok";

		ReqDAO reqdao = new ReqDAO();
		MemberDAO mdao = new MemberDAO();
		ReqDTO reqdto = new ReqDTO(start_time, end_time, title, site, confer_nm, name, phone, email, mon, tues, wed, thur, fri, weekly, monthly, details);


		System.out.println("===========InsertReservation.java============");

		if(mdao.isCheckInBlock(phone) == true) {
			message = "You are blocked";
		} else {				
			MemberDTO mdto = new MemberDTO(name, phone, email, site);
			if (mdao.isCheckID(phone) == false) {
				mdao.insert(mdto);
			} else {
				mdao.updateMemberPhone(mdto);
			}

			reqdao.insert(reqdto);

			message = "sucess";
		}
	


	JSONObject json = new JSONObject();
	json.put("message", message);

	JSONObject obj = new JSONObject();
	obj.put("result", json);


	response.setContentType("text/html;charset=UTF-8");
	response.getWriter().print(obj);
	response.setCharacterEncoding("UTF-8");

}
}
