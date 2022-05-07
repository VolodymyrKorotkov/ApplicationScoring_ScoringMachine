<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 25.03.2022
  Time: 11:27
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
            <h2><spring:message code="testScoringModelPage.testScoringModelTitle"/></h2> / / <strong>${testScoringModel.id}</strong></h2>
            <h6><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/> / <spring:message code="scoringModelTesting.listModelsTestsPageTitle"/> / <spring:message code="testScoringModelPage.testScoringModelTitle"/> / ${testScoringModel.id} / <spring:message code="common.edit"/> </h6>
          </div>
          <div class="col-lg-5">
            <div class="title-action">
              <a href="/testing-model/test-scoring-model/${testScoringModel.id}" class="btn btn-default">
                <spring:message code="common.backButton"/>
              </a>
            </div>
          </div>
        </div>
        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="testScoringModel">
                <div class="clearfix">

                  <div class="col-lg-6">

                    <div class="form-group">
                      <label for="title" class="col-sm-4 control-label">
                        <spring:message code="testingScoringModelsListPage.fieldTestingModelTitle"/>:
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
                        <spring:message code="testScoringModelPage.titleDescriptionTest"/>
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
                      <a class="btn btn-white btn-back" href="/testing-model/test-scoring-model/${testScoringModel.id}"><spring:message code="common.cancelButton"/></a>
                      <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                    </div>
                  </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
