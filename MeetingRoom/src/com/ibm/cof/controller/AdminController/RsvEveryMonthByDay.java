/*package com.ibm.cof.controller.AdminController;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
 * Servlet implementation class RsvEveryMonthByDay
 *//*
@WebServlet("/RsvEveryMonthByDay.do")
public class RsvEveryMonthByDay extends HttpServlet {
   private static final long serialVersionUID = 1L;
   SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
   // SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd");
   private int repeat_seq;
   
   *//**
    * @see HttpServlet#HttpServlet()
    *//*
   public RsvEveryMonthByDay() {
      super();
      // TODO Auto-generated constructor stub
   }

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
         
         Calendar start_day = Calendar.getInstance();
         Calendar end_day = Calendar.getInstance();
         Calendar today = Calendar.getInstance();
         Date start = transFormat.parse(start_date); // String -> Date
         Date end = transFormat.parse(end_date); // String -> Date
         Date today_dd = new Date();
         
          愿�由ъ옄媛� �젣�븳�슏�닔(�떖)�쓣 媛��졇�삩�떎 
         AdminDAO adao = new AdminDAO();
         int month = adao.selectMonthbyName(site);
                  
         RsvDAO rdao = new RsvDAO();
         RsvDTO rdto = new RsvDTO(start_time, end_time, title, site, confer_nm, name, phone, email, del_pw, color);
         
         // 諛섎났 repeat_seq 媛��졇�삤湲�
         repeat_seq = rdao.selectRepeatSeq(site);
                  
         start_day.setTime(transFormat.parse(start_date)); // String -> Calendar
         end_day.setTime(transFormat.parse(end_date)); // String -> Calendar
         today.add(Calendar.MONTH, month);
         //today.setTime(today_dd); // Calendar -> Date
                
         int prevDayOfWeekInMonth; // �삤�뒛�씠 �씠踰덈떖 紐뉗㎏二�
         int prevDayOfWeek; // �씠踰� �떖�쓽 泥レ㎏�씪�쓽 �슂�씪
        
         int compare = start_day.compareTo(end_day);
         int compare_admin = start_day.compareTo(today); // �떆�옉�궇吏쒖� �삤�뒛�궇吏�+愿�由ъ옄�젣�븳�떖�닔瑜� 鍮꾧탳�븳�떎.
         int compare_result = end_day.compareTo(today);  // 醫낅즺�궇吏쒖� �삤�뒛�궇吏�_愿�由ъ옄�젣�븳�떖�닔瑜� 鍮꾧탳�븳�떎. 
                                       // 醫낅즺�궇吏쒓� 愿�由ъ옄�젣�븳�떖蹂대떎 �옉�쑝硫� �쓬�닔 , �겕硫� �뼇�닔 諛섑솚
        
         
         System.out.println("�삤�뒛 : "+today.getTime()+"�떆�옉�궇吏� : "+start_day.getTime()+"醫낅즺�궇吏� : "+end_day.getTime());
         System.out.println("compare_result : "+compare_result);
         if(compare_result < 0 ) // 醫낅즺�궇吏� < 愿�由щ궇吏�
         {
        	 System.out.println("start_day : "+start_day.getTime());
        	 while (compare < 0) { // �떆�옉�궇吏� < 醫낅즺�궇吏�
        		
        		 
                 if (rdao.CheckRsv(confer_nm, start_time, end_time, site,
                       transFormat.format(start_day.getTime())) == false) {
                    message = start_date + " is already booked.";
                    request.setAttribute("message", message);
                    //out.println(message);
                    break;
                 }
                 // else {
                 // rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq);
                 prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); // �삤�뒛�씠 �씠踰덈떖 紐뉗㎏二�
                 prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // �씠踰� �떖�쓽 泥レ㎏�씪�쓽 �슂�씪
                                                         
                 start_day.add(Calendar.MONTH, 1); // �븳�떖 �뜑�븯湲� -> 10.22
                 // System.out.println(date.getTime());
                 start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // �씠踰덈떖�쓽 泥レ㎏�씪�쓣
                                                        // �꽕�젙
                 // System.out.println(date.getTime());
                 start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // �삤�뒛�씠
                                                                    // �씠踰덈떖
                                                                    // 紐뉗㎏二�
                 compare = start_day.compareTo(end_day);
                 // }
              }
              if (message.equals(start_date + " is already booked.") != true) {
                 start_day.setTime(transFormat.parse(start_date)); // String ->
                                                        // Calendar
                 end_day.setTime(transFormat.parse(end_date)); // String ->
                                                     // Calendar
                 compare = start_day.compareTo(end_day);
                 while (compare < 0) {
                    rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq+1);
                    prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); // �삤�뒛�씠
                                                                          // �씠踰덈떖
                                                                          // 紐뉗㎏二�
                    prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // �씠踰�
                                                              // �떖�쓽
                                                              // 泥レ㎏�씪�쓽
                                                              // �슂�씪

                    start_day.add(Calendar.MONTH, 1); // �븳�떖 �뜑�븯湲� -> 10.22
                    // System.out.println(date.getTime());
                    start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // �씠踰덈떖�쓽
                                                           // 泥レ㎏�씪�쓣
                                                           // �꽕�젙
                    // System.out.println(date.getTime());
                    start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // �삤�뒛�씠
                                                                       // �씠踰덈떖
                                                                       // 紐뉗㎏二�
                    compare = start_day.compareTo(end_day);
                 }
                 
              }         	 
         } 
         
         else // 愿�由щ궇吏� < 醫낅즺�궇吏�
         {
        	 while (compare_admin < 0) { // �떆�옉�궇吏� < 愿�由щ궇吏� 

                 if (rdao.CheckRsv(confer_nm, start_time, end_time, site,
                       transFormat.format(start_day.getTime())) == false) {
                    message = start_date + " is already booked.";
                    request.setAttribute("message", message);
                    out.println(message);
                    
                    break;
                 }
                 // else {
                 // rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq);
                 prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); // �삤�뒛�씠
                                                                       // �씠踰덈떖
                                                                       // 紐뉗㎏二�
                 prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // �씠踰� �떖�쓽
                                                           // 泥レ㎏�씪�쓽
                                                           // �슂�씪

                 start_day.add(Calendar.MONTH, 1); // �븳�떖 �뜑�븯湲� -> 10.22
                 // System.out.println(date.getTime());
                 start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // �씠踰덈떖�쓽 泥レ㎏�씪�쓣
                                                        // �꽕�젙
                 // System.out.println(date.getTime());
                 start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // �삤�뒛�씠
                                                                    // �씠踰덈떖
                                                                    // 紐뉗㎏二�
                 compare_admin = start_day.compareTo(today);
                 // }
              }
              if (message.equals(start_date + " is already booked.") != true) {
                 start_day.setTime(transFormat.parse(start_date)); // String ->
                                                        // Calendar
                 end_day.setTime(transFormat.parse(end_date)); // String ->
                                                     // Calendar
                 compare = start_day.compareTo(today);
                 while (compare < 0) {
                	System.out.println("test_dayrepeat");
                    rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq+1);
                    prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); // �삤�뒛�씠
                                                                          // �씠踰덈떖
                                                                          // 紐뉗㎏二�
                    prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // �씠踰�
                                                              // �떖�쓽
                                                              // 泥レ㎏�씪�쓽
                                                              // �슂�씪

                    start_day.add(Calendar.MONTH, 1); // �븳�떖 �뜑�븯湲� -> 10.22
                    // System.out.println(date.getTime());
                    start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // �씠踰덈떖�쓽
                                                           // 泥レ㎏�씪�쓣
                                                           // �꽕�젙
                    // System.out.println(date.getTime());
                    start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // �삤�뒛�씠
                                                                       // �씠踰덈떖
                                                                       // 紐뉗㎏二�
                    compare = start_day.compareTo(today);
                 }
                 
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
import java.text.ParseException;
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
 * Servlet implementation class RsvEveryMonthByDay
 */
