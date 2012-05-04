package com.chris.hibernate.model;

public class Fenye {
	private long id;
	private String city;
	private String page;
	private String content;
	private String url;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString(){
		return "id:" + id +
				" city:" + city +
				" page:" + page +
				" content:" + content +
				" url:" + url;
	}
	public Fenye(String city, String page, String content, String url){
		this.city = city;
		this.page = page;
		this.content = content;
		this.url = url;
	}
	public Fenye(){
	}
}
