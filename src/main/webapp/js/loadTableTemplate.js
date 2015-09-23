var loadTableApp = new Mn.Application();
loadTableApp.addRegions({
	markerTbl:"#markerTableDiv",
	myProfileTbl:"#myProfileContent",
	userTbl:"#myProfileContent",
	updUserProfileTbl:"#updateUserProfile"
});

var markerTRView = Mn.ItemView.extend({
	tagName:'tr',
	template:'#tr-template-marker',
	ui:{
		view:'.view',
		del:'.delete'
	},
	events:{
		'click @ui.view':'viewRow',
		'click @ui.del':'deleteRow',
	},
	viewRow:function(){
		var markerId = this.model.attributes.markerId;
		var infoBoxDiv = $(".infoBox");
		refillInfoBoxDiv(markerId,infoBoxDiv);
		var marker = globalMarker["" + markerId];
		map.setCenter(marker.point);
		marker.F.click();
	},
	deleteRow:function(){
		var markerId = this.model.attributes.markerId;
		var inputName = $("#userName").data("id");
		$("#deleteButton").on("click", function() {
			var param = {"marker.markerId":markerId,"marker.inputName":inputName};
			$.ajax({
				type : "get",
				url : "/baiduMap/marker/delMarker",
				cache : false,
				data : param
			}).done(function() {
				var infoBoxDiv = $(".infoBox");
				map.removeOverlay(globalMarker["" + markerId]);
				infoBoxDiv.hide();
				refreshMarkerTable(inputName);
			});	
		});
	}
});

var markerTableView = Mn.CompositeView.extend({
	childView:markerTRView,
	childViewContainer: "tbody",
	template:'#table-template-marker'
});

var userTRView = Mn.ItemView.extend({
	tagName:'tr',
	template:'#tr-template-user',
	ui:{
		upd:'.updateUser',
		del:'.deleteUser'
	},
	events:{
		'click @ui.upd':'updateRow',
		'click @ui.del':'deleteRow',
	},
	updateRow:function(){
		initUpdUserProfile(this.model.attributes.id);
	},
	deleteRow:function(){
		alert('delete');
	}
});

var userTableView = Mn.CompositeView.extend({
	childView:userTRView,
	childViewContainer: "tbody",
	template:'#table-template-user'
});


var MyProfileView = Mn.ItemView.extend({
	template:'#div-template-myProfile'
});

var UpdUserProfileView = Mn.ItemView.extend({
	template:'#form-template-updUserProfile'
});