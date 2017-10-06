package com.ibm.cof.dto;

/*
 * ReqDTO.java
 * By Nam Ho Kang
 */

public class ReqDTO {
	private int Req_Seq;
	private String Req_Start_Time;
	private String Req_End_Time;
	private String Req_Title;
	private String Req_Site;
	private String Req_Confer_Nm;
	private String Req_Mem_Nm;
	private String Req_Mem_Pn;
	private String Req_Mem_Em;
	private String Req_mon;
	private String Req_tues;
	private String Req_wed;
	private String Req_thur;
	private String Req_fri ;
	private String Req_weekly;
	private String Req_monthly;
	private String Req_details;

	public ReqDTO() {
	};


	public ReqDTO(String start_time, String end_time, String title, String site, String confer_nm, String name, String phone, String email, String mon, String tues, String wed, String thur, String fri, String weekly, String monthly, String details) {
		Req_Start_Time = start_time;
		Req_End_Time = end_time;
		Req_Title = title;
		Req_Site = site;
		Req_Confer_Nm = confer_nm;
		Req_Mem_Nm = name;
		Req_Mem_Pn = phone;
		Req_Mem_Em = email;
		Req_mon = mon;
		Req_tues = tues;
		Req_wed = wed;
		Req_thur = thur;
		Req_fri = fri;
		Req_weekly = weekly;
		Req_monthly = monthly;
		Req_details = details;
	}


	public int getReq_Seq() {
		return Req_Seq;
	}

	public void setReq_Seq(int req_seq) {
		Req_Seq = req_seq;
	}

	public String getReq_Start_Time() {
		return Req_Start_Time;
	}

	public void setReq_Start_Time(String req_Start_Time) {
		Req_Start_Time = req_Start_Time;
	}

	public String getReq_End_Time() {
		return Req_End_Time;
	}

	public void setReq_End_Time(String req_End_Time) {
		Req_End_Time = req_End_Time;
	}

	public String getReq_Title() {
		return Req_Title;
	}

	public void setReq_Title(String req_Title) {
		Req_Title = req_Title;
	}

	public String getReq_Site() {
		return Req_Site;
	}

	public void setReq_Site(String req_Site) {
		Req_Site = req_Site;
	}

	public String getReq_Confer_Nm() {
		return Req_Confer_Nm;
	}

	public void setReq_Confer_Nm(String req_Confer_Nm) {
		Req_Confer_Nm = req_Confer_Nm;
	}

	public String getReq_Mem_Nm() {
		return Req_Mem_Nm;
	}

	public void setReq_Mem_Nm(String req_Mem_Nm) {
		Req_Mem_Nm = req_Mem_Nm;
	}

	public String getReq_Mem_Pn() {
		return Req_Mem_Pn;
	}

	public void setReq_Mem_Pn(String req_Mem_Pn) {
		Req_Mem_Pn = req_Mem_Pn;
	}

	public String getReq_Mem_Em() {
		return Req_Mem_Em;
	}

	public void setReq_Mem_Em(String req_Mem_Em) {
		Req_Mem_Em = req_Mem_Em;
	}
	

	public String getReq_mon() {
		return Req_mon;
	}

	public void setReq_mon(String req_mon) {
		Req_mon = req_mon;
	}
	public String getReq_tues() {
		return Req_tues;
	}

	public void setReq_tues(String req_tues) {
		Req_tues = req_tues;
	}
	public String getReq_wed() {
		return Req_Mem_Em;
	}

	public void setReq_wed(String req_wed) {
		Req_wed = req_wed;
	}
	public String getReq_thur() {
		return Req_Mem_Em;
	}

	public void setReq_thur(String req_thur) {
		Req_thur = req_thur;
	}
	public String getReq_fri() {
		return Req_Mem_Em;
	}

	public void setReq_fri(String req_fri) {
		Req_fri = req_fri;
	}
	public String getReq_weekly() {
		return Req_Mem_Em;
	}

	public void setReq_weekly(String req_weekly) {
		Req_weekly = req_weekly;
	}
	public String getReq_monthly() {
		return Req_Mem_Em;
	}

	public void setReq_monthly(String req_monthly) {
		Req_monthly = req_monthly;
	}
	public String getReq_details() {
		return Req_Mem_Em;
	}

	public void setReq_details(String req_details) {
		Req_details = req_details;
	}

}
