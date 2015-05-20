package com.chinalbs.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinalbs.framework.entity.BaseEntity;

/**
 * Entity - 用户功能配置
 * 
 * @author shijun
 * 
 */
@Entity
@Table(name = "ec_customize_function")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ec_customize_function_sequence")
public class CustomizeFun extends BaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 2532596168101311193L;

  /** 功能名称 */
  private String funcationName;
  
  private String description;
  
  private String picPath;
  
  private String iconPath;

  /** 用户 */
  private Set<BackendModeUserExtend> backendModeUserExtends = new HashSet<BackendModeUserExtend>();

  @ManyToMany(mappedBy = "userCustomizeFuns",fetch = FetchType.EAGER)
  public Set<BackendModeUserExtend> getBackendModeUserExtends() {
    return backendModeUserExtends;
  }

  public void setBackendModeUserExtends(Set<BackendModeUserExtend> backendModeUserExtends) {
    this.backendModeUserExtends = backendModeUserExtends;
  }
  
  @Column(length=30)
  public String getFuncationName() {
    return funcationName;
  }

  public void setFuncationName(String funcationName) {
    this.funcationName = funcationName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getPicPath() {
    return picPath;
  }

  public void setPicPath(String picPath) {
    this.picPath = picPath;
  }

  public String getIconPath() {
    return iconPath;
  }

  public void setIconPath(String iconPath) {
    this.iconPath = iconPath;
  }

  
}
