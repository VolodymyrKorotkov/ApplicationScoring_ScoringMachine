<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 13.03.2022
  Time: 00:04
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
                <h2><spring:message code="modelsListPage.modelListTitle"/></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelsListPage.modelListTitle"/></h6>
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
                                            <spring:message code="modelsListPage.fieldTitle"/>
                                        </label>
                                        <input type="text" id="title" class="form-control" name="title" value="${param.get(title)}">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>
                                            <spring:message code="modelsListPage.fieldStatus"/>
                                        </label>
                                        <select class="chosen-select" name="status" id="status" style="font-size: larger">
                                            <option value="NaN">
                                                <spring:message code="modelsListPage.statusSearchAllStatuses"/>
                                            </option>
                                            <option value="ACTIVE">
                                                <spring:message code="modelsListPage.statusSearchActive"/>
                                            </option>
                                            <option value="INACTIVE">
                                                <spring:message code="modelsListPage.statusSearchInactive"/>
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <button type="submit" class="btn btn-white btn-sm">
                                <spring:message code="modelsListPage.ApplyFilters"/>
                            </button>
                            <a class="btn btn-white btn-sm btn-clear-filter" href="/creating-model/scoring-model-list">
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
                    <spring:message code="modelListPage.textCommonForDetailedInfoModel"/>
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
                        <spring:message code="modelsListPage.fieldTitle"/>
                    </th>
                    <th class="center">
                        <spring:message code="modelsListPage.fieldStatus"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="modelList" items="${scoringModelList}" varStatus="i">
                    <tr class="clickable" href="<c:url value="/creating-model/scoring-model/${modelList.id}"/>">
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
                        <td class="center title-action">
                            <c:set var="status" value="${modelList.status}"/>
                            <c:if test="${status == 'ACTIVE'}">
                                <spring:message code="modelsListPage.statusSearchActive"/>
                            </c:if>
                            <c:if test="${status == 'INACTIVE'}">
                                <spring:message code="modelsListPage.statusSearchInactive"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="999">
                        <div class="pull-right">
                            <c:url value="/creating-model/scoring-model-list" var="urlFirstPage">
                                <c:param name="pageString" value="1"/>
                                <c:param name="title" value="${param.title}"/>
                                <c:param name="status" value="${param.status}"/>
                            </c:url>
                            <c:url value="/creating-model/scoring-model-list" var="urlLastPage">
                                <c:param name="pageString" value="${pagesCount}"/>
                                <c:param name="title" value="${param.title}"/>
                                <c:param name="status" value="${param.status}"/>
                            </c:url>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page == 1}">
                                        <c:url value="/creating-model/scoring-model-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                        </c:url>
                                    </c:when>
                                    <c:when test="${empty page}">
                                        <c:url value="/creating-model/scoring-model-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="1"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/creating-model/scoring-model-list" var="urlPreviousPage">
                                            <c:param name="pageString" value="${page - 1}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                        </c:url>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <c:if test="${not empty page || empty page}">
                                <c:choose>
                                    <c:when test="${page >= pagesCount}">
                                        <c:url value="/creating-model/scoring-model-list" var="urlNextPage">
                                            <c:param name="pageString" value="${pagesCount}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
                                        </c:url>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/creating-model/scoring-model-list" var="urlNextPage">
                                            <c:param name="pageString" value="${page + 1}"/>
                                            <c:param name="title" value="${param.title}"/>
                                            <c:param name="status" value="${param.status}"/>
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
                            <spring:message code="modelListPage.totalCountModels"/> ${scoringModelsCount}
                        </div>
                    </td>
                </tr>
                </tfoot>

            </table>
            </c:if>
            <c:if test="${pagesCount <= 0}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="modelListPage.textCommonNoModelsInList"/>
                </p>
                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="/creating-model/creating-new-model"><span class="fa fa-link"></span> <spring:message code="modelListPage.textLinkToCreateModel"/></a>
                </p>
            </c:if>



        </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
