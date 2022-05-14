$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid-------------------------
	$("#formItem").submit();

});

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#username").val($(this).closest("tr").find('td:eq(0)').text());
	$("#category").val($(this).closest("tr").find('td:eq(1)').text());
	$("#units").val($(this).closest("tr").find('td:eq(3)').text());
	
	$("#use").val($(this).closest("tr").find('td:eq(4)').text());
	$("#tax").val($(this).closest("tr").find('td:eq(5)').text());
	$("#bill").val($(this).closest("tr").find('td:eq(6)').text());
});

// CLIENT-MODEL================================================================
function validateItemForm()
{
// CODE
if ($("#username").val().trim() == "")
 {
 return "Insert User Name.";
 }
// NAME
if ($("#category").val().trim() == "")
 {
 return "Select Category.";
 }
// PRICE-------------------------------
if ($("#units").val().trim() == "")
 {
 return "Insert Item Price.";
 }
// is numerical value
var tmpPrice = $("#units").val().trim();
if (!$.isNumeric(tmpPrice))
 {
 return "Insert a numerical value for Units.";
 }
return true;
}


