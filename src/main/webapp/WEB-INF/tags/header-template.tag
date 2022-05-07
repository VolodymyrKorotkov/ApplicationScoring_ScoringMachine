<%@ tag import="java.time.LocalDateTime" %>
<%@ tag import="java.time.ZoneId" %>
<%@ tag import="static com.korotkov.main.config.PortalConstants.TIME_ZONE" %>
<%@tag description="Template Header tag" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="contentPage" fragment="true" %>

<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title><spring:message code = "company.name"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<%--    <link href="${pageContext.request.contextPath}/res/css/bootstrap.min.css}" rel="stylesheet">--%>
    <link href="${pageContext.request.contextPath}/res/css/font-awesome/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/dataTables/datatables.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/summernote/summernote.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/summernote/summernote-bs3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/datepicker/datepicker3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/filestyle/jquery-filestyle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/plugins/footable.bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/res/css/tables.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/custom-style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/css/verification.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/res/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/collapse.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/transition.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/flot/jquery.flot.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/flot/jquery.flot.spline.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/flot/jquery.flot.resize.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/flot/jquery.flot.pie.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/peity/jquery.peity.min.js"></script>
<%--    <!--    <script th:src="@{/js/demo/peity-demo.js}"></script>-->--%>
    <script src="${pageContext.request.contextPath}/res/js/inspinia.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/pace/pace.min.js"></script>
    <!--<script th:src="@{/js/plugins/jquery-ui/jquery-ui.min.js}"></script>-->
    <script src="${pageContext.request.contextPath}/res/js/plugins/gritter/jquery.gritter.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/chartJs/Chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/toastr/toastr.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/iCheck/icheck.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/chosen/chosen-readonly.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/moment/moment-with-locales-2.24.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/filestyle/jquery-filestyle.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/switchery/switchery.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/blueimp-file-upload/jquery.ui.widget.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/blueimp-file-upload/jquery.iframe-transport.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/blueimp-file-upload/jquery.fileupload.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/blueimp-file-upload/jquery.fileupload-process.min.js"></script>

    <!--<script th:src="@{/js/plugins/jasny/jasny-bootstrap.min.js}"></script>-->
    <script src="${pageContext.request.contextPath}/res/js/jquery.mask.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/jquery.inputmask.bundle.min.js"></script>
<%--    <script src="${pageContext.request.contextPath}/res/js/mask-classes.js"></script>--%>
<%--    <script src="${pageContext.request.contextPath}/res/js/validation-mesages.js"></script>--%>
    <script src="${pageContext.request.contextPath}/res/js/plugins/summernote/summernote.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/summernote/lang/summernote-vi.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/plugins/chartJs/Chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/res/js/tables.js"></script>
<%--    <script src="${pageContext.request.contextPath}/res/js/forms.js"></script>--%>
    <!--    <script th:src="@{/js/charts.js}"></script>-->
    <script src="${pageContext.request.contextPath}/res/js/js.cookie.js"></script>
    <!--    <script th:src="@{/js/bpmn-viewer.production.min.js}"></script>-->
<%--    <script src="${pageContext.request.contextPath}/res/js/bpmn-navigated-viewer.min.js"></script>--%>
    <script src="${pageContext.request.contextPath}/res/js/footable.min.js"></script>
