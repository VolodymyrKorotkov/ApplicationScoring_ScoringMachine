<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 06.05.2022
  Time: 21:04
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
                <h2><spring:message code="paymentHistoryPage.paymentHistoryTitle"/></h2>
                <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="paymentHistoryPage.paymentHistoryTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>

<%--        <c:if test="${not empty message}">--%>
<%--            <div class="alert alert-success alert-dismissable">--%>
<%--                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>--%>
<%--                <spring:message code="${message}"/>--%>
<%--            </div>--%>
<%--        </c:if>--%>

        <div class="col-lg-12">
            <c:if test="${listSize == 0}">
                <div class="text-box" style="margin-bottom: 10px;">
                    <spring:message code="paymentsPage.notHavePayments"/>
                </div>
            </c:if>
            <c:if test="${listSize > 0}">
                <table class="table table-bordered table-hover table-pageable" style="font-size: small">
                    <thead>
                    <tr>
                        <th class="center" style="width: 50px;">
                            #
                        </th>
                        <th class="center">
                            <spring:message code="paymentsPage.paymentDateTitle"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentsPage.paymentAmount"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentPage.typePurchase"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentPage.levelSubscription"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentPage.period"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentPage.card"/>
                        </th>
                        <th class="center">
                            <spring:message code="paymentPage.typeCard"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="paymentRow" items="${paymentList}" varStatus="items">
                        <tr>
                            <td class="center">${items.index + 1}</td>
                            <td class="center">
                                <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${paymentRow.createdAt}" type="both"/>
                                <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                            </td>
                            <td class="center">
                                <fmt:formatNumber var="paymentAmount" value="${paymentRow.amount}" maxFractionDigits="0"/>
                                $${paymentAmount}
                            </td>
                            <td class="center">
                                <spring:message code="${paymentRow.typePurchase}"/>
                            </td>
                            <td class="center">
                                <spring:message code="${paymentRow.userRole.name}"/>
                            </td>
                            <td class="center">
                                <spring:message code="${paymentRow.periodPurchase}"/>
                            </td>
                            <td class="center">
                                ${paymentRow.maskedCard}
                            </td>
                            <td class="center">
                                ${paymentRow.cardType}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

    </jsp:attribute>
</mytags:header-template>

</html>
