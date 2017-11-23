<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title></title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <link href="<c:url value="/resource/css/style.css" />" rel="stylesheet">

</head>
<body>
<jsp:include page="../template/nav.jsp" />

<div class="container">
    <div class="clearfix">
        <div class="pull-right">
            <a class="btn btn-primary btn-sm" href="<spring:url value="/persons/create" />">Create</a>
        </div>
    </div>
    <hr/>
    <table class="table col-xs-12">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Username</th>
            <th scope="col">email</th>
            <th scope="col">roles</th>
            <th scope="col">
                <i class="glyphicon glyphicon-cog"></i>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="row">
            <tr>
                <th scope="row">${row.id}</th>
                <td>${row.firstName}</td>
                <td>${row.lastName}</td>
                <td>${row.username}</td>
                <td>${row.email}</td>
                <td>
                    <c:forEach items="${row.personRoles}" var="cRow" varStatus="cStatus">
                        ${cRow.role.name} <c:if test="${!cStatus.last}"> / </c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:url value="/persons/delete/${row.id}" var="deleteUrl"/>
                    <a class="btn btn-danger btn-xs" href="${deleteUrl}">
                        <i class="glyphicon glyphicon-trash" title="delete"></i>
                    </a>
                    <c:url value="/persons/changeRole/${row.id}" var="changeRoleUrl"/>
                    <a class="btn btn-info btn-xs" href="${changeRoleUrl}">
                        <i class="glyphicon glyphicon-sort-by-attributes" title="changeRole"></i>
                    </a>
                    <c:url value="/persons/edit/${row.id}" var="editUrl"/>
                    <a class="btn btn-info btn-xs" href="${editUrl}">
                        <i class="glyphicon glyphicon-edit" title="edit"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>
