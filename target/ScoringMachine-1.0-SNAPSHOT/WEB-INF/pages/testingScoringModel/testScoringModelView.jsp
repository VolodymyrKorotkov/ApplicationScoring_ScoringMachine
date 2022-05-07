<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 24.03.2022
  Time: 00:40
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
            <h2><spring:message code="testScoringModelPage.testScoringModelTitle"/> / <strong>${testScoringModel.title}</strong></h2>
            <h6><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/> / <spring:message code="scoringModelTesting.listModelsTestsPageTitle"/> / <spring:message code="testScoringModelPage.testScoringModelTitle"/> / ${testScoringModel.title} / <spring:message code="common.view"/> </h6>
          </div>
          <div class="col-lg-5">
            <div class="title-action">
              <a href="/testing-model/scoring-model-test-list" class="btn btn-default">
                <spring:message code="common.backButton"/>
              </a>
              <div class="btn-group">
                <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                  <spring:message code="common.actionsButton"/> <span class="caret"></span>
                </button>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li>
                    <a href="/testing-model/test-scoring-model/${testScoringModel.id}/edit">
                      <spring:message code="testScoringModelPage.buttonActionsEditTest"/>
                    </a>
                  </li>
                  <li>
                    <a href="/testing-model/test-scoring-model/${testScoringModel.id}/export-to-excel">
                      <spring:message code="testScoringModelPage.buttonExportToExcelTest"/>
                    </a>
                  </li>
                  <li>
                    <a href="/testing-model/test-scoring-model/${testScoringModel.id}/delete">
                      <spring:message code="testScoringModelPage.buttonActionsDeleteTest"/>
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
      <div class="col-lg-12" style="margin-bottom: 10px;">
        <div class="col-sm-4 col-view">
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="testingScoringModelsListPage.fieldScoringModelTitle"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              <p class="client-link">
                <a href="/creating-model/scoring-model/${testScoringModel.scoringModel.id}"><span class="fa fa-link"></span> ${testScoringModel.scoringModel.title}</a>
              </p>
            </div>
          </div>
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="testingScoringModelsListPage.fieldTestingModelTitle"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              ${testScoringModel.title}
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-view">
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="testingScoringModelsListPage.fieldGiniIndex"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              <fmt:formatNumber var="gini" value="${testScoringModel.giniIndex * 100}" maxFractionDigits="2"/>
              <strong><span class="text-info">${gini}%</span></strong>
            </div>
          </div>
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="testScoringModelPage.titleDescriptionTest"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              <c:if test="${empty testScoringModel.description}">-</c:if>
              <c:if test="${not empty testScoringModel.description}">
                ${testScoringModel.description}
              </c:if>
            </div>
          </div>
        </div>
        <div class="col-sm-4 col-view">
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="modelsListPage.fieldCreatedAt"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="createdAt" value="${testScoringModel.createdAt}" type="both"/>
              <fmt:formatDate value="${createdAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </div>
          </div>
          <div class="row" style="margin-bottom: 10px;">
            <div class="col-lg-12 view-data-title">
              <spring:message code="modelsListPage.fieldLastModifiedAt"/>:
            </div>
            <div class="col-lg-12 view-data-key">
              <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="lastModifiedAt" value="${testScoringModel.lastModifiedAt}" type="both"/>
              <fmt:formatDate value="${lastModifiedAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </div>
          </div>
        </div>
      </div>

      <div class="text-box" style="margin-bottom: 10px;">
        <spring:message code="testScoringModelPage.textAboutTableOfResultTest"/>
      </div>
      <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

      <div class="container-scroll">
        <table class="table table-bordered table-hover table-pageable" style="font-size: small; width: 1700px;">
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
              <spring:message code="testScoringModelPage.fieldTextCumItemsCount"/>
            </th>
            <th class="center">
              <spring:message code="testScoringModelPage.fieldTextCumItemsPercent"/>
            </th>
            <th class="center">
              <spring:message code="testScoringModelPage.fieldTextCumGoodCount"/>
            </th>
            <th class="center">
              <spring:message code="testScoringModelPage.fieldTextCumGoodPercent"/>
            </th>
            <th class="center">
              <spring:message code="testScoringModelPage.fieldTextCumBadCount"/>
            </th>
            <th class="center">
              <spring:message code="testScoringModelPage.fieldTextCumBadPercent"/>
            </th>
            <th class="center">
              <span class="text-info"><spring:message code="testScoringModelPage.fieldTextGiniResult"/></span>
            </th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="testResultRow" items="${listTestResultRows}" varStatus="items">
            <tr>
              <td class="center">${items.index + 1}</td>
              <td class="center">
                <span class="text-info">
                  ${testResultRow.score}
                </span>
              </td>
              <td class="center">
                ${testResultRow.countTotalItems}
              </td>
              <td class="center">
                ${testResultRow.countGoodItems}
              </td>
              <td class="center">
                ${testResultRow.countBadItems}
              </td>
              <td class="center">
                <fmt:formatNumber var="badRate" value="${testResultRow.badRate * 100}" maxFractionDigits="2"/>
                <span class="text-danger">
                  ${badRate}%
                </span>
              </td>
              <td class="center">
                ${testResultRow.cumTotalItemsCount}
              </td>
              <td class="center">
                <fmt:formatNumber var="cumTotal" value="${testResultRow.cumTotalItemsPercent * 100}" maxFractionDigits="2"/>
                ${cumTotal}%
              </td>
              <td class="center">
                ${testResultRow.cumGoodItemsCount}
              </td>
              <td class="center">
                <fmt:formatNumber var="cumGood" value="${testResultRow.cumGoodItemsPercent * 100}" maxFractionDigits="2"/>
                ${cumGood}%
              </td>
              <td class="center">
                ${testResultRow.cumBadItemsCount}
              </td>
              <td class="center">
                <fmt:formatNumber var="cumBad" value="${testResultRow.cumBadItemsPercent * 100}" maxFractionDigits="2"/>
                ${cumBad}%
              </td>
              <td class="center">
                <fmt:formatNumber var="giniIndex" value="${testResultRow.giniResult * 100}" maxFractionDigits="2"/>
                <span class="text-info">
                  ${giniIndex}%
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
                ${totalTestResult.countTotalItems}
              </strong>
            </td>
            <td class="center">
              <strong>
                ${totalTestResult.countGoodItems}
              </strong>
            </td>
            <td class="center">
              <strong>
                ${totalTestResult.countBadItems}
              </strong>
            </td>
            <td class="center">
              <fmt:formatNumber var="totalBadRate" value="${totalTestResult.badRate * 100}" maxFractionDigits="2"/>
              <strong>
                <span class="text-danger">
                  ${totalBadRate}%
                </span>
              </strong>
            </td>
            <td class="center"></td>
            <td class="center"></td>
            <td class="center"></td>
            <td class="center"></td>
            <td class="center"></td>
            <td class="center"></td>
            <td class="center">
              <fmt:formatNumber var="totalGini" value="${totalTestResult.giniResult * 100}" maxFractionDigits="2"/>
              <strong>
                <span class="text-info">
                  ${totalGini}%
                </span>
              </strong>
            </td>
          </tr>
          </tfoot>
        </table>
      </div>

        <div class="clearfix" style="padding-top: 40px"></div>
    </jsp:attribute>
</mytags:header-template>

</html>
