function AsyncConfirmYesNo(title, msg, yesFn, noFn) {
	try{
		var $confirm = $("#modalConfirmYesNo");
	    $confirm.modal('show');
	    $("#lblTitleConfirmYesNo").html(title);
	    $("#lblMsgConfirmYesNo").html(msg);
	    $("#btnYesConfirmYesNo").off('click').click(function () {
	        yesFn();
	        $confirm.modal("hide");
	    });
	    $("#btnNoConfirmYesNo").off('click').click(function () {
	        noFn();
	        $confirm.modal("hide");
	    });
	}catch(e){
		alert("e: AsyncConfirmYesNo ["+e.message+"]");
	}
    
}
function confirmDelete(delUrl) {
	try{
		var title = "Confimation";
		var msg = "Do you want to be delete ?";
		console.log("delUrl["+delUrl+"]");
		var confirmFunYes = function() {
			document.location = delUrl;
		};
		var confirmFunNo = function() {
			return false;
		};
		AsyncConfirmYesNo(title, msg, confirmFunYes, confirmFunNo );
		
	}catch(e){
		alert("e: confirmDelete["+e.message+"]");
	}
	
}

//View file
function viewFile(url){
	window.open(url, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=400,width=700,height=500");
}
/*$("#fileName").click(function(event) {
	event.preventDefault();
	var url = $(this).attr("href");
	window.open(url, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=400,width=700,height=500");
});*/