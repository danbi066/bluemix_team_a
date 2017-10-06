package com.ibm.cof.controller.AdminController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cof.dao.AdminDAO;
import com.ibm.cof.dao.RsvDAO;
import com.ibm.cof.dto.RsvDTO;

/*
 * 
 */

@WebServlet("/RsvEveryDay.do")
public class RsvEveryDay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int repeat_seq;
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RsvEveryDay() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String message = "Free day";

		/* Start_date, End_date(String) -> transform to Date type */
		try {
			String start_date = request.getParameter("start_dt"); // �떆�옉�궇吏�
			String end_date = request.getParameter("end_dt"); // 醫낅즺�궇吏�
			String phone = request.getParameter("phone");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String site = request.getParameter("site");
			String confer_nm = request.getParameter("confer_nm");
			String start_time = request.getParameter("start_time");
			String end_time = request.getParameter("end_time");
			String title = request.getParameter("title");
			String del_pw = request.getParameter("del_pw");
			String color = request.getParameter("color");
			Date d = new Date();

			String s = d.toString();
			Date start = transFormat.parse(start_date);
			Date start_temp = transFormat.parse(start_date);
			Date end = transFormat.parse(end_date);

			AdminDAO adao = new AdminDAO();
			
			int compare = start.compareTo(end);
			RsvDAO rdao = new RsvDAO();
			repeat_seq = rdao.selectRepeatSeq(site);
			RsvDTO rdto = new RsvDTO(start_time, end_time, title, site,
					confer_nm, name, phone, email, del_pw, color);
			
			
			while (compare < 0) {

				if (rdao.CheckRsv(confer_nm, start_time, end_time, site,
						transFormat.format(start)) == false) {
					start_date = transFormat.format(start);
					// System.out.println(start_date + " is alreay booked.");
					message = start_date + " is already booked.";
					request.setAttribute("message", message);
					break;
				}
				start.setDate(start.getDate() + 1);
				compare = start.compareTo(end);

			}

			if (message.equals(start_date + " is already booked.") != true) {
				start_date = request.getParameter("start_dt");
				start = transFormat.parse(start_date);
				compare = start.compareTo(end);

				while (compare < 0) {
					rdao.insertRepeat(rdto, start, end, repeat_seq + 1);
					start.setDate(start.getDate() + 1);
					compare = start.compareTo(end);
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (message.equals("Free day") != true) {
			RequestDispatcher rd = request.getRequestDispatcher("fail_repeat.jsp");
			rd.forward(request, response);
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("success_repeat.jsp");
		rd.forward(request, response);
		return;

	}

}
