<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="model.Electro"%>
    
    <%
//Save---------------------------------
if (request.getParameter("username") != null) {
	Electro itemObj = new Electro();
	String stsMsg = "";
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {
		int category = Integer.parseInt(request.getParameter("category"));
		int units = Integer.parseInt(request.getParameter("units"));
		stsMsg = itemObj.insertBill(request.getParameter("username"), category,units);
	} 
	
	else//Update----------------------
	{
		
		int userID = Integer.parseInt(request.getParameter("hidItemIDSave"));
		int category = Integer.parseInt(request.getParameter("category"));
		int units = Integer.parseInt(request.getParameter("units"));
		
		stsMsg = itemObj.updateBill(userID, request.getParameter("username"),category,units);
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	Electro itemObj = new Electro();
	String stsMsg = itemObj.deleteBill(request.getParameter("hidItemIDDelete"));
	session.setAttribute("statusMsg", stsMsg);
}
%>
    
    <%
    Electro itemObj = new Electro();
String recieve = itemObj.readBill();
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Billing</title>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-8">
				<h1 class="m-3">Make your Bill</h1>
				<form id="formItem" name="formItem" method="post" action="Billing.jsp">
					User Name: 
					<input id="username" name="username" type="text" class="form-control form-control-sm"> <br>
					
					 User Category:
					<select class="form-control form-control-sm" id="category" name="category"> 
					  <option value="1">Individual User</option>
					  <option value="2">PVT Company</option>
					  <option value="3">GOV Company</option>
					</select><br>
										
					 Number Of Units Used:
					 <input id="units" name="units" type="text" class="form-control form-control-sm"> <br>
					 
					   <div class="row">
					    <div class="col">
					    Charge for use
					      <input id="use" name="use" type="text" class="form-control form-control-sm" disabled>
					    </div>
					    <div class="col">
					    Tax for usage 
					      <input  id="tax" name="tax" type="text" class="form-control form-control-sm" disabled>
					    </div>
					    <div class="col">
					    Total Charge for the month 
					      <input id="bill" name="bill" type="text" class="form-control form-control-sm" disabled>
					    </div>
					  </div><br>
					 
					 <input id="btnSave" name="btnSave" type="button" value="Add" class="btn btn-primary"> <br> 
					 <input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value=""><br>

					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
				</form>
			</div>
		</div>

		<br>
		<div class="row">
			<div class="col-12" id="colStudents">
			<%= recieve %>
			</div>
		</div>

		
	</div>

<script src="Components/items.js"></script>
<script src="Components/jquery-3.2.1.min.js"></script>


	


</body>
</html>