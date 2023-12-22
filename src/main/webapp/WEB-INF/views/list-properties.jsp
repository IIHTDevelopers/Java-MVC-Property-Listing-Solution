<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

	    <div>
        	<h2> Search Property </h2>
                <form:form action="/list?page=0&size=5" method="POST">
        			<div>
        			    <div>
        			      <div><i aria-hidden="true"></i></div>
        			    </div>
        			    <input type="text" placeholder="Search By Property Name" name="name" value = "${name}">
        			    <input type="number" placeholder="Search By Max Price" name="max" value = "${max}">
        			    <input type="submit" value="Search"/>
        			</div>
                </form:form>
        		<table border = "1">
                			<tr>
                			    <th> S No. </th>
                				<th> Property Name
                				        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=name,desc"> Desc </a>
                                        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=name"> Asc </a>
                                </th>
                				<th> Address
                				        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=address,desc"> Desc </a>
                                        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=address"> Asc </a>
                                </th>
                				<th> Dimensions
                				        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=dimensions,desc"> Desc </a>
                                        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=dimensions"> Asc </a>
                                </th>
                				<th> Rooms
                				        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=rooms,desc"> Desc </a>
                                        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=rooms"> Asc </a>
                                </th>
                				<th> Price
                				        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=price,desc"> Desc </a>
                                        &nbsp &nbsp <a href= "/list?page=0&size=5&name=${name}&max=${max}&sort=price"> Asc </a>
                                </th>
                				<th> Action</th>
                			</tr>
                			<c:set var="index" value="${page * 5 + 1}" />
                			<c:if test="${empty properties}">
                	        </c:if>

                			<c:forEach var="tempProperty" items="${properties}">

                			<!-- Add embedded link to update the customer -->
                			<c:url var="updateLink" value="/property/showFormForUpdate">
                				<c:param name="propertyId" value="${tempProperty.id}"/>
                			</c:url>
                			<c:url var="deleteLink" value="/property/showFormForDelete">
                				<c:param name="propertyId" value="${tempProperty.id}"/>
                			</c:url>

                					<tr>
                					    <td>${index}</td>
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
                					<c:set var="index" value="${index + 1}" />
                			</c:forEach>
                		</table>
        	</div>
        	<br><br>
        	<c:choose>
                <c:when test="${totalPage == 0}">
                    No Record Found
                </c:when>
                <c:otherwise>
                    <c:forEach begin="0" end="${totalPage-1}" varStatus="loop">
                            &nbsp &nbsp<a href="/list?page=${loop.index}&size=5&name=${name}&max=${max}&sort=${sortBy}">${loop.index + 1}</a></li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

		</div>
	</div>
</body>
</html>