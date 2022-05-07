<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 23.03.2022
  Time: 20:28
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
                <h2><spring:message code="scoringModelTesting.listModelsTestsPageTitle"/></h2>
                <h6><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/> / <spring:message code="scoringModelTesting.listModelsTestsPageTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <button class="btn btn-primary table-filter-button">
                        <spring:message code="modelsListPage.filterSearch"/>
                    </button>
                </div>
            </div>
            <div class="col-lg-12" style="margin-top: 10px; margin-bottom: 10px;">

                <div class="content-section row wrapper wrapper-content border-bottom white-bg table-filter-container"
                     hidden = "${(viewState == null or not viewState.filtersPanelShown) and (param.sf == null or param.sf[0] ne 'true')}">
                    <form method="get" class="table-filter-form" style="margin-top: 20px; margin-bottom: 10px;">
                        <div class="col-lg-12">
                            <div class="col-lg-6">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="testingScoringModelsListPage.fieldTestingModelTitle"/>:
                                        </label>
                                        <input type="text" id="titleTestingModel" class="form-control" name="titleTestingModel" value="${param.get(titleTestingModel)}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="testingScoringModelsListPage.fieldScoringModelTitle"/>:
                                        </label>
                                        <input type="text" id="titleScoringModel" class="form-control" name="titleScoringModel" value="${param.get(titleScoringModel)}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-white btn-sm">
                                <spring:message code="modelsListPage.ApplyFilters"/>
                            </button>
                            <a class="btn btn-white btn-sm btn-clear-filter" href="/testing-model/scoring-model-test-list">
                                <spring:message code="modelsListPage.ResetFilters"/>
                            </a>
                        </div>
                    </form>
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
            <c:if test="${pagesCount > 0}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="testingScoringModelsListPage.textCommonForDetailedInfoModel"/>
                </p>

            <table class="table table-bordered table-hover table-pageable">
                <thead>
                <tr>
                    <th class="center" style="width: 60px;">
                        #
                    </th>
                    <th class="center">
                        <spring:message code="modelsListPage.fieldCreatedAt"/>
                    </th>
                    <th class="center">
                        <spring:message code="modelsListPage.fieldLastModifiedAt"/>
                    </th>
                    <th class="center">
                        <spring:message code="testingScoringModelsListPage.fieldTestingModelTitle"/>
                    </th>
                    <th class="center">
                        <spring:message code="testingScoringModelsListPage.fieldScoringModelTitle"/>
                    </th>
                    <th class="center">
                        <spring:message code="testingScoringModelsListPage.fieldGiniIndex"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="modelList" items="${testScoringModelList}" varStatus="i">
                    <tr class="clickable" href="<c:url value="/testing-model/test-scoring-model/${modelList.id}"/>">
                        <td class="center">${i.index + 1 + (page - 1) * 10}</td>
                        <td class="center">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${modelList.createdAt}" type="both"/>
                            <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center date">
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="lastModifiedAt" value="${modelList.lastModifiedAt}" type="both"/>
                            <fmt:formatDate value="${lastModifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                        </td>
                        <td class="center title">
                                ${modelList.title}
                        </td>
                        <td class="center title">
                            <p class="client-link">
                                <a href="/creating-model/scoring-model/${modelList.scoringModel.id}"><span class="fa fa-link"></span> ${modelList.scoringModel.title}</a>
                            </p>
                        </td>
                        <td class="center title">
                            <fmt:formatNumber var="gini" value="${modelList.giniIndex * 100}" maxFractionDigits="2"/>
                            <span class="text-info">${gini}%</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="999">
                        <div class="pull-right">
                            <c:url value="/testing-model/scoring-model-test-list" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                            </c:url>
                            <c:url value="/testing-model/scoring-model-test-list" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                            </c:url>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/testing-model/scoring-model-test-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                            <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/testing-model/scoring-model-test-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                            <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/testing-model/scoring-model-test-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="${page - 1}"/>
                                            <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                            <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page >= pagesCount}">
                                        <c:url value="/testing-model/scoring-model-test-list" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                            <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/testing-model/scoring-model-test-list" var="urlNextPage">
                                            <c:param name="pageString" value="${page + 1}"/>
                                            <c:param name="titleTestingModel" value="${param.titleTestingModel}"/>
                                            <c:param name="titleScoringModel" value="${param.titleScoringModel}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-double-left table-filter-btn hidden-xs"
                               href="${urlFirstPage}"></a>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-left table-filter-btn hidden-xs"
                               href="${urlPreviousPage}"></a>
                            <span class="label label-primary" style="margin-right: 5px;">
                                <spring:message code="common.pageFooterNumber"/>${page} <spring:message code="common.pagesFooterFrom"/> ${pagesCount}
                            </span>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-right table-filter-btn hidden-xs"
                               href="${urlNextPage}"></a>
                            <a class="btn btn-default btn-circle btn-xs fa fa-angle-double-right table-filter-btn hidden-xs"
                               href="${urlLastPage}"></a>
                        </div>
                        <div class="hidden-xs" style="padding-top: 5px;">
                            <spring:message code="testingScoringModelsListPage.totalCountTests"/> ${testingModelsCount}
                        </div>
                    </td>
                </tr>
                </tfoot>

            </table>
            </c:if>
            <c:if test="${pagesCount <= 0}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="testingScoringModelsListPage.textCommonNoModelsInList"/>
                </p>
                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="/testing-model/creating-new-test"><span class="fa fa-link"></span> <spring:message code="testingScoringModelsListPage.textLinkToCreateTest"/></a>
                </p>
            </c:if>



        </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
