package com.chinalbs.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "rd_enterprise_to_conductor")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "rd_enterprise_to_conductor_sequence")
public class EnterpriseToConductor implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
	private long id;

	@Column(name = "enterprise_id")
	private long enterpriseId;

	@Column(name = "conductor_id")
	private long conductorId;

	@Column(name = "create_time")
	private Date createTime;

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public long getConductorId() {
		return conductorId;
	}

	public void setConductorId(long conductorId) {
		this.conductorId = conductorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
