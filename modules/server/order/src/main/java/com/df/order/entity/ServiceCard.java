package com.df.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Table(name = "SERVICE_CARD")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public class ServiceCard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(initialValue = 1, name = "SERVICE_CARD_ID_SEQUENCE", sequenceName = "SERVICE_CARD_ID_SEQUENCE")
	@Column(name = "ID")
	private long id;

	@ElementCollection(targetClass = String.class)
	@Column(name = "TABLE_CODE", length = 255)
	@CollectionTable(name = "SERVICE_TABLE", joinColumns = @JoinColumn(name = "SERVICE_ID"))
	private List<String> tables;

	@Column(nullable = false, name = "STORE_CODE", length = 64)
	private String storeCode;

	@Column(nullable = true, name = "ORDER_ID")
	private Long orderId;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "CREATED_TIME")
	private Date createdTime;

	public ServiceCard() {
	}

	public ServiceCard(String storeCode, String tableCode) {
		this.storeCode = storeCode;
		this.addTable(tableCode);
	}

	public long getId() {
		return id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public void addTable(String... tableCodes) {
		if (tableCodes != null) {
			if (this.tables == null) {
				this.tables = new ArrayList<String>();
			}
			for (String tableCode : tableCodes) {
				this.tables.add(tableCode);
			}
		}
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@PrePersist
	protected void fillDefaultValue() {
		if (this.createdTime == null) {
			this.setCreatedTime(new Date());
		}
	}
}
