<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 25.02.2022
  Time: 13:31
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
                <h2><spring:message code="myProfilePage.myProfileChangeEmail"/></h2>
                <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="myProfilePage.profile"/> / <spring:message code="myProfilePage.myProfileChangeEmail"/> </h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/myprofile/profile/view" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col-lg-12">
            <form:form method="post" cssClass="form-horizontal edit-user-form-js" role="form" modelAttribute="userAccount">
                <div class="clearfix">
                    <div class="form-group">
                        <form:hidden path="username" readonly="true"></form:hidden>
                    </div>

                    <div class="col-lg-6">

                        <div class="form-group">
                            <label for="newEmail" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.newEmail"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="tempEmailDuringChange" type="text" cssClass="form-control not-empty required" id="newEmail" required=""></form:input>
                                <form:errors path="tempEmailDuringChange"></form:errors>
                                <c:if test="${not empty usernameError}">
                                    <p class="help-block error text-danger"><spring:message code="${usernameError}"/></p>
                                </c:if>
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="currentPassword" class="col-sm-4 control-label">
                                <spring:message code="myProfilePage.currentPassword"/>
                            </label>
                            <div class="col-sm-8">
                                <form:input path="password" type="password" cssClass="form-control not-empty required" id="currentPassword" required=""></form:input>
                                <form:errors path="password"></form:errors>
                                <c:if test="${not empty errorBadPassword}">
                                    <p class="help-block error text-danger"><spring:message code="${errorBadPassword}"/></p>
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
