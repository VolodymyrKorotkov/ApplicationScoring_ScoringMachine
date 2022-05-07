<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 17.03.2022
  Time: 18:37
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
                <h2><spring:message code="scoringModelPage.scoringModelAttributeValue"/></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelsListPage.modelListTitle"/> / <spring:message code="scoringModelPage.scoringModelTitle"/> / <spring:message code="scoringModelPage.scoringModelAttribute"/> / ${scoringModelAttribute.nameParameter} / <spring:message code="scoringModelPage.scoringModelAttributeValue"/> / ${scoringModelAttribute.title} / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/creating-model/scoring-model/${modelId}" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="scoringModelAttribute">
                <div class="clearfix">
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <span class="text-info"><spring:message code="scoringModelPage.textParameterName"/></span>
                            </div>
                            <div class="col-lg-12 view-data-key">
                                <span class="text-info">${scoringModelAttribute.nameParameter}</span>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <span class="text-warning"><spring:message code="scoringModelPage.textTableScoringModelParameterValue"/>:</span>
                            </div>
                            <div class="col-lg-12 view-data-key">
                                <span class="text-warning">${scoringModelAttribute.title}</span>
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterGoodCount"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.goodCount}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterBadCount"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.badCount}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterTotalCount"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.totalCount}
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="score" class="col-sm-8 control-label">
                                <span class="text-info"><spring:message code="scoringModelPage.textTableScoringModelParameterCountScores"/>:</span>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="score" type="number" cssClass="form-control not-empty required" required="" maxlength="100" id="score"></form:input>
                                <form:errors path="score"></form:errors>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterGoodRate"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.goodRate}%
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterBadRate"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.badRate}%
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterGoodPopulation"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.goodPopulationPercent}%
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterBadPopulation"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.badPopulationPercent}%
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterTotalPopulation"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.totalPopulationPercent}%
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterGiG"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.giG}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterBiB"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.biB}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterPgPb"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.pgPb}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textTableScoringModelParameterWoE"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.woe}
                            </div>
                        </div>
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="scoringModelPage.textScoringModelParameterInformationValue"/>:
                            </div>
                            <div class="col-lg-12 view-data-key">
                                    ${scoringModelAttribute.iv}
                            </div>
                        </div>
                    </div>

                <div class="clearfix"></div>
                <div class="hr-line-dashed"></div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-4">
                            <a class="btn btn-white btn-back" href="/creating-model/scoring-model/${modelId}"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
