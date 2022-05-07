<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 27.02.2022
  Time: 21:15
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
                <h2><spring:message code="createNewModelPage.createNewModelTitle"/></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="createNewModelPage.createNewModelTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>

            </div>

        </div>

        <c:if test="${countCurrentSavedModels >= countMaxPossibleSavedModels}">
            <div class="alert alert-warning alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="createNewModelPage.messageWarningWillDelModel"/>
            </div>
        </c:if>

        <div class="col-lg-12">
            <c:if test="${canBuildModel == 'true'}">
                <div class="col-lg-12" style="margin-bottom: 10px">
                    <div class="col-lg-6">
                                <label for="countCurrentSavedModels" class="col-sm-6 control-label">
                                    <spring:message code="modelCreationPage.textNameCountModelsNow"/>
                                </label>
                            <div class="col-sm-2" id="countCurrentSavedModels">
                                ${countCurrentSavedModels}
                            </div>
                    </div>
                    <div class="col-lg-6">
                        <label for="countLimitSavedModels" class="col-sm-10 control-label">
                            <spring:message code="modelCreationPage.textNameLimitModels"/>
                        </label>
                        <div class="col-sm-2" id="countLimitSavedModels">
                                ${countMaxPossibleSavedModels}
                        </div>
                    </div>
                </div>

                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="modelCreationPage.textForStartScoring"/>
                </p>

                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="<spring:message code="common.urlToLandingPage"/>/preparation-of-initial-data" target="_blank"><span class="fa fa-link"></span> <spring:message code="modelCreationPage.textInstructionLinkRequirementsForFile"/></a>
                </p>
                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="/creating-model/scoring-model-settings/view"><span class="fa fa-link"></span> <spring:message code="modelCreationPage.textCheckSettingsForBuildingModel"/></a>
                </p>
                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="/myprofile/mysubscription"><span class="fa fa-link"></span> <spring:message code="modelCreationPage.textCheckMySubscriptions"/></a>
                </p>
                <p class="text-box" style="margin-bottom: 10px">
                    <strong><spring:message code="modelCreationPage.textWaitModelAfterStart"/></strong>
                </p>
                <div class="hr-line-dashed" style="margin-bottom: 10px"></div>


                <form method="post" class="m-t" action="/upload-new-file-for-creating-model" enctype="multipart/form-data">
                    <div class="form-group" style="margin-bottom: 10px">
                        <table>
                            <td><b><spring:message code="modelCreationPage.textFile"/></b></td>
                            <td><input type="file" name="file"/></td>
                            <td>
                                <div class="col-sm-4">
                                    <button class="btn btn-primary" type="submit"><spring:message code="modelCreationPage.buttonBuildNewModel"/></button>
                                </div>
                            </td>
                        </table>
                        <c:if test="${not empty fileError}">
                            <p class="help-block error text-danger">
                                <spring:message code="${fileError}"/>
                            </p>
                        </c:if>
                    </div>
                </form>
            </c:if>



        </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
