/*package com.ibm.cof.controller.AdminController;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

*//**
 * Servlet implementation class RsvEveryWeekByDay
 *//*
@WebServlet("/RsvEveryWeekByDay.do")
public class RsvEveryWeekByDay extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private int repeat_seq;
   SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
   Calendar start_day = Calendar.getInstance();
   Calendar end_day = Calendar.getInstance();
   Date today = new Date();
   String rsvdate;

   *//**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    *//*
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doProcess(request, response);
   }

   *//**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    *//*
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doProcess(request, response);
   }

   protected void doProcess(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      String message = "Free day";

       Start_date, End_date(String) -> transform to Date type 
      try {
         Integer per = Integer.parseInt(request.getParameter("per"));
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
         rsvdate = request.getParameter("selectDate");

         Date start = transFormat.parse(start_date); // String -> Date
         Date end = transFormat.parse(end_date); // String -> Date
         
         // 諛섎났 repeat_seq 媛��졇�삤湲�
         RsvDAO rdao = new RsvDAO();
         repeat_seq = rdao.selectRepeatSeq(site);
         
          愿�由ъ옄媛� �젣�븳�슏�닔(�떖)�쓣 媛��졇�삩�떎 
         AdminDAO adao = new AdminDAO();
         int month = adao.selectMonthbyName(site);
         
          �삤�뒛�궇吏쒖뿉 愿�由ъ옄媛� �젣�븳�븳 �떖�쓣 �닔瑜� �뜑�븳�떎 
         
         today.setMonth(today.getMonth()+month); // �삤�뒛�궇吏� �뜑�븯湲� �젣�븳�떖
         
         int compare = start.compareTo(end); // �떆�옉�궇吏쒖� 醫낅즺�궇吏쒕�� 鍮꾧탳�븳�떎.
         int compare_admin = start.compareTo(today); // �떆�옉�궇吏쒖� �삤�뒛�궇吏�+愿�由ъ옄�젣�븳�떖�닔瑜� 鍮꾧탳�븳�떎.
         int compare_result = end.compareTo(today);  // 醫낅즺�궇吏쒖� �삤�뒛�궇吏�+愿�由ъ옄�젣�븳�떖�닔瑜� 鍮꾧탳�븳�떎. 
                                       // 醫낅즺�궇吏쒓� 愿�由ъ옄�젣�븳�떖蹂대떎 �옉�쑝硫� �쓬�닔 , �겕硫� �뼇�닔 諛섑솚
         
         RsvDTO rdto = new RsvDTO(start_time, end_time, title, site, confer_nm, name, phone, email, del_pw, color);
         
         System.out.println("�삤�뒛 : "+today+"�떆�옉�궇吏� : "+start+"醫낅즺�궇吏� : "+end);

         if( compare_result < 0 ) // 醫낅즺�궇吏� < 愿�由щ궇吏�
         {
        	 while (compare < 0) {

                 if (rdao.CheckRsv(confer_nm, start_time, end_time, site, transFormat.format(start)) == false) {
                    start_date = transFormat.format(start);
                    System.out.println(start_date + " is alreay booked.");
                    message = start_date + " is already booked.";
                    request.setAttribute("message", message);
                    out.println(message);
                    break;
                 }
                 start.setDate(start.getDate() + 7 *per);
                 System.out.println("�젣�븳�궇吏� < 醫낅즺�궇吏쒖씪寃쎌슦 - �떆�옉�궇吏� : "+start+"醫낅즺�궇吏� : "+end);
                 
                 compare = start.compareTo(end);

              }

              if (message.equals(start_date + " is already booked.") != true) {
                 start_date = request.getParameter("start_dt");
                 start = transFormat.parse(start_date);
                 compare = start.compareTo(end);

                 while (compare < 0) {
                    rdao.insertRepeat(rdto, start, end, repeat_seq+1);
                    start.setDate(start.getDate() + 7 *per);
                    compare = start.compareTo(end);
                 }

                 
              }
         }
         
         else { // 愿�由щ궇吏� < 醫낅즺�궇吏�
        	 
        	 while (compare_admin < 0) { // �떆�옉�궇吏� < 愿�由щ궇吏�

                 if (rdao.CheckRsv(confer_nm, start_time, end_time, site, transFormat.format(start)) == false) {
                    start_date = transFormat.format(start);
                    System.out.println(start_date + " is alreay booked.");
                    message = start_date + " is already booked.";
                    request.setAttribute("message", message);
                    out.println(message);
                    break;
                 }
                 start.setDate(start.getDate() + 7 * per);
                 System.out.println("�젣�븳�궇吏� < 醫낅즺�궇吏쒖씪寃쎌슦 - �떆�옉�궇吏� : "+start+"�삤�뒛 : "+today);
                 compare_admin = start.compareTo(today);

              }

              if (message.equals(start_date + " is already booked.") != true) {
                 start_date = request.getParameter("start_dt");
                 start = transFormat.parse(start_date);
                 compare = start.compareTo(today);

                 while (compare < 0) {
                    rdao.insertRepeat(rdto, start, today, repeat_seq+1);
                    start.setDate(start.getDate() + 7 *per);
                    compare = start.compareTo(today);
                 }

                 repeat_seq = repeat_seq + 1;
              }
         }

      } catch (Exception e) {
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

}*/

