<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<!DOCTYPE html>
<html lang="en">

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Appointments</title>

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<link href="<c:url value="/resource/css/style.css" />" rel="stylesheet">

</head>
<body>
<jsp:include page="../template/nav.jsp" />

<div class="container " >
	<table class="table table-striped">
		<thead>
		<tr>
			<th class="hdrOfTable">ID</th>
			<th class="hdrOfTable">Counslee</th>
			<th class="hdrOfTable">Counselor</th>
			<th class="hdrOfTable">Date</th>
			<th class="hdrOfTable">Start time</th>
			<th class="hdrOfTable">Location</th>
			<th class="hdrOfTable"></th>

		</tr>
		</thead>
		<tbody>
		<c:forEach items="${appointmentList}" var="appointment">
			<tr>
				<td>  ${appointment.id}</td>
				<td>  ${appointment.person.username}</td>
				<td>  ${appointment.session.person.firstName}</td>
				<td>  ${appointment.session.date}</td>
				<td>  ${appointment.session.startTime}</td>
				<td>  ${appointment.session.location}</td>
				<td>
					<c:if test="${ requestPath ne '/appointment/counselor' }">
					<a href="<spring:url value="/appointment/delete?id=${appointment.id}"/>"
					   class="btn btn-warning btn-large"> <span class="glyphicon"></span>
					Cancel
				</a> </c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%--
    <div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
        <div class="thumbnail">
            <div class="caption">
            <tr>
            <td>
                <h4></h4></td>
                <h4></h4>
                <h4></h4>


            </div>
        </div>
    </div> --%>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
