package com.longbridge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 * Created by Longbridge on 21/12/2017.
 */
@Entity
public class Items extends CommonFields{

    @JsonIgnore
    @ManyToOne
    public Orders orders;

    private Long productId;

    private int quantity;

    private Long designerId;

    private String deliveryStatus;

    private Date deliveryDate;

    private String size;

    private String amount;

    private Long artWorkPictureId;

    private Long materialPictureId;

    private String color;

    private String materialStatus; //Y-Yes, N-No

    private String materialLocation;

    private String materialPickupDate;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Items() {
    }

    public Items(Long productId, int quantity, Long designerId, String deliveryStatus, Date deliveryDate, String size, String amount, Long artWorkPictureId, Long materialPictureId, String color, String materialStatus, String materialLocation, String materialPickupDate) {
        this.productId = productId;
        this.quantity = quantity;
        this.designerId = designerId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryDate = deliveryDate;
        this.size = size;
        this.amount = amount;
        this.artWorkPictureId = artWorkPictureId;
        this.materialPictureId = materialPictureId;
        this.color = color;
        this.materialStatus = materialStatus;
        this.materialLocation = materialLocation;
        this.materialPickupDate = materialPickupDate;
    }


    @Override
    public String toString() {
        return "Items{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", designerId=" + designerId +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", size='" + size + '\'' +
                ", amount='" + amount + '\'' +
                ", artWorkPictureId=" + artWorkPictureId +
                ", materialPictureId=" + materialPictureId +
                ", color='" + color + '\'' +
                ", materialStatus='" + materialStatus + '\'' +
                ", materialLocation='" + materialLocation + '\'' +
                ", materialPickupDate='" + materialPickupDate + '\'' +
                '}';
    }
}
