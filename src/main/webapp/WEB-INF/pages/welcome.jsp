<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 01.02.2022
  Time: 09:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<mytags:header-template>
    <jsp:attribute name="contentPage">
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-7">
                <h2><spring:message code="welcomePage.welcomeTitle"/></h2>
                <h6><spring:message code="welcomePage.welcomeTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action"></div>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top: 20px;">
            <p class="text-box" style="margin-bottom: 20px;">
                <spring:message code="welcomePage.welcomeText"/>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/what-is-scoring" target="_blank">
                    1. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction1"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/general-acquaintance" target="_blank">
                    2. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction2"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/my-profile" target="_blank">
                    3. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction3"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/creating-scoring-model" target="_blank">
                    4. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction4"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/testing-scoring-model" target="_blank">
                    5. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction5"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/advanced-settings" target="_blank">
                    6. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction6"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/preparation-of-initial-data" target="_blank">
                    7. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction7"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/model-attributes-and-parameters" target="_blank">
                    8. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction8"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/test-of-the-scoring-model" target="_blank">
                    9. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction9"/>
                </a>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px;">
                <a href="<spring:message code="common.urlToLandingPage"/>/exporting-finished-data-to-excel" target="_blank">
                    10. <i class="fa fa-external-link-square"></i>
                    <spring:message code="welcomePage.welcomeInstruction10"/>
                </a>
            </p>
        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
