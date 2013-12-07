package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@SecondaryTable(name = "STORE_IMAGE", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "STORE_ID") })
@Indexes({
        @Index(name = "IDX_STORE_T_CODE", unique = true, columnNames = { "CODE", MultiTenantSupport.TENANT_COLUMN }),
        @Index(name = "IDX_STORE_T_NAME", unique = true, columnNames = { "NAME", MultiTenantSupport.TENANT_COLUMN }) })
public class Store extends MasterData {

	@Column(nullable = false, name = "NAME", length = 255)
	private String name;

	@Lob
	@Column(name = "DESCRIPTION")
	private String description;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "STORE_ADDRESS", joinColumns = @JoinColumn(name = "STORE_ID"))
	@JoinFetch(JoinFetchType.OUTER)
	private List<Address> addresses;

	@Embedded
	@AttributeOverrides({
	        @AttributeOverride(name = "imageId", column = @Column(table = "STORE_IMAGE", name = "IMG_ID")),
	        @AttributeOverride(name = "width", column = @Column(table = "STORE_IMAGE", name = "WIDTH")),
	        @AttributeOverride(name = "heigth", column = @Column(table = "STORE_IMAGE", name = "HEIGTH")),
	        @AttributeOverride(name = "format", column = @Column(table = "STORE_IMAGE", name = "FORMAT"))
	})
	@JoinFetch(JoinFetchType.OUTER)
	private PictureRef image;

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

	public Address getAddress() {
		if (addresses != null && addresses.size() > 0) {
			return addresses.get(0);
		}
		return null;
	}

	public void setAddress(Address address) {
		if (addresses == null) {
			addresses = new ArrayList<Address>();
		}
		this.addresses.add(address);
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

	public PictureRef getImage() {
		return image;
	}

	public void setImage(PictureRef image) {
		this.image = image;
	}

	public void createImageLink(ImageLinkCreator creator) {
		if (this.image != null) {
			image.setImageLink(creator.createImageLink(image.getImageId()));
		}
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
