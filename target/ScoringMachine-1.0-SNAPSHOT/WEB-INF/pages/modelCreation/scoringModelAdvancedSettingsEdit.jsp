<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 18.03.2022
  Time: 14:58
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
                <h2><spring:message code="modelCreationAdvancedSettingsPage.modelCreationSettingsTitle"/></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelCreationAdvancedSettingsPage.modelCreationSettingsTitle"/> / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/creating-model/scoring-model-advanced-settings/view" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="scoringSettingsModel">
                <div class="clearfix">

                    <div class="col-lg-4">
                        <div class="form-group">
                            <label for="goodResult" class="col-sm-8 control-label">
                                <spring:message code="modelCreationSettingsPage.goodResultTitle"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="goodResult" type="text" cssClass="form-control not-empty required" id="goodResult" required="" maxlength="100"></form:input>
                                <form:errors path="goodResult"></form:errors>
                                <c:if test="${not empty goodResultError}">
                                    <p class="help-block error text-danger"><spring:message code="${goodResultError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="badResult" class="col-sm-8 control-label">
                                <spring:message code="modelCreationSettingsPage.badResultTitle"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="badResult" type="text" cssClass="form-control not-empty required" id="badResult" required="" maxlength="100"></form:input>
                                <form:errors path="badResult"></form:errors>
                                <c:if test="${not empty badResultError}">
                                    <p class="help-block error text-danger"><spring:message code="${badResultError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="factor" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.factor"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="factor" type="number" min="1" cssClass="form-control not-empty required" id="factor" required="" maxlength="100"></form:input>
                                <form:errors path="factor"></form:errors>
                                <c:if test="${not empty factorError}">
                                    <p class="help-block error text-danger"><spring:message code="${factorError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="offset" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.offset"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="offset" type="number" min="0" cssClass="form-control not-empty required" id="offset" required="" maxlength="100"></form:input>
                                <form:errors path="offset"></form:errors>
                                <c:if test="${not empty offsetError}">
                                    <p class="help-block error text-danger"><spring:message code="${offsetError}"/></p>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-8">
                        <div class="form-group">
                            <label for="minimumNeededIVForParameterOne" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.minimumNeededIVForParameterOne"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="minimumNeededIVForParameterOne" type="number" min="0" step="any" cssClass="form-control not-empty required" id="minimumNeededIVForParameterOne" required="" maxlength="100"></form:input>
                                <form:errors path="minimumNeededIVForParameterOne"></form:errors>
                                <c:if test="${not empty minIvOneValueError}">
                                    <p class="help-block error text-danger"><spring:message code="${minIvOneValueError}"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="minimumNeededAverageIVForKyeParameterOne" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.minimumNeededAverageIVForKyeParameterOne"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="minimumNeededAverageIVForKeyOfParameterOne" type="number" min="0" step="any" cssClass="form-control not-empty required" id="minimumNeededAverageIVForKyeParameterOne" required="" maxlength="100"></form:input>
                                <form:errors path="minimumNeededAverageIVForKeyOfParameterOne"></form:errors>
                                <c:if test="${not empty minIvAverageOneValueError}">
                                    <p class="help-block error text-danger"><spring:message code="${minIvAverageOneValueError}"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="minimumNeededIVForParameterTwo" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.minimumNeededIVForParameterTwo"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="minimumNeededIVForParameterTwo" type="number" min="0" step="any" cssClass="form-control not-empty required" id="minimumNeededIVForParameterTwo" required="" maxlength="100"></form:input>
                                <form:errors path="minimumNeededIVForParameterTwo"></form:errors>
                                <c:if test="${not empty minIvTwoValueError}">
                                    <p class="help-block error text-danger"><spring:message code="${minIvTwoValueError}"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="minimumNeededAverageIVForKyeParameterTwo" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.minimumNeededAverageIVForKyeParameterTwo"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="minimumNeededAverageIVForKeyOfParameterTwo" type="number" min="0" step="any" cssClass="form-control not-empty required" id="minimumNeededAverageIVForKyeParameterTwo" required="" maxlength="100"></form:input>
                                <form:errors path="minimumNeededAverageIVForKeyOfParameterTwo"></form:errors>
                                <c:if test="${not empty minIvAverageTwoValueError}">
                                    <p class="help-block error text-danger"><spring:message code="${minIvAverageTwoValueError}"/></p>
                                </c:if>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="maxRowsForInfluenceParameterTwo" class="col-sm-8 control-label">
                                <spring:message code="modelCreationAdvancedSettingsPage.maxRowsForInfluenceParameterTwo"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input path="maxRowsForInfluenceParameterTwo" type="number" min="10" cssClass="form-control not-empty required" id="maxRowsForInfluenceParameterTwo" required="" maxlength="100"></form:input>
                                <form:errors path="maxRowsForInfluenceParameterTwo"></form:errors>
                                <c:if test="${not empty maxRowsTwoError}">
                                    <p class="help-block error text-danger"><spring:message code="${maxRowsTwoError}"/></p>
                                </c:if>
                            </div>
                        </div>
                    </div>


                </div>

                <div class="clearfix"></div>
                <div class="hr-line-dashed"></div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <div class="col-sm-8 col-sm-offset-4">
                            <a class="btn btn-white btn-back" href="/creating-model/scoring-model-advanced-settings/view"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
