package com.gmy.cnmo.entity;

public class AiMeiZhiImageDetail {
	private String id;
	private String isCollected;
	private String title;
	private String date;
	private String author;
	private String docUrl;
	private String type;
	private String cid;
	private String[] content;
	private String[] picurl;
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
	public String getDocUrl() {
		return docUrl;
	}
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String[] getContent() {
		return content;
	}
	public void setContent(String[] content) {
		this.content = content;
	}
	public String[] getPicurl() {
		return picurl;
	}
	public void setPicurl(String[] picurl) {
		this.picurl = picurl;
	}

	
}
