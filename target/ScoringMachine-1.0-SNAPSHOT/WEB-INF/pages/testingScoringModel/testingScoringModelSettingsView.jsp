<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 19.03.2022
  Time: 16:36
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
                <h2><spring:message code="scoringModelTesting.scoringModelTestSettingsPageTitle"/></h2>
                <h6><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/> / <spring:message code="scoringModelTesting.scoringModelTestSettingsPageTitle"/> / <spring:message code="common.view"/> </h6>
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
                                <a href="/testing-model/scoring-model-test-settings/edit">
                                    <spring:message code="common.editButton"/>
                                </a>
                            </li>
                            <li>
                                <a href="/testing-model/scoring-model-test-settings/restore-default-settings">
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
                <spring:message code="scoringModelTestingSettingsPage.textAboutScoringSettingsPage"/>
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
                            <spring:message code="testingScoringModelSettingsPage.numberWishedRowsFoCalcTestModelTitle"/>
                        </div>
                        <div class="col-lg-12 view-data-key">
                            ${scoringSettingsModel.numberWishedRowsForCalcTestModel}
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
