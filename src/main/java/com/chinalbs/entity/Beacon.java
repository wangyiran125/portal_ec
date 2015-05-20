package com.chinalbs.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 该Entity 不用使用JPA ,因为会去连接客户数据库来获取数据
 * 
 */
@Entity
@Table(name = "us_indoor_beacon")
//@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_indoor_beacon_sequence")
public class Beacon implements Serializable {

	private static final long serialVersionUID = 6414443084806048027L;

	@Id
	private String fMac;//mac地址

	private Long fServiceId;//后台用户id

	private Double fLng;//经度

	private Double fLat;//纬度

	private String fLocate;//位置

	private Integer fFloor;//楼层
	
	private Integer fRoom;//房间号

	private String fSystem;//坐标系

	private Double fPx;//坐标x点

	private Double fPy;//坐标y点

	public String getfMac() {
		return fMac;
	}

	public void setfMac(String fMac) {
		this.fMac = fMac;
	}

	public Long getfServiceId() {
		return fServiceId;
	}

	public void setfServiceId(Long fServiceId) {
		this.fServiceId = fServiceId;
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

	public String getfLocate() {
		return fLocate;
	}

	public void setfLocate(String fLocate) {
		this.fLocate = fLocate;
	}

	public Integer getfFloor() {
		return fFloor;
	}

	public void setfFloor(Integer fFloor) {
		this.fFloor = fFloor;
	}

	public String getfSystem() {
		return fSystem;
	}

	public void setfSystem(String fSystem) {
		this.fSystem = fSystem;
	}

	public Double getfPx() {
		return fPx;
	}

	public void setfPx(Double fPx) {
		this.fPx = fPx;
	}

	public Double getfPy() {
		return fPy;
	}

	public void setfPy(Double fPy) {
		this.fPy = fPy;
	}

	public Integer getfRoom() {
		return fRoom;
	}

	public void setfRoom(Integer fRoom) {
		this.fRoom = fRoom;
	}

}
