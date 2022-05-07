<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 12.03.2022
  Time: 00:56
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
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelCreationSettingsPage.modelCreationSettingsTitle"/> / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/creating-model/scoring-model-settings/view" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="scoringSettingsModel">
                <div class="clearfix">

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="goodResult" class="col-sm-4 control-label">
                                <spring:message code="modelCreationSettingsPage.goodResultTitle"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="goodResult" type="text" cssClass="form-control not-empty required" id="goodResult" required="" maxlength="100"></form:input>
                                <form:errors path="goodResult"></form:errors>
                                <c:if test="${not empty goodResultError}">
                                    <p class="help-block error text-danger"><spring:message code="${goodResultError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="badResult" class="col-sm-4 control-label">
                                <spring:message code="modelCreationSettingsPage.badResultTitle"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="badResult" type="text" cssClass="form-control not-empty required" id="badResult" required="" maxlength="100"></form:input>
                                <form:errors path="badResult"></form:errors>
                                <c:if test="${not empty badResultError}">
                                    <p class="help-block error text-danger"><spring:message code="${badResultError}"/></p>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="qualityLevel" class="col-sm-4 control-label">
                                <spring:message code="modelCreationSettingsPage.modelQualityLevel"/>
                            </label>
                            <div class="col-sm-8">
                                <form:select path="modelQualityLevel" type="text" cssClass="form-control not-empty required chosen-select" required="" id="qualityLevel">
                                    <form:option value="LEVEL_1"><spring:message code="modelCreationSettingsPage.textLevel1"/></form:option>
                                    <form:option value="LEVEL_2"><spring:message code="modelCreationSettingsPage.textLevel2"/></form:option>
                                    <form:option value="LEVEL_3"><spring:message code="modelCreationSettingsPage.textLevel3"/></form:option>
                                    <form:option value="LEVEL_4"><spring:message code="modelCreationSettingsPage.textLevel4"/></form:option>
                                    <form:option value="LEVEL_5"><spring:message code="modelCreationSettingsPage.textLevel5"/></form:option>
                                </form:select>
                                <form:errors path="modelQualityLevel"></form:errors>
                                <c:if test="${not empty modelQualityLevelError}">
                                <p class="help-block error text-danger"><spring:message code="${modelQualityLevelError}"/></p>
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
                            <a class="btn btn-white btn-back" href="/creating-model/scoring-model-settings/view"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
