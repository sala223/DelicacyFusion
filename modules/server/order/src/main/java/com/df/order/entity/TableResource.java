package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.masterdata.entity.DiningTable;

@Entity
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public class TableResource implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum TableStatus {
		RESERVED, OCCUPIED, AVALIABLE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	@JsonIgnore
	@XmlTransient
	private long id;

	@Column(nullable = false, length = 32, name = "TABLE_CODE")
	private String tableCode;

	@Column(nullable = false, length = 32, name = "STATUS")
	private TableStatus status;

	@Column(nullable = true, name = "ORDER_ID")
	private Long orderId;

	@XmlTransient
	@JsonIgnore
	@Column(nullable = false, name = "STORE_CODE", length = 64)
	private String storeCode;

	TableResource() {
	}

	public TableResource(String storeCode, String tableCode) {
		this.storeCode = storeCode;
		this.tableCode = tableCode;
		this.status = TableStatus.AVALIABLE;
	}

	public TableResource(DiningTable table) {
		this(table.getStoreCode(), table.getCode());
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
}
