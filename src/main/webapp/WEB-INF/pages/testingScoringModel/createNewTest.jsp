<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 19.03.2022
  Time: 22:24
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
                <h2><spring:message code="scoringModelTesting.createNewTestPageTitle"/></h2>
                <h6><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/> / <spring:message code="scoringModelTesting.createNewTestPageTitle"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>

            </div>

        </div>

        <c:if test="${countCurrentSavedTests >= countMaxPossibleSavedTests}">
            <div class="alert alert-warning alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="createNewTestPage.messageWarningWillDelTest"/>
            </div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${message}"/>
            </div>
        </c:if>

        <div class="col-lg-12">
            <c:if test="${canBuildTestModel == 'true'}">
                <div class="col-lg-12" style="margin-bottom: 10px">
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="creatingTestModelPage.textScoringModelUsingForTestTitle"/>
                            </div>
                            <div class="col-lg-12 view-data-key">
                                <p class="client-link">
                                    <a href="/creating-model/scoring-model/${scoringModelForTest.id}"><span class="fa fa-link"></span> ${scoringModelForTest.title}</a>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="creatingTestModelPage.textCountCurrentSavedTestsTitle"/>
                            </div>
                            <div class="col-lg-12 view-data-key">
                                ${countCurrentSavedTests}
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-view">
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-lg-12 view-data-title">
                                <spring:message code="creatingTestModelPage.textCountMaxPossibleSavedTestsTitle"/>
                            </div>
                            <div class="col-lg-12 view-data-key">
                                ${countMaxPossibleSavedTests}
                            </div>
                        </div>
                    </div>
                </div>

                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="creatingTestModelPage.textForStartTesting"/>
                </p>

                <p class="text-box client-link" style="margin-bottom: 10px">
                    <a href="/myprofile/mysubscription"><span class="fa fa-link"></span> <spring:message code="modelCreationPage.textCheckMySubscriptions"/></a>
                </p>
                <p class="text-box" style="margin-bottom: 10px">
                    <strong><spring:message code="creatingTestModelPage.textWaitModelAfterStart"/></strong>
                </p>
                <div class="hr-line-dashed" style="margin-bottom: 10px"></div>


                <form method="post" class="m-t" action="/upload-new-file-for-creating-test" enctype="multipart/form-data">
                    <div class="form-group" style="margin-bottom: 10px">
                        <table>
                            <td><b><spring:message code="modelCreationPage.textFile"/></b></td>
                            <td><input type="file" name="file"/></td>
                            <td>
                                <div class="col-sm-4">
                                    <button class="btn btn-primary" type="submit"><spring:message code="creatingTestModelPage.buttonBuildNewTest"/></button>
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

            <c:if test="${canBuildTestModel == 'false'}">
                <p class="text-box" style="margin-bottom: 10px">
                    <spring:message code="creatingTestModelPage.textTestingInProcess"/>
                </p>
            </c:if>



        </div>


        </div>
    </jsp:attribute>
</mytags:header-template>

</html>
