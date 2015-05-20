package com.chinalbs.json;

import java.util.List;

/***
 * 文档明细
 * @author shijun
 *
 */
public class APIDocDetailJson extends ApiCommonJson {
  private List<APIReturningDescJson> return_desc;

  private List<APIReturningJsonDescJson> return_json;

  private List<APIParamDescJson> request_param;

  public List<APIReturningDescJson> getReturn_desc() {
    return return_desc;
  }

  public void setReturn_desc(List<APIReturningDescJson> return_desc) {
    this.return_desc = return_desc;
  }

  public List<APIReturningJsonDescJson> getReturn_json() {
    return return_json;
  }

  public void setReturn_json(List<APIReturningJsonDescJson> return_json) {
    this.return_json = return_json;
  }

  public List<APIParamDescJson> getRequest_param() {
    return request_param;
  }

  public void setRequest_param(List<APIParamDescJson> request_param) {
    this.request_param = request_param;
  }
}
