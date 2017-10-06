<%@page import="com.ibm.cof.dto.RsvDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ibm.cof.dto.HistoryDTO"%>
<%@ page import="java.util.ArrayList"%>

<!-- AdminRsvApprove.jsp by Nam Ho Kang -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자페이지</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- date picker -->
<link rel="stylesheet" href="css/datepicker.css?ver=1">
<script src="js/bootstrap-datepicker.js?ver=1"></script>

<style>
#admintitle {
	font-family: 'Jeju Gothic', serif;
	margin-left: 10%;
}

#subtitle {
	font-size: 18px;
	font-family: 'Jeju Gothic', serif;
	margin-left: 11%;
	margin-top: 1%;
	"
}

.nav-pills {
	font-family: 'Jeju Gothic', serif;
	font-size: 130%;
	margin-left: 10%;
	margin-top: 3%
}
</style>


</head>
<body>
	<!-- navigation bar -->
	<%@ include file="header.jsp"%>
	<h3 id="admintitle">&nbsp;&nbsp;${project} 관리자 페이지</h3>

	<ul class="nav nav-pills">
		<li><a href="AdminRsv.jsp">예약관리</a></li>
		<li><a href="AdminRsvHist.jsp">예약내역</a></li>
		<li class="active"><a href="SearchApprove.do">예약승인</a></li>
		<li><a href="SearchMember.do?option=all">On-Boarding</a></li>
		<li><a href="SearchMember.do?option=all">On-Boarding</a></li>
		<li><a href="SearchBlock.do?option=all">Off-Boarding</a></li>
		<li><a href="SelectConf.do">회의실관리</a></li>
		<li><a href="AdminSetting.do">설정</a></li>
	</ul>

	<div id="subtitle">승인 대기중인 예약 현황입니다.</div>


	<div class="container">

		<%
			ArrayList<RsvDTO> dtos = (ArrayList)request.getAttribute("list");
		%>
		<table class="table" style="margin-top: 30px;">
			<tr>
				<td>예약날짜</td>
				<td>시간</td>
				<td>예약자</td>
				<td>전화번호</td>
				<td>회의실</td>
				<td>회의제목</td>
				<td>승인/반려</td>
			</tr>
			<%
				if(dtos != null){
				for(int i=0; i<dtos.size(); i++){
					RsvDTO dto = dtos.get(i);
					String startTime = dto.getRsv_Start_Time();
					String endTime = dto.getRsv_End_Time();
					String time = startTime.substring(0,2) + ":" + startTime.substring(2,4) + "~";
					time += endTime.substring(0,2) + ":" + endTime.substring(2,4);
					
			%>
			<tr>
				<td><%=dto.getRsv_Date()%></td>
				<td><%=time%></td>
				<td><%=dto.getRsv_Mem_Nm()%></td>
				<td><%=dto.getRsv_Mem_Pn()%></td>
				<td><%=dto.getRsv_Confer_Nm()%></td>
				<td><%=dto.getRsv_Title()%></td>
				<td>
				<button type="button" class="btn btn-success" onClick="approve(<%=dto.getRsv_Seq()%>);">승인</button>
				<button type="button" class="btn btn-danger" onClick="deny(<%=dto.getRsv_Seq()%>);">반려</button></td>
			</tr>
			<%
				}}
			%>
		</table>

		<br>
		<br>
		<br>
		<br>
		<script> 
			function approve(idx){
				if (confirm("정말 승인하시겠습니까?") == true){
					location.replace("ApproveRsv.do?seq="+idx);
				}else return false;
			}
			
			function deny(idx){
				if (confirm("정말 반려하시겠습니까?") == true){
					location.replace("DenyRsv.do?seq="+idx);
				}else return false;
			}
		</script>
		

	</div>
	<br><br><br><br><br>
		<!-- footer -->
		<%@ include file="footer.jsp"%>
</body>
</html>