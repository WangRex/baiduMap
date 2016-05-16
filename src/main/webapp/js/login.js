$(document).ready(function() {

	var form = $(".form-signin");
	$("#loginButton").click(function(e) {
		e.preventDefault();
		e.stopPropagation();
		$.ajax({
			type : "post",
			url : "/baiduMap/login/login",
			data : form.serialize(),
			cache : false,
			dataType : "json"
		}).done(function(data) {
			if(ValueUtil.valNotUndefinedOrEmpty(data)) {
				$("#errorMessage").html("").hide();
				$("#loginCancelButton").click();
				$("#userName").html(data.userName);
				$("#userName").data("id", data.id);
				$("#userName").data("role", data.role);
				$("#userName").data("token", data.token);
				$(".preSignon").hide();
				$(".postSignon" + data.role).show();
				$(".postSignon").show();
				form[0].reset();
				initMarkerAndTable(data.id);
			} else {
				$("#errorMessage").html("用户名或密码错误!!!").addClass("alert alert-error").show();
			}
		}).error(function(data) {
			$("#errorMessage").html("用户名或密码错误!!!").addClass("alert alert-error").show();
		});
	});
	$("#logoutButton").click(function() {
		$.ajax({
			type : "post",
			url : "/baiduMap/login/removeUserSession",
			data : form.serialize(),
			cache : false
		}).done(function(data) {
			form[0].reset();
			$("#userName").html("访客");
			$(".preSignon").show();
			$("*[class*='postSignon']").hide();
			$("#userName").removeData();
			$("#mainContentMap").show();
			$("#mainContent").hide();
			//initMarkerAndTable(undefined);
			$("#markerManage").hide();
		});

	});
	$("#myProfileButton").on("click", function() {

		$.ajax({
			type : "post",
			url : "/baiduMap/login/getUser",
			data : {id : $("#userName").data("id")},
			cache : false,
			dataType : "json"
		}).done(function(data) {
			console.log(data);
			$("#mainContentTitle").html("我的信息");
			loadTableApp.myProfileTbl.show(new MyProfileView({model:new Backbone.Model(data)}));
			loadTableApp.start();
			initDataTable("userTable");
		});
		$("#mainContentMap").hide();
		$("#mainContent").show();
	});
	$("#homePage, .brand").click(function() {
		$("#mainContentMap").show();
		$("#mainContent").hide();
	});
	$("#registerName").on("keyup", function() {
		if($(this).val() == "") {
			$("#registerLoginButton").data("flag", false);
			$("#okMessageRegister").hide();
			$("#errorMessageRegister").html("不好意思，用户名不能为空，请输入！").show();
		} else {
			$.ajax({
				type : "post",
				url : "/baiduMap/login/getUser",
				cache : false,
				data : {loginName: $("#registerName").val()},
				dataType : "json"
			}).done(function(data) {
				if(data.id != undefined && data.id != "") {
					$("#registerLoginButton").data("flag", false);
					$("#okMessageRegister").hide();
					$("#errorMessageRegister").html("不好意思，用户名已经存在了，再换个吧！").show();
				}
			}).error(function(data) {
				$("#registerLoginButton").data("flag", true);
				$("#errorMessageRegister").hide();
				$("#okMessageRegister").html("恭喜，用户名可用！").show();
			});
		}
	});
	$("#registerPasswordConfirm").on("change", function() {
		var password = $("#registerPassword").val();
		var passwordConfirm = $("#registerPasswordConfirm").val();
		if(password != passwordConfirm) {
			$("#registerLoginButton").data("flag", false);
			$("#okMessageRegister").hide();
			$("#errorMessageRegister").html("不好意思，两次输入的密码不一致，请重新输入！").show();
		} else {
			$("#registerLoginButton").data("flag", true);
			$("#errorMessageRegister").hide();
		}
	});
	$("#registerLoginButton").click(function() {
		var form = $(".form-register");
		$.ajax({
			type : "post",
			url : "/baiduMap/login/addUser",
			cache : false,
			data : form.serialize(),
			dataType : "json"
		}).done(function(data) {
			$("#errorMessage").html("").hide();
			$("#loginCancelButton").click();
			$("#userName").html(data.userName);
			$("#userName").data("id",data.id);
			$("#userName").data("role",data.role);
			$("#userName").data("token",data.token);
			$(".preSignon").hide();
			$(".postSignon" + data.role).show();
			$(".postSignon").show();
			form[0].reset();
			initMarkerAndTable(data.id);
		});
	});
	
	$("#addUserButton").on("click", function() {
		var form = $(".form-addUser");
		$.ajax({
			type : "post",
			url : "/baiduMap/user/addUser",
			cache : false,
			data : form.serialize(),
			dataType : "json"
		}).done(function(data) {
			form[0].reset();
			$("#userManage").click();
		});
	});
});

function addUser() {
	$("#addUserHideA").click();
	$(".form-addUser")[0].reset();
}
