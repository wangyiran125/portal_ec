package com.chinalbs.json;

/**
 * 返回字段描述
 * 
 * @author shijun
 * 
 */
public class APIReturningDescJson {
  private Integer id;

  private String name;

  private String type;

  private String must;

  private String returnType;

  private Integer docId;

  private String json;

  private String desc;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMust() {
    return must;
  }

  public void setMust(String must) {
    this.must = must;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public Integer getDocId() {
    return docId;
  }

  public void setDocId(Integer docId) {
    this.docId = docId;
  }

  public String getJson() {
    return json;
  }

  public void setJson(String json) {
    this.json = json;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
