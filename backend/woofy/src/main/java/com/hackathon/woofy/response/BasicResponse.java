package com.hackathon.woofy.response;

public class BasicResponse {

	public boolean status;
	public String data;
	public Object dataHeader;
	public Object dataBody;
	
	@Override
	public String toString() {
		return "BasicResponse [status=" + status + ", data=" + data + ", dataHeader=" + dataHeader + ", dataBody="
				+ dataBody + "]";
	}
}
