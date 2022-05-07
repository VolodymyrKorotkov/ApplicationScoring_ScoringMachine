<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 14.03.2022
  Time: 14:46
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
                <h2><spring:message code="scoringModelPage.scoringModelTitle"/> / <strong>${scoringModel.title}</strong></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelsListPage.modelListTitle"/> / <spring:message code="scoringModelPage.scoringModelTitle"/> / ${scoringModel.title} / <spring:message code="common.view"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/creating-model/scoring-model-list" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                    <div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                            <spring:message code="common.actionsButton"/> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">

                            <c:if test="${scoringModel.status == 'INACTIVE'}">
                                <li>
                                    <a href="/creating-model/scoring-model/${modelId}/activate">
                                        <spring:message code="scoringModelPage.buttonActionsActivateModel"/>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${scoringModel.status == 'ACTIVE'}">
                                <li>
                                    <a href="/creating-model/scoring-model/${modelId}/deactivate">
                                        <spring:message code="scoringModelPage.buttonActionsDeactivateModel"/>
                                    </a>
                                </li>
                            </c:if>
                            <li>
                                <a href="/creating-model/scoring-model/${modelId}/edit">
                                    <spring:message code="scoringModelPage.buttonActionsEditModel"/>
                                </a>
                            </li>
                            <li>
                                <a href="/creating-model/scoring-model/${modelId}/activate-and-make-test">
                                    <spring:message code="scoringModelPage.buttonActionsCreateNewTestModel"/>
                                </a>
                            </li>
                            <li>
                                <a href="/creating-model/scoring-model/${modelId}/export-to-excel">
                                    <spring:message code="scoringModelPage.buttonExportModelToExcel"/>
                                </a>
                            </li>
                            <li>
                                <a href="/creating-model/scoring-model/${modelId}/delete">
                                    <spring:message code="scoringModelPage.buttonActionsDeleteModel"/>
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

        <div class="tabs-container">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#common"><spring:message code="scoringModelPage.tabTitleCommonInfo"/></a></li>
                <li><a data-toggle="tab" href="#model" id="model_btn"><spring:message code="scoringModelPage.tabTitleScoringModel"/></a></li>
                <li><a data-toggle="tab" href="#tests" id="tests_btn"><spring:message code="scoringModelPage.tabTitleTests"/></a></li>
            </ul>
            <div class="tab-content">
                <div id="common" class="tab-pane active">
                    <div class="panel-body">
                        <div class="clearfix"></div>
                        <div class="col-lg-12" style="margin-bottom: 10px;">
                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="scoringModelPage.textScoringModelTitle"/>
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                            ${scoringModel.title}
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="scoringModelPage.textCreatedAt"/>
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="createdAt" value="${scoringModel.createdAt}"/>
                                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="created" value="${createdAt}" type="both"/>
                                        <span>
                                        <fmt:formatDate value="${created}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </span>
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="scoringModelPage.textModifiedAt"/>
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="modifiedAt" value="${scoringModel.lastModifiedAt}"/>
                                        <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="modified" value="${modifiedAt}" type="both"/>
                                        <span>
                                        <fmt:formatDate value="${modified}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                    </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="scoringModelPage.textScoringModelStatus"/>
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:set var="status" value="${scoringModel.status}"/>
                                        <c:if test="${status == 'ACTIVE'}">
                                            <spring:message code="modelsListPage.statusSearchActive"/>
                                        </c:if>
                                        <c:if test="${status == 'INACTIVE'}">
                                            <spring:message code="modelsListPage.statusSearchInactive"/>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="row" style="margin-bottom: 10px;">
                                    <div class="col-lg-12 view-data-title">
                                        <spring:message code="scoringModelPage.textScoringModelDescription"/>
                                    </div>
                                    <div class="col-lg-12 view-data-key">
                                        <c:if test="${empty scoringModel.description}">-</c:if>
                                        <c:if test="${not empty scoringModel.description}">
                                        ${scoringModel.description}
                                    </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="text-box" style="margin-bottom: 10px;">
                            <spring:message code="scoringModelPage.textAboutTableOfInfluencedParameters"/>
                        </div>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

                        <table class="table table-bordered table-hover table-pageable">
                            <thead>
                            <tr>
                                <th class="center" style="width: 60px;">
                                    #
                                </th>
                                <th class="center">
                                    <spring:message code="scoringModelPage.textTableScoringModelParameterName"/>
                                </th>
                                <th class="center">
                                    <spring:message code="scoringModelPage.textTableScoringModelParameterInformationValuePercent"/>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="allParametersOneList" items="${allParametersInfluenceOne}" varStatus="i">
                                <tr>
                                    <td class="center">${i.index + 1}</td>
                                    <td class="center">
                                        ${allParametersOneList.nameParameter}
                                    </td>
                                    <td class="center">
                                        <fmt:formatNumber var="ivTotalOneParameterAll" value="${allParametersOneList.iv * 100}"
                                                          maxFractionDigits="2"/>
                                        ${ivTotalOneParameterAll}%
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="999">
                                    <div class="hidden-xs" style="padding-top: 5px;">
                                        <spring:message code="scoringModelPage.textTableTotalCountParameters"/> ${allParametersInfluenceOneSize}
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                </div>
                <div id="model" class="tab-pane">
                    <div class="panel-body">
                        <div class="clearfix"></div>
                        <c:if test="${scoringModelRecommendedParametersListOneSize == 0 && scoringModelRecommendedParametersListTwoSize == 0}">
                            <div class="text-box" style="margin-bottom: 10px;">
                                <spring:message code="scoringModelPage.textAboutScoringModelParametersIfNull"/>
                            </div>
                            <p class="text-box client-link" style="margin-bottom: 10px">
                                <a href="<spring:message code="common.urlToLandingPage"/>/creating-scoring-model" target="_blank"><span class="fa fa-link"></span> <spring:message code="scoringModelPage.textLinkAboutCreatingScoringModel"/></a>
                            </p>
                            <p class="text-box client-link" style="margin-bottom: 10px">
                                <a href="/creating-model/scoring-model-settings/view"><span class="fa fa-link"></span> <spring:message code="scoringModelPage.textLinkCheckAndChangeScoringSettings"/></a>
                            </p>
                            <p class="text-box client-link" style="margin-bottom: 10px">
                                <a href="/creating-model/creating-new-model"><span class="fa fa-link"></span> <spring:message code="scoringModelPage.textLinkMakeNewScoringModel"/></a>
                            </p>
                        </c:if>
                        <c:if test="${scoringModelRecommendedParametersListOneSize > 0 || scoringModelRecommendedParametersListTwoSize > 0}">
                            <div class="text-box" style="margin-bottom: 10px;">
                                <spring:message code="scoringModelPage.textAboutScoringModelParameters"/>
                            </div>
                            <p class="text-box client-link" style="margin-bottom: 10px">
                                <a href="<spring:message code="common.urlToLandingPage"/>/model-attributes-and-parameters" target="_blank"><span class="fa fa-link"></span> <spring:message code="scoringModelPage.textLinkAboutColumnsTablesModel"/></a>
                            </p>
                            <p class="text-box client-link" style="margin-bottom: 10px;">
                                <a href="/creating-model/scoring-model/${modelId}/activate-and-make-test"><span class="fa fa-link"></span> <spring:message code="scoringModelPage.textLinkMakeTestModel"/></a>
                            </p>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>
                        <div class="text-box" style="margin-bottom: 10px;">
                            <strong><spring:message code="scoringModelPage.textScoringModelParameterTotalAttributes"/> ${scoringModelRecommendedParametersListOneSize + scoringModelRecommendedParametersListTwoSize}</strong>
                        </div>
                        <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>
                        <c:if test="${scoringModelRecommendedParametersListOneSize > 0}">
                            <div class="tabs-container">
                                <div class="tabs">
                                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                                    <c:forEach var="parametersListOne" items="${scoringModelRecommendedParametersListOne}" varStatus="item">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab" id="heading${item.index}">
                                                <h5 class="panel-title">
                                                    <a role="button" data-toggle="collapse" data-target="#collapse${item.index}" aria-expanded="true" aria-controls="collapse${item.index}" class="collapsed">
                                                        <fmt:formatNumber var="ivTotalOneParamete" value="${parametersListOne.ivTotal * 100}"
                                                                          maxFractionDigits="2"/>
                                                        <span class="fa fa-leaf"></span> <spring:message code="scoringModelPage.textParameterName"/> <span class="text-info">${parametersListOne.nameParameter}</span>. <spring:message code="scoringModelPage.textScoringModelParameterInformationValue"/> - ${ivTotalOneParamete}%
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="collapse${item.index}" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading${item.index}">
                                                <div class="panel-body">
                                                    <div class="container-scroll">
                                                        <table class="table table-bordered table-hover table-pageable" style="font-size: small; width: 3000px;">
                                                            <thead>
                                                            <tr>
                                                                <th class="center" style="width: 50px;">
                                                                    #
                                                                </th>
                                                                <th class="center">
                                                                    <span class="text-info"><spring:message code="scoringModelPage.textTableScoringModelParameterValue"/></span>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterCountScores"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterActions"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodRate"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadRate"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterTotalCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterTotalPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGiG"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBiB"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterPgPb"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterWoE"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textScoringModelParameterInformationValue"/>
                                                                </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="valuesListParameterOne" items="${parametersListOne.scoringParameterPortalRows}" varStatus="itemsValuesOne">
                                                            <tr>
                                                                <td class="center">${itemsValuesOne.index + 1}</td>
                                                                <td class="center">
                                                                    <span class="text-info">${valuesListParameterOne.title}</span>
                                                                </td>
                                                                <td class="center">
                                                                    ${valuesListParameterOne.score}
                                                                </td>
                                                                <td class="center">
                                                                    <a class="btn btn-primary" style="font-size: x-small" href="/creating-model/scoring-model/${modelId}/attribute/${valuesListParameterOne.idInDataBase}/attribute-edit-score"><span class="fa fa-edit"></span> <spring:message code="scoringModelPage.buttonTableScoringModelParameterChangeScore"/></a>
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.goodCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.badCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.goodRate}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.badRate}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.totalCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.goodPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.badPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.totalPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.giG}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.biB}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.pgPb}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.woe}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterOne.iv}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                            </tbody>
                                                            <tfoot>
                                                            <tr>
                                                                <td class="center"><span class="fa fa-envira"></span></td>
                                                                <td class="center">
                                                                    <strong><span class="text-info"><spring:message code="scoringModelPage.textTableTotal"/></span></strong>
                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.goodCountTotal}</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.badCountTotal}</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.goodRateTotal}%</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.badRateTotal}%</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.totalCountTotal}</strong>
                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListOne.ivTotal}</strong>
                                                                </td>
                                                            </tr>
                                                            </tfoot>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${scoringModelRecommendedParametersListTwoSize > 0}">
                            <div class="tabs-container">
                                <div class="tabs">
                                    <div class="panel-group" id="accordionTwo" role="tablist" aria-multiselectable="true">
                                    <c:forEach var="parametersListTwo" items="${scoringModelRecommendedParametersListTwo}" varStatus="item2">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" role="tab" id="headingTwo${item2.index}">
                                                <h5 class="panel-title">
                                                    <a role="button" data-toggle="collapse" data-target="#collapseTwo${item2.index}" aria-expanded="true" aria-controls="collapseTwo${item2.index}" class="collapsed">
                                                        <fmt:formatNumber var="ivTotalTwoParamete" value="${parametersListTwo.ivTotal * 100}"
                                                                          maxFractionDigits="2"/>
                                                        <span class="fa fa-leaf"></span> <span class="text-danger"><spring:message code="scoringModelPage.textScoringModelConnectTwoParameter"/></span> <span class="fa fa-angellist"></span> <spring:message code="scoringModelPage.textParameterName"/> <span class="text-info">${parametersListTwo.nameParameter}</span>. <spring:message code="scoringModelPage.textScoringModelParameterInformationValue"/> - ${ivTotalTwoParamete}%
                                                    </a>
                                                </h5>
                                            </div>
                                            <div id="collapseTwo${item2.index}" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingTwo${item2.index}">
                                                <div class="panel-body">
                                                    <div class="container-scroll">
                                                        <table class="table table-bordered table-hover table-pageable" style="font-size: small; width: 3000px;">
                                                            <thead>
                                                            <tr>
                                                                <th class="center" style="width: 50px;">
                                                                    #
                                                                </th>
                                                                <th class="center">
                                                                    <span class="text-info"><spring:message code="scoringModelPage.textTableScoringModelParameterValue"/></span>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterCountScores"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterActions"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodRate"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadRate"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterTotalCount"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGoodPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBadPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterTotalPopulation"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterGiG"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterBiB"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterPgPb"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textTableScoringModelParameterWoE"/>
                                                                </th>
                                                                <th class="center">
                                                                    <spring:message code="scoringModelPage.textScoringModelParameterInformationValue"/>
                                                                </th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="valuesListParameterTwo" items="${parametersListTwo.scoringParameterPortalRows}" varStatus="itemsValuesTwo">
                                                            <tr>
                                                                <td class="center">${itemsValuesTwo.index + 1}</td>
                                                                <td class="center">
                                                                    <span class="text-info">${valuesListParameterTwo.title}</span>
                                                                </td>
                                                                <td class="center">
                                                                    ${valuesListParameterTwo.score}
                                                                </td>
                                                                <td class="center">
                                                                    <a class="btn btn-primary" style="font-size: x-small" href="/creating-model/scoring-model/${modelId}/attribute/${valuesListParameterTwo.idInDataBase}/attribute-edit-score"><span class="fa fa-edit"></span> <spring:message code="scoringModelPage.buttonTableScoringModelParameterChangeScore"/></a>
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.goodCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.badCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.goodRate}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.badRate}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.totalCount}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.goodPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.badPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.totalPopulationPercent}%
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.giG}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.biB}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.pgPb}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.woe}
                                                                </td>
                                                                <td class="center">
                                                                        ${valuesListParameterTwo.iv}
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                            </tbody>
                                                            <tfoot>
                                                            <tr>
                                                                <td class="center"><span class="fa fa-envira"></span> </td>
                                                                <td class="center">
                                                                    <strong><span class="text-info"><spring:message code="scoringModelPage.textTableTotal"/></span></strong>
                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.goodCountTotal}</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.badCountTotal}</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.goodRateTotal}%</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.badRateTotal}%</strong>
                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.totalCountTotal}</strong>
                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">

                                                                </td>
                                                                <td class="center">
                                                                    <strong>${parametersListTwo.ivTotal}</strong>
                                                                </td>
                                                            </tr>
                                                            </tfoot>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    </c:if>
                </div>
                <div id="tests" class="tab-pane">
                    <div class="panel-body">
                        <div class="clearfix"></div>
                        <c:if test="${empty testsForModel}">
                            <div class="text-box" style="margin-bottom: 10px;">
                                <spring:message code="scoringModelPage.textNoHaveTestModel"/>
                            </div>
                            <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>
                            <a class="btn btn-primary" href="/creating-model/scoring-model/${modelId}/activate-and-make-test"><spring:message code="scoringModelPage.buttonActionsCreateNewTestModel"/></a>
                        </c:if>
                        <c:if test="${not empty testsForModel}">
                            <div class="text-box" style="margin-bottom: 10px;">
                                <spring:message code="scoringModelPage.textAboutTestModel"/>
                            </div>
                            <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>
                            <div class="tabs-container">
                                <div class="tabs">
                                    <div class="panel-group" id="accordionTests" role="tablist" aria-multiselectable="true">
                                        <c:forEach var="testsModel" varStatus="itemTests" items="${testsForModel}">
                                            <div class="panel panel-default">
                                                <div class="panel-heading" role="tab" id="headingTests${itemTests.index}">
                                                    <h5 class="panel-title">
                                                        <a role="button" data-toggle="collapse" data-target="#collapseTests${itemTests.index}" aria-expanded="true" aria-controls="collapseTests${itemTests.index}" class="collapsed">
                                                            <fmt:formatNumber var="giniIndexTotal" value="${testsModel.giniResult * 100}" maxFractionDigits="2"/>
                                                            <fmt:formatNumber var="badRateTotal" value="${testsModel.badRate * 100}" maxFractionDigits="2"/>
                                                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAtTotal" value="${testsModel.createdAt}" type="both"/>
                                                            <fmt:formatDate value="${createdAtTotal}" var="createdAtTotalTest" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                            <span class="fa fa-chevron-circle-right"></span> #${itemTests.index + 1}. <strong><span class="text-info">${testsModel.title}</span></strong>. <spring:message code="scoringModelPage.Gini"/> - ${giniIndexTotal}%. <spring:message code="scoringModelPage.badRate"/> - ${badRateTotal}%. <spring:message code="testScoringModelPage.fieldTextCountItems"/> - ${testsModel.countTotalItems}. <spring:message code="scoringModelPage.textCreatedAt"/> ${createdAtTotalTest}
                                                        </a>
                                                    </h5>
                                                </div>
                                                <div id="collapseTests${itemTests.index}" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingTests${itemTests.index}">
                                                    <div class="panel-body">
                                                        <a class="btn btn-primary" style="font-size: small;" href="/testing-model/test-scoring-model/${testsModel.id}"><spring:message code="scoringModelPage.buttonGoToPageTest"/></a>
                                                        <div class="hr-line-dashed" style="margin-bottom: 10px;"></div>
                                                        <div class="container-scroll">
                                                            <table class="table table-bordered table-hover table-pageable" style="font-size: small;">
                                                                <thead>
                                                                <tr>
                                                                    <th class="center" style="width: 50px;">
                                                                        #
                                                                    </th>
                                                                    <th class="center">
                                                                        <span class="text-info"><spring:message code="testScoringModelPage.fieldTextScore"/></span>
                                                                    </th>
                                                                    <th class="center">
                                                                        <spring:message code="testScoringModelPage.fieldTextCountItems"/>
                                                                    </th>
                                                                    <th class="center">
                                                                        <spring:message code="testScoringModelPage.fieldTextCountGood"/>
                                                                    </th>
                                                                    <th class="center">
                                                                        <spring:message code="testScoringModelPage.fieldTextCountBad"/>
                                                                    </th>
                                                                    <th class="center">
                                                                        <span class="text-danger"><spring:message code="testScoringModelPage.fieldTextBadRate"/></span>
                                                                    </th>
                                                                    <th class="center">
                                                                        <span class="text-info"><spring:message code="testScoringModelPage.fieldTextGiniResult"/></span>
                                                                    </th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <c:forEach var="testListResultRow" items="${testsModel.testResultsPortalList}" varStatus="itemsTestRow">
                                                                    <tr>
                                                                        <td class="center">${itemsTestRow.index + 1}</td>
                                                                        <td class="center">
                                                                            <span class="text-info">
                                                                                ${testListResultRow.score}
                                                                            </span>
                                                                        </td>
                                                                        <td class="center">
                                                                            ${testListResultRow.countTotalItems}
                                                                        </td>
                                                                        <td class="center">
                                                                            ${testListResultRow.countGoodItems}
                                                                        </td>
                                                                        <td class="center">
                                                                            ${testListResultRow.countBadItems}
                                                                        </td>
                                                                        <td class="center">
                                                                            <fmt:formatNumber var="badRateRow" value="${testListResultRow.badRate * 100}" maxFractionDigits="2"/>
                                                                            <span class="text-danger">
                                                                                ${badRateRow}%
                                                                            </span>
                                                                        </td>
                                                                        <td class="center">
                                                                            <fmt:formatNumber var="giniRow" value="${testListResultRow.giniResult * 100}" maxFractionDigits="2"/>
                                                                            <span class="text-info">
                                                                                ${giniRow}%
                                                                            </span>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                                </tbody>
                                                                <tfoot>
                                                                <tr>
                                                                    <td class="center"><span class="fa fa-envira"></span></td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            <span class="text-info">
                                                                                <spring:message code="scoringModelPage.textTableTotal"/>
                                                                            </span>
                                                                        </strong>
                                                                    </td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            ${testsModel.countTotalItems}
                                                                        </strong>
                                                                    </td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            ${testsModel.countGoodItems}
                                                                        </strong>
                                                                    </td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            ${testsModel.countBadItems}
                                                                        </strong>
                                                                    </td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            <span class="text-danger">
                                                                                ${badRateTotal}%
                                                                            </span>
                                                                        </strong>
                                                                    </td>
                                                                    <td class="center">
                                                                        <strong>
                                                                            <span class="text-info">
                                                                                ${giniIndexTotal}%
                                                                            </span>
                                                                        </strong>
                                                                    </td>
                                                                </tr>
                                                                </tfoot>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <div class="clearfix" style="padding-top: 40px"></div>
    </jsp:attribute>
</mytags:header-template>

</html>
