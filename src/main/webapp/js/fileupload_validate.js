/**

 * upload_01.jsp validation
 * => jquery validation 플러그인 이용

 */

$(document).ready(function(){
	$("#uploadForm").validate({		
		//규칙지정
		rules:{
			file:{
				extension:"png|jpg|gif|jpeg",
				maxsizetotal:2097152
			}			
		},

		//개발자가 원하는 에러 메시지 작성
		messages:{
			file:{
				required : "(파일 선택)",	
				extension:"(이미지 파일 입력)",
				maxsizetotal:"(파일 사이즈 초과)"
			}
		},		
		errorElement: "span",
		errorPlacement: function(error, element) {
			
			if(element.prop("type")==="file"){
				$( element ).closest( "form" )
				.find( "small[id='" + element.attr( "id" ) + "']" )
					.append(error);
			}
		},

	});	

});
