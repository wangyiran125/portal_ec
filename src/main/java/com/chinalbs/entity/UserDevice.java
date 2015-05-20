package com.chinalbs.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "us_user_device")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_user_device_sequence")
public class UserDevice implements Serializable {

    /**
     * 用户设备绑定
     */
    private static final long serialVersionUID = 9156218273242379186L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              fId;

    /** 设备IMEI 号码 */
    private String            fDeviceSn;

    /** 用户id */
    private Long              fUserId;

    /** 设备绑定时间 */
    private Long              fTime;

    public Long getfId () {

        return fId;
    }

    public void setfId (Long fId) {

        this.fId = fId;
    }

    public String getfDeviceSn () {

        return fDeviceSn;
    }

    public void setfDeviceSn (String fDeviceSn) {

        this.fDeviceSn = fDeviceSn;
    }

    public Long getfUserId () {

        return fUserId;
    }

    public void setfUserId (Long fUserId) {

        this.fUserId = fUserId;
    }

    public Long getfTime () {

        return fTime;
    }

    public void setfTime (Long fTime) {

        this.fTime = fTime;
    }

}
