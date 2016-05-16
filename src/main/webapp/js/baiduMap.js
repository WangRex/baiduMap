$(document).ready(function() {
	
	globalMarker = {};

	var top_left_control = new BMap.ScaleControl({
		anchor : BMAP_ANCHOR_TOP_LEFT
	});
	var top_left_navigation = new BMap.NavigationControl();
	var top_right_navigation = new BMap.NavigationControl({
		anchor : BMAP_ANCHOR_TOP_RIGHT,
		type : BMAP_NAVIGATION_CONTROL_SMALL
	});

	add_control(top_left_control, top_left_navigation);

	initUserStatus();
	
	initInfoBoxDivButton();
	
	initSearchButton();
	
	initCreateButton();
	
	initUserManage();
	
	initNav();
	
	ValueUtil.initCancelBtnResetDialog();
	
	$("#searchResultNum").change(function() {
		initSearchButton();
	});
	var menu = new BMap.ContextMenu(); // 右键菜单

	var txtMenuItem = [ // 右键菜单项目
			{
				text : '在此添加标注',
				callback : function(p) {
					var infoBoxDiv = $(".infoBox");
					var marker = new BMap.Marker(p);
					map.addOverlay(marker);
					marker.enableDragging(true);
					// 给标注添加单击事件
					marker.addEventListener("click", function() {
						var thisMarker = this;
						var spanMarker = $($(this)[0].F);
						var offset = spanMarker.offset();
						// 把添加的标注绑定到infoBox中
						infoBoxDiv.data("spanMarker", spanMarker);
						infoBoxDiv.data("thisMarker", thisMarker);
						if(spanMarker.data("markerId") == undefined) {
							infoBoxDiv.find("#markerName").val("");
							$("#BoardPicker1").find(".CurrentBoard").html("--");
							$("#BoardPicker2").find(".CurrentBoard").html("--");
							infoBoxDiv.find("#lng_lat").val(p.lng + "," + p.lat);
							geocodeSearch(p, infoBoxDiv.find("#address"));
						} else {
							getMarker(spanMarker.data("markerId"));
						}
						var top = offset.top - infoBoxDiv.height() - 15;
						var left = offset.left - infoBoxDiv.width() / 2 + 30;
						infoBoxDiv.css(
								{
									"top" : top,
									"left" : left
								}).show();
						infoBoxDiv.find("#saveMarker").off("click");
						infoBoxDiv.find("#saveMarker").on("click",
								function() {
									var type1 = infoBoxDiv.find("#BoardPicker1").find("span[class='CurrentBoard']").html();
									var type2 = infoBoxDiv.find("#BoardPicker2").find("span[class='CurrentBoard']").html();
									var type = type1 + "," + type2;
									infoBoxDiv.find("#markerType").val(type);
									infoBoxDiv.find("#markerInputName").val($("#userName").data("id") == undefined ? "0" : $("#userName").data("id"));
									var param = $("form#infoBoxForm").serialize();
									if($("#userName").data("id") != undefined && $("#userName").data("id") != 0) {
										$.ajax({
											type : "post",
											url : "/baiduMap/marker/addMarker",
											cache : false,
											data : param,
											dataType : "json"
										}).done(function(data) {
											if(ValueUtil.valNotUndefinedOrEmpty(data.markerId)) {
												globalMarker[data.markerId] = marker;
												spanMarker.data("markerId",data.markerId);
												refreshMarkerTable($("#userName").data("id"));
												infoBoxDiv.hide();
											} else {
												$("#notAbleToMarkA").click();
												$("#closeButton").click();
												infoBoxDiv.find("input").val("");
												map.removeOverlay(thisMarker);
											}
										});
									} else {
										$("#signonConfirmA").click();
										$("#closeButton").click();
										infoBoxDiv.find("input").val("");
										map.removeOverlay(thisMarker);
									}
								});
					});
					
					marker.addEventListener("dragging", function(e) {
						repositionInfoBoxDiv();
						infoBoxDiv.find("#lng_lat").val(e.point.lng + "," + e.point.lat);
						geocodeSearch(e.point, infoBoxDiv.find("#address"));
					});
				}
			}, {
				text : '放大',
				callback : function() {
					map.zoomIn()
				}
			}, {
				text : '缩小',
				callback : function() {
					map.zoomOut()
				}
			} ];
	for (var i = 0; i < txtMenuItem.length; i++) {
		menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,
				txtMenuItem[i].callback, 100)); // 菜单添加项目
	}

	map.addContextMenu(menu);

	map.addEventListener("dragging", function() {
		repositionInfoBoxDiv();
	});
	map.addEventListener("dragend", function() {
		repositionInfoBoxDiv();
	});

	map.addEventListener("zoomend", function() {
		$(".infoBox").hide();
	});

	// ---------------------------------------------改变鼠标样式---------------------------------------------
	// 需要自己制作。cur格式的静态光标
	// map.setDefaultCursor("url('01.cur')"); //设置地图默认的鼠标指针样式
	// map.setDraggingCursor("url('03.cur')"); //设置地图拖拽时的鼠标指针样式

});

