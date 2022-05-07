package com.korotkov.main.model;

import com.korotkov.main.model.UserAccount;
import com.korotkov.main.model.UserRole;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class CommonPaymentModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserAccount userAccount;

    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "purchase_role_id")
    @ManyToOne(fetch = FetchType.EAGER)
    UserRole userRole;

    @Column(name = "status_response")
    String statusResponse;

    @Column(name = "merchant_id")
    Integer merchantId;

    @Column(name = "order_desc")
    String orderDescription;

    @Column(name = "amount")
    Double amount;

    @Column(name = "currency")
    String currency;

    @Column(name = "response_url")
    String responseUrl;

    @Column(name = "server_callback_url")
    String serverCallbackUrl;

    @Column(name = "sender_email_request")
    String senderEmailRequest;

    @Column(name = "order_status")
    String orderStatus;

    @Column(name = "tran_type")
    String tranType;

    @Column(name = "sender_cell_phone")
    String senderCellPhone;

    @Column(name = "sender_account")
    String senderAccount;

    @Column(name = "masked_card")
    String maskedCard;

    @Column(name = "card_bin")
    Integer cardBin;

    @Column(name = "card_type")
    String cardType;

    @Column(name = "rrn")
    String rrn;

    @Column(name = "approval_code")
    String approvalCode;

    @Column(name = "response_code")
    Integer responseCode;

    @Column(name = "response_description")
    String responseDescription;

    @Column(name = "reversal_amount")
    Double reversalAmount;

    @Column(name = "settlement_amount")
    Double settlementAmount;

    @Column(name = "settlement_currency")
    String settlementCurrency;

    @Column(name = "order_time")
    LocalDateTime orderTime;

    @Column(name = "settlement_date")
    LocalDate settlementDate;

    @Column(name = "eci")
    Integer eci;

    @Column(name = "fee")
    Double fee;

    @Column(name = "payment_system")
    String paymentSystem;

    @Column(name = "sender_email_response")
    String senderEmailResponse;

    @Column(name = "payment_id")
    Integer paymentId;

    @Column(name = "actual_amount")
    Double actualAmount;

    @Column(name = "actual_currency")
    String actualCurrency;

    @Column(name = "rectoken")
    String rectoken;

    @Column(name = "rectoken_lifetime")
    LocalDateTime rectokenLifetime;

    @Column(name = "temp_response_status")
    String tempResponseStatus;

    @Column(name = "temp_checkout_url")
    String tempCheckoutUrl;

    @Column(name = "temp_payment_id")
    Integer tempPaymentId;

    @Column(name = "error_code")
    Integer errorCode;

    @Column(name = "error_message")
    String errorMessage;

    @Column(name = "period_purchase")
    String periodPurchase;

    @Column(name = "type_purchase")
    String typePurchase;

    @Column(name = "status_request")
    String statusRequest;

    @Column(name = "error_request")
    String errorRequest;

    public CommonPaymentModel() {

    }

    public String getErrorRequest() {
        return errorRequest;
    }

    public void setErrorRequest(String errorRequest) {
        this.errorRequest = errorRequest;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getTypePurchase() {
        return typePurchase;
    }

    public void setTypePurchase(String typePurchase) {
        this.typePurchase = typePurchase;
    }

    public String getPeriodPurchase() {
        return periodPurchase;
    }

    public void setPeriodPurchase(String periodPurchase) {
        this.periodPurchase = periodPurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getStatusResponse () {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getServerCallbackUrl() {
        return serverCallbackUrl;
    }

    public void setServerCallbackUrl(String serverCallbackUrl) {
        this.serverCallbackUrl = serverCallbackUrl;
    }

    public String getSenderEmailRequest() {
        return senderEmailRequest;
    }

    public void setSenderEmailRequest(String senderEmailRequest) {
        this.senderEmailRequest = senderEmailRequest;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getSenderCellPhone() {
        return senderCellPhone;
    }

    public void setSenderCellPhone(String senderCellPhone) {
        this.senderCellPhone = senderCellPhone;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getMaskedCard() {
        return maskedCard;
    }

    public void setMaskedCard(String maskedCard) {
        this.maskedCard = maskedCard;
    }

    public Integer getCardBin() {
        return cardBin;
    }

    public void setCardBin(Integer cardBin) {
        this.cardBin = cardBin;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public Double getReversalAmount() {
        return reversalAmount;
    }

    public void setReversalAmount(Double reversalAmount) {
        this.reversalAmount = reversalAmount;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(LocalDate settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Integer getEci() {
        return eci;
    }

    public void setEci(Integer eci) {
        this.eci = eci;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getSenderEmailResponse() {
        return senderEmailResponse;
    }

    public void setSenderEmailResponse(String senderEmailResponse) {
        this.senderEmailResponse = senderEmailResponse;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getActualCurrency() {
        return actualCurrency;
    }

    public void setActualCurrency(String actualCurrency) {
        this.actualCurrency = actualCurrency;
    }

    public String getRectoken() {
        return rectoken;
    }

    public void setRectoken(String rectoken) {
        this.rectoken = rectoken;
    }

    public LocalDateTime getRectokenLifetime() {
        return rectokenLifetime;
    }

    public void setRectokenLifetime(LocalDateTime rectokenLifetime) {
        this.rectokenLifetime = rectokenLifetime;
    }

    public String getTempResponseStatus() {
        return tempResponseStatus;
    }

    public void setTempResponseStatus(String tempResponseStatus) {
        this.tempResponseStatus = tempResponseStatus;
    }

    public String getTempCheckoutUrl() {
        return tempCheckoutUrl;
    }

    public void setTempCheckoutUrl(String tempCheckoutUrl) {
        this.tempCheckoutUrl = tempCheckoutUrl;
    }

    public Integer getTempPaymentId() {
        return tempPaymentId;
    }

    public void setTempPaymentId(Integer tempPaymentId) {
        this.tempPaymentId = tempPaymentId;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
