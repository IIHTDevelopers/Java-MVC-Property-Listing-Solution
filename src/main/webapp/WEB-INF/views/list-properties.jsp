<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<title>Property List</title>

</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2> Property Management System</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">
		<!--  Add "Add Property" Button -->
		<input type="button" value="Post Property"
			   onclick="window.location.href='showFormForAdd'; return false;"
			   class="add-button"/>
			   <br>
			   <br>
			   <br>
		<!-- Add Table Content here -->
		<table border = "1">
			<tr>
				<th> Property Name</th>
				<th> Address </th>
				<th> Dimensions </th>
				<th> Rooms </th>
				<th> Price </th>
				<th> Action</th>
			</tr>
			<c:forEach var="tempProperty" items="${properties}">

			<!-- Add embedded link to update the customer -->
			<c:url var="updateLink" value="/property/showFormForUpdate">
				<c:param name="propertyId" value="${tempProperty.id}"/>
			</c:url>
			<c:url var="deleteLink" value="/property/showFormForDelete">
				<c:param name="propertyId" value="${tempProperty.id}"/>
			</c:url>

					<tr>
						<td> ${tempProperty.name} </td>
						<td> ${tempProperty.address} </td>
						<td> ${tempProperty.dimensions} </td>
						<td> ${tempProperty.rooms} </td>
						<td> Rs. ${tempProperty.price} </td>
						<td>
							<a href="${updateLink}">Update</a>
							<a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to clear this property?'))) return false">
							|Clear</a>
						</td>
					</tr>
			</c:forEach>
		</table>
		</div>
	</div>
</body>
</html>