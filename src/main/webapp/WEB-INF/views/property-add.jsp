<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>

<head>
	<title>Property Listing</title>

</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Property Listing Management System</h2>
		</div>
	</div>
	<div id="container">
		<h3> Post Property</h3>
		<form:form action="saveProperty" modelAttribute="property" method="POST">
		<!-- Associate the data with a given customer with a hidden form param -->
		<form:hidden path="id"/> <!-- Customer.setId will be called -->
		<table>
			<tbody>
				<tr>
					<td><label> Name:</label></td>
					<td><form:input path="name" /> <form:errors path="name" /></td>
				</tr>
				<tr>
                	<td><label> Address:</label></td>
                	<td><form:textarea path="address" /><form:errors path="address" /></td>
                </tr>
				<tr>
					<td><label> Dimensions:</label></td>
					<td><form:input path="dimensions" /><form:errors path="dimensions" /></td>
				</tr>
				<tr>
					<td><label> Total rooms:</label></td>
					<td><form:input path="rooms" type = "number"/><form:errors path="rooms" /></td>
				</tr>
				<tr>
                	<td><label> Price(in INR):</label></td>
                	<td><form:input path="price" type = "number" step="0.01" /><form:errors path="price" /></td>
                </tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="save"/></td>
				</tr>
			</tbody>
		</table>
		</form:form>
	</div>
	<!--  Adding The link to the bottom of the page rather than at the top -->
	<br><br>
	<br><br>
	<div id="container">
			<a href="${pageContext.request.contextPath}/property/list">Back to Property List Page</a>
	</div>

</body>
</html>