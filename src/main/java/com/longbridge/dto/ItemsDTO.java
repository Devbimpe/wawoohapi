package com.longbridge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Longbridge on 03/01/2018.
 */
public class ItemsDTO {

    private Long id;

    private Long productId;

    private String productName;

    private String productAvailability;

    private int quantity;

    private Long designerId;

    private String deliveryStatus;

    private String deliveryDate;

    private String orderDate;

    private String size;

    private String amount;

    private String productPicture;

    private String artWorkPicture;

    private String materialPicture;

    private Long artWorkPictureId;

    private Long materialPictureId;

    private String color;

    private String materialStatus; //Y-Yes, N-No

    private String materialLocation;

    private String materialPickupDate;

    private String orderNumber;

    private String measurement;

    private Long orderId;

    private String customerName;

    private Long customerId;

    private String rejectReason;


    private Long statusId;
    private String status;


    private String failedInspectionReason;

    private Long messageId;
    private String message;

    private String waitTime;

    private String action;

    private String accountName;

    private String accountNumber;

    private String complain;

    private boolean readyMade;

    private String trackingNumber;

    @JsonIgnore
    private String link;

    private ArrayList<String> pictures;


    public ItemsDTO() {
    }

    public ItemsDTO(Long id, Long productId, String productName, String productAvailability, int quantity, Long designerId, String deliveryStatus, String deliveryDate, String orderDate, String size, String amount, String productPicture, String artWorkPicture, String materialPicture, Long artWorkPictureId, Long materialPictureId, String color, String materialStatus, String materialLocation, String materialPickupDate, String orderNumber, String measurement, Long orderId, String customerName, Long customerId, String rejectReason, Long statusId, String status, String failedInspectionReason, Long messageId, String message, String waitTime, String action, String accountName, String accountNumber, String complain, boolean readyMade, String trackingNumber, String link, ArrayList<String> pictures) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productAvailability = productAvailability;
        this.quantity = quantity;
        this.designerId = designerId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.size = size;
        this.amount = amount;
        this.productPicture = productPicture;
        this.artWorkPicture = artWorkPicture;
        this.materialPicture = materialPicture;
        this.artWorkPictureId = artWorkPictureId;
        this.materialPictureId = materialPictureId;
        this.color = color;
        this.materialStatus = materialStatus;
        this.materialLocation = materialLocation;
        this.materialPickupDate = materialPickupDate;
        this.orderNumber = orderNumber;
        this.measurement = measurement;
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerId = customerId;
        this.rejectReason = rejectReason;
        this.statusId = statusId;
        this.status = status;
        this.failedInspectionReason = failedInspectionReason;
        this.messageId = messageId;
        this.message = message;
        this.waitTime = waitTime;
        this.action = action;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.complain = complain;
        this.readyMade = readyMade;
        this.trackingNumber = trackingNumber;
        this.link = link;
        this.pictures = pictures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getArtWorkPicture() {
        return artWorkPicture;
    }

    public void setArtWorkPicture(String artWorkPicture) {
        this.artWorkPicture = artWorkPicture;
    }

    public String getMaterialPicture() {
        return materialPicture;
    }

    public void setMaterialPicture(String materialPicture) {
        this.materialPicture = materialPicture;
    }

    public Long getArtWorkPictureId() {
        return artWorkPictureId;
    }

    public void setArtWorkPictureId(Long artWorkPictureId) {
        this.artWorkPictureId = artWorkPictureId;
    }

    public Long getMaterialPictureId() {
        return materialPictureId;
    }

    public void setMaterialPictureId(Long materialPictureId) {
        this.materialPictureId = materialPictureId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterialStatus() {
        return materialStatus;
    }

    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }

    public String getMaterialLocation() {
        return materialLocation;
    }

    public void setMaterialLocation(String materialLocation) {
        this.materialLocation = materialLocation;
    }

    public String getMaterialPickupDate() {
        return materialPickupDate;
    }

    public void setMaterialPickupDate(String materialPickupDate) {
        this.materialPickupDate = materialPickupDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(String productAvailability) {
        this.productAvailability = productAvailability;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public boolean isReadyMade() {
        return readyMade;
    }

    public void setReadyMade(boolean readyMade) {
        this.readyMade = readyMade;
    }

    public String getFailedInspectionReason() {
        return failedInspectionReason;
    }

    public void setFailedInspectionReason(String failedInspectionReason) {
        this.failedInspectionReason = failedInspectionReason;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<String> picture) {
        this.pictures = picture;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
