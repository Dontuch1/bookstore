/**
 * 
 */
 
 function inputNum(input){
	input.value = input.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}

/*************************************************************
 * 비동기 호출(json 형식으로 서버에서 수신)
 *************************************************************/
function doAjax(url, param, callback) {
    $.ajax({
        async    : true        // true, false
       ,type     : "POST"       // POST, GET
       ,url      : url
       ,dataType : "json"       // 전송받을 데이터의 타입[xml, html, script, json 등 지정 가능, 미지정시 자동 판단]
     //,timeout  : 30000        // 제한시간 지정
       ,cache    : false        // true, false
       ,data     : param        // 서버에 보낼 파라메터 [form 에 serialize() 실행시 a=b&c=d 형태로 생성되며 한글은 UTF-8 방식으로 인코딩, {a:b, c:d} json 형식 입력 가능]
       ,contentType : "application/x-www-form-urlencoded; charset=UTF-8"
       ,success : function(data) {
           if(data.error){
               //Ajax요청은 성공하였으나 오류가 발생한 경우
           }else if($.type(callback) == "function"){
               callback(data);
           }

       }
       ,error: function(request, status, error){      // 통신 에러 발생시 처리
           console.log("시스템 오류가 발생하였습니다.");
           console.log(request);
           console.log(status);
           console.log(error); 
       }
       ,beforeSend: function(xhr) {        // 통신을 시작할때 처리
            console.log("Ajax 요청 준비");

       }
       ,complete  : function() {        // 통신이 완료된 후 처리
            console.log("Ajax 요청 완료");
       }
    });
};

/*************************************************************
 * 비동기 호출(HTML 형식으로 서버에서 수신)
 *************************************************************/
function doAjaxHtml(url, param, callback) {
    $.ajax({
        async    : true        // true(비동기), false(동기)
       ,type     : "POST"       // POST, GET
       ,url      : url
       ,dataType : "html"       // 전송받을 데이터의 타입[xml, html, script, json 등 지정 가능, 미지정시 자동 판단]
     //,timeout  : 30000        // 제한시간 지정
       ,cache    : false        // true, false
       ,data     : param        // 서버에 보낼 파라메터 [form 에 serialize() 실행시 a=b&c=d 형태로 생성되며 한글은 UTF-8 방식으로 인코딩, {a:b, c:d} json 형식 입력 가능]
       ,contentType : "application/x-www-form-urlencoded; charset=UTF-8"
       ,success : function(data) {
           if(data.error){
               //Ajax요청은 성공하였으나 오류가 발생한 경우
           }else if($.type(callback) == "function"){
               callback(data);
           }

       }
       ,error: function(request, status, error){      // 통신 에러 발생시 처리
           console.log("시스템 오류가 발생하였습니다.");
           console.log(request);
           console.log(status);
           console.log(error); 
       }
       ,beforeSend: function(xhr) {        // 통신을 시작할때 처리
            console.log("Ajax 요청 준비");

       }
       ,complete  : function() {        // 통신이 완료된 후 처리
            console.log("Ajax 요청 완료");
       }
    });
};

/*************************************************************
 * 비동기 호출(파일 다운로드)
 *************************************************************/
function doAjaxDownload(url, param) {
    $.ajax({
        async    : true        // true, false
       ,type     : "POST"       // POST, GET
       ,url      : url
	   ,xhr: function () { 
			let xhr = new XMLHttpRequest(); 
			xhr.onreadystatechange = function () { 
                //response 데이터를 바이너리로 처리한다. 세팅하지 않으면 default가 text 
				xhr.responseType = "blob"; 
			}; 
			return xhr; 
		} 
     //,timeout  : 30000        // 제한시간 지정
       ,cache    : false        // true, false
       ,data     : param        // 서버에 보낼 파라메터 [form 에 serialize() 실행시 a=b&c=d 형태로 생성되며 한글은 UTF-8 방식으로 인코딩, {a:b, c:d} json 형식 입력 가능]
       ,contentType : "application/x-www-form-urlencoded; charset=UTF-8"
       ,success : function(data, message, xhr) {
    	   if (xhr.readyState == 4 && xhr.status == 200) {
				// 성공했을때만 파일 다운로드 처리하고
				let disposition = xhr.getResponseHeader('Content-Disposition'); 
				let filename; 
				if (disposition && disposition.indexOf('attachment') !== -1) { 
					let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/; 
					let matches = filenameRegex.exec(disposition); 
					if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, ''); 
				} 
				let blob = new Blob([data]); 
				let link = document.createElement('a'); 
				link.href = window.URL.createObjectURL(blob); 
				link.download = filename; 
				link.click(); 
			}else{   
			    //실패했을때는 alert 메시지 출력
				console.log("파일 다운로드에 실패");
			} 

       }
       ,error: function(request, status, error){      // 통신 에러 발생시 처리
           console.log("시스템 오류가 발생하였습니다.");
           console.log(request);
           console.log(status);
           console.log(error); 
       }
       ,beforeSend: function(xhr) {        // 통신을 시작할때 처리
            console.log("Ajax 요청 준비");

       }
       ,complete  : function() {        // 통신이 완료된 후 처리
            console.log("Ajax 요청 완료");
       }
    });
};

/*************************************************************
 * 파라미터 인코딩 // Usage: utf8_to_b64('✓ à la mode'); // "4pyTIMOgIGxhIG1vZGU="
 *************************************************************/
function utf8_to_b64( str ) {
    return window.btoa(unescape(encodeURIComponent( str )));
}
/*************************************************************
 * 파라미터 디코딩 // Usage: b64_to_utf8('4pyTIMOgIGxhIG1vZGU='); // "✓ à la mode"
 *************************************************************/
function b64_to_utf8( str ) {
    return decodeURIComponent(escape(window.atob( str )));
}



