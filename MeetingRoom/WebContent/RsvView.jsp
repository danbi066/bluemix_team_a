<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.cof.dto.ConfDTO"%>
<%@ page import="com.ibm.cof.dto.ProjectDTO"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회의실 예약 시스템</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js" type="text/javascript"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- ajax -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>  -->

<!-- 자동채우기 -->
<script src="js/ajax_auto.js?ver=1"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="js/RsvView.js?ver=1"></script>
<link rel="stylesheet" type="text/css" href="css/RsvView.css?ver=1">



<!--이모티콘 Font Awesome (added because you use icons in your prepend/append)-->
<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

<!-- date picker -->
<link rel="stylesheet" href="css/datepicker.css?ver=1">
<script src="js/bootstrap-datepicker.js?ver=1"></script>

<style>
.bootstrap-iso .formden_header h2,.bootstrap-iso .formden_header p,.bootstrap-iso form
   {
   font-family: Arial, Helvetica, sans-serif;
   color: black
}
.bootstrap-iso form button,.bootstrap-iso form button:hover {
   color: white !important;
}
.asteriskField {
   color: red;
}

textarea:focus,input:focus,input[type]:focus,.uneditable-input:focus {
   border-color: rgba(102, 102, 102, 0.7);
   box-shadow: 0 1px 1px rgba(51, 51, 51, 0.3) inset, 0 0 8px
      rgba(51, 51, 51, 0.9);
   outline: 0 none;
}

</style>

<script language="javascript">
//window.setTimeout('window.location.reload()',60000); //60초마다 새로고침


function adminMonthValidation(){
   var projectnm = "<%=(String)session.getAttribute("project")%>";
   if (document.myForm.date.value != "") {
      getAdminMonth(projectnm);
   }
}

</script>
</head>

<body>

