package com.infrastructure.util;
/**
 * Created by suyl on 2016/5/3
 */
public class NameValuePair {

	private String name; // 名称
	private String value; // 值
	private int flag; // URL编码标识 0：不编码 1：编码
	private String charsetName; // 字符编码名称，例如：GBK或者UTF-8等等

	public NameValuePair() {
		super();
	}

	public NameValuePair(String name, String value, int flag, String charsetName) {
		super();
		this.name = name;
		this.value = value;
		this.flag = flag;
		this.charsetName = charsetName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

}