</head>
<body>
<div id = "wrapper">

    <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="slidebar-collapse">
                <ul class="nav metismenu" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                                 <span class="clear">
                                     <span class="block m-t-xs"> <strong class="font-bold">Hello,</strong> </span>
                                     <span class="text-muted text-xs block">
                                         <security:authentication property="principal.firstName" var="firstName"/>
                                         <security:authentication property="principal.lastName" var="lastName"/>
                                         <c:if test="${firstName != null && lastName != null}">
                                             ${firstName} ${lastName}
                                             <br/>
                                         </c:if>
                                         <security:authentication property="principal.username"/>
                                     </span>
                                    </span>
                        </div>
                        <div class="logo-element">
                            <spring:message code="company.shortName"/>
                        </div>
                    </li>

                    <li>
                        <a href="<c:url value="/"/>"><i class="fa fa-book"></i>
                            <span class="nav-label"><spring:message code="welcomePage.welcomeTitle"/></span></a>
                    </li>

                    <li>
                        <a>
                            <i class="fa fa-address-card"></i>
                            <span class="nav-label"><spring:message code="myProfilePage.myProfile"/></span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="<c:url value="/myprofile/profile/view"/>"><i class="fa fa-user"></i> <spring:message code="myProfilePage.myProfile"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/myprofile/mysubscription"/>"><i class="fa fa-paper-plane"></i> <spring:message code="subscriptionPage.title"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/myprofile/payments"/>"><i class="fa fa-dollar"></i> <spring:message code="paymentHistoryPage.paymentHistoryTitle"/></a>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a>
                            <i class="fa fa-linode"></i>
                            <span class="nav-label"><spring:message code="modelCreationPage.modelCreationTitle"/></span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="<c:url value="/creating-model/creating-new-model"/>"><i class="fa fa-linode"></i> <spring:message code="createNewModelPage.createNewModelTitle"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/creating-model/scoring-model-list"/>"><i class="fa fa-list"></i> <spring:message code="modelsListPage.modelListTitle"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/creating-model/scoring-model-settings/view"/>"><i class="fa fa-cog"></i> <spring:message code="modelCreationSettingsPage.modelCreationSettingsTitle"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/creating-model/scoring-model-advanced-settings/view"/>"><i class="fa fa-cogs"></i> <spring:message code="modelCreationAdvancedSettingsPage.modelCreationSettingsTitle"/></a>
                            </li>
                        </ul>
                    </li>

                    <li>
                        <a>
                            <i class="fa fa-bar-chart"></i>
                            <span class="nav-label"><spring:message code="scoringModelTesting.scoringModelTestingPageTitle"/></span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level collapse">
                            <li>
                                <a href="<c:url value="/testing-model/creating-new-test"/>"><i class="fa fa-plus-square"></i> <spring:message code="scoringModelTesting.createNewTestPageTitle"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/testing-model/scoring-model-test-list"/>"><i class="fa fa-list"></i> <spring:message code="scoringModelTesting.listModelsTestsPageTitle"/></a>
                            </li>
                            <li>
                                <a href="<c:url value="/testing-model/scoring-model-test-settings/view"/>"><i class="fa fa-cog"></i> <spring:message code="scoringModelTesting.scoringModelTestSettingsPageTitle"/></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
    </nav>

    <div id="page-wrapper" class="white-bg dashbard-1">

        <div class="row border-bottom">
            <div>
                <nav class="navbar navbar-static-top gray-bg-forced" role="navigation" style="margin-bottom: 0px">
                    <div class="navbar-header">
                        <a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#"><i class="fa fa-bars"></i> </a>
                    </div>

                    <ul class="nav navbar-top-links navbar-left" style="margin-left: 30px; margin-top: 10px;">
                        <li class="text-muted">
                            <span></span>
                        </li>
                        <li class="text-muted">
                            <a href="<%=request.getContextPath()%>?languageVar=en">English</a>
                        </li>
                        <li class="text-muted">
                            <span>|</span>
                        </li>
                        <li class="text-muted">
                            <a href="<%=request.getContextPath()%>?languageVar=ru">Русский</a>
                        </li>
                        <li class="text-muted">
                            <span>|</span>
                        </li>
                        <li class="text-muted">
                            <a href="<%=request.getContextPath()%>?languageVar=ua">Українська</a>
                        </li>
                    </ul>


                    <ul class="nav navbar-top-links navbar-right">

                        <li class="hidden-xs">
                            <c:set value="<%=java.time.LocalDateTime.now(ZoneId.of(TIME_ZONE))%>" var="currentTime"/>
                            <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="currDate" value="${currentTime}" type="both"/>
                            <spring:message code="header.currentTimeZone"/>
                            <span>
                                <fmt:formatDate value="${currDate}" pattern="dd.MM.yyyy HH:mm"/>
                            </span>
                        </li>

                        <li class="hidden-sm hidden-md hidden-lg pull-right">
                            <a href="<c:url value="/security/logout"/>"><i class="fa fa-sign-out"></i><spring:message code="common.logoutButton"/></a>
                        </li>

                        <li class="hidden-xs">
                            <a href="<c:url value="/security/logout"/>"><i class="fa fa-sign-out"></i><spring:message code="common.logoutButton"/></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

        <div class="content-section row wrapper wrapper-content">
            <div class="clearfix" style="padding-left: 8px; padding-right: 8px;" fragment="content">
                <jsp:invoke fragment="contentPage"/>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="footer fixed">
                <div class="pull-right">
                    System Version: <strong>1.0</strong>
                </div>
                <div>
                    <spring:message code="company.name"/> &copy; 2022
                    <span class="fa fa-angle-double-right"></span>
                    <a href="mailto: volodymyr.korotkov@icloud.com"><spring:message code="common.textSupportEmail"/></a>
                </div>
            </div>
        </div>
    </div>


</div>

</body>
</html>