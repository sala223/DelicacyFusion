package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

import com.df.blobstore.image.http.ImageReference;
import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Indexes({
        @Index(name = "IDX_STORE_T_CODE", unique = true, columnNames = { "CODE", MultiTenantSupport.TENANT_COLUMN }),
        @Index(name = "IDX_STORE_T_NAME", unique = true, columnNames = { "NAME", MultiTenantSupport.TENANT_COLUMN }) })
public class Store extends MasterData {

	@Column(nullable = false, name = "NAME", length = 255)
	private String name;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ADDRESS", length = 1024, nullable = false)
	private String address;

	@Transient
	private ImageReference image;

	@Column(length = 512, nullable = true, name = "IMG_ID")
	private String imageId;

	@Column(length = 32, nullable = false, name = "TELEPHONE1")
	private String telephone1;

	@Column(length = 32, name = "TELEPHONE2")
	private String telephone2;

	@Column(nullable = false, name = "BUSINESS_HOUR_FROM")
	private int businessHourFrom = -1;

	@Column(nullable = false, name = "BUSINESS_HOUR_TO")
	private int businessHourTo = -1;

	@Column(length = 512, name = "TRAFFIC_INFO")
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