function add_control(top_left_control, top_left_navigation) {
	map.addControl(top_left_control);
	map.addControl(top_left_navigation);
}

function delete_control(top_left_control, top_left_navigation) {
	map.removeControl(top_left_control);
	map.removeControl(top_left_navigation);
}

function initInfoBoxDivButton() {

	$("#closeButton").click(function() {
		$(".infoBox").hide();
		$(".BoardList").hide();
	});

	$("#cancelMarker").click(function() {
		$(".infoBox").hide();
		$(".BoardList").hide();
	});

	$("#delMarker").click(function() {
		$(".infoBox").hide();
		map.removeOverlay($(".infoBox").data("thisMarker"));
		$(".BoardList").hide();
	});

}

function initUserStatus() {
	$.ajax({
		type : "post",
		url : "/baiduMap/login/getSession",
		cache : false,
		dataType : "json"
	}).done(function(data) {
		var form = $(".form-signin");
		if(ValueUtil.valNotUndefinedOrEmpty(data)) {
			if(data.token != undefined) {
				$("#userName").html(data.userName);
				$("#userName").data("id",data.id);
				$("#userName").data("role",data.role);
				$("#userName").data("token",data.token);
				$(".preSignon").hide();
				$(".postSignon" + data.role).show();
				$(".postSignon").show();
				form[0].reset();
				refreshMarkerTable($("#userName").data("id"));
				initMarkers($("#userName").data("id"));
			}
		}
	}).error(function() {
		refreshMarkerTable($("#userName").data("id"));
//		initMarkers($("#userName").data("id"));
	});
}
function repositionInfoBoxDiv() {
	var infoBoxDiv = $(".infoBox");
	if (infoBoxDiv.data("spanMarker")) {
		var offset = $(infoBoxDiv.data("spanMarker")).offset();
		if (offset != undefined) {
			var top = offset.top - infoBoxDiv.height() - 15;
			var left = offset.left - infoBoxDiv.width() / 2 + 30;
			infoBoxDiv.css({
				"top" : top,
				"left" : left
			});
		}
	}
}

function refillInfoBoxDiv(markerId,infoBoxDiv) {
	$.ajax({
		type : "post",
		url : "/baiduMap/marker/getMarker",
		cache : false,
		data : {"marker.markerId" : markerId},
		dataType : "json"
	}).done(function(data) {
		infoBoxDiv.find("#markerId").val(data.markerId);
		infoBoxDiv.find("#markerName").val(data.markerName);
		infoBoxDiv.find("#lng_lat").val(data.markerLnglat);
		infoBoxDiv.find("#address").val(data.markerAddress);
		var type = data.markerType.split(",");
		if(type[0] != undefined && type[0] != null && type[0] != "") {
			infoBoxDiv.find("#BoardPicker1").find("span[class='CurrentBoard']").html(type[0]);
		}
		if(type[1] != undefined && type[1] != null && type[1] != "") {
			infoBoxDiv.find("#BoardPicker2").find("span[class='CurrentBoard']").html(type[1]);
		}
		
	});
}

function geocodeSearch(pt, obj) {
	var myGeo = new BMap.Geocoder();
	myGeo.getLocation(pt, function(rs) {
		var addComp = rs.addressComponents;
		var position = addComp.province + ", " + addComp.city + ", "
				+ addComp.district;
		obj.val(position);
	});
}

