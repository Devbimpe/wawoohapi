package com.longbridge.exception;

import com.longbridge.dto.DesignerOrderDTO;
import com.longbridge.dto.ItemsDTO;

/**
 * Created by Longbridge on 28/02/2018.
 */
public class AppException extends RuntimeException{
    private String newPassword;
    private String name;
    private String recipient;
    private String subject;
    private String link;
    private String orderNum;
    private DesignerOrderDTO designerOrderDTO;
    private ItemsDTO itemsDTO;
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public DesignerOrderDTO getDesignerOrderDTO() {
        return designerOrderDTO;
    }

    public void setDesignerOrderDTO(DesignerOrderDTO designerOrderDTO) {
        this.designerOrderDTO = designerOrderDTO;
    }

    public ItemsDTO getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(ItemsDTO itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public AppException(String newPassword, String name, String recipient, String subject, String link) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");
        this.newPassword=newPassword;
        this.name=name;
        this.recipient=recipient;
        this.subject=subject;
        this.link=link;
    }

    public AppException(String name, String recipient, String subject, String orderNum) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");

        this.name=name;
        this.recipient=recipient;
        this.subject=subject;
        this.orderNum=orderNum;

    }

    public AppException(String name, String recipient, String subject, String orderNum,String link,String empty) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");

        this.name=name;
        this.recipient=recipient;
        this.subject=subject;
        this.orderNum=orderNum;
        this.link=link;

    }

    public AppException(String name, String recipient, String subject, ItemsDTO itemsDTO) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");

        this.name=name;
        this.recipient=recipient;
        this.subject=subject;
        this.itemsDTO=itemsDTO;

    }

    public AppException(DesignerOrderDTO designerOrderDTO, String recipient, String subject, String orderNum) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");
        this.designerOrderDTO=designerOrderDTO;
        this.recipient=recipient;
        this.subject=subject;
        this.orderNum=orderNum;

    }



    public AppException(String name, String recipient, String subject) {
//        super("Failed to perform the requested action");
        System.out.println("i got here");

        this.name=name;
        this.recipient=recipient;
        this.subject=subject;

    }



    public AppException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public AppException(String message) {
        super(message);
    }


    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
