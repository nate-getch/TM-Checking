<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Erdenebayar
  Date: 11/21/2017
  Time: 1:54 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create Person</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <style>
        body {padding-top: 54px; background: #fff;}
        @media (min-width: 992px) {
            body {padding-top: 0px;}
        }
        .main-con{padding:15px; border:1px solid #ccc; background: #fff; border-radius: 5px;padding-bottom: 30px;}
        hr{margin: 10px 0px;}
    </style>

</head>

<body>
<jsp:include page="../template/nav.jsp" />

<div class="container " style="margin-bottom: 80px;">
    <div class="container" style="width: 600px; margin-top: 20px;">
        <c:url var="url" value="/profile/changePassword"/>
        <form:form commandName="moduleForm" action="${url}" method="post">
            <form:hidden path="id"/>
            <form:errors path="*" cssClass="alert alert-danger" element="div"/>
            <div class="form-group">
                <label for="password">Password</label>
                <form:password path="password" cssClass="form-control" placeholder="password"/>
                <form:errors path="password" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label for="passwordConfirm">passwordConfirm</label>
                <form:password path="passwordConfirm" cssClass="form-control" placeholder="passwordConfirm"/>
                <form:errors path="passwordConfirm" cssClass="text-danger"/>
            </div>
            <hr/>
            <div>
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
            <security:csrfInput/>
        </form:form>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>