@WebServlet("/RsvEveryMonthByDay.do")
public class RsvEveryMonthByDay extends HttpServlet {
   private static final long serialVersionUID = 1L;
   SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
   // SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy-MM-dd");
   private int repeat_seq;
   Calendar start_day = Calendar.getInstance();
   Calendar end_day = Calendar.getInstance();

   /**
    * @see HttpServlet#HttpServlet()
    */
   public RsvEveryMonthByDay() {
      super();
      // TODO Auto-generated constructor stub
   }

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
         String start_date = request.getParameter("start_dt"); //시작날짜
         String end_date = request.getParameter("end_dt"); //종료날짜
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

         // System.out.println("start : "+transFormat1+"end :
         // "+transFormat2);
         start_day.setTime(transFormat.parse(start_date)); // String ->
                                                // Calendar
         end_day.setTime(transFormat.parse(end_date)); // String -> Calendar

         Date start = transFormat.parse(start_date); // String -> Date
         Date end = transFormat.parse(end_date); // String -> Date

         int prevDayOfWeekInMonth; // 오늘이 이번달 몇째주
         int prevDayOfWeek;// 이번 달의 첫째일의 요일


         int compare = start_day.compareTo(end_day);
         RsvDAO rdao = new RsvDAO();
         repeat_seq = rdao.selectRepeatSeq(site);
         RsvDTO rdto = new RsvDTO(start_time, end_time, title, site, confer_nm, name, phone, email, del_pw,color);
         repeat_seq = rdao.selectRepeatSeq(site);
         while (compare < 0) {

            if (rdao.CheckRsv(confer_nm, start_time, end_time, site,
                  transFormat.format(start_day.getTime())) == false) {
               message = start_date + " is already booked.";
               request.setAttribute("message", message);
               out.println(message);
               
               break;
            }
            // else {
            // rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq);
            prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); // 오늘이
                                                                  // 이번달
                                                                  // 몇째주
            prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // 이번 달의
                                                      //  첫째일의
                                                      // 요일

