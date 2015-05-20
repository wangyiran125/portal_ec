package com.chinalbs.entity;

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

import com.chinalbs.framework.entity.BaseEntity;

/**
 * Entiry - 后台模式
 * 
 * @author shijun
 * 
 */

@Entity
@Table(name = "ec_backend_mode_user_extend")
@SequenceGenerator(name = "sequenceGenerator",
    sequenceName = "ec_backend_mode_user_extend_sequence")
public class BackendModeUserExtend extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -7850196515728578857L;
  /**
   * 网站名称
   */
  private String siteName;
  /**
   * 网站LOGO
   */
  private String logo;
  /**
   * Faicon
   */
  private String favIcon;
  /**
   * icon
   */
  private String icon;

  /**
   * 网站域名
   */
  private String siteDomain;

  /**
   * 默认网站域名
   */
  private String defaultSiteDomain;

  /** 报警开关状态 */
  private Alarm alarmState;

  /** 样式选择 */
  private String frontStyle;

  /**
   * 自定义版权信息
   */
  private String custCopyRight;
  /** 用户自定功能 */
  private Set<CustomizeFun> userCustomizeFuns = new HashSet<CustomizeFun>();

  private User user;

  /** 接口报警接收开关 */
  public enum Alarm {
    /** 开启 */
    OPEN,
    /** 关闭 */
    CLOSE
  }

  @Length(max = 20)
  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }


  @Length(max = 20)
  public String getDefaultSiteDomain() {
    return defaultSiteDomain;
  }

  public void setDefaultSiteDomain(String defaultSiteDomain) {
    this.defaultSiteDomain = defaultSiteDomain;
  }

  @Length(max = 50)
  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  @Length(max = 50)
  public String getFavIcon() {
    return favIcon;
  }

  public void setFavIcon(String favIcon) {
    this.favIcon = favIcon;
  }

  @Length(max = 50)
  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  @Column(length = 20)
  public String getFrontStyle() {
    return frontStyle;
  }

  public void setFrontStyle(String frontStyle) {
    this.frontStyle = frontStyle;
  }

  public String getSiteDomain() {
    return siteDomain;
  }

  @Column(length = 20)
  public void setSiteDomain(String siteDomain) {
    this.siteDomain = siteDomain;
  }

  
  public String getCustCopyRight() {
    return custCopyRight;
  }

  public void setCustCopyRight(String custCopyRight) {
    this.custCopyRight = custCopyRight;
  }

  @OneToOne(fetch = FetchType.LAZY)
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Alarm getAlarmState() {
    return alarmState;
  }

  public void setAlarmState(Alarm alarmState) {
    this.alarmState = alarmState;
  }

  /**
   * 获取用户自定义功能
   * 
   * @return
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "ec_customize_function_backend_mode_user_extend")
  public Set<CustomizeFun> getUserCustomizeFuns() {
    return userCustomizeFuns;
  }

  /**
   * 设置用户自定义功能
   * 
   * @param userCustomizeFuns
   */
  public void setUserCustomizeFuns(Set<CustomizeFun> userCustomizeFuns) {
    this.userCustomizeFuns = userCustomizeFuns;
  }


}
