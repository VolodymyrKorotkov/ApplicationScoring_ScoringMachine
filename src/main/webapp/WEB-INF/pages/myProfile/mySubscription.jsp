<%--
  Created by IntelliJ IDEA.
  User: vladimirkorotkov
  Date: 30.03.2022
  Time: 13:11
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
                <h2><spring:message code="subscriptionPage.title"/></h2>
                <h6><spring:message code="myProfilePage.myProfile"/> / <spring:message code="subscriptionPage.title"/></h6>
            </div>
            <div class="col-lg-5">
                <div class="title-action">
                    <a href="/" class="btn btn-default">
                        <spring:message code="common.backButton"/>
                    </a>
                </div>

            </div>

        </div>

        <c:if test="${not empty badMessage}">
            <div class="alert alert-danger alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${badMessage}"/>
            </div>
        </c:if>

        <c:if test="${not empty goodMessage}">
            <div class="alert alert-success alert-dismissable">
                <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>
                <spring:message code="${goodMessage}"/>
            </div>
        </c:if>


      <div class="col-lg-12" style="margin-bottom: 10px;">
          <div class="text-box" style="margin-bottom: 10px;">
              <spring:message code="mySubscriptionPage.textWelcomeToPage"/>
          </div>
          <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>
          <div class="tabs-container" style="margin-bottom: 20px;">
              <div class="tabs">
                  <div class="panel-group" id="accordionUserRole" role="tablist" aria-multiselectable="true">
                      <div class="panel panel-default">
                          <div class="panel-heading" role="tab" id="headingUserRole">
                              <h4 class="panel-title">
                                  <a role="button" data-toggle="collapse" data-target="#collapseUserRole" aria-expanded="true" aria-controls="collapseUserRole" class="collapsed">
                                      <span class="fa fa-tags"></span> <span class="text-danger"><spring:message code="mySubscriptionPage.textCurrentSubscriptions"/>:</span> <span class="text-info"><spring:message code="${currentRole.name}"/></span>.
                                      <c:set var="subscriptionExpiredAt" value="${subscriptionActiveUntil}"/>
                                      <c:if test="${not empty subscriptionExpiredAt}">
                                          <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                          <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                          <span>
                                              <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                          </span>
                                      </c:if>
                                  </a>
                              </h4>
                          </div>
                          <div id="collapseUserRole" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="headingUserRole">
                              <div class="panel-body">
                                  <c:if test="${currentRole.name == 'LEVEL_ONE'}">
                                      <div class="heading" style="margin-bottom: 10px;">
                                          <h3>
                                              <strong>
                                                  <span class="text-info">
                                                      <spring:message code="${currentRole.name}"/>
                                                  </span>
                                              </strong>
                                          </h3>
                                      </div>
                                      <c:if test="${not empty subscriptionExpiredAt}">
                                      <div class="text-box" style="margin-bottom: 20px;">
                                          <h5 class="text-danger">
                                              <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                          <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                          <span>
                                              <strong>
                                                  <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                              </strong>
                                          </span>
                                          </h5>
                                      </div>
                                      </c:if>
                                      <div class="text-box">
                                          <spring:message code="mySubscriptionPage.descriptionLevelOne"/>
                                      </div>
                                  </c:if>
                                  <c:if test="${currentRole.name == 'LEVEL_TWO'}">
                                      <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 20px;">
                                          <div class="col-lg-8">
                                              <h3 class="text-info" style="margin-bottom: 10px;">
                                                  <strong>
                                                      <spring:message code="${currentRole.name}"/>
                                                  </strong>
                                              </h3>
                                              <h5 class="text-danger" style="margin-bottom: 10px;">
                                                  <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                                  <c:if test="${empty subscriptionExpiredAt}">∞</c:if>
                                                  <c:if test="${not empty subscriptionExpiredAt}">
                                                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                                      <strong>
                                                          <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                      </strong>
                                                  </c:if>
                                              </h5>
                                          </div>
                                          <div class="col-lg-4">
                                              <div style="float: right">
                                                  <div class="btn-group">
                                                      <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                                          <spring:message code="mySubscriptionPage.buttonRenewSubscription"/> <span class="caret"></span>
                                                      </button>
                                                      <ul class="dropdown-menu dropdown-menu-right">
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_MONTH">
                                                                  <spring:message code="mySubscription.buttonRenewOneMonth"/> $${currentRole.priceOneMonth}
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_THREE_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewThreeMonths"/> $${currentRole.priceThreeMonths} <s>$${currentRole.priceOneMonth * 3}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_SIX_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewSixMonths"/> $${currentRole.priceSixMonths} <s>$${currentRole.priceOneMonth * 6}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_YEAR">
                                                                  <spring:message code="mySubscription.buttonRenewOneYear"/> $${currentRole.priceOneYear} <s>$${currentRole.priceOneMonth * 12}</s>
                                                              </a>
                                                          </li>
                                                      </ul>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="col-lg-12">
                                          <div class="text-box" style="margin-bottom: 20px;">
                                              <strong>
                                                  <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                              </strong>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${currentRole.countMaxSavedModels}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${currentRole.countMaxSavedTestsForModel}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                          </div>
                                      </div>
                                  </c:if>
                                  <c:if test="${currentRole.name == 'LEVEL_THREE'}">
                                      <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 20px;">
                                          <div class="col-lg-8">
                                              <h3 class="text-info" style="margin-bottom: 10px;">
                                                  <strong>
                                                      <spring:message code="${currentRole.name}"/>
                                                  </strong>
                                              </h3>
                                              <h5 class="text-danger" style="margin-bottom: 10px;">
                                                  <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                                  <c:if test="${empty subscriptionExpiredAt}">∞</c:if>
                                                  <c:if test="${not empty subscriptionExpiredAt}">
                                                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                                      <strong>
                                                          <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                      </strong>
                                                  </c:if>
                                              </h5>
                                          </div>
                                          <div class="col-lg-4">
                                              <div style="float: right">
                                                  <div class="btn-group">
                                                      <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                                          <spring:message code="mySubscriptionPage.buttonRenewSubscription"/> <span class="caret"></span>
                                                      </button>
                                                      <ul class="dropdown-menu dropdown-menu-right">
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_MONTH">
                                                                  <spring:message code="mySubscription.buttonRenewOneMonth"/> $${currentRole.priceOneMonth}
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_THREE_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewThreeMonths"/> $${currentRole.priceThreeMonths} <s>$${currentRole.priceOneMonth * 3}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_SIX_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewSixMonths"/> $${currentRole.priceSixMonths} <s>$${currentRole.priceOneMonth * 6}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_YEAR">
                                                                  <spring:message code="mySubscription.buttonRenewOneYear"/> $${currentRole.priceOneYear} <s>$${currentRole.priceOneMonth * 12}</s>
                                                              </a>
                                                          </li>
                                                      </ul>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="col-lg-12">
                                          <div class="text-box" style="margin-bottom: 20px;">
                                              <strong>
                                                  <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                              </strong>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${currentRole.countMaxSavedModels}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${currentRole.countMaxSavedTestsForModel}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                          </div>
                                      </div>
                                  </c:if>
                                  <c:if test="${currentRole.name == 'LEVEL_FOUR'}">
                                      <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 20px;">
                                          <div class="col-lg-8">
                                              <h3 class="text-info" style="margin-bottom: 10px;">
                                                  <strong>
                                                      <spring:message code="${currentRole.name}"/>
                                                  </strong>
                                              </h3>
                                              <h5 class="text-danger" style="margin-bottom: 10px;">
                                                  <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                                  <c:if test="${empty subscriptionExpiredAt}">∞</c:if>
                                                  <c:if test="${not empty subscriptionExpiredAt}">
                                                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                                      <strong>
                                                          <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                      </strong>
                                                  </c:if>
                                              </h5>
                                          </div>
                                          <div class="col-lg-4">
                                              <div style="float: right">
                                                  <div class="btn-group">
                                                      <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                                          <spring:message code="mySubscriptionPage.buttonRenewSubscription"/> <span class="caret"></span>
                                                      </button>
                                                      <ul class="dropdown-menu dropdown-menu-right">
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_MONTH">
                                                                  <spring:message code="mySubscription.buttonRenewOneMonth"/> $${currentRole.priceOneMonth}
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_THREE_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewThreeMonths"/> $${currentRole.priceThreeMonths} <s>$${currentRole.priceOneMonth * 3}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_SIX_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewSixMonths"/> $${currentRole.priceSixMonths} <s>$${currentRole.priceOneMonth * 6}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_YEAR">
                                                                  <spring:message code="mySubscription.buttonRenewOneYear"/> $${currentRole.priceOneYear} <s>$${currentRole.priceOneMonth * 12}</s>
                                                              </a>
                                                          </li>
                                                      </ul>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="col-lg-12">
                                          <div class="text-box" style="margin-bottom: 20px;">
                                              <strong>
                                                  <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                              </strong>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${currentRole.countMaxSavedModels}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${currentRole.countMaxSavedTestsForModel}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToEditScoreForAttribute"/>
                                          </div>
                                      </div>
                                  </c:if>
                                  <c:if test="${currentRole.name == 'LEVEL_FIVE'}">
                                      <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 20px;">
                                          <div class="col-lg-8">
                                              <h3 class="text-info" style="margin-bottom: 10px;">
                                                  <strong>
                                                      <spring:message code="${currentRole.name}"/>
                                                  </strong>
                                              </h3>
                                              <h5 class="text-danger" style="margin-bottom: 10px;">
                                                  <spring:message code="myProfilePage.subscriptionExpiredAt"/>
                                                  <c:if test="${empty subscriptionExpiredAt}">∞</c:if>
                                                  <c:if test="${not empty subscriptionExpiredAt}">
                                                      <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm:ss" var="subscriptionExpAt" value="${subscriptionExpiredAt}" type="both"/>
                                                      <strong>
                                                          <fmt:formatDate value="${subscriptionExpAt}" pattern="dd.MM.yyyy HH:mm:ss"/>
                                                      </strong>
                                                  </c:if>
                                              </h5>
                                          </div>
                                          <div class="col-lg-4">
                                              <div style="float: right">
                                                  <div class="btn-group">
                                                      <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                                          <spring:message code="mySubscriptionPage.buttonRenewSubscription"/> <span class="caret"></span>
                                                      </button>
                                                      <ul class="dropdown-menu dropdown-menu-right">
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_MONTH">
                                                                  <spring:message code="mySubscription.buttonRenewOneMonth"/> $${currentRole.priceOneMonth}
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_THREE_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewThreeMonths"/> $${currentRole.priceThreeMonths} <s>$${currentRole.priceOneMonth * 3}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_SIX_MONTHS">
                                                                  <spring:message code="mySubscription.buttonRenewSixMonths"/> $${currentRole.priceSixMonths} <s>$${currentRole.priceOneMonth * 6}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/renew/${currentRole.id}/PERIOD_ONE_YEAR">
                                                                  <spring:message code="mySubscription.buttonRenewOneYear"/> $${currentRole.priceOneYear} <s>$${currentRole.priceOneMonth * 12}</s>
                                                              </a>
                                                          </li>
                                                      </ul>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="col-lg-12">
                                          <div class="text-box" style="margin-bottom: 20px;">
                                              <strong>
                                                  <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                              </strong>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${currentRole.countMaxSavedModels}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${currentRole.countMaxSavedTestsForModel}</span></strong>.
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToEditScoreForAttribute"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationAdvancedSettings"/>
                                          </div>
                                          <div class="text-box" style="margin-bottom: 10px;">
                                              <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToExportToExcelModelAndTest"/>
                                          </div>
                                      </div>
                                  </c:if>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>

          <div class="hr-line-dashed" style="margin-bottom: 20px;"></div>

          <h2 style="margin-bottom: 20px;">
              <strong>
                  <spring:message code="mySubscriptionPage.otherSubscriptions"/>
              </strong>
          </h2>

          <div class="tabs-container" style="margin-bottom: 20px;">
              <div class="tabs">
                  <div class="panel-group" id="accordionRoles" role="tablist" aria-multiselectable="true">
                      <c:forEach var="roleList" varStatus="item" items="${listOtherRoles}">
                          <div class="panel panel-default">
                              <div class="panel-heading" role="tab" id="heading${item.index}">
                                  <h4 class="panel-title">
                                      <a role="button" data-toggle="collapse" data-target="#collapse${item.index}" aria-expanded="true" aria-controls="collapse${item.index}" class="collapsed">
                                          <span class="fa fa-tags"></span> <span class="text-info"><spring:message code="${roleList.name}"/></span>
                                          <c:if test="${roleList.name == 'LEVEL_FIVE'}">
                                              <span class="fa fa-angle-double-right"></span> <span class="text-danger"><spring:message code="mySubscription.textBestChoice"/></span>
                                          </c:if>
                                          <span class="fa fa-angle-double-right"></span> <spring:message code="mySubscription.textDetailedDescription"/>
                                      </a>
                                  </h4>
                              </div>
                              <div id="collapse${item.index}" class="panel-collapse collapse" aria-expanded="false" role="tabpanel" aria-labelledby="heading${item.index}">
                                  <div class="panel-body">
                                      <div class="row wrapper border-bottom white-bg page-heading" style="margin-bottom: 20px;">
                                          <div class="col-lg-8">
                                              <h3 class="text-info" style="margin-bottom: 10px;">
                                                  <strong>
                                                      <spring:message code="${roleList.name}"/>
                                                  </strong>
                                              </h3>
                                          </div>
                                          <div class="col-lg-4">
                                              <div style="float: right">
                                                  <div class="btn-group">
                                                      <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" aria-expanded="false">
                                                          <spring:message code="mySubscriptionPage.buttonBuySubscription"/> <span class="caret"></span>
                                                      </button>
                                                      <ul class="dropdown-menu dropdown-menu-right">
                                                          <li>
                                                              <a href="/myprofile/mysubscription/buy/${roleList.id}/PERIOD_ONE_MONTH">
                                                                  <spring:message code="mySubscription.buttonBuyOneMonth"/> $${roleList.priceOneMonth}
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/buy/${roleList.id}/PERIOD_THREE_MONTHS">
                                                                  <spring:message code="mySubscription.buttonBuyThreeMonths"/> $${roleList.priceThreeMonths} <s>$${roleList.priceOneMonth * 3}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/buy/${roleList.id}/PERIOD_SIX_MONTHS">
                                                                  <spring:message code="mySubscription.buttonBuySixMonths"/> $${roleList.priceSixMonths} <s>$${roleList.priceOneMonth * 6}</s>
                                                              </a>
                                                          </li>
                                                          <li>
                                                              <a href="/myprofile/mysubscription/buy/${roleList.id}/PERIOD_ONE_YEAR">
                                                                  <spring:message code="mySubscription.buttonBuyOneYear"/> $${roleList.priceOneYear} <s>$${roleList.priceOneMonth * 12}</s>
                                                              </a>
                                                          </li>
                                                      </ul>
                                                  </div>
                                              </div>
                                          </div>
                                      </div>
                                      <c:if test="${roleList.name == 'LEVEL_TWO'}">
                                          <div class="col-lg-12">
                                              <div class="text-box" style="margin-bottom: 20px;">
                                                  <strong>
                                                      <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                                  </strong>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${roleList.countMaxSavedModels}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${roleList.countMaxSavedTestsForModel}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                              </div>
                                          </div>
                                      </c:if>
                                      <c:if test="${roleList.name == 'LEVEL_THREE'}">
                                          <div class="col-lg-12">
                                              <div class="text-box" style="margin-bottom: 20px;">
                                                  <strong>
                                                      <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                                  </strong>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${roleList.countMaxSavedModels}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${roleList.countMaxSavedTestsForModel}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                              </div>
                                          </div>
                                      </c:if>
                                      <c:if test="${roleList.name == 'LEVEL_FOUR'}">
                                          <div class="col-lg-12">
                                              <div class="text-box" style="margin-bottom: 20px;">
                                                  <strong>
                                                      <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                                  </strong>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${roleList.countMaxSavedModels}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${roleList.countMaxSavedTestsForModel}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToEditScoreForAttribute"/>
                                              </div>
                                          </div>
                                      </c:if>
                                      <c:if test="${roleList.name == 'LEVEL_FIVE'}">
                                          <div class="col-lg-12">
                                              <div class="text-box" style="margin-bottom: 20px;">
                                                  <strong>
                                                      <spring:message code="mySubscriptionPage.textBenefitsAndFeatures"/>
                                                  </strong>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedModels"/> <strong><span class="text-info">${roleList.countMaxSavedModels}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessCountPossibleSavedTests"/> <strong><span class="text-info">${roleList.countMaxSavedTestsForModel}</span></strong>.
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToWelcomeInstruction"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToAllBlockMyProfileViewAndEdit"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreateNewScoringModel"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewModelAndEditTileDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToCreteNewTest"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToViewTestAndEditTitleDesc"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToScoringModelTestSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToEditScoreForAttribute"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToModelCreationAdvancedSettings"/>
                                              </div>
                                              <div class="text-box" style="margin-bottom: 10px;">
                                                  <span class="fa fa-check-square-o"></span> <spring:message code="mySubscriptionPage.accessToExportToExcelModelAndTest"/>
                                              </div>
                                          </div>
                                      </c:if>
                                  </div>
                              </div>
                          </div>
                      </c:forEach>
                  </div>
              </div>
          </div>

          <div class="clearfix" style="padding-top: 40px"></div>
      </div>



    </jsp:attribute>
</mytags:header-template>

</html>
