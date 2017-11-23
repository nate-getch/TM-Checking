<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <link href="<c:url value="/resource/css/style.css" />" rel="stylesheet">
    <title>Sessions</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<style>
	</style>
</head>
<body>
<jsp:include page="../template/nav.jsp" />

<div class="container">
<%-- 	<form:form action="search/#search" method="get"> --%>
<!-- 		<div class="form-group"> -->
<!-- 			<label for="search">Search: </label> -->
<%-- 			<form:input id="search" path="searchValue" cssClass="form-control date" --%>
<%-- 				type="text"/> --%>
<!-- 		</div> -->
<%-- 	</form:form> --%>
	<security:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_COUNCELOR')">
	    <div class="clearfix">
	        <div class="pull-right">
	            <a class="btn btn-primary btn-sm" href="<spring:url value="/session/add" />">Create</a>
	        </div>
	    </div>
    </security:authorize>
    <hr/>
    <div style="height:300px;overflow:auto;">
    <table class="table col-xs-12">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Counselor</th>
            <th scope="col">Date</th>
            <th scope="col">Start time</th>
            <th scope="col">Building</th>
            <th scope="col">Room Number</th>
            <th scope="col">Available Sits</th>
            <th scope="col">
                <i class="glyphicon glyphicon-cog"></i>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessions}" var="row">
            <tr>
                <th scope="row">${row.id}</th>
                <td>${row.person.firstName}</td>
                <td>${row.date}</td>
                <td>${row.startTime}</td>
                <td>${row.location.building}</td>
                <td>${row.location.roomNumber}</td>
                <td>${row.numberofsits - row.availablesits}</td>
                <td>
           			<security:authorize access="hasRole('ROLE_ADMIN') or (hasRole('ROLE_COUNCELOR') and ${currentPerson.id == row.person.id})">
	                    <a class="btn btn-danger btn-xs" href="<spring:url value="/session/remove/${row.id}" />">
	                        <i class="glyphicon glyphicon-trash" title="Delete"></i>
	                    </a>
	                    <a class="btn btn-info btn-xs" href="<spring:url value="/session/${row.id}" />">
	                        <i class="glyphicon glyphicon-edit" title="Edit"></i>
	                    </a>
                    </security:authorize>
                    <security:authorize access="!hasRole('ROLE_ADMIN')">
	                    <a class="btn btn-info btn-xs" href="<spring:url value="/appointment/book/${row.id}" />">
	                        <i class="glyphicon glyphicons-parents" title="Appointment">book</i>
	                    </a>
                    </security:authorize>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