package com.ibm.cof.controller.AdminController;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.cof.dao.RsvDAO;
import com.ibm.cof.dto.RsvDTO;

/**
 * Servlet implementation class RsvEveryWeekByDay
 */
@WebServlet("/RsvEveryWeekByDay.do")
public class RsvEveryWeekByDay extends HttpServlet {
   private static final long serialVersionUID = 1L;
   SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
   Calendar start_day = Calendar.getInstance();
   Calendar end_day = Calendar.getInstance();
   private int repeat_seq;

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doProcess(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
    *      response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      doProcess(request, response);
   }

   protected void doProcess(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      String message = "Free day";

      /* Start_date, End_date(String) -> transform to Date type */
      try {
         Integer per = Integer.parseInt(request.getParameter("per"));
         String start_date = request.getParameter("start_dt"); 
         String end_date = request.getParameter("end_dt"); 
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
         
         System.out.println("Per : " + per + "Start : " + start_date + "End : " + end_date);
         // start_day.setTime(transFormat.parse(start_date)); // String ->
         // Calendar
         // end_day.setTime(transFormat.parse(end_date)); // String ->
         // Calendar

         Date start = transFormat.parse(start_date); // String -> Date
         Date end = transFormat.parse(end_date); // String -> Date

         int compare = start.compareTo(end);
         RsvDAO rdao = new RsvDAO();
         repeat_seq = rdao.selectRepeatSeq(site);
         RsvDTO rdto = new RsvDTO(start_time, end_time, title, site, confer_nm, name, phone, email, del_pw,color);

         while (compare < 0) {

            if (rdao.CheckRsv(confer_nm, start_time, end_time, site,
                  transFormat.format(start)) == false) {
               message = start_date + " is already booked.";
               request.setAttribute("message", message);
               break;
            }
            // else {
            // rdao.insertRepeat(rdto, start, end, repeat_seq);
            start.setDate(start.getDate() + 7 * per);
            compare = start.compareTo(end);
            // }

         }
         if (message.equals(start_date + " is already booked.") != true) {
            start = transFormat.parse(start_date); // String -> Date
            end = transFormat.parse(end_date); // String -> Date
            compare = start.compareTo(end);

            while (compare < 0) {
               rdao.insertRepeat(rdto, start, end, repeat_seq+1);
               start.setDate(start.getDate() + 7 * per);
               compare = start.compareTo(end);
            }
            
         }

      } catch (Exception e) {
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

