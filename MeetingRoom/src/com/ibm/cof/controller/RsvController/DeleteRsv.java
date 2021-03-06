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
 */
@WebServlet("/DeleteRsv.do")
public class DeleteRsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteRsv() {
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

		Integer seq = Integer.parseInt(request.getParameter("rsv_seq"));
		Integer repeat_seq = Integer.parseInt(request.getParameter("repeat_seq"));
		Integer option = Integer.parseInt(request.getParameter("option"));
		String message = "error";        
        

		RsvDAO rdao = new RsvDAO();
		String password = rdao.getPassword(seq);


		RsvDTO rdto = rdao.selectBySeq(seq);
		
		HistoryDAO hdao = new HistoryDAO();
		HistoryDTO hdto = new HistoryDTO(rdto.getRsv_Date(), rdto.getRsv_Start_Time(), rdto.getRsv_End_Time(), 
				rdto.getRsv_Title(), rdto.getRsv_Site(), rdto.getRsv_Confer_Nm(), rdto.getRsv_Mem_Nm(),
				rdto.getRsv_Mem_Pn(), rdto.getRsv_Mem_Em(), rdto.getRsv_Del_Pw(), "delete");
	
		AdminDAO adao = new AdminDAO();
		
		if(option == 1 && repeat_seq > 0) {
			rdao.deleteAllRepeat(repeat_seq);
			System.out.println("전체삭제완료");
		} else  {
			rdao.delete(seq);
			System.out.println("삭제완료");
		}
		
		hdao.insert(hdto);
		message = "sucess";
		
		JSONObject json = new JSONObject();
		json.put("message", message);
		
		JSONObject obj = new JSONObject();
		obj.put("result", json);
		
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(obj);
		response.setCharacterEncoding("UTF-8");
		
		/*request.setAttribute("message", message);
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.do");
		dispatcher.forward(request, response);*/
	}
	
}
