package com.chinalbs.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.chinalbs.framework.entity.BaseEntity;

/**
 * Entity - 用户表
 * 
 */
@Entity
@Table(name = "ec_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ec_user_sequence")
public class User extends BaseEntity {

  private static final long serialVersionUID = -7519486823153844426L;


  /** 用户名 */
  private String userName;

  /** 密码 */
  private String password;

  /** E-mail */
  private String email;

  /** 姓名 */
  private String name;

  /** 昵称 */
  private String nickName;

  /** 手机号码 */
  private String mobile;

  /** 最后登录日期 */
  private Date loginDate;

  /** 最后登录IP */
  private String loginIp;

  /** 是否内置 */
  private Integer isSystem;

  /** 应用名称 */
  private String appName;

  /** 企业用户扩展信息 */
  private EnterpriseUserExtend enterpriseUserExtend;

  /** 个人用户扩展信息 */
  private IndividualUserExtend individualUserExtend;

  /** 开发者扩展信息 */
  private DevModeUserExtend devModeUserExtend;

  private BackendModeUserExtend backendModeUserExtend;

  /** 用户类型 */
  private UserType userType;

  private ModeType modeType;

  /** 配置数据是否生效 */
  private Integer confIsEnable;
  /** 角色 */
  private Set<Role> roles = new HashSet<Role>();

  /** 用户类型枚举 */
  public enum UserType {

    /** 企业 */
    ENTERPRISE,
    /** 个人 */
    INDIVIDUAL
  }

  public enum ModeType {

    /** 开发者模式 */
    DEVMODE,
    /** 后台模式 */
    BACKENDMODE

  }

  public enum ConfigState {
    /** 生效 */
    ENABLE,
    /** 无效 */
    DISABLE
  }

  /**
   * 获取用户名
   * 
   * @return 用户名
   */
  public String getUserName() {
    return userName;
  }

  /**
   * 设置用户名
   * 
   * @param 用户名
   * 
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * 获取昵称
   * 
   * @return 昵称
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * 设置昵称
   * 
   * @param 昵称
   * 
   */
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  /**
   * 获取密码
   * 
   * @return 密码
   */
  @Length(min = 4, max = 20)
  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  /**
   * 设置密码
   * 
   * @param password 密码
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 获取E-mail
   * 
   * @return E-mail
   */
  @Length(max = 200)
  @Column(nullable = false)
  public String getEmail() {
    return email;
  }

  /**
   * 设置E-mail
   * 
   * @param email E-mail
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * 获取姓名
   * 
   * @return 姓名
   */
  @Length(max = 200)
  public String getName() {
    return name;
  }

  /**
   * 设置姓名
   * 
   * @param name 姓名
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * 获取手机号码
   * 
   * @return 手机号码
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * 设置手机号码
   * 
   * @param mobile 手机号码
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * 获取最后登录日期
   * 
   * @return 最后登录日期
   */
  public Date getLoginDate() {
    return loginDate;
  }

  /**
   * 设置最后登录日期
   * 
   * @param loginDate 最后登录日期
   */
  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }

  /**
   * 获取最后登录IP
   * 
   * @return 最后登录IP
   */
  public String getLoginIp() {
    return loginIp;
  }

  /**
   * 设置最后登录IP
   * 
   * @param loginIp 最后登录IP
   */
  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }

  /**
   * 获取是否内置
   * 
   * @return 是否内置
   */
  @Column(nullable = false, updatable = false)
  public Integer getIsSystem() {
    return isSystem;
  }

  /**
   * 获取是否完成引导设置
   * 
   * @param isSystem
   */
  public void setIsSystem(Integer isSystem) {
    this.isSystem = isSystem;
  }



  /**
   * 获取企业用户扩展信息
   * 
   * @return
   */
  @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
  public EnterpriseUserExtend getEnterpriseUserExtend() {
    return enterpriseUserExtend;
  }

  /**
   * 设置企业用户扩展信息
   * 
   * @param enterpriseUserExtend
   */
  public void setEnterpriseUserExtend(EnterpriseUserExtend enterpriseUserExtend) {
    this.enterpriseUserExtend = enterpriseUserExtend;
  }

  /**
   * 获取个人用户扩展信息
   * 
   * @return
   */
  @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
  public IndividualUserExtend getIndividualUserExtend() {
    return individualUserExtend;
  }

  /**
   * 设置个人用户扩展信息
   * 
   * @param individualUserExtend
   */
  public void setIndividualUserExtend(IndividualUserExtend individualUserExtend) {
    this.individualUserExtend = individualUserExtend;
  }

  /**
   * 获取开发者扩展信息
   * 
   * @return devModeUserExtend
   */
  @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
  public DevModeUserExtend getDevModeUserExtend() {
    return devModeUserExtend;
  }

  /**
   * 设置开发者扩展信息
   * 
   * @param devModeUserExtend
   */
  public void setDevModeUserExtend(DevModeUserExtend devModeUserExtend) {
    this.devModeUserExtend = devModeUserExtend;
  }


  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  public BackendModeUserExtend getBackendModeUserExtend() {
    return backendModeUserExtend;
  }

  public void setBackendModeUserExtend(BackendModeUserExtend backendModeUserExtend) {
    this.backendModeUserExtend = backendModeUserExtend;
  }

  /**
   * 获取角色
   * 
   * @return 角色
   */
  @NotEmpty
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "ec_user_role")
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * 设置角色
   * 
   * @param roles 角色
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /**
   * 获取用户类型
   * 
   * @return
   */
  public UserType getUserType() {
    return userType;
  }

  /**
   * 设置用户类型
   * 
   * @param userType
   */
  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  /**
   * 获取用户类型
   * 
   * @return
   */
  public ModeType getModeType() {
    return modeType;
  }

  /**
   * 设置用户类型
   * 
   * @param userType
   */
  public void setModeType(ModeType modeType) {
    this.modeType = modeType;
  }

  public Integer getConfIsEnable() {
    return confIsEnable;
  }

  public void setConfIsEnable(Integer confIsEnable) {
    this.confIsEnable = confIsEnable;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

}