function initMarkers(inputName) {
	$("#markerManage").show();
	inputName = inputName == undefined ? 0 : inputName;
	var param = {"marker.inputName":inputName};
	$.ajax({
		type : "post",
		url : "/baiduMap/marker/getList",
		cache : false,
		data : param,
		dataType : "json"
	}).done(function(data) {
		$.each( data, function( key, value ) {
			var infoBoxDiv = $(".infoBox");
			var markerId = value.markerId;
			var lnglat = value.markerLnglat.split(",");
			var markerType = value.markerType.split(",");
			var point = new BMap.Point(lnglat[0],lnglat[1]);
			var marker = new BMap.Marker(point);
			map.addOverlay(marker);
			
			globalMarker[markerId] = marker;
			
			marker.addEventListener("click", function() {
				var infoBoxDiv = $(".infoBox");
				var thisMarker = this;
				var spanMarker = $($(this)[0].F);
				var offset = spanMarker.offset();
				var inputName = $("#userName").data("id");
				infoBoxDiv.data("spanMarker", spanMarker);
				infoBoxDiv.data("thisMarker", thisMarker);
				infoBoxDiv.find("#markerId").val(value.markerId);
				infoBoxDiv.find("#markerName").val(value.markerName);
				$("#BoardPicker1").find(".CurrentBoard").html(markerType[0]);
				$("#BoardPicker2").find(".CurrentBoard").html(markerType[1]);
				infoBoxDiv.find("#lng_lat").val(lnglat);
				infoBoxDiv.find("#address").val(value.markerAddress);
				
				refillInfoBoxDiv(markerId,infoBoxDiv);
				
				var top = offset.top - infoBoxDiv.height() - 15;
				var left = offset.left - infoBoxDiv.width() / 2 + 30;
				infoBoxDiv.css(
						{
							"top" : top,
							"left" : left
						}).show();
				
				infoBoxDiv.find("#saveMarker").off("click");
				infoBoxDiv.find("#saveMarker").click(
						function() {
							var type1 = infoBoxDiv.find("#BoardPicker1").find("span[class='CurrentBoard']").html();
							var type2 = infoBoxDiv.find("#BoardPicker2").find("span[class='CurrentBoard']").html();
							var type = type1 + "," + type2;
							infoBoxDiv.find("#markerType").val(type);
							infoBoxDiv.find("#updateName").val(inputName == undefined ? "0" : inputName);
							var param = $("form#infoBoxForm").serialize();
							$.ajax({
								type : "post",
								url : "/baiduMap/marker/updMarker",
								cache : false,
								data : param
							}).done(function(data) {
								infoBoxDiv.hide();
								refreshMarkerTable(inputName);
							});
						});
				infoBoxDiv.find("#delMarker").click(
						function() {
							var param = {"marker.markerId":markerId,"marker.inputName":inputName};
							$("#delete").click();
							$.ajax({
								type : "post",
								url : "/baiduMap/marker/delMarker",
								cache : false,
								data : param
							}).done(function(data) {
								infoBoxDiv.hide();
								map.removeOverlay(infoBoxDiv.data("thisMarker"));
								$(".BoardList").hide();
								refreshMarkerTable(inputName);
							});
						});
			});
		});
	});
}

function refreshMarkerTable(inputName) {
	inputName = inputName == undefined ? 0 : inputName;
	var param = {"marker.inputName":inputName};
	$.ajax({
		type : "post",
		url : "/baiduMap/marker/getList",
		cache : false,
		data : param,
		dataType : "json"
	}).done(function(data) {
		loadTableApp.markerTbl.show(new markerTableView({collection:new Backbone.Collection(data)}));
		loadTableApp.start();
		initDataTable("example");
	}).error(function() {
		console.log("error");
	});
}

function showTypeDiv(num) {

	initBoardList(num);

}

function initSearchButton() {
	$("#searchButton").click(function(e) {
		e.preventDefault();
		e.stopPropagation();
		var city = $("#searchCity").val();
		var keyWords = $("#searchKeyWords").val();
		var options = {  
			 onSearchComplete: function(results){
			   if (local.getStatus() == BMAP_STATUS_SUCCESS){
			     // 判断状态是否正确  
			     var s = [];
			     for (var i = 0; i < results.getCurrentNumPois(); i ++){
			       s.push(results.getPoi(i).title + ", " + results.getPoi(i).address);
			     }
			     $("#searchResultNum").html("共搜索到" + results.getCurrentNumPois() + "条记录");
			     $('#searchResultDialog').modal('toggle');
			   }
			 },
			 renderOptions:{map: map},
			 pageCapacity: 100 
		};
		var local = new BMap.LocalSearch(city,options);
		local.search(keyWords);  
	});
}

function initBoardList(num) {
	var typeParentId;
	if (num == 1) {
		typeParentId = 0;
	}
	if (num == 2) {
		if ($("#BoardPicker1").find("span[class='CurrentBoard']").data("typeId") == undefined
				|| $("#BoardPicker1").find("span[class='CurrentBoard']").data(
						"typeId") == "") {
			alert("请选择父类别");
		} else {
			typeParentId = $("#BoardPicker1").find("span[class='CurrentBoard']").data("typeId");
		}
	}
	var param = {
		"typeParentId" : typeParentId,
		"type_level" : num
	};

	var BoardListUl = $("#BoardList" + num).find("ul");
	BoardListUl.html("");
	$.ajax({
		type : "post",
		url : "/baiduMap/type/getList",
		cache : false,
		data : param,
		dataType : "json"
	}).done(function(data) {
		$.each(data, function(key, value) {
			var $li = $("<li>" + value.typeName + "</li>");
			$li.data("typeId", value.typeId);
			BoardListUl.append($li);
		});
		var $boardList = $('#BoardList' + num);
		var $boardPicker = $("#BoardPicker" + num);
		if (num == 2) {
			$boardList.css({
				left : -94
			});
		}
		$boardList.show();
		$boardPicker.find(".wrapper").find("li").each(function() {
			var $li = $(this);
			$li.on("click",function() {
				$boardPicker.find(".CurrentBoard").html($li.text()).data("typeId",$li.data("typeId"));
				$boardList.hide();
			});
		});
	});
}

