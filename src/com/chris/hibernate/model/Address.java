package com.chris.hibernate.model;

public class Address {
	private long id;
	private String smladd;
	private String bigadd;
	private String district;
	private String city;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSmladd() {
		return smladd;
	}
	public void setSmladd(String smladd) {
		this.smladd = smladd;
	}
	public String getBigadd() {
		return bigadd;
	}
	public void setBigadd(String bigadd) {
		this.bigadd = bigadd;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String toString(){
		return "id:" + id +
				" city:" + city +
				" district:" + district +
				" bigadd:" + bigadd +
				" smladd:" + smladd;
	}
}
