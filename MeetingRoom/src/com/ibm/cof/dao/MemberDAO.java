package com.ibm.cof.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.cof.dto.BlockDTO;
import com.ibm.cof.dto.MemberDTO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class MemberDAO {
	DBCon db = new DBCon();
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement st = null;
    ResultSet rs = null;

    public MemberDAO() {
    
    }

    /* 회의실 예약과 동시에 회원가입이 이루어짐 */
	public void insert(MemberDTO mdto) {
	
		String query = "insert into tb_member(mem_nm,mem_pn,mem_em,mem_site,mem_reg_date)"
				+ " values(?,?,?,?,now())";
		try {
			conn = db.connect();				
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mdto.getMem_Nm());
			pstmt.setString(2, mdto.getMem_Pn());
			pstmt.setString(3, mdto.getMem_Em());
			pstmt.setString(4, mdto.getMem_Site());
			
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
	
	/* 해당 프로젝트 회원의 모든 정보를 가져올 수 있음 */
	public ArrayList<MemberDTO> selectAll(String site)
	{
		ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
		String query = "select * from tb_member where mem_site=?";
		MemberDTO dto =null;
		String name, phone, email;
		int seq;
		
		try {
			conn = db.connect();				
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, site);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				seq = rs.getInt("mem_seq");
				name = rs.getString("mem_nm");
				phone = rs.getString("mem_pn");
				email = rs.getString("mem_em");
				
				dto = new MemberDTO(seq, name, phone, email, site);
				dtos.add(dto);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close(rs, pstmt, conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dtos;
	}
	
	/* 검색 조건(이름, 프로젝트)에 따라 해당 프로젝트 회원 정보를 가져올 수 있음 */
	public ArrayList<MemberDTO> selectByCondition(String site, String option, String context)
	{
		ArrayList<MemberDTO> dtos = new ArrayList<MemberDTO>();
		String query = "select * from tb_member where " + option + " = '" + context + "' and mem_site=?";
		MemberDTO dto =null;
		String name, phone, email;
		
		try {
			conn = db.connect();				
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, site);			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				name = rs.getString("mem_nm");
				phone = rs.getString("mem_pn");
				email = rs.getString("mem_em");
				site = rs.getString("mem_site");
				
				dto = new MemberDTO(name, phone, email, site);
				dtos.add(dto);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close(rs, pstmt, conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dtos;
	}
	
	public void updateMemberState(MemberDTO mdto, int mem_seq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		DBCon db = new DBCon();

		try {
			String query = "update tb_member set mem_nm=?,mem_pn=?,mem_em=?,mem_site=?"
					+ " where mem_seq=?";
			conn = db.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mdto.getMem_Nm());
			pstmt.setString(2, mdto.getMem_Pn());
			pstmt.setString(3, mdto.getMem_Em());
			pstmt.setString(4, mdto.getMem_Site());
			pstmt.setInt(5, mem_seq);

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
	
	public void updateMemberPhone(MemberDTO mdto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		DBCon db = new DBCon();

		try {
			String query = "update tb_member set mem_nm=?,mem_em=?,mem_site=?"
					+ " where mem_pn=?";
			conn = db.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mdto.getMem_Nm());
			pstmt.setString(2, mdto.getMem_Em());
			pstmt.setString(3, mdto.getMem_Site());
			pstmt.setString(4, mdto.getMem_Pn());

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

	/* 회원이 퇴직 또는 어떠한 사정에 의해 더이상 사이트의 소속이 아니게 될 경우 관리자는 회원을 삭제할 수 있다. */
	public void delete(int mem_seq) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		DBCon db = new DBCon();

		try {
			conn = db.connect();
			String sql = "delete from tb_member where mem_seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_seq);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt, conn);
		}
	}
	
	
	public JSONArray selectBySeq(String seq)
	{
		//Map mlist = new Map<MemberDTO>();
		
		String query = "select * from tb_member where mem_seq=?";
		//JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
		String name, phone, email, site;
				
		try {
			conn = db.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, seq);
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				
				JSONObject json = new JSONObject();
				json.put("name", rs.getString("mem_nm"));
				json.put("phone", rs.getString("mem_pn"));
				json.put("email", rs.getString("mem_em"));
				json.put("site", rs.getString("mem_site"));
				jarray.add(json);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close(rs, pstmt, conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return jarray;
	}
	
	public JSONArray selectListByName(String name)
	{
		
		String query = "select * from tb_member where mem_nm=?";
		//JSONObject json = new JSONObject();
		JSONArray jarray = new JSONArray();
				
		try {
			conn = db.connect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				
				JSONObject json = new JSONObject();
				json.put("phone", rs.getString("mem_pn"));
				json.put("email", rs.getString("mem_em"));
				json.put("site", rs.getString("mem_site"));
				jarray.add(json);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				db.close(rs, pstmt, conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return jarray;
	}
	
	
	 public Boolean isCheckID(String mem_pn) {

	      Boolean bool = false;
	      try {
	         conn = db.connect();

	         String query = "select * from tb_member where mem_pn=?";
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, mem_pn);
	         rs = pstmt.executeQuery();

	         if (rs.next())
	            bool = true;
	         else
	            bool = false;

	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            db.close(rs, pstmt, conn);
	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }

	      return bool;
	   }

	   public ArrayList<String> getNameList(String phone) {
	      // List<MemberDTO> nameLst = null;
	      ArrayList<String> list = new ArrayList<String>();
	      // MemberDTO mdto = null;
	      System.out.println("Phone : " + phone);
	      try {
	         conn = db.connect();
	         String query = "select distinct mem_pn from tb_member where mem_pn like ?";
	         String data;
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, "%" + phone + "%");
	         rs = pstmt.executeQuery();

	         if (rs == null) {
	            System.out.println("NULL");
	         } else {
	            System.out.println("Not NULL");
	         }

	         while (rs.next()) {

	            data = rs.getString("mem_pn");
	            list.add(data);
	         }

	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         db.close(pstmt, conn);
	      }
	      return list;

	   }

	   public JSONArray fillListByName(String phone) {
	      String query = "select * from tb_member where mem_pn=?";
	      JSONArray jarray = new JSONArray();

	      try {
	         conn = db.connect();
	         pstmt = conn.prepareStatement(query);
	         pstmt.setString(1, phone);
	         rs = pstmt.executeQuery();

	         while (rs.next()) {

	            JSONObject json = new JSONObject();
	            json.put("name", rs.getString("mem_nm"));
	            json.put("email", rs.getString("mem_em"));
	            json.put("site", rs.getString("mem_site"));
	            jarray.add(json);
	            // System.out.println("pn : "+pn+"em : "+em+"site : "+site);
	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            db.close(rs, pstmt, conn);
	         } catch (Exception e2) {
	            e2.printStackTrace();
	         }
	      }
	      return jarray;
	   }
	   
	   /*
		 * 수정날짜 : 2016.10.15 수정자 : 정연우 목적 : 관리자가 Off-Boarding한 사람을 Off-Boarding 테이블에
		 * 모아놓기 위해
		 */
		public void off_boarding(String name, String phone, String email,
				String site) {

			String query = "insert into tb_block(block_nm,block_pn,block_em,block_site)"
					+ " values(?,?,?,?)";
			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				pstmt.setString(3, email);
				pstmt.setString(4, site);

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

		/*
		 * 수정날짜 : 2016.10.15 수정자 : 정연우 목적 : seq를 이용하여 Member정보를 가져와서 off_boarding
		 * 메서드에 넘겨준다.
		 */

		public MemberDTO fetchAllBySeq(int seq) {
			MemberDTO dto = null;
			String name, phone, email, site;
			int repeat_seq;
			String query = "select * from tb_member where mem_seq = ?";

			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					name = rs.getString("mem_nm");
					phone = rs.getString("mem_pn");
					email = rs.getString("mem_em");
					site = rs.getString("mem_site");

					dto = new MemberDTO(name, phone, email, site);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					db.close(rs, pstmt, conn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return dto;
		}

		/*
		 * 수정날짜 : 2016.10.15 수정자 : 정연우 목적 : seq를 이용하여 BlockMember정보를 가져와서
		 * insertBlock 메서드에 넘겨준다.
		 */
		public BlockDTO fetchAllBySeq_Block(int seq) {
			BlockDTO dto = null;
			String name, phone, email, site;
			int repeat_seq;
			String query = "select * from tb_block where block_seq = ?";

			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, seq);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					name = rs.getString("block_nm");
					phone = rs.getString("block_pn");
					email = rs.getString("block_em");
					site = rs.getString("block_site");

					dto = new BlockDTO(name, phone, email, site);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					db.close(rs, pstmt, conn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return dto;
		}

		public ArrayList<BlockDTO> selectAll_Block(String site) {
			ArrayList<BlockDTO> dtos = new ArrayList<BlockDTO>();
			String query = "select * from tb_block where block_site=?";
			BlockDTO dto = null;
			String name, phone, email;
			int seq;

			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, site);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					seq = rs.getInt("block_seq");
					name = rs.getString("block_nm");
					phone = rs.getString("block_pn");
					email = rs.getString("block_em");

					dto = new BlockDTO(seq, name, phone, email, site);
					dtos.add(dto);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					db.close(rs, pstmt, conn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return dtos;
		}

		public ArrayList<BlockDTO> selectByCondition_Block(String site,
				String option, String context) {
			ArrayList<BlockDTO> dtos = new ArrayList<BlockDTO>();
			String query = "select * from tb_block where " + option + " = '"
					+ context + "' and block_site=?";
			BlockDTO dto = null;
			String name, phone, email;

			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, site);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					name = rs.getString("block_nm");
					phone = rs.getString("block_pn");
					email = rs.getString("block_em");
					site = rs.getString("block_site");

					dto = new BlockDTO(name, phone, email, site);
					dtos.add(dto);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					db.close(rs, pstmt, conn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return dtos;
		}

		/*
		 * 수정날짜 : 2016.10.15 수정자 : 정연우 목적 : In Off-Boarding Tab, if admin restore
		 * member, selected tb_block member is deleted
		 */
		
		public void deleteBlock(int block_seq) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			DBCon db = new DBCon();

			try {
				conn = db.connect();
				String sql = "delete from tb_block where block_seq=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, block_seq);
				pstmt.executeUpdate();

			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close(pstmt, conn);
			}
		}

		/*
		 * 수정날짜 : 2016.10.15 수정자 : 정연우 목적 : Insert members who are restored by admin
		 * in BlockMember tab.
		 */
		
		public void insertBlock(BlockDTO bdto) {

			String query = "insert into tb_member(mem_nm,mem_pn,mem_em,mem_site,mem_reg_date)"
					+ " values(?,?,?,?,now())";
			try {
				conn = db.connect();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, bdto.getBlock_Nm());
				pstmt.setString(2, bdto.getBlock_Pn());
				pstmt.setString(3, bdto.getBlock_Em());
				pstmt.setString(4, bdto.getBlock_Site());

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
		
		/*
		 * 수정날짜 : 2016.10.15 	수정자 : 정연우 
		 * 목적 : Block 목록에 회원의 번호가 있는지 검사하고 있으면 True 없으면 False 반환
		 */
		
		public Boolean isCheckInBlock(String phone) {

			Boolean bool = false;
			try {
				conn = db.connect();

				String query = "select * from tb_block where block_pn=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, phone);
				rs = pstmt.executeQuery();

				if (rs.next())
					bool = true;
				else
					bool = false;

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					db.close(rs, pstmt, conn);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			return bool;
		}


}
