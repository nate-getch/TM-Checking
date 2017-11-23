<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<link href="<c:url value="/resource/css/style.css" />" rel="stylesheet">
<title>Add new session</title>
</head>
<body>

	<jsp:include page="../template/nav.jsp" />
	<div class="container " style="margin-bottom: 80px;">
		<div class="container" style="width: 600px; margin-top: 20px;">
			<form:form modelAttribute="session" action="add"
				class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="availablesits" />
				<form:errors path="*" cssClass="alert alert-danger" element="div" />
				<fieldset>
					<legend>Add new session</legend>

					<div class="form-group">
						<label for="sessionDate">Date</label>
						<form:input id="sessionDate" path="date" cssClass="form-control date"
							type="date" maxlength="10"/>
						<form:errors path="date" cssClass="text-danger" />
					</div>
					<c:if test="${isAdmin == false}">
						<form:hidden path="person.id" />
						<div class="form-group">
							<label for="counselor_id">Counselor</label>
							<form:input id="counselor_id" path="person.firstName"
								cssClass="form-control" readonly="true" />
						</div>
					</c:if>
					<c:if test="${isAdmin == true}">
						<div class="form-group">
							<label for="counselor">Counselor</label>
							<form:select id="counselor" path="person.id">
								<form:option value="0" label="Choose counselor..." />
								<form:options items="${counselors}" itemLabel="username"
									itemValue="id" />
							</form:select>
						</div>
					</c:if>
					<div class="form-group">
						<label for="startTime">Start time</label>
						<form:input id="startTime" path="startTime"
							cssClass="form-control" type="time"/>
						<form:errors path="startTime" cssClass="text-danger" />
					</div>
					<div class="form-group">
						<label for="duration">Duration</label>
						<form:input id="duration" path="duration" cssClass="form-control" maxlength="4" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
						<form:errors path="duration" cssClass="text-danger" />
					</div>
					<div class="form-group">
						<label for="numberofsits">Number of sits</label>
						<form:input id="numberofsits" path="numberofsits"
							cssClass="form-control" maxlength="4" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
						<form:errors path="numberofsits" cssClass="text-danger" />
					</div>
					<form:hidden path="location.id" />
					<div class="form-group">
						<label for="building">Building</label>
						<form:input id="building" path="location.building"
							cssClass="form-control" maxlength="25" />
						<form:errors path="location.building" cssClass="text-danger" />
					</div>
					<div class="form-group">
						<label for="roomNumber">Room number</label>
						<form:input id="roomNumber" path="location.roomNumber"
							cssClass="form-control" maxlength="5" onkeypress='return event.charCode >= 48 && event.charCode <= 57'/>
						<form:errors path="location.roomNumber" cssClass="text-danger" />
					</div>

					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
							<input type="submit" id="btnAdd" class="btn btn-primary"
								value="Save" /> <a href="<spring:url value="/session" />"
								class="btn btn-info  btn-mini">Back</a>
						</div>
					</div>

				</fieldset>
			</form:form>
		</div>
	</div>
</body>
</html>
