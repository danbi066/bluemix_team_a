<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/datepicker.css?ver=1">
<script src="js/bootstrap-datepicker.js?ver=1"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css?ver=1">
<title>Insert title here</title>

<style>@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);</style>
</head>

<style>
	.fontSize{
		font-size: 24px;
	}
	input{
		font-size: 24px;
	}
	select{
		font-size: 24px;
	}
	button{
		font-size: 24px;
	}
</style>

<script>
<%

request.setCharacterEncoding("UTF-8");

%>

window.onload = function() {
	document.frm.start_dt.value = window.opener.document.myForm.date.value;
	document.frm.phone.value = window.opener.document.myForm.phone.value;
	document.frm.name.value = window.opener.document.myForm.name.value;
	document.frm.start_time.value = window.opener.document.myForm.start_time.value;
	document.frm.end_time.value = window.opener.document.myForm.end_time.value;
	document.frm.email.value = window.opener.document.myForm.email.value;
	document.frm.confer_nm.value = window.opener.document.myForm.confer_nm.value;
	document.frm.title.value = window.opener.document.myForm.title.value;
	document.frm.site.value = window.opener.document.myForm.site.value;
	document.frm.del_pw.value = window.opener.document.myForm.del_pw.value;
	document.frm.color.value = window.opener.document.myForm.color.value;
	document.frm.rsvdate.value = window.opener.document.myForm.date.value;
	
}

function selectRadio()
{
	var test =$(":radio[name=rd]:checked").val();
	
	if(test == "repeat")
	{		
		$("input[name=end_dt]").attr("disabled",true);
		$("input[name=end_dt]").val("");
		
	}
	if(test == "end_dt") {
		$("input[name=end_dt]").attr("disabled",false);
		
		
	}
}

$(document).ready(function()
		{
		    $('#per').change(function(){
		          
		       var per = $('#per option:selected').val();
		        $("#summary").empty();
		        $("#summary").val("");
		        var sDate = $("#start_dt").val();
		           
		        var yy = parseInt(sDate.substr(0, 4), 10); 
		       var mm = parseInt(sDate.substr(5, 2), 10); 
		       var dd = parseInt(sDate.substr(8), 10);
		       var day = null;
		           
		       var d = new Date(yy,mm - 1, dd);
		       var weekday=new Array(7);
		       weekday[0]="일";
		       weekday[1]="월";
		       weekday[2]="화";
		       weekday[3]="수";
		       weekday[4]="목";
		       weekday[5]="금";
		       weekday[6]="토";
		           
		       sDate = sDate.replace(/\-/g,''); // 2016-09-20에서 -지워서 20160920 만들기
		           //getSecofWeek(sDate); // ~번째 주에서 주 만들기
		         
		         
		       mm = mm + "월 "; // ~월
		       day = weekday[d.getDay()] + "요일,"; // ~요일 
		       var dayofweek = getSecofWeek(sDate) + "번째 주 "; // ~번째 주
		       var total = day+per+"주마다 반복";
		       //$("#summary").val(total);
		       
		       if( per == 1) {
		          $("#summary").val(total);
		       } else if( per == 2) {
		          $("#summary").val(total);
		       } else if( per == 3) {
		          $("#summary").val(total);
		       } else if( per == 4) {
		          $("#summary").val(total);
		       }
		              
		    });
		}); 

/* 몇번째 주인지 반환하는 함수 */
function getSecofWeek(date){
	var d = new Date( date.substring(0,4), parseInt(date.substring(4,6))-1, date.substring(6,8) );
	var fd = new Date( date.substring(0,4), parseInt(date.substring(4,6))-1, 1 );
	return Math.ceil((parseInt(date.substring(6,8))+fd.getDay())/7);
}


	function closeMe(f) {
		if (f.end_dt.value == "") {
			alert("종료날짜를 선택하세요.");
			f.end_dt.focus();
			return false;
		} else if (f.end_dt.value < document.frm.start_dt.value) {
			alert("시작날짜보다 종료날짜가 이릅니다.");
			f.end_dt.focus();
			return false;
		}

		f.action = "RsvEveryWeekByDay.do";
		return true;

		window.close();
	}
</script>
<style>
@import url(http://fonts.googleapis.com/earlyaccess/nanumpenscript.css);
@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);
@import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);


	.img-responsive3 {
	   display: inline-block;
	   width: 100%;
	   max-width: 100%;
	   height: 1px;
	   margin-top: 1%;
	}
	
	.fontSize{
		font-size: 24px;
	}
	input{
		font-size: 24px;
	}
	select{
		font-size: 24px;
	}
	
</style>
<body>

   
   <img class="img-responsive2" src="image/colorbar-01.png" title="top">
   

   <form name="frm" method="post" onsubmit="return closeMe(this);">

      <input type="hidden" id="phone" name="phone">
      <input type="hidden" id="name" name="name">
      <input type="hidden" id="start_time" name="start_time">
      <input type="hidden" id="email" name="email">
      <input type="hidden" id="end_time" name="end_time">
      <input type="hidden" id="confer_nm"   name="confer_nm"> 
      <input type="hidden" id="title"   name="title">
      <input type="hidden" id="del_pw" name="del_pw">
      <input type="hidden" id="site" name="site">
      <input type="hidden" id="color" name="color">
      <input type="hidden" id="rsvdate" name="rsvdate">

	  <br>
      <div class="fontSize">
	  <center>
         <table class="col-md-9 col-sm-8 col-xs-12" style="font-family: 'Jeju Gothic', serif !important; font-size:80%;padding-top:5%;
         margin-left:3%" align="center">
            <tr>
               <td>반복빈도 :</td>
               <td>매주</td>
            </tr>
            <tr>
               <td>반복주기 :</td>
               <td><select id="per" name="per" style="font-size: 80%;">
               		 <option value="select" selected="selected">선택하세요!</option>
                     <option value="1">1</option>
                     <option value="2">2</option>
                     <option value="3">3</option>
                     <option value="4">4</option>
               </select></td>
            </tr>
            <tr>
               <td>
                  <!-- 사용자가 선택한 예약날짜가 시작날짜에 들어가야함 --> 시작날짜 :
               </td>
               <td><input type="text" name="start_dt" id="start_dt" readonly
                  style="width: 50%; font-size: 80%;" onfocus="getWeekday(this.value);"></td>
            </tr>
            <tr>
               <td>종료날짜 :</td>
               <td><input type="text" name="end_dt" id="end_dt"
                  style="width: 50%; font-size: 80%;"><br> <script>
                     $('#end_dt').datepicker({
                        dateFormat : 'yy-mm-dd'

                     });

                     $('#end_dt').datepicker('hide');
                  </script></td>
            </tr>
            <tr>
               <td>Summary :</td>
               <td><input type="text" readonly id="summary" name="summary" style="font-size: 80%;">
               </td>
            </tr>
         </table>
		</center>
		<br><br>
		
            <center>
               
               <button class="btn btn-primary" type="submit" value="설정" style="font-size: 70%;" onClick="closeMe();">설정</button>
               &nbsp;&nbsp;&nbsp;
               <button class="btn btn-primary" type="reset" value="취소" style="font-size: 70%;" onClick="window.close();">취소</button>
            </center>
         </div>
      </div>
   </form>
</body>
</html>