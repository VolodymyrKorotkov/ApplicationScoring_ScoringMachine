<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 19.02.2022
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<mytags:header-template>
    <jsp:attribute name="contentPage">
        <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 30px;">
            <div class="col-lg-7">
                <h2><spring:message code="myProfilePage.myProfile"/></h2>
                <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="myProfilePage.profile"/> / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="/myprofile/profile/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/myprofile/profile/change-password">
                                    <spring:message code="myProfilePage.changePasswordButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/myprofile/profile/change-email">
                                    <spring:message code="myProfilePage.changeEmailButton"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>

        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${message}"/>

            </div>
        </c:if>

        <div class="col-lg-12">
            <div class="col-sm-4 col-view">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.usernameAndEmail"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        ${userAccount.username}
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.firstName"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="firstName" value="${userAccount.firstName}"/>
                        <c:if test="${empty firstName}">-</c:if>
                        <c:if test="${not empty firstName}">
                            ${firstName}
                        </c:if>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.lastName"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="lastName" value="${userAccount.lastName}"/>
                        <c:if test="${empty lastName}">-</c:if>
                        <c:if test="${not empty lastName}">
                            ${lastName}
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 col-view">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.passwordExpiredAt"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="passwordExpiredAt" value="${userAccount.passwordExpiredAt}"/>
                        <c:if test="${empty passwordExpiredAt}">∞</c:if>
                        <c:if test="${not empty passwordExpiredAt}">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="passExpAt" value="${passwordExpiredAt}" type="both"/>
                            <span>
                                <fmt:formatDate value="${passExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </span>
                        </c:if>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.dateOfBirth"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="dateOfBirth" value="${userAccount.dateOfBirth}"/>
                        <c:if test="${empty dateOfBirth}">-</c:if>
                        <c:if test="${not empty dateOfBirth}">
                            <fmt:parseDate pattern="yyyy-MM-dd" var="birthDate" value="${dateOfBirth}" type="both"/>
                            <span>
                                <fmt:formatDate value="${birthDate}" pattern="dd.MM.yyyy"/>
                            </span>
                        </c:if>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 10px;">
                    <div class="col-lg-12 view-data-title">
                        <spring:message code="myProfilePage.gender"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <c:set var="gender" value="${userAccount.gender}"/>
                        <c:if test="${empty gender}">-</c:if>
                        <c:if test="${not empty gender}">
                            <spring:message code="${gender}"/>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-view">
                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="myProfilePage.role"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <span class="label label-primary" style="font-size: large">
                            <spring:message code="${userAccount.role}"/>
                        </span>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 view-data-title" style="margin-bottom: 10px;">
                        <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                    </div>
                    <div class="col-lg-12 view-data-key">
                        <span class="label label-danger" style="font-size: large">
                            <c:set var="subscriptionExpiredAt" value="${userAccount.subscriptionExpiredAt}"/>
                            <c:if test="${empty subscriptionExpiredAt}">∞</c:if>
                            <c:if test="${not empty subscriptionExpiredAt}">
                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                    <span>
                                        <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </span>
                            </c:if>
                        </span>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-lg-12 client-link" style="margin-bottom: 10px;">
                        <a href="/myprofile/mysubscription">
                            <span class="fa fa-link"></span> <spring:message code="myProfilePage.knowMoreAboutSubscription"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
