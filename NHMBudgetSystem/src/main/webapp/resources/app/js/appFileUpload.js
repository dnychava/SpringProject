/**
 * 
 */
//File upload
function fileUploadApp( viewFileRequestMapping ){
	var errorMsg = "Testing";
	var fileName = $("#file").val();
	if( fileName == ""){
		alert("Please select file");
		return;
		
	} else if( !fileName.endsWith(".pdf")){
		alert("Selected file is not correct format. File only accept PNG or JPG format");
		return;
	}
	
	//stop submit the form, we will post it manually.
    event.preventDefault();

    // Get form
    var form = $('#fileUploadForm')[0];

	// Create an FormData object
    var data = new FormData(form);

    var documentRid = $("#documentEntityRid").val();
	// If you want to add an extra field for the FormData
    data.append("documentRid", documentRid);

	// disabled the submit button
    $("#btnSubmit").prop("disabled", true);
	
    $("#result").text("");
    
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "savefile",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	try{
        		console.log("RESPONSE : ", JSON.stringify(data));
        		var obj = jQuery.parseJSON(JSON.stringify(data));
            	$("#result").text(obj.fileUploadMsg);
            	console.log("SUCCESS : ", obj.fileUploadMsg);
            	
            	var urlHref = "javascript:viewFile('"+ viewFileRequestMapping +"/"+obj.renameFileName+"');";
            	//Setting file for view it
            	$("#fileName").attr("href",urlHref);
            	//Display the file name.
            	$("#fileName span").text(obj.orignalFileName);
            	
            	//Setting the orignal file name for store in DB
            	$("#documentEntityFileName").attr("value",obj.orignalFileName);
            	$("#btnSubmit").prop("disabled", false);
                $("#fileUploadModal .close").click();	
        	}catch(e){
        		console.log(e.message);
        		alert("Exceptin while parsing json data["+e.message+"]")
        	}
        },
        error: function (e) {
            $("#result").text(e.responseText);
            console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);

        }
    });
}

//View file
$("#fileName").click(function(event) {
	event.preventDefault();
	var url = $(this).attr("href");
	window.open(url, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=100,left=400,width=700,height=500");
});
