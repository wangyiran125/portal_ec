package com.chinalbs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinalbs.framework.entity.BaseEntity;

/**
 * Entity - 开发者模式
 * @author shijun
 *
 */
@Entity
@Table(name = "ec_dev_mode_user_extend")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ec_dev_mode_user_extend_sequence")
public class DevModeUserExtend extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -4954061778306217743L;

  /** 开发者ID */
  private String devId;
  /** 开发者域名 */
  private String devDomain;
  /** 开发者Key */
  private String devKey;
  /** 报警开关状态 */
  private Alarm alarmState;
  /** 用户 */
  private User user;

  /** 接口报警接收开关 */
  public enum Alarm {
    /** 开启 */
    OPEN,
    /** 关闭 */
    CLOSE
  }

  @Column(length = 30)
  public String getDevId() {
    return devId;
  }

  public void setDevId(String devId) {
    this.devId = devId;
  }

  @Column(length = 50)
  public String getDevDomain() {
    return devDomain;
  }

  public void setDevDomain(String devDomain) {
    this.devDomain = devDomain;
  }

  @Column(length = 100)
  public String getDevKey() {
    return devKey;
  }

  public void setDevKey(String devKey) {
    this.devKey = devKey;
  }

  @Column(length = 5)
  public Alarm getAlarmState() {
    return alarmState;
  }

  public void setAlarmState(Alarm alarmState) {
    this.alarmState = alarmState;
  }

  @OneToOne(fetch = FetchType.LAZY)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
