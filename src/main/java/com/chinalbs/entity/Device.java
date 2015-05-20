package com.chinalbs.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * Entity - 设备
 * 
 * 该Entity 不用使用JPA ,因为会去连接客户数据库来获取数据
 * 
 * @author shijun
 * 
 */
@Entity
@Table(name = "us_device")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "us_device_sequence")
public class Device implements Serializable {

	/**
   * 
   */
	private static final long serialVersionUID = 6414443084806048027L;

	/** 设备IMEI 号码 */
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String fSn;
	/**
	 * 是否虚拟设备（0：真实设备，1:虚拟设备）
	 */
	private Isvirtual fIsvirtual;

	/** 设备协议 */
	private Integer fProtocol;

	/** 设备sim卡号 */
	private String fPhone;

	/** 设备类型(车，宠物，人) */
	private Integer fType;

	/** 设备状态(启用:1,禁用:2) */
	private Integer fStatus;

	/** 上传时间 */
	@Transient
	private Date uploadDate;

	/** 第一次上传时间 */
	@Transient
	private Date firstUploadDate;

	/** 最后一次上传时间 */
	@Transient
	private Date lastUploadDate;

	/** 是否已经绑定 */
	@Transient
	private Boolean isBinding;

	/** 是否上传位置 */
	@Transient
	private Boolean isUploadSpot;

	/** 设备所属服务用户(跟绑定用户不是一个概念) */
	private Long fServiceId;

	/** 绑定该设备的用户id */
	@Transient
	private Long targetId;

	/** 时间 */
	private Long fStamp;

	private String fAppName;

	private Integer fFenceSwitch;

	private Integer fMoveingSwitch;

	private Integer fSpeedingSwitch;

	private Integer fTick;

	private Integer fSpeedThreshold;

	private String fIcon;

	private Float fWeight;

	private Float fHeight;

	private Long fBirth;

	private Integer fAge;

	private String fSosNum;

	@Transient
	// 指挥机sn
	private String conductorSn;

	/** 文件 */
	@Transient
	private MultipartFile file;

	public enum Isvirtual {
		YES, NO;
	}

	public void setfStatus(Integer fStatus) {
		this.fStatus = fStatus;
	}

	public Integer getfStatus() {
		return fStatus;
	}

	public String getfSn() {
		return fSn;
	}

	public void setfSn(String fSn) {
		this.fSn = fSn;
	}

	public String getfPhone() {
		return fPhone;
	}

	public void setfPhone(String fPhone) {
		this.fPhone = fPhone;
	}

	public Isvirtual getfIsvirtual() {
		return fIsvirtual;
	}

	public void setfIsvirtual(Isvirtual fIsvirtual) {
		this.fIsvirtual = fIsvirtual;
	}

	public Integer getfProtocol() {
		return fProtocol;
	}

	public void setfProtocol(Integer fProtocol) {
		this.fProtocol = fProtocol;
	}

	public Integer getfType() {
		return fType;
	}

	public void setfType(Integer fType) {
		this.fType = fType;
	}

	public Long getfServiceId() {
		return fServiceId;
	}

	public void setfServiceId(Long fServiceId) {
		this.fServiceId = fServiceId;
	}

	public Date getFirstUploadDate() {
		return firstUploadDate;
	}

	public void setFirstUploadDate(Date firstUploadDate) {
		this.firstUploadDate = firstUploadDate;
	}

	public Date getLastUploadDate() {
		return lastUploadDate;
	}

	public void setLastUploadDate(Date lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	public Boolean getIsBinding() {
		return isBinding;
	}

	public void setIsBinding(Boolean isBinding) {
		this.isBinding = isBinding;
	}

	public Boolean getIsUploadSpot() {
		return isUploadSpot;
	}

	public void setIsUploadSpot(Boolean isUploadSpot) {
		this.isUploadSpot = isUploadSpot;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getfStamp() {
		return fStamp;
	}

	public void setfStamp(Long fStamp) {
		this.fStamp = fStamp;
	}

	public String getfAppName() {
		return fAppName;
	}

	public void setfAppName(String fAppName) {
		this.fAppName = fAppName;
	}

	public Integer getfFenceSwitch() {
		return fFenceSwitch;
	}

	public void setfFenceSwitch(Integer fFenceSwitch) {
		this.fFenceSwitch = fFenceSwitch;
	}

	public Integer getfMoveingSwitch() {
		return fMoveingSwitch;
	}

	public void setfMoveingSwitch(Integer fMoveingSwitch) {
		this.fMoveingSwitch = fMoveingSwitch;
	}

	public Integer getfSpeedingSwitch() {
		return fSpeedingSwitch;
	}

	public void setfSpeedingSwitch(Integer fSpeedingSwitch) {
		this.fSpeedingSwitch = fSpeedingSwitch;
	}

	public Integer getfTick() {
		return fTick;
	}

	public void setfTick(Integer fTick) {
		this.fTick = fTick;
	}

	public Integer getfSpeedThreshold() {
		return fSpeedThreshold;
	}

	public void setfSpeedThreshold(Integer fSpeedThreshold) {
		this.fSpeedThreshold = fSpeedThreshold;
	}

	public String getfIcon() {
		return fIcon;
	}

	public void setfIcon(String fIcon) {
		this.fIcon = fIcon;
	}

	public Float getfWeight() {
		return fWeight;
	}

	public void setfWeight(Float fWeight) {
		this.fWeight = fWeight;
	}

	public Float getfHeight() {
		return fHeight;
	}

	public void setfHeight(Float fHeight) {
		this.fHeight = fHeight;
	}

	public Long getfBirth() {
		return fBirth;
	}

	public void setfBirth(Long fBirth) {
		this.fBirth = fBirth;
	}

	public Integer getfAge() {
		return fAge;
	}

	public void setfAge(Integer fAge) {
		this.fAge = fAge;
	}

	public String getfSosNum() {
		return fSosNum;
	}

	public void setfSosNum(String fSosNum) {
		this.fSosNum = fSosNum;
	}

	public String getConductorSn() {
		return conductorSn;
	}

	public void setConductorSn(String conductorSn) {
		this.conductorSn = conductorSn;
	}

}
