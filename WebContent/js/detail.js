/**
 * 
 */
var id = window.location.hash.substring(1);
if(id.length != 32) {
	window.location.href = "./index.html";
}
function loadGoodImage() {
	$('#goodImage').css('background-image', 'url("./good-image/' + id + '.jpg")');
}
function loadGoodData() {
	$.ajax({
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		url : "./getGoodById.do",
		data: JSON.stringify({
			"id" : id
		}),
		success : function(responseData) {
			//
			$('#goodName').text(responseData.name);
			$('#goodPrice').text(responseData.price + "元/" + responseData.spec);
			$('#goodStock').text("剩余：" + responseData.stock + responseData.spec);
			$('#goodRemark').text("介绍：" + responseData.remark);
		},
		error: function() {
			window.location.href = "./index.html";
		}
	});
}
loadGoodData();
loadGoodImage();