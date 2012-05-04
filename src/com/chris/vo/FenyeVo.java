package com.chris.vo;

public class FenyeVo {
	private String city;
	private String prefix;
	private String suffix;
	private int start;
	private int end;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public FenyeVo(){}
	public FenyeVo(String city, String prefix, int start, int end){
		this.city = city;
		this.prefix = prefix;
		this.suffix = "";
		this.start = start;
		this.end = end;
	}
	public FenyeVo(String city, String prefix, String suffix, int start, int end){
		this.city = city;
		this.prefix = prefix;
		this.suffix = suffix;
		this.start = start;
		this.end = end;
	}
	public String toString(){
		return  " city:" + city +
				" prefix:" + prefix +
				" suffix:" + suffix +
				" start:" + start +
				" end:" + end;
	}
}
