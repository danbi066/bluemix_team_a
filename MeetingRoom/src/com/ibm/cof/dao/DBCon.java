package com.ibm.cof.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCon {
	
	public Connection connect(){
		Connection conn = null;
		



		//	String url = "jdbc:mysql://us-cdbr-sl-dfw-01.cleardb.net:3306/ibmx_e21af70c6f35c23?useUnicode=true&characterEncoding=UTF-8";
		//	String id = "b81098c1ee198d";
		//	String pw = "8c032d00";
		/*String url = "jdbc:oracle:thin:@localhost:1521:xe";
	      String id = "system";
	      String pw = "1234";*/
		

	      // local �� ������ �ٲ㼭 ��������! 
	      String url = "jdbc:mysql://localhost:3306/sys?autoReconnect=true&useSSL=false";
	      String id = "bluemix";
	      String pw = "ibmbluemix123";
	      
	      try{
	         Class.forName("com.mysql.jdbc.Driver");
	         conn = DriverManager.getConnection(url, id, pw);
	      }catch(SQLException se){
	         se.printStackTrace();
	      }catch(Exception e){
	         e.printStackTrace();
	      }
		
		return conn;
	}
	
	
	public void close(PreparedStatement pstmt, Connection conn){
		try{
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs, Statement stmt, Connection conn){
		try{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void close(ResultSet rs, PreparedStatement pstmt, Connection conn){
		try{
			if(rs != null){
				rs.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}