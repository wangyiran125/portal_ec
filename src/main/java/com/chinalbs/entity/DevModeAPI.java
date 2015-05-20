package com.chinalbs.entity;

public class DevModeAPI
{
  /**API id*/
  private Integer id;
  
  /** API 名字*/
  private String apiName;
  
  /**API 接口*/
  private String api;
  
  /** API 状态*/
  private Integer apiState;
  
  public String getApiName ()
  {
    return apiName;
  }
  public void setApiName (String apiName)
  {
    this.apiName = apiName;
  }
  public Integer getApiState ()
  {
    return apiState;
  }
  public void setApiState (Integer apiState)
  {
    this.apiState = apiState;
  }
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getApi() {
    return api;
  }
  public void setApi(String api) {
    this.api = api;
  }
  
  
}