<%String selectDate = (String)request.getAttribute("selectDate");%>

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

      <!-- 달력 -->
      <%//@ include file="calendar/calendar.jsp"%>
       
         <div class="form-inline" align="right">
         	날짜<input type="text" name="datepicker" id="datepicker" class="form-control" value='<%=selectDate%>' 
         	onchange="hide()">
         	<script>
         		
         		$('#datepicker').datepicker({
           	   		dateFormat : 'yyyy-mm-dd',
            		onSelect: function(selected,evnt) {
            		
                    test(selected);
                	}
            	});
            	$('#datepicker').datepicker('hide');
            
            	function test(value){
            	   alert(value);
            	}
         	</script>
         </div>
         
         <br>
          
          
         <div class="row">
            <!-- 회의실tab & 예약현황 -->
            <div class="col-md-12 col-sm-12 col-xs-12">
               <div>
                    <!-- 회의실탭 -->
                    <div class="col-md-6 col-sm-6 col-xs-12" id="conference"></div>
               
                  <div id="schedule" class="col-md-12 col-sm-12 col-xs-12">
                     <div id="timeDiv"></div>
                     <div id="meetings"></div>
                  </div>
               </div>
            </div> 
         </div>
         <div class="row">
            <!-- 회의실 예약 입력창 -->
            <div class="search-container" style="font-family: 'Nanum Gothic', serif;">
               <div class="row" id="resv_container">
                  <div class="col-md-12 col-sm-12 col-xs-12"></div>
                  <div>
                     <div class="well well-lg col-md-12 col-sm-12 col-xs-12" role="register">
                        <section class="register-form">
                           
                        <div class="row">
                           <div class="col-md-3">
                              <div class="form-group">
                                 <label class="control-label" for="date"> 날짜 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" class="form-control" id="date"  name="date">
                                    <!-- 수정2) 날짜 선택 가능하게 변경 Nam Ho Kang -->
                                       <script>
																				$('#date').datepicker({
																					dateFormat : 'yyyy-mm-dd'
																				});
																				$('#date').datepicker('hide');
																			</script>
																		<!-- 수정 END -->
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-group ">
                                 <label class="control-label " for="start_time"> 시작시간
                                 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <select class="form-control" name="start_time" id="start_time" onchange="timeclick()">
                                       <option value="">선택</option>
                                    </select>
                                 </div>
                              </div>
                           </div>
                           <div class="col-md-2">
                              <div class="form-group ">
                                 <label class="control-label " for="end_time"> 끝시간 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <select class="form-control" name="end_time" id="end_time" onchange="timeclick()">
                                       <option value="">선택</option>
                                    </select>
                                 </div>
                              </div>
                           </div>
                            <div class="col-md-2">
                              <div class="form-group">
                                 <label class="control-label " for="total_time"> 총시간 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <input class="form-control" id="total_time" name="total_time" type="text" readonly  />                                    
                                 </div>
                              </div>
                           </div>
                           <input type = "hidden" name = "hdn_time" id = "hdn_time" value = ""/>
                           <div class="col-md-3">
                              <div class="form-group ">
                                 <label class="control-label" for="color"> 색깔
                                 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-clock-o"> </i>
                                    </div>
                                    <select class="form-control" name="color" id="color">
                                       <option value="#00599D">파랑</option>
                                       <option value="#001D59">남색</option>
                                       <option value="#3399ff">하늘</option>
                                       <option value="#33cc33">초록</option>
                                       <option value="#fe9a2e">주황</option>
                                       <option value="#ff1a1a">빨강</option>
                                    </select>
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
                              <div class="form-group ">
                                 <label class="control-label" for="del_pw"> 비밀번호 </label>
                                 <div class="input-group">
                                    <div class="input-group-addon">
                                       <i class="fa fa-lock"> </i>
                                    </div>
                                    <input type="password" class="form-control" id="del_pw"
                                       name="del_pw" data-toggle="tooltip" data-trigger="manual"
                                       data-title="Caps lock is on">
                                 </div>
                              </div>
                           </div>
                           
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
                        <!-- 예약 버튼 -->
		                  <div id="register" align="right">
		                     <button type="button" class="btn btn-primary" onClick="Reservation();">예약</button>
		                  </div>
		                  <!-- 수정 및 삭제 -->
		                  <div id="registerInfo" align="right">
		                     <button type="button" class="btn btn-primary" onClick="Modify(0);">수정</button>
		                     <button type="button" class="btn btn-primary" onClick="Delete(0);">삭제</button>
		                  </div>
                        </section>
                        <div style="background-color: #3399ff"></div>
                        <input type="hidden" id="rsv_seq" name="rsv_seq">
                        <input type="hidden" id="rsv_repeat_seq" name="rsv_repeat_seq">
                        <input type="hidden" id="rsv_correct_pw" name="rsv_correct_pw">
                     </div>
                  </div>
               </div>
            </div>
         </div>
   		</div>
   </form>
   <div style="margin-top: 30px"></div>
	<script>
			//기능1) 예약시 총 시간 확인 Nam Ho Kang
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
			/*
			* 수정3) 화면 자동 Refresh
			* 3초에 한번씩 화면 자동 Refresh
			* Nam Ho Kang
			*/
      function autoRefresh_schedule(){
		  displaySchedule($("#date").val());
			}
			setInterval('autoRefresh_schedule()', 3000); 

      //해당 날짜 선택되어 있게
      <%
      if(selectDate != null){%>
         setDate('<%=selectDate%>');
      <%}else{
         %>
         var date = getToday();         
         setDate(date);
      <%}%>
      function setDate(date){
         document.myForm.date.value = date;
         document.myForm.datepicker.value = date;
         displaySchedule(date);
      }
      
      function Reservation() {
         if(ValidationCheck() == false){   
            return false;
         }
         
         if(PasswordValidation() == false){
        	 return false;
         }

         if(ApprovedCheck() == false){
        	 return false;
         }
         
         var status = "T";
         if ((document.myForm.end_time.value - document.myForm.start_time.value) >= 500)
        	 status = "N";
         $.ajax({
        	  async : false,
              type : "post",
              url : "Reservation.do",
              dataType : 'json',
              data : {
                 phone : document.myForm.phone.value,
                 name : document.myForm.name.value,
                 email : document.myForm.email.value,
                 site : document.myForm.site.value,                 
                 confer_nm : document.myForm.confer_nm.value,
                 date : document.myForm.date.value,
                 start_time : document.myForm.start_time.value,
                 end_time : document.myForm.end_time.value,
                 color : document.myForm.color.value,
                 title : document.myForm.title.value,
                 del_pw : document.myForm.del_pw.value,
                 approved : status
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

      function Modify(option) {
         if(ValidationCheck() == false){   
            return false;
         }
         if(PasswordCheck() == false){
             return false;
         }
         /*
         * 기능1)
         * Nam Ho Kang
         */
         var status = "T";
         if(document.myForm.hdn_time.value < 500){
        	if(ApprovedCheck() == false){
        	 	return false;
        	 }else status = "N";
         }else if ((document.myForm.end_time.value - document.myForm.start_time.value) >= document.myForm.hdn_time.value){
    		 	if(confirm("예약을 5시간 이상으로 수정하기 위해선 관리자의 승인이 필요합니다. 승인신청 하시겠습니까?") == false){
         	 	return false;
    	 	 	}else status = "N";
    	 		}
         
         $.ajax({
        	  async : false,
              type : "post",
              url : "ModifyRsv.do",
              dataType : 'json',
              data : {
                 rsv_seq : document.myForm.rsv_seq.value,
                 phone : document.myForm.phone.value,
                 name : document.myForm.name.value,
                 email : document.myForm.email.value,
                 site : document.myForm.site.value,
                 
                 confer_nm : document.myForm.confer_nm.value,
                 date : document.myForm.date.value,
                 start_time : document.myForm.start_time.value,
                 end_time : document.myForm.end_time.value,
                 color : document.myForm.color.value,
                 title : document.myForm.title.value,
                 del_pw : document.myForm.del_pw.value,
                 approved : status,
                 
                 repeat_seq : document.myForm.rsv_repeat_seq.value,
                 option : option
              },

              success : function(data) {
            	   var msg = "" + data.result.message;
                   if(msg == "sucess") {
	                  alert("수정되었습니다.");
	               } else {
	                  alert("시간이 겹쳐서 수정이 불가합니다.");
	               }
              },
              error : function() {
                 console.log("error");
              }
           });
         document.myForm.action = "home.do?selectDate="+document.myForm.date.value;
         document.myForm.submit();
      }

      function Delete(option) {
    	 if(PasswordCheck() == false){
              return false;
         }
    	 
         $.ajax({
        	  async : false,
              type : "post",
              url : "DeleteRsv.do",
              dataType : 'json',
              data : {
                 rsv_seq : document.myForm.rsv_seq.value,
                 repeat_seq : document.myForm.rsv_repeat_seq.value,
                 option : option
              },

              success : function(data) {
            	  var msg = "" + data.result.message;
                  if(msg == "sucess") {
	                  alert("삭제되었습니다.");
	               }
              },
              error : function() {
                 console.log("error");
              }
           });
         //setDate(document.myForm.date.value);
         document.myForm.action = "home.do?selectDate="+document.myForm.date.value;
         document.myForm.submit();
      }
      
      function PasswordCheck(){
	   	  theForm = document.myForm;
	   	  var userPW = theForm.del_pw.value;
	   	  var correctPW = theForm.rsv_correct_pw.value;
	   	  
	   	  var projectnm = "<%=(String)session.getAttribute("project")%>";
	   	  var admin = "<%=(String)session.getAttribute("admin")%>";

			if (projectnm != "master" && admin == "yes")
				return true;

			if (userPW == "") {
				alert("비밀번호를 입력하세요.");
				theForm.del_pw.focus();
				return false;
			}

			if (userPW != correctPW) {
				alert("비밀번호가 일치하지 않습니다.");
				theForm.del_pw.focus();
				return false;
			}

			return true;
		}

		var title = document.getElementById("title");
		title.addEventListener("focus", confCheck, true);

		function confCheck() {
			theForm = document.myForm;
			if (theForm.confer_nm.value == ""){
				alert("회의실을 선택하세요.");
				title.blur();
			}
		}
	</script>
	<!-- footer -->
   <%@ include file="footer.jsp"%>  
   
</body>
</html>