<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<%--<c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>--%>

<section>
    <div class="jumbotron jumbotron-md">
        <div class="container">
            <div class="col-md-3" style="float:right">
                <img src="http://transcendental-meditation.be/wp-content/themes/imtheme/images/maharishi-new.png" alt=""/>
            </div>
            <h2> ${header_title} </h2>
             <security:authorize access="isAuthenticated()">
                 <p> Logged in as  <security:authentication property="principal.username" /> </p>
                </security:authorize>
            <div class="clearfix"></div>
        </div>
    </div>

    <div class="container">
        <ul class="nav nav-pills">
            <security:authorize access="!isAnonymous()">
            <li role="presentation"
                    <c:if test="${ requestPath eq '/appointment' }">
                class = "active" </c:if> >
                <spring:url value="/appointment" var="appointment_url" />
                <a href="${appointment_url}">My Appointments</a>
            </li>

            <security:authorize access="hasRole('ROLE_ADMIN')">
                <li role="presentation"
                        <c:if test="${ requestPath eq '/appointment/manage' }">
                            class = "active" </c:if>  >
                    <spring:url value="/appointment/manage" var="manage_appointment_url" />
                    <a href="${manage_appointment_url}">Manage Appointments</a>
                </li>
            </security:authorize>

                <security:authorize access="hasRole('ROLE_COUNCELOR')">
                    <li role="presentation"
                            <c:if test="${ requestPath eq '/appointment/counselor' }">
                                class = "active" </c:if>  >
                        <spring:url value="/appointment/counselor" var="counsel_appointment_url" />
                        <a href="${counsel_appointment_url}">Counselor Appointments</a>
                    </li>
                </security:authorize>

            <li role="presentation"
                    <c:if test="${ requestPath eq '/session' }">
                        class = "active" </c:if> >
                <spring:url value="/session" var="session_url" />
                <a href="${session_url}">Sessions</a>
            </li>

                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <li role="presentation"
                            <c:if test="${ requestPath eq '/persons' }">
                                class = "active" </c:if> >
                        <spring:url value="/persons" var="persons_uri" />
                        <a href="${persons_uri}">Persons</a>
                    </li>
                </security:authorize>

                <li class="pull-right" role="presentation">
                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            <security:authentication property="principal.username" />
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="<spring:url value='/profile/changePassword' />">Change Password</a></li>
                            <li><a href="<spring:url value='/profile/edit' />">Profile edit</a></li>
                            <li>
                                <a href="#logout" onclick="$('#logout').submit()">
                                    Log Out
                                </a>
                                <spring:url value="/logout" var="logout_uri" />
                                <form:form id="logout" action="${logout_uri}" class="form-horizontal" method="POST"/>
                            </li>
                            <li role="separator" class="divider"></li>
                        </ul>
                    </div>
                </li>
            </security:authorize>

            <security:authorize access="isAnonymous()">
                <li role="presentation" class="active">
                    <a href="<spring:url value='/login' />" > Login</a>
                </li>
            </security:authorize>
        </ul>
        <hr/>
        <c:choose>
            <c:when test="${!empty errorMessage}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:when>
            <c:when test="${!empty successMessage}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:when>
        </c:choose>
    </div>
</section>

<%--<a href="<spring:url value="/members/add" />" class="btn btn-primary btn-mini pull-right">Add a new one</a>	--%>
