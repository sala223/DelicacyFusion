package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.df.blobstore.image.http.ImageReference;
import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Indexes({
        @Index(name = "IDX_STORE_T_CODE", unique = true, columnNames = { "CODE", MultiTenantSupport.TENANT_COLUMN }),
        @Index(name = "IDX_STORE_T_NAME", unique = true, columnNames = { "NAME", MultiTenantSupport.TENANT_COLUMN }) })
public class Store extends MasterData {

	@NotEmpty(message = "{store.name.NotEmpty}")
	@Size(message = "{store.name.Size}", max = 255)
	@Column(nullable = false, name = "NAME", length = 255)
	private String name;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@NotEmpty(message = "{store.address.NotEmpty}")
	@Size(message = "{store.address.Size}", max = 255)
	@Column(name = "ADDRESS", length = 1024, nullable = false)
	private String address;

	@Transient
	private ImageReference image;

	@Column(length = 512, nullable = true, name = "IMG_ID")
	private String imageId;

	@Column(length = 32, nullable = false, name = "TELEPHONE1")
	@Size(message = "{store.telephone.Size}", max = 32)
	private String telephone1;

	@Column(length = 32, name = "TELEPHONE2")
	@Size(message = "{store.telephone.Size}", max = 32)
	private String telephone2;

	@Range(message = "{store.businessHourFrom.Range}", min = 0, max = 1440)
	@Column(nullable = false, name = "BUSINESS_HOUR_FROM")
	private int businessHourFrom = 0;

	@Range(message = "{store.businessHourTo.Range}", min = 0, max = 1440)
	@Column(nullable = false, name = "BUSINESS_HOUR_TO")
	private int businessHourTo = 0;

	@Column(length = 1024, name = "TRAFFIC_INFO")
	@Size(message = "{store.trafficInfo.Size}", max = 1024)
	private String trafficInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public int getBusinessHourFrom() {
		return businessHourFrom;
	}

	public void setBusinessHourFrom(int businessHourFrom) {
		this.businessHourFrom = businessHourFrom;
	}

	public int getBusinessHourTo() {
		return businessHourTo;
	}

	public void setBusinessHourTo(int businessHourTo) {
		this.businessHourTo = businessHourTo;
	}

	public String getTrafficInfo() {
		return trafficInfo;
	}

	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	public ImageReference getImage() {
		return image;
	}

	public void setImage(ImageReference image) {
		this.image = image;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Override
	protected void fillDefaultValue() {
		super.fillDefaultValue();
		if (this.businessHourFrom < 0) {
			this.businessHourFrom = 60 * 7;
		}
		if (this.businessHourTo < 0) {
			this.businessHourTo = 60 * 22;
		}
	}
}
