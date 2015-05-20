package com.chinalbs.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chinalbs.framework.entity.BaseEntity;

@Entity
@Table(name = "us_indoor_coordinate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_indoor_coordinate_sequence")
public class Coordinate extends BaseEntity {

	private static final long serialVersionUID = -3435265484451395239L;

	private String fLocation;// 坐标系地理位置

	private String fName;// 坐标系名称

	private Double fLng;// 经度

	private Double fLat;// 纬度

	private Integer fFloor;// 楼层

	private Long fServiceId;// 后台用户id

	public String getfLocation() {
		return fLocation;
	}

	public void setfLocation(String fLocation) {
		this.fLocation = fLocation;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public Double getfLng() {
		return fLng;
	}

	public void setfLng(Double fLng) {
		this.fLng = fLng;
	}

	public Double getfLat() {
		return fLat;
	}

	public void setfLat(Double fLat) {
		this.fLat = fLat;
	}

	public Integer getfFloor() {
		return fFloor;
	}

	public void setfFloor(Integer fFloor) {
		this.fFloor = fFloor;
	}

	public Long getfServiceId() {
		return fServiceId;
	}

	public void setfServiceId(Long fServiceId) {
		this.fServiceId = fServiceId;
	}

}
