var conference = [];
var hrSpan = 50; //1�떆媛� 留덈떎 pixel �겕湲�
var totalHeight = hrSpan * 9;

function ValidationCheck(){
   theForm = document.myForm;
   
   if (theForm.date.value == "") {
      alert("날짜를 선택하세요."); //check the date
      theForm.date.focus();
      return false;
   }
   else if (theForm.confer_nm.value == "") {
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
   
   function isEmail(email) {
	   var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	   return regex.test(email);
	}
   
   return true;
}

function PasswordValidation() {
	theForm = document.myForm;
	var userPW = theForm.del_pw.value;

	if (userPW == "") {
		alert("비밀번호를 입력해주세요"); //check password
		theForm.del_pw.focus();
		return false;
	}
}

/*
function isPossible(seq, pw){
   var possible = false;
   $.ajax({
      type : "post",
      url : "PasswordCheck.do",
      dataType : 'json',
      data : {
         seq : seq,
         pw: pw
      },
      success : function(data) {
         possible = data.result.valid;
         return possible;
      },
      error : function() {
         console.log("error");
      }
   });
   
   return possible;
}*/


/* 날짜 validation */
function getAdminMonth(projectname){
   $.ajax({
      type: "post",
      url : "GetAdminMonth.do",
      dataType : 'json',
      data: {
         project : projectname
      },

      success : function(data) {      
         
         var adminmonth = 0;
         var newdate; 
         var d = new Date();
         var date = leadingZeros(d.getFullYear(), 4) + '-' +
         leadingZeros(d.getMonth() + 1, 2) + '-' +
         leadingZeros(d.getDate(), 2);
         
         adminmonth = data.result[0].admin_month;  //db에서 admin_month 가져옴 
       //alert("이번달날짜:"+date);
         
         if((d.getMonth()+adminmonth+1)>12)  //이번달 + admin_month 가 12월을 초과하면 
         {
            newdate = leadingZeros((d.getFullYear()+1), 4) + '-' +
            leadingZeros((d.getMonth()+adminmonth-12) + 1, 2) + '-' +
            leadingZeros(d.getDate(), 2) + ' ';  //유의해야할게 1월은 javascript에서 0으로 표시 ㅎ...  
         }
         else 
         {
            newdate = leadingZeros(d.getFullYear(), 4) + '-' +
            leadingZeros((d.getMonth()+adminmonth) + 1, 2) + '-' +
            leadingZeros(d.getDate(), 2) + ' ';
         }

        //alert("달력에서고른날짜"+document.myForm.date.value);
         if (document.myForm.date.value > newdate) {
            alert("관리자가 지정한 날짜보다 초과하였습니다. 날짜를 다시 선택해주세요.");
            $('input[name="date"]').val("");    
         }
         else if(document.myForm.date.value < date){
            $('input[name="date"]').val("");
            alert("오늘보다 이전 날짜는 예약이 되지 않습니다. 날짜를 다시 선택해주세요.");
         }

         else {
            $('input[name="confer_nm"]').val("");
         }

      },
      error : function() {
         console.log("error");
      }
   });
   return adminmonth; 
}

   /*adminMonthValidation()*/
   

   function leadingZeros(n, digits) {
       var zero = '';
       n = n.toString();

       if (n.length < digits) {
           for (var i = 0; i < digits - n.length; i++)
               zero += '0';
       }
       return zero + n;
   }
   
   //�삤�뒛�쓽 �궇吏� 怨꾩궛
   function getToday(){
	   var currentTime = new Date();
       var date = "";
       var year = currentTime.getFullYear();
       date += year;
       var month = currentTime.getMonth() + 1;
       if(month < 10)
          date += "-0" + month + "-";
       else
          date += "-" + month + "-";
       var day = currentTime.getDate();
       if(day < 10)
          date += "0"+day;
       else
          date += day;
       
       return date;
   }

   // 遺꾩쓣 �떆媛� 臾몄옄�뿴濡� 蹂��솚�빐 以��떎 540 -> 09:00
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
   
   //�떆媛� 臾몄옄�뿴�쓣 遺꾩쑝濡� 蹂�寃� 0900 -> 540
   function timeToMin(time){
      var hr = parseInt(time.substring(0,2));
      var min = parseInt(time.substring(2,4));
      return hr*60 + min; 
   }
   
   //遺꾩쓣 �뵒鍮꾩뿉 ���옣�븯�뒗 �떆媛� 臾몄옄�뿴濡� 蹂�寃� 540 -> 0900
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
   
   function masterDisplay(project){
	   displayConf(project);
	   var date = getToday();
	   displaySchedule(date);
   }
      
      // �봽濡쒖젥�듃蹂� �쉶�쓽�떎 紐⑸줉�쓣 蹂댁뿬以��떎.
      function displayConf(value){
         $.ajax({
            type : "post",
            url : "SelectBySite.do",
            dataType : 'json',
            data : {
               proj : value
            },

            success : function(data) {
               var confNumber = data.result.length;
               var width = (document.getElementById('schedule').offsetWidth-70)/confNumber;
               var left = 70;
               
               $('#meetings').empty();
               $('#conference').empty();
               for (var i = 0; i < data.result.length; i++) {
                  $('#conference').append("<div class='conf' style='top:0px; left:"+left+"px; width:"+width+"px;'>"+
                  data.result[i].confname+"</div>");
                  
                  $('#meetings').append("<div class='line' style='top:0px; left:"+left+"px;'></div>");

                  left += width;
               }            
               displayTime();
            },
            error : function() {
               console.log("error");
            }
         });
      }
      
      // �떆媛꾩젙蹂대�� 蹂댁뿬以��떎 09:00 ~ 18:00
      function displayTime(){
          var time = 540;
          var top = 0;
          $('#timeDiv').empty();
          for (var i = 0; i < 10; i++) {

             if(i!=10){
             $('#timeDiv').append("<div class='time' style='top:"+top+"px; '>"+minToTime(time)+"</div>");
             } 
             //媛�濡쒖꽑湲뗪린
             $('#meetings').append("<div class='line2' style='top:"+top+"px; height:1px; width:"+(document.getElementById('schedule').offsetWidth-70)+"px;'</div>"); //�떆媛꾨퀎 媛�濡쒕씪�씤
             
             top += hrSpan;
             time += 60;
          }
          
          top = hrSpan/2; 
          for (var i = 0; i <= 8; i++) {
              //媛�濡쒖꽑湲뗪린(30遺꾩꽑)
              $('#meetings').append("<div class='line3' style='top:"+top+"px; height:1px; width:"+(document.getElementById('schedule').offsetWidth-70)+"px;'</div>"); //�떆媛꾨퀎 媛�濡쒕씪�씤
              
              top += hrSpan;
           }
          
      }

      // �궇吏쒕�� �겢由��븷 �븣留덈떎 �씠踰ㅽ듃媛� 諛쒖깮�븯硫� �쉶�쓽 �뒪耳�以꾩쓣 蹂댁뿬以��떎
      function displaySchedule(date){
         var site = document.getElementById('site').value;
         $.ajax({
            type : "post",
            url : "SelectRsvBySiteDate.do",
            dataType : 'json',
            data : {
               site : site,
               date : date
            },

            success : function(data) {
               var confNumber = data.confers.length;
               var width = (document.getElementById('schedule').offsetWidth - 70)/confNumber;
               var left = 70;
               var top, top2=0, height, j=0, bottom;
               var start, end, alphaT;
               var confName = "";
               var confIdx = 0;

               $('#meetings').empty();      

               conference = [];
               var check = [];
               for (var i = 0; i < data.confers.length; i++) {
                  conference.push(data.confers[i].confname);
                  check.push(false);
               }

               for (var i = 0; i < 10; i++) {
                  $('#meetings').append("<div class='line2' style='top: "+top2+"px; height:1px; left:0px; width:"+(document.getElementById('schedule').offsetWidth)+"px; </div>"); //�떆媛꾨퀎 媛�濡쒕씪�씤
                  top2 += hrSpan;
               }
               
               for (var i = 0; i < data.confers.length; i++) {
                  var flag = false;
                  bottom = 0;
                  
                  if(j < data.meetings.length){
                     confName = data.meetings[j].confer_nm;
                     confIdx = conference.indexOf(confName);
                     left = 70 + width * confIdx;
                     check[conference.indexOf(confName)] = true;
                  }  
                  
                  while (j < data.meetings.length && data.meetings[j].confer_nm == confName) {
                     flag = true;
                     
                     top = (timeToMin(data.meetings[j].start) - 540) / 30 * (hrSpan/2);
                     height = (timeToMin(data.meetings[j].end) - timeToMin(data.meetings[j].start)) / 30 * (hrSpan/2);

                     // �삁�빟�릺�뼱�엳吏� �븡�뒗 怨듦컙
                     start = bottom/(hrSpan/2)*30+540;
                     end = top/(hrSpan/2)*30+540;
                     alphaT = 0;
                     for(var k = start; k < end; k += 30){
                        $('#meetings').append("<div id='empty' class='empty'"
                           + "style='top:"+(bottom+alphaT)+"px; left:"+left+"px; width:"+width+"px; height:"+(hrSpan/2)+"px;'"
                           + "onClick='reserve("+confIdx+", "+start+", "+end+", "+k+");'></div>");
                        alphaT += (hrSpan/2);
                     }

                     bottom = top + height;

                     j++;
                  }

                  if(flag){
                     // �삁�빟�릺�뼱�엳吏� �븡�뒗 怨듦컙
                     start = bottom/(hrSpan/2)*30+540;
                     end = totalHeight/(hrSpan/2)*30+540;
                     alphaT = 0;
                     for(var k = start; k < end; k += 30){
                        $('#meetings').append("<div id='empty' class='empty'"
                           + "style='top:"+(bottom+alphaT)+"px; left:"+left+"px; width:"+width+"px; height:"+(hrSpan/2)+"px;'"
                           + "onClick='reserve("+confIdx+", "+start+", "+end+", "+k+");'></div>");
                        alphaT += (hrSpan/2);
                     }
                     
                     $('#meetings').append("<div class='line' style='top:0px; left:"+left+"px;'></div>"); //�쉶�쓽�떎蹂� �꽭濡쒕씪�씤 
                  }
               }
               
               for(var i = 0; i < data.confers.length; i++){
                  if(check[i] == false){
                     left = 70 + width * i;
                     top = 0;
                     for(var k = 540; k<1080; k+=30){
                        $('#meetings').append("<div id='empty' class='empty'"
                              + "style='top:"+top+"px; left:"+left+"px; width:"+width+"px; height:"+(hrSpan/2)+"px;'"
                              + "onClick='reserve("+i+", "+540+", "+1080+", "+k+");'></div>");
                        top += (hrSpan/2);
                     }
                     //�쉶�쓽�떎蹂� �꽭濡쒕씪�씤
                     $('#meetings').append("<div class='line' style='top:0px; left:"+left+"px;'></div>");
                  }
               }
               $('#meetings').append("<div class='line' style='top:0px; left:"+(left+width)+"px;'></div>");
               
               displayTime();
               
               // �삁�빟�맂 怨듦컙
               for(var j = 0; j < data.meetings.length; j++) {
            	   confName = data.meetings[j].confer_nm;
                   confIdx = conference.indexOf(confName);
                   left = 70 + width * confIdx;
                   
                   top = (timeToMin(data.meetings[j].start) - 540) / 30 * (hrSpan/2);
                   height = (timeToMin(data.meetings[j].end) - timeToMin(data.meetings[j].start)) / 30 * (hrSpan/2);

                   var color = data.meetings[j].color;
                   if(height <= (hrSpan/2))//30遺꾩쭨由� �삁�빟�씪�븣留� (湲��뵪媛� �옉�븘吏�誘�濡�)
                      $('#meetings').append(
                           
                            "<div id='reserved' class='meeting'"
                            + "style='background-color:"+color+"; top:"+top+"px; left:"+left+"px; width:"+width+"px; height:"+height+"px;'"
                            + "onClick='reserveInfo("+data.meetings[j].seq+");'>"+data.meetings[j].title+"</div>");

                   else
                   $('#meetings').append(
                        
                         "<div id='reserved' class='meeting'"
                         + "style='background-color:"+color+"; top:"+top+"px; left:"+left+"px; width:"+width+"px; height:"+height+"px; padding-top:"+(height-15)/2+"px'"
                         + "onClick='reserveInfo("+data.meetings[j].seq+");'>"+data.meetings[j].title+"</div>");
               }
            },
            error : function() {
               console.log("error");
            }
         });
      }
      

      // �삁�빟�릺�뼱 �엳�뒗 �떆媛꾪몴瑜� �겢由��떆 �긽�꽭 �젙蹂대�� 蹂댁뿬以��떎.
      function reserveInfo(seq){
         $("#register").hide();
         $("#registerInfo").show();

         $.ajax({
            type: "post",
            url : "SelectRsvInfo.do",
            dataType : 'json',
            data: {
               seq : seq
            },

            success : function(data) {
               $('#name').empty();
               $('#phone').empty();
               $('#email').empty();
               $('#date').empty();
               $('#confer_nm').empty();
               $('#title').empty();
               $('#rsv_seq').empty();
               $('#rsv_correct_pw').empty();
               $('#rsv_repeat_seq').empty();

               for(var i=0; i<data.result.length; i++) {
                  $('input[name="name"]').val(data.result[i].name);
                  $('input[name="phone"]').val(data.result[i].phone);
                  $('input[name="email"]').val(data.result[i].email);
                  $('input[name="date"]').val(data.result[i].date);
                  $('input[name="confer_nm"]').val(data.result[i].confer_nm);
                  $('input[name="title"]').val(data.result[i].title);
                  $('input[name="rsv_seq"]').val(""+seq);
                  $('input[name="rsv_correct_pw"]').val(data.result[i].password);
                  $('input[name="rsv_repeat_seq"]').val(data.result[i].repeat_seq);
                  $("#color").val(data.result[i].color).attr("selected", "selected");
                  
                  if(data.result[i].repeat_seq == 0){
                	  $('#adminRsvButton').empty();
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Modify(0);'>�닔�젙</button>");
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Delete(0);'>�궘�젣</button>");
                  }else{
                	  $('#adminRsvButton').empty();
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Modify(0);'>�닔�젙</button>");
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Delete(0);'>�궘�젣</button>");
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Modify(1);'>�쟾泥댁닔�젙</button>");
                	  $("#adminRsvButton").append("<button type='button' class='btn btn-primary' onClick='Delete(1);'>�쟾泥댁궘�젣</button>");
                  }
               }

               timeSelectListAll(data.result[0].start_time, data.result[0].end_time);
            },
            error : function() {
               console.log("error");
            }
         });
      }

      // 鍮� �쉶�쓽 �떆媛꾪몴瑜� �겢由��떆 �삁�빟 �븷 �닔 �엳�룄濡� �빐以��떎.
      function reserve(conf_idx, start, end, selectT){
         //alert(minToTime(start) + " - " + minToTime(end));
         //alert(minToTime(start)+"/"+minToTime(end)+"/"+minToTime(selectT));
         
         $("#registerInfo").hide();
         $("#register").show();

         timeSelectList(start, end, selectT);

         $('#name').empty();
         $('#phone').empty();
         $('#email').empty();
         $('#confer_nm').empty();
         $('#title').empty();
         $('#del_pw').empty();

         $('input[name="name"]').val("");
         $('input[name="phone"]').val("");
         $('input[name="email"]').val("");
         $('input[name="confer_nm"]').val(conference[conf_idx]);
         $('input[name="title"]').val("");
         $('input[name="del_pw"]').val("");
         $("#color").val("#00599D").attr("selected", "selected");
      }

      function timeSelectList(start, end, selectT){
         $('#start_time').empty();
         for(var i=start; i<end; i+=30) {
            $('#start_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
         }
         $("#start_time").val(minToStr(selectT)).attr("selected", "selected");

         $('#end_time').empty();
         for(var i=start+30; i<=end; i+=30) {
            $('#end_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
         }
         var endTime;
         if(selectT + 60 <= 1080)
        	 endTime = selectT + 60;
         else
        	 endTime = 1080;
         
         $("#end_time").val(minToStr(endTime)).attr("selected", "selected");
      }

      function timeSelectListAll(start, end){

         $('#start_time').empty();
         for(var i=540; i<1080; i+=30) {
            $('#start_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
         }
         $("#start_time").val(start).attr("selected", "selected");

         $('#end_time').empty();
         for(var i=570; i<=1080; i+=30) {
            $('#end_time').append("<option value='"+minToStr(i)+"'>"+minToTime(i)+"</option>");
         }
         $("#end_time").val(end).attr("selected", "selected");
      }


      $(document).ready(function(){
         $("#registerInfo").hide();
      });