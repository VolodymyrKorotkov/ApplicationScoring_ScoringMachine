<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 12.05.2022
  Time: 12:19
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

    <link href="<c:url value="/res/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/res/css/font-awesome/font-awesome.css"/>" rel="stylesheet">

    <link href="<c:url value="/res/css/animate.css"/>" rel="stylesheet">
    <link href="<c:url value="/res/css/style.css"/>" rel="stylesheet">

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

<div class="middle-box text-center animated fadeInDown" style="margin-top: 20px;">
    <h2 class="font-bold"><spring:message code="soonStartPage.title"/></h2>

    <div class="error-desc">
        <spring:message code="soonStartPage.description"/>
        <form class="form-inline m-t" role="form">
            <a href="<c:url value="/"/>" class="btn btn-primary"><spring:message code="errorAllPage.toMainPage"/></a>
        </form>
    </div>
</div>


<script type="text/javascript" src="/res/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="/res/js/bootstrap.min.js"></script>

</body>

</html>
