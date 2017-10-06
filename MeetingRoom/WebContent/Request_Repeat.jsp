<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.cof.dto.RsvDTO"%>
<%@ page import="java.util.ArrayList"%>

<!-- Request_Repeat.jsp by Nam Ho Kang -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회의실 예약 신청</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!--이모티콘 Font Awesome (added because you use icons in your prepend/append)-->
<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

<style>
@import url(http://fonts.googleapis.com/earlyaccess/kopubbatang.css);/*font*/
@import url(http://fonts.googleapis.com/earlyaccess/nanumpenscript.css);
body {
   font-family: 'Noto Sans KR', sans-serif;
}

input {
   /*사용자가 입력하는 내용은 나눔명조체*/
   font-family: 'Noto Sans KR', sans-serif !important;
}

textarea:focus,input:focus,input[type]:focus,.uneditable-input:focus {
   border-color: rgbargba(0, 29, 89, 0.34);
   box-shadow: 0 1px 1px rgba(0, 29, 89, 0.34) inset, 0 0 8px
      rgba(0, 29, 89, 0.34);
   outline: 0 none;
}
.col-centered {
   float: none;
   margin-right: auto;
   margin-left: auto;
}
</style>
</head>

<body>

   <!-- navigation bar -->
   <%@ include file="header.jsp"%>
<form method="post" name="myForm" action="Reservation.do">
      <div class="container">
         <!-- 사이트 선택 -->
         <div class="row" >
         	<div class="col-md-6 col-sm-9 col-xs-12">
         	<%if(session.getAttribute("project").equals("master")){ %>
         	   <select class="form-control" id="site" name="site" onchange="masterDisplay(this.value);">
         	   <option value="">선택하세요</option>
         	   <%ArrayList<String> proj = (ArrayList) request.getAttribute("proj");
           		    if (proj != null) {
            	    	 for (int i = 0; i < proj.size(); i++) {
                	     String name = proj.get(i);%>
               		<option value="<%=name%>"><%=name%></option> <%}%>
            	</select>
            <%}}else{
               String project = (String)session.getAttribute("project");%>
               <h2 style="margin-left: 10%; font-family: 'Jeju Gothic', serif;">${project} 회의실</h2>
               <input type="hidden" id="site" name="site" value=${project}>
               <script>displayConf('<%=project%>');</script>
            <%}%>
         	</div>
      	</div>

         <br>
          
          
         
            <!-- 회의실 예약 입력창 -->
            <div class="search-container" style="font-family: 'Nanum Gothic', serif;">
               <div class="row" id="resv_container">
                  <div class="col-md-12 col-sm-12 col-xs-12"></div>
                     <div class="well well-lg col-md-12 col-sm-12 col-xs-12" role="register">
                        <section class="register-form">
                           
                        <div class="row">
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label " for="start_time"> 시작시간
                                 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <select class="form-control" name="start_time" id="start_time" onchange="timeclick()">
                                    </select>
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label " for="end_time"> 끝시간 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <select class="form-control" name="end_time" id="end_time" onchange="timeclick()">
                                    </select>
                                 </div>
                              </div>
                           </div>
                            <div class="col-md-4">
                              <div class="form-group">
                                 <label class="control-label " for="total_time"> 총시간 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <input class="form-control" id="total_time" name="total_time" type="text" readonly  />                                      
                                    <input type = "hidden" id="hdn_time" name = "hdn_time">                                  
                                 </div>
                              </div>
                           </div>
                           </div>
                        <div class="row">
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label" for="confer_nm"> 회의실 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-building"> </i>
                                    </div>
                           					<!-- 수정2) 회의실 선택 가능하게 변경 Nam Ho Kang -->                                    
                                    <select class="form-control" id="confer_nm"
                                       name="confer_nm">
                                       <option value="에스프레소A">에스프레소A</option>
                                       <option value="에스프레소B">에스프레소B</option>
                                       <option value="마끼야또">마끼야또</option>
                                       <option value="카푸치노">카푸치노</option>
                                    </select>
                                    <!-- 수정 END -->
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label" for="title"> 회의제목 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-commenting"> </i>
                                    </div>
                                    <input type="text" class="form-control" id="title"
                                       name="title">
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-4">
                           		<div class="form-group">
                           		     <label class="control-label" for="rep"> 반복 </label>
                           		     <br>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "mon" value="">월</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "tues" value="">화</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "wed" value="">수</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "thur" value="">목</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "fri" value="">금</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "weekly" value="">매주</label>
                           		       	<label class="checkbox-inline"><input type="checkbox" id = "monthly" value="">매달</label>
                           		       	<input type = "hidden" name = "hdn_days" id = "hdn_days">
                           		</div>
                          </div>
                      	</div>
                        <div class="row">
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label" for="phone"> 전화번호 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-phone"> </i>
                                    </div>
                                    <input type="text" id="phone" class="form-control"
                                       name="phone" value="0104995">
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label" for="name"> 이름 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-smile-o"> </i>
                                    </div>
                                    <input class="form-control" id="name" name="name"
                                       placeholder="ex)홍길동" type="text" />
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-4">
                              <div class="form-group ">
                                 <label class="control-label" for="email"> 이메일 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-envelope-o"></i>
                                    </div>
                                    <input type="text" class="form-control" id="email"
                                       name="email">
                                 </div>
                              </div>
                           </div>                        
                        </div>
                        <div class="row">
                        <div class = "col-md-1">
                        </div>
                        	<div class="col-md-10">
                        		<div class="form-group" >
                        			<label class="control-label" for="details"> 상세내용</label>
                                 <div class="input-group" style = "height: 100px;">
                                    <div class="input-group-addon" >
                                       <i class="fa fa-pencil-square-o"></i>
                                    </div>
                                    <textarea maxlength="140" class="form-control" id="details"
																		name="details" placeholder="상세내용" style = "height: 100px;"></textarea>
                                 </div>
                              </div>
                        </div>
                        </div>
                        <!-- 예약 버튼 -->
		                  <div id="request" align="right">
		                     <button type="button" class="btn btn-primary" onClick="ReqRepRsv();">신청</button>
		                  </div>
                  </div>
         </div>
         </div>
         </div>
   </form>
		<!-- footer -->
		<%@ include file="footer.jsp"%>
</body>

<script>
	function ReqRepRsv(){
		if (ValidationCheck() == false || DoubleCheck() == false)
			return false;
		alert(document.myForm.mon.value);
		$.ajax({
      	  async : false,
            type : "post",
            url : "ReqRepRsv.do",
            dataType : 'json',
            data : {
               phone : document.myForm.phone.value,
               name : document.myForm.name.value,
               email : document.myForm.email.value,
               site : document.myForm.site.value,                 
               confer_nm : document.myForm.confer_nm.value,
               start_time : document.myForm.start_time.value,
               end_time : document.myForm.end_time.value,
               title : document.myForm.title.value,
               mon : document.myForm.mon.value,
            	 tues : document.myForm.tues.value,
            	 wed : document.myForm.wed.value,
            	 thur : document.myForm.thur.value,
            	 fri : document.myForm.fri.value,
            	 weekly : document.myForm.weekly.value,
            	 monthly : document.myForm.monthly.value,
            	 details : document.myForm.details.value

            },
            
            success : function(data) {
          	 var msg = "" + data.result.message;
               if(msg == "sucess") {
                  alert("예약이 되었습니다.");
               } else {
                  alert("선택하신 날짜,회의실,시간에 예약이 되어있거나 Off-Boarding된 회원이므로 예약이 불가능 합니다.");
               }
                  
            },
            error : function() {
               console.log("error");
            }
         });
       document.myForm.action = "home.do?selectDate="+document.myForm.date.value;
       document.myForm.submit();
	}

	function timeclick(){
		var total_time =  document.myForm.end_time.value - document.myForm.start_time.value;
		if (total_time <= 0){
			alert("시간을 확인하여 주십시오.");
			return false;
		}
		if (total_time <= 70)
			total_time = "0" + total_time;	
		var total_time_hour = total_time.toString().substring(0, 1);
		var total_time_minute = total_time.toString().substring(1, 3);
		if (total_time_minute == "70")
			total_time_minute = "30";
		total_time = total_time_hour + "시간 " + total_time_minute + "분";
		$('input[name="total_time"]').val(total_time);
	}
	
	function timeSelectList(){
		$('#start_time').empty();
		for(var i=540; i<1080; i+=30) {
		$('#start_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
	}
		$('#end_time').empty();
	for(var i=570; i<=1080; i+=30) {
		$('#end_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
	}
	 }
 
function minToTime(time){
    var hr = time/60 - time/60%1;
    var min = time%60;
    var str;
    if(hr<10)
       str = "0"+hr+":";
    else
       str = hr+":";
    if(min<10)
       str += "0"+min;
    else
       str += min;
    return str;
 }
 
  function minToStr(time){
    var hr = time/60 - time/60%1;
    var min = time%60;
    var str = "";
    if(hr<10)
       str += "0"+hr;
    else
       str += hr;
    if(min<10)
       str += "0"+min;
    else
       str += min;
    return str;
 }
  
  function ValidationCheck(){
	  theForm = document.myForm;
	   
	   if (theForm.confer_nm.value == "") {
	      alert("회의실을 선택하세요."); //select conference room
	      theForm.confer_nm.focus();
	      return false;
	   }
	   else if (theForm.start_time.value >= theForm.end_time.value) {
	      alert("유효하지 않은 시간입니다."); //invalid time
	      return false;
	   }
	   else if (theForm.title.value == "") {
	      alert("회의제목을 입력하세요."); //write title
	      theForm.title.focus();
	      return false;
	   }
	   else if (theForm.phone.value == "") {
	      alert("전화번호를 입력하세요."); //write telephone number
	      theForm.phone.focus();
	      return false;
	   }
	   else if (theForm.name.value == "") {
	      alert("이름을 입력하세요."); //write name
	      theForm.name.focus();
	      return false;
	   }
	   else if (theForm.email.value == "") {
	      alert("이메일을입력하세요."); //write email
	      theForm.email.focus();
	      return false;
	   }	   
	   else if (theForm.title.value.length >= 20) {
	      alert("회의제목을 20자 이내로 입력해주세요."); // only under 20 characters
	      theForm.title.focus();
	      return false;
	   }
	   else if (!parseInt(theForm.phone.value)) {
	      alert("전화번호는 숫자만 입력해주세요"); // write only numbers
	      theForm.phone.focus();
	      return false;
	   }
	   else if (!(theForm.phone.value.length == 11 || theForm.phone.value.length == 10)) {
		  alert("전화번호 자리수는 10~11개여야 합니다."); // phone number should be inbetween 10 to 11
	      theForm.phone.focus();
	      return false;
	   }
	   else if (theForm.name.value.length >= 15) {
	      alert("이름을 확인해 주세요"); // check name
	      theForm.name.focus();
	      return false;
	   }
	   else if (!isEmail(theForm.email.value)) {
	      alert("이메일을 확인해 주세요"); // check email
	      theForm.email.focus();
	      return false;
	   }
	   else if (theForm.details.value == "" || theForm.details.value.length <= 3){
		 		alert("상세내용을 정확히 입력해 주세요");
		 		theForm.details.focus();
		 		return false;
	   }
	   else if (document.getElementById("mon").checked == false && document.getElementById("tues").checked == false && 
			   			document.getElementById("wed").checked == false && document.getElementById("thur").checked == false &&
			   			document.getElementById("fri").checked == false ){
		   alert("반복을 원하는 요일을 선택해 주세요");
		   return false;
	   }else if (document.getElementById("weekly").checked == false && document.getElementById("monthly").checked == false ){
		   alert("주간/월간 반복을 선택해 주세요");
		   return false;
		}		
	   
	   function isEmail(email) {
		   var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		   return regex.test(email);
		}
	   
	   return true;
  }
  
  function DoubleCheck(){
	  var day = "";
	  if (document.getElementById("mon").checked == true && document.getElementById("tues").checked == true && 
	   		document.getElementById("wed").checked == true && document.getElementById("thur").checked == true &&
	   		document.getElementById("fri").checked == true){
		  day = "매일";
	  }else{
		  if (document.getElementById("mon").checked == true){
			  document.myForm.mon.value = "T";
			  day = ", 월";
		  }if (document.getElementById("tues").checked == true){
			  document.myForm.tues.value = "T";
			  day = day + ", 화";
		  }if (document.getElementById("wed").checked == true){
			  document.myForm.wed.value = "T";
			  day = day + ", 수";
		  }if (document.getElementById("thur").checked == true){
			  document.myForm.thur.value = "T";
			  day = day + ", 목";
		  }if (document.getElementById("fri").checked == true){
			  document.myForm.fri.value = "T";
			  day = day + ", 금";
		  }day = day.substr(2, day.length);
	  }
	  
	  var rep = "";
	  if (document.getElementById("weekly").checked == true){
		  document.myForm.weekly.value = "T";
		  if(document.getElementById("monthly").checked == true){
			  document.myForm.monthly.value = "T";
		  	rep = "매달, 매주";
		  }else rep = "매주";
	  }else rep = "매달";
	  
	  var startTime = document.myForm.start_time.value;
		var endTime = document.myForm.end_time.value;
		var time = startTime.substring(0,2) + ":" + startTime.substring(2,4) + "~";
		time += endTime.substring(0,2) + ":" + endTime.substring(2,4);
		$('input[name="hdn_days"]').val(day);
	  var proceed = confirm("내용을 확인하여 주십시오." +
			  									"\n회의 제목: " + theForm.title.value +
			  								  "\n반복: " + rep +
			  									"\n요일: " + day +
			  									"\n시간: " + time +
			  									"\n회의실: " + theForm.confer_nm.value +
			  									"\n상세내용: " + theForm.details.value);
	  if (proceed == false){
		  document.myForm.mon.value = "F";
		  document.myForm.tues.value = "F";
		  document.myForm.wed.value = "F";
		  document.myForm.thur.value = "F";
		  document.myForm.fri.value = "F";
		  document.myForm.weekly.value = "F";
		  document.myForm.monthly.value = "F";
		  return false;
	  }
	  return true;
  }
  
	$(document).ready(function(){
		timeSelectList();
		timeclick();
	});

</script>
</html>