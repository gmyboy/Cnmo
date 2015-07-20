package com.gmy.cnmo.entity;

public class ImageDetail {
	private String id;
	private String isCollected;
	private String title;
	private String date;
	private String author;
	private String[] picUrl;
	private String cid;
	private String digest;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(String isCollected) {
		this.isCollected = isCollected;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String[] getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String[] picUrl) {
		this.picUrl = picUrl;
	}

}
