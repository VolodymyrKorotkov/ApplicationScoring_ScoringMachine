<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 24.01.2022
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title><spring:message code = "company.name"/></title>

    <link href="${pageContext.request.contextPath}/res/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/font-awesome/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/res/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/style.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="row border-bottom">
    <div>
        <nav class="navbar navbar-static-top gray-bg-forced" role="navigation" style="margin-bottom: 0px;">
            <ul class="nav navbar-header navbar-top-links" style="margin-left: 50px;">
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=en">English</a>
                </li>
                <li class="text-muted">
                    <span>|</span>
                </li>
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=ru">Русский</a>
                </li>
                <li class="text-muted">
                    <span>|</span>
                </li>
                <li class="text-muted">
                    <a href="<%=request.getContextPath()%>?languageVar=ua">Українська</a>
                </li>
            </ul>
            <ul class="nav navbar-header navbar-top-links navbar-right" style="margin-right: 50px;">
                <li class="hidden-xs">
                    <a href="<spring:message code="common.urlToLandingPage"/>" target="_blank">
                        <i class="fa fa-link"></i>
                        <spring:message code="common.backToLandingPage"/>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>

<%--<sec:authorize access="isAuthenticated()">--%>
<%--    <%response.sendRedirect("/");%>--%>
<%--</sec:authorize>--%>
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div style="margin-top: 100px;">
        <h1 class="logo-name" style="position: relative; left: -15px; top: -40px; font-size: 70px; font-weight: 600;">
            <spring:message code="company.name"/>
        </h1>
        <c:if test="${param.accountexpired == 'true'}">
            <div class="alert alert-warning alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="loginPage.messageExpiredSubscription"/>
            </div>
        </c:if>
        <p><spring:message code = "loginPage.pleaseLoginText"/></p>
        <c:url value="/security/login" var = "var"/>
        <form action="${var}" class="m-t" role="form" method="post">

            <div class="form-group">
                <spring:message code="loginPage.placeHolderUsername" var="placeHolderUsername"/>
                <input type="text" name="username" class="form-control" placeholder="${placeHolderUsername}" required="">
            </div>
            <div class="form-group">
                <spring:message code="loginPage.placeHolderPassword" var="placeHolderPassword"/>
                <input type="password" name="password" class="form-control" placeholder="${placeHolderPassword}" required="">
            </div>
            <c:if test="${param.badcredentials == 'true'}">
                <p class="help-block error text-danger"><spring:message code="loginPage.errorBadCredentials"/></p>
            </c:if>
            <c:if test="${param.error == 'true'}">
                <p class="help-block error text-danger"><spring:message code="loginPage.errorOtherError"/></p>
            </c:if>
            <c:if test="${param.locked == 'true'}">
                <p class="help-block error text-danger"><spring:message code="loginPage.errorLockedAccount"/></p>
            </c:if>

            <button type="submit" class="btn btn-primary block full-width m-b">
                <spring:message code = "loginPage.loginButton"/>
            </button>

        </form>
        <form action="/security/registration" class="m-t" role="form" method="get">
            <spring:message code="loginPage.dontHaveAccText"/>
            <button type="submit" class="btn btn-link">
                <u><spring:message code="loginPage.registrationButton"/></u>
            </button>
        </form>
        <form action="/security/reset-password" class="m-t" role="form" method="get">
            <spring:message code="loginPage.forgotPasswordText"/>
                <button type="submit" class="btn btn-link">
                    <u><spring:message code="loginPage.recoveryPasswordButton"/></u>
                </button>
        </form>

        <p class="m-t">
            <small>Scoring Machine &copy; 2022</small>
        </p>
    </div>
</div>



<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/res/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>

</body>

</html>
