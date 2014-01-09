package com.df.masterdata.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.df.blobstore.image.http.ImageLinkCreator;

@XmlRootElement
@Entity
public class Item extends StoreAwareMasterData {

	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name="CODE",referencedColumnName="CODE") })
	@JsonIgnore
	private ItemTemplate itemTemplate;

	@Column(name = "PRICE", scale = 2)
	private Float price;

	Item() {
	}

	public Item(ItemTemplate itemTemplate, String storeCode) {
		this.itemTemplate = itemTemplate;
		this.setStoreCode(storeCode);
		this.setCode(itemTemplate.getCode());
	}

	public ItemTemplate getItemTemplate() {
		return itemTemplate;
	}

	@JsonProperty
	@XmlElement
	public float getPrice() {
		if (this.price == null) {
			return itemTemplate.getPrice();
		}
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@JsonProperty
	@XmlElement
	public String getName() {
		return itemTemplate.getName();
	}

	@JsonProperty
	@XmlElement
	public String getCode() {
		return itemTemplate.getCode();
	}

	@JsonProperty
	@XmlElement
	public List<String> getCategories() {
		return this.itemTemplate.getCategories();
	}

	@JsonProperty
	@XmlElement
	public ItemType getType() {
		return this.itemTemplate.getType();
	}

	@JsonProperty
	@XmlElement
	public List<PictureRef> getPictureSet() {
		return this.itemTemplate.getPictureSet();
	}

	public PictureRef getImageByImageId(String imageId) {
		return this.itemTemplate.getImageByImageId(imageId);
	}

	@JsonProperty
	@XmlElement
	public String getDescription() {
		return this.itemTemplate.getDescription();
	}

	@JsonProperty
	@XmlElement
	public String getCurrency() {
		return this.itemTemplate.getCurrency();
	}

	@JsonProperty
	@XmlElement
	public ItemUnit getItemUnit() {
		return this.itemTemplate.getItemUnit();
	}

	@Override
	public boolean isEnabled() {
		if (this.itemTemplate.isEnabled()) {
			return super.isEnabled();
		} else {
			return false;
		}
	}

	public void createImageLink(ImageLinkCreator creator) {
		this.itemTemplate.createImageLink(creator);
	}
}