            start_day.add(Calendar.MONTH, 1); // 한달 더하기 -> 10.22
            // System.out.println(date.getTime());
            start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // 이번달의 첫째일을
                                                   //설정
            // System.out.println(date.getTime());
            start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // 오늘이
                                                               // 이번달
                                                               // 몇째주
            compare = start_day.compareTo(end_day);
            // }
         }
         if (message.equals(start_date + " is already booked.") != true) {
            start_day.setTime(transFormat.parse(start_date)); // String ->
                                                   // Calendar
            end_day.setTime(transFormat.parse(end_date)); // String ->
                                                // Calendar
            compare = start_day.compareTo(end_day);
            while (compare < 0) {
               rdao.insertRepeatByDay(rdto, start_day, end_day, repeat_seq+1);
               prevDayOfWeekInMonth = start_day.get(Calendar.DAY_OF_WEEK_IN_MONTH); //오늘이
                                                                     // 이번달
                                                                     // 몇째주
               prevDayOfWeek = start_day.get(Calendar.DAY_OF_WEEK); // 이번
                                                         // 달의
                                                         // 첫째일의
                                                         // 요일

               start_day.add(Calendar.MONTH, 1); // 한달 더하기 -> 10.22
               // System.out.println(date.getTime());
               start_day.set(Calendar.DAY_OF_WEEK, prevDayOfWeek); // �씠踰덈떖�쓽
                                                      // 泥レ㎏�씪�쓣
                                                      // �꽕�젙
               // System.out.println(date.getTime());
               start_day.set(Calendar.DAY_OF_WEEK_IN_MONTH, prevDayOfWeekInMonth); // 이번달의
                                                                  // 첫째일을
                                                                  // 설정
               compare = start_day.compareTo(end_day);
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

      // int per = Integer.parseInt(request.getParameter("per")); // 반복횟수

   }

}