function initCreateButton() {

	$("button[id^='createboard']").each(function() {
		$(this).on('click', function() {
			var idVal = $(this).attr('id');
			var num = idVal.substr(idVal.length - 1, 1);
			var $boardName = $('#boardname' + num);
			$boardName.on("change",function() {
				if($boardName.val() != "") {
					$boardName.css({"background-color":"#fff"});
				}
			});
			var typename = $boardName.val();
			var $BoardList = $('#BoardList' + num);

			if (num == 1) {
				var param = {
					"type_parent_id" : 0,
					"type_name" : typename,
					"type_level" : num
				};
			}
			if (num == 2) {
				var param = {
					"type_parent_id" : $("#BoardPicker1").find("span[class='CurrentBoard']").data("typeId"),
					"type_name" : typename,
					"type_level" : num
				};
			}
			if(typename != null && typename != "") {
				$.ajax({
					type : "post",
					url : "/baiduMap/type/addType",
					cache : false,
					data : param
				}).done(function() {
					$BoardList.find(".wrapper").find("ul").append($("<li>" + typename + "</li>"));
					initBoardList(num);
					$boardName.val("");
				});	
			} else {
				$boardName.val("").attr("placeholder","请输入类别名称").css({"background-color":"#eed3d7"});
			}
		});
	});
}

function initNav() {
	$(".nav").each(function() {
		var $nav = $(this);
		$(this).find("a").on("click",function() {
			$nav.find("li").removeClass("active");
			$(this).closest("li").addClass("active");
		});
	});
}

function initUserManage() {
	$("#userManage").on("click", function() {
		var param = {};
		$.ajax({
			type : "post",
			url : "/baiduMap/user/getUserList",
			cache : false,
			data : param,
			dataType : "json"
		}).done(function(data) {
			loadTableApp.userTbl.show(new userTableView({collection:new Backbone.Collection(data)}));
			loadTableApp.start();
			initDataTable("userTable");
			$("#mainContentMap").hide();
			$("#mainContent").show();
		});
	});
	$("#updateButton").on("click", function() {
		var param = $("#updUserProfileForm").serialize();
		$.ajax({
			type : "post",
			url : "/baiduMap/user/updUser",
			cache : false,
			data : param
		}).done(function() {
			var param = {};
			$.ajax({
				type : "post",
				url : "/baiduMap/user/getUserList",
				cache : false,
				data : param,
				dataType : "json"
			}).done(function(data) {
				loadTableApp.userTbl.show(new userTableView({collection:new Backbone.Collection(data)}));
				loadTableApp.start();
				initDataTable("userTable");
				$("#mainContentMap").hide();
				$("#mainContent").show();
			});
		});
	});
}

function getMarker(markerId) {
	var param = {"marker.markerId":markerId};
	$.ajax({
		type : "post",
		url : "/baiduMap/marker/getMarker",
		cache : false,
		data : param,
		dataType : "json"
	}).done(function(data) {
		var markerType = data.markerType.split(",");
		var infoBoxDiv = $(".infoBox");
		initMarkerAndTable($("#userName").data("id"));
		infoBoxDiv.find("#markerName").val(data.markerName);
		$("#BoardPicker1").find(".CurrentBoard").html(markerType[0]);
		$("#BoardPicker2").find(".CurrentBoard").html(markerType[1]);
		infoBoxDiv.find("#lng_lat").val(data.markerLnglat);
		infoBoxDiv.find("#address").val(data.markerAddress);
	});
}

function initMarkerAndTable(inputName) {
	map.clearOverlays();
	initMarkers(inputName);
	refreshMarkerTable(inputName);
}

function initUpdUserProfile(id) {
	
	var param = {"user.id":id};
	$.ajax({
		type : "post",
		url : "/baiduMap/user/getUser",
		cache : false,
		data : param,
		dataType : "json"
	}).done(function(data) {
		loadTableApp.updUserProfileTbl.show(new UpdUserProfileView({model:new Backbone.Model(data)}));
		loadTableApp.start();
		
		if($('#updateUserMarkerFlag').val() == "1") {
			$('#update-text-toggle-button').find("input").attr("checked", false);
		} else {
			$('#update-text-toggle-button').find("input").attr("checked", true);
		}

	    $('#update-text-toggle-button').toggleButtons({
	        width: 220,
	        label: {
	            enabled: "是",
	            disabled: "否"
	        }
	    });
		$("#updateUserHidden").click();
	});
}

function hideDialog(obj) {
	$("#" + obj).hide();
}

function signon(obj) {
	/*$("#" + obj).destory();
	$("#signonButton").click();*/
}