package com.ibm.cof.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ibm.cof.dto.ReqDTO;
import com.ibm.cof.dto.RsvDTO;

/*
 * ReqDAO.java
 * By Nam Ho Kang
 */
public class ReqDAO {
	@Resource(name = "jdbc/ibm")
	DBCon db = new DBCon();
	Connection conn = null;
	PreparedStatement pstmt = null;
	Statement st = null;
	ResultSet rs = null;
	SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" ); 
	
	public ReqDAO() {
		
	}

	/* 사용자가 자신이 원하는 회의실을 예약할 때 사용자가 입력한 모든 정보가 입력된다. */
	public void insert(ReqDTO rdto) {
		String query = "insert into tb_request(req_rsv_site,req_rsv_confer_nm,"
				+ "req_rsv_start_time, req_rsv_end_time, req_rsv_title, "
				+ "req_rsv_mem_nm, req_rsv_mem_pn, req_rsv_mem_em, req_rsv_mon, req_rsv_tues, req_rsv_wed, req_rsv_thur, req_rsv_fri, "
				+ "req_rsv_weekly, req_rsv_monthly, req_rsv_details)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = db.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, rdto.getReq_Site());
			pstmt.setString(2, rdto.getReq_Confer_Nm());
			pstmt.setString(3, rdto.getReq_Start_Time());
			pstmt.setString(4, rdto.getReq_End_Time());
			pstmt.setString(5, rdto.getReq_Title());
			pstmt.setString(6, rdto.getReq_Mem_Nm());
			pstmt.setString(7, rdto.getReq_Mem_Pn());
			pstmt.setString(8, rdto.getReq_Mem_Em());
			pstmt.setString(9, rdto.getReq_mon());
			pstmt.setString(10, rdto.getReq_tues());
			pstmt.setString(11, rdto.getReq_wed());
			pstmt.setString(12, rdto.getReq_thur());
			pstmt.setString(13, rdto.getReq_fri());
			pstmt.setString(14, rdto.getReq_weekly());
			pstmt.setString(15, rdto.getReq_monthly());
			pstmt.setString(16, rdto.getReq_details());


			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close(pstmt, conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}



	/* 사용자가 예약한 것을 삭제할 수 있다. */
	public void delete(int rsv_seq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		DBCon db = new DBCon();

		try {
			conn = db.connect();
			String sql = "delete from tb_reservation where rsv_seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rsv_seq);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt, conn);
		}
	}
	
	
}
