/**
 * 
 */

function callJSONData(url, callBackFunc, params) {

	$.ajax({
		url : url,
		dataType : "json",
		type : 'POST',
		traditional : false,
		data : params,
		cache : false,
		async : true,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : callBackFunc,
		error : callJSONDataError
	});
}

function callJSONDataError(data, errorType, errorMsg) {
	hidelWait();
	if (errorType) {
		alert('Ошибка: type=' + errorType + '\n msg=' + errorMsg + '\ndata='
				+ data.responseText);
		return;
	}
	alert(errorMsg);
	var w = window.open();
	w.document.write(data.responseText);
}

function showWait() {

}

function hidelWait() {

}