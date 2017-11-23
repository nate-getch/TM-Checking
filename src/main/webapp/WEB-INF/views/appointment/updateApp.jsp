<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form:form  modelAttribute="newMember" class="form-horizontal"  >
			<fieldset>
				<legend>Add new member</legend>

				<form:errors path="*" cssClass="alert alert-danger" element="div"/>
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="personId"> Person Id</label>
					<div class="col-lg-10">
						<form:input id="personId" path="persontId" type="text" class="form:input-large"/>
						<form:errors path="personId" cssClass="text-danger"/>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-lg-2" for="sessionId"> Session Id</label>
					<div class="col-lg-10">
						<form:input id="sessionId" path="sessionId" type="text" class="form:input-large"/>
						<form:errors path="sessionId" cssClass="text-danger"/>
					</div>
				</div>

			<a href="<spring:url value="/appointment/update/${appointment.id}"/>"
						class="btn btn-warning btn-large"> <span class="glyphicon"></span>
							Update
				</a>
				
			</fieldset>
</form:form>

</body>
</html>