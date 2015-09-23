var ValueUtil = {};

ValueUtil.valNotUndefinedOrEmpty = function(str) {
	
	var flag = true;
	if(str == undefined || str == null) {
		flag = false;
	}
	if(str instanceof String) {
		flag = str == "" ? false : true;
	}
	if(str instanceof Number) {
		flag = str == 0 ? false : true;
	}
	if(str instanceof Boolean) {
		flag = str == false ? false : true;
	}
	if(str instanceof Object) {
		flag = str == {} ? false : true;
	}
	if(str instanceof Array) {
		flag = str == [] ? false : true;
	}
	return flag;
}

ValueUtil.initCancelBtnResetDialog = function() {
	$(".cancelButton").each(function() {
		$(this).on("click", function(event) {
			var $div = $(this).parent().parent(); 
			$div.find("input").val("");
			$div.find(".alert-error").html("").removeAttr("class");
		});
	});
}