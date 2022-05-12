<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 04.02.2022
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-0YNWZ6W90S"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'G-0YNWZ6W90S');
    </script>

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
                    <a href="<c:url value="https://google.com"/>" target="_blank">
                        <i class="fa fa-link"></i>
                        <spring:message code="common.backToLandingPage"/>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div style="margin-top: 100px;">

        <h1 class="logo-name" style="position: relative; left: -15px; top: -40px; font-size: 60px; font-weight: 600;">
            <spring:message code="registrationPage.registrationTitleText"/>
        </h1>
        <c:if test="${needDynamicCode == 'null'}">
        <form:form method="POST" cssClass="m-t" role="form" modelAttribute="userAccountForm">


                <p><spring:message code = "registrationPage.pleaseSignUpText"/></p>
                <br/>

            <div class="form-group">
                <spring:message code="registrationPage.placeHolderUsername" var="usernamePlaceHolder"/>
                <form:input type="text" path="username" class="form-control" placeholder="${usernamePlaceHolder}" required=""></form:input>
                <form:errors path="username"></form:errors>
                <p class="hidden">${usernameError}</p>
                <c:if test="${not empty usernameError}">
                    <p class="help-block error text-danger"><spring:message code="${usernameError}"/></p>
                </c:if>
            </div>
            <div class="form-group">
                <spring:message code="registrationPage.placeHolderPassword" var="passwordPlaceHolder"/>
                <form:input type="password" path="password" class="form-control" placeholder="${passwordPlaceHolder}" required=""></form:input>
            </div>
            <div class="form-group">
                <spring:message code="registrationPage.placeHolderConfirmPassword" var="confirmPasswordPlaceHolder"/>
                <form:input type="password" path="passwordConfirm" class="form-control" placeholder="${confirmPasswordPlaceHolder}" required=""></form:input>
                <form:errors path="password"></form:errors>
                <p class="hidden">${passwordError}</p>
                <c:if test="${not empty passwordError}">
                    <p class="help-block error text-danger"><spring:message code="${passwordError}"/></p>
                </c:if>
            </div>

            <button type="submit" class="btn btn-primary block full-width m-b">
                <spring:message code = "registrationPage.signUpButton"/>
            </button>

        </form:form>
        </c:if>

        <c:if test="${needDynamicCode == 'true'}">
            <form action="/security/login" class="m-t" role="form" method="get">
                <p><spring:message code = "registrationPage.pleaseConfirmEmailText"/></p>
                <br/>
                <button type="submit" class="btn btn-primary block full-width m-b">
                    <spring:message code = "registrationPage.loginButton"/>
                </button>
            </form>
        </c:if>

        <c:if test="${needDynamicCode == 'false'}">
            <form action="/security/login" class="m-t" role="form" method="get">
                <p><spring:message code = "registrationPage.successRegistrationText"/></p>
                <br/>
                <button type="submit" class="btn btn-primary block full-width m-b">
                    <spring:message code = "registrationPage.loginButton"/>
                </button>
            </form>
        </c:if>

        <c:if test="${needDynamicCode == 'error'}">
            <form action="/security/registration" class="m-t" role="form" method="get">
                <p><spring:message code = "registrationPage.errorVerificationEmail"/></p>
                <br/>
                <button type="submit" class="btn btn-primary block full-width m-b">
                    <spring:message code = "registrationPage.signUpButton"/>
                </button>
            </form>
        </c:if>

        <c:if test="${needDynamicCode == 'emailError'}">
            <form action="/security/registration" class="m-t" role="form" method="get">
                <p><spring:message code = "registrationPage.errorSendEmail"/></p>
                <br/>
                <button type="submit" class="btn btn-primary block full-width m-b">
                    <spring:message code = "registrationPage.signUpButton"/>
                </button>
            </form>
        </c:if>

        <form action="/security/login" class="m-t" role="form" method="get">
            <spring:message code="registrationPage.alreadyHaveAccText"/>
            <button type="submit" class="btn btn-link">
               <u><spring:message code="registrationPage.loginButton"/></u>
            </button>
        </form>


        <p class="m-t">
            <small>Scoring Machine &copy; 2022</small>
        </p>
    </div>
</div>



<%--<!-- Mainly scripts -->--%>
<script src="${pageContext.request.contextPath}/res/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>


</body>

</html>
