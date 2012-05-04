package com.chris.hibernate.model;

public class Office {
	private long id;
	private String addr;
	private String name;
	private String city;
	private String tel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String toString(){
		return "id:" + id +
				" addr:" + addr +
				" name:" + name +
				" city:" + city +
				" tel:" + tel;
	}
	public Office(String addr, String name, String city, String tel){
		this.addr = addr;
		this.name = name;
		this.city = city;
		this.tel = tel;
	}
	public Office(){
	}
}
