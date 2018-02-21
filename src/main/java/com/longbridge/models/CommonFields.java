package com.longbridge.models;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by longbridge on 10/18/17.
 */
@MappedSuperclass
public class CommonFields {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    private Date updatedOn;
    private Date verfiedOn;
    public Date createdOn;

    protected String delFlag = "N";

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Date getVerfiedOn() {
        return verfiedOn;
    }

    public void setVerfiedOn(Date verfiedOn) {
        this.verfiedOn = verfiedOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
