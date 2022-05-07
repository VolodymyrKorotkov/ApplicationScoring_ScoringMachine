<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 21.02.2022
  Time: 21:55
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
                <h2><spring:message code="myProfilePage.myProfileEdit"/></h2>
                <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="myProfilePage.profile"/> / <spring:message code="common.edit"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/myprofile/profile/view" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-danger alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${message}"/>
            </div>
        </c:if>

        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="userAccount">
                <div class="clearfix">
                    <div class="form-group">
                            <form:hidden path="username" readonly="true"></form:hidden>
                    </div>

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="firstName" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.firstName"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="firstName" type="text" cssClass="form-control not-empty required fioMask" id="firstName" required="" maxlength="100"></form:input>
                                <form:errors path="firstName"></form:errors>
                                <c:if test="${not empty firstNameError}">
                                    <p class="help-block error text-danger"><spring:message code="${firstNameError}"/></p>
                                </c:if>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="lastName" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.lastName"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="lastName" type="text" cssClass="form-control not-empty required fioMask" id="lastName" required="" maxlength="100"></form:input>
                                <form:errors path="lastName"></form:errors>
                                <c:if test="${not empty lastNameError}">
                                    <p class="help-block error text-danger"><spring:message code="${lastNameError}"/></p>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="form-group">
                            <label for="birthDate" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.dateOfBirth"/>
                            </label>
                            <div class="col-sm-8">
                                <div class="input-group date">
                                    <span class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </span>
                                    <form:input path="dateOfBirth" type="date" cssClass="form-control date-mask" id="birthDate" required=""></form:input>
                                    <form:errors path="dateOfBirth"></form:errors>
                                    <c:if test="${not empty birthDateError}">
                                    <p class="help-block error text-danger"><spring:message code="${birthDateError}"/></p>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="gender" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.gender"/>
                            </label>
                            <div class="col-sm-8">
                                <form:select path="gender" type="text" cssClass="form-control not-empty required chosen-select" required="" id="gender">
                                    <c:set var="gender" value="${userAccount.gender}"/>
                                    <c:if test="${empty gender}">
                                        <form:option value=""><spring:message code="common.selectValue"/></form:option>
                                    </c:if>
                                    <form:option value="MALE"><spring:message code="MALE"/></form:option>
                                    <form:option value="FEMALE"><spring:message code="FEMALE"/></form:option>
                                </form:select>
                                <form:errors path="gender"></form:errors>
                                <c:if test="${not empty genderError}">
                                <p class="help-block error text-danger"><spring:message code="${genderError}"/></p>
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
                            <a class="btn btn-white btn-back" href="/myprofile/profile/view"><spring:message code="common.cancelButton"/></a>
                            <button class="btn btn-primary" type="submit"><spring:message code="common.saveButton"/></button>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>



    </jsp:attribute>
</mytags:header-template>

</html>
