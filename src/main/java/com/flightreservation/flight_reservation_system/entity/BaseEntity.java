package com.flightreservation.flight_reservation_system.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "deleted")
    private boolean deleted = false;

    public BaseEntity() {
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    public BaseEntity(Date createDate, Date updateDate, boolean deleted) {
        this.createDate = createDate != null ? createDate : new Date();
        this.updateDate = updateDate != null ? updateDate : new Date();
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

