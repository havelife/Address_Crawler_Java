package com.chris.hibernate.model;
/**
 *state enum{1, 2, 3}
 * 
 * 1. 是在某个页面上被采集到的url
 * 2. 它这个url页面上的所有其他url已经被采集
 * 3. 这个url页面上的地址信息已经被收集
 */
public class Url {
	private long id;
	private String url;
    private String state;
    private String pcity;
    private String hcity;
    private String pdistrict;
    private String hdistrict;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPcity() {
		return pcity;
	}
	public void setPcity(String pcity) {
		this.pcity = pcity;
	}
	public String getHcity() {
		return hcity;
	}
	public void setHcity(String hcity) {
		this.hcity = hcity;
	}
	public String getPdistrict() {
		return pdistrict;
	}
	public void setPdistrict(String pdistrict) {
		this.pdistrict = pdistrict;
	}
	public String getHdistrict() {
		return hdistrict;
	}
	public void setHdistrict(String hdistrict) {
		this.hdistrict = hdistrict;
	}
	public String toString(){
		return "id:" + id +
				", url:" + url + 
				", state:" + state +
				", pcity:" + pcity +
				", hcity:" + hcity +
				", pdistrict:" + pdistrict +
				", hdistrict:" + hdistrict;
	}
}
