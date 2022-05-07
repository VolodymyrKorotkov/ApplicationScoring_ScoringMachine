<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 11.03.2022
  Time: 00:38
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
                <h2><spring:message code="modelCreationSettingsPage.modelCreationSettingsTitle"/></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelCreationSettingsPage.modelCreationSettingsTitle"/> / <spring:message code="common.view"/> </h6>
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
                                <a href="/creating-model/scoring-model-settings/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/creating-model/scoring-model-settings/restore-default-settings">
                                    <spring:message code="modelCreationSettingsPage.buttonRestoreDefaultSettings"/>
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
            <p class="text-box" style="margin-bottom: 10px">
                <spring:message code="modelCreationSettingsPage.textAboutScoringSettingsPage"/>
            </p>
            <p class="text-box client-link" style="margin-bottom: 10px">
                <a href="<spring:message code="common.urlToLandingPage"/>/creating-scoring-model" target="_blank"><span class="fa fa-link"></span> <spring:message code="modelCreationSettingsPage.textInstructionLinkAboutSettings"/></a>
            </p>
            <div class="hr-line-dashed" style="margin-bottom: 10px"></div>

            <div class="col-lg-12">
                <div class="col-sm-4 col-view">
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-lg-12 view-data-title">
                            <spring:message code="modelCreationSettingsPage.goodResultTitle"/>
                        </div>
                        <div class="text-box col-lg-12 view-data-key">
                                ${scoringSettingsModel.goodResult}
                        </div>
                    </div>

                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-lg-12 view-data-title">
                            <spring:message code="modelCreationSettingsPage.badResultTitle"/>
                        </div>
                        <div class="col-lg-12 view-data-key">
                                ${scoringSettingsModel.badResult}
                        </div>
                    </div>
                </div>

                <div class="col-sm-4 col-view">
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-lg-12 view-data-title">
                            <spring:message code="modelCreationSettingsPage.modelQualityLevel"/>
                        </div>
                        <div class="col-lg-12 view-data-key">
                            <c:set var="modelQualityLevel" value="${scoringSettingsModel.modelQualityLevel}"/>
                            <c:if test="${modelQualityLevel == 'LEVEL_1'}">
                            <spring:message code="modelCreationSettingsPage.textLevel1"/>
                        </c:if>
                            <c:if test="${modelQualityLevel == 'LEVEL_2'}">
                            <spring:message code="modelCreationSettingsPage.textLevel2"/>
                        </c:if>
                            <c:if test="${modelQualityLevel == 'LEVEL_3'}">
                            <spring:message code="modelCreationSettingsPage.textLevel3"/>
                        </c:if>
                            <c:if test="${modelQualityLevel == 'LEVEL_4'}">
                            <spring:message code="modelCreationSettingsPage.textLevel4"/>
                        </c:if>
                            <c:if test="${modelQualityLevel == 'LEVEL_5'}">
                            <spring:message code="modelCreationSettingsPage.textLevel5"/>
                        </c:if>
                            <c:if test="${modelQualityLevel == 'LEVEL_CUSTOM'}">
                            <spring:message code="modelCreationSettingsPage.textLevelCustom"/>
                        </c:if>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
