<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 16.03.2022
  Time: 22:13
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
                <h2><spring:message code="scoringModelPage.scoringModelTitle"/></h2> / / <strong>${scoringModel.title}</strong></h2>
                <h6><spring:message code="modelCreationPage.modelCreationTitle"/> / <spring:message code="modelsListPage.modelListTitle"/> / <spring:message code="scoringModelPage.scoringModelTitle"/> / ${scoringModel.title} / <spring:message code="common.edit"/> </h6>
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
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="scoringModel">
                <div class="clearfix">

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="title" class="col-sm-4 control-label">
                                <spring:message code="scoringModelPage.textScoringModelTitle"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="title" type="text" cssClass="form-control not-empty required" id="title" required=""></form:input>
                                <form:errors path="title"></form:errors>
                                <c:if test="${not empty titleError}">
                                    <p class="help-block error text-danger"><spring:message code="${titleError}"/></p>
                                </c:if>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="description" class="col-sm-4 control-label">
                                <spring:message code="scoringModelPage.textScoringModelDescription"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="description" type="text" cssClass="form-control not-empty required" id="description" required=""></form:input>
                                <form:errors path="description"></form:errors>
                            </div>
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
