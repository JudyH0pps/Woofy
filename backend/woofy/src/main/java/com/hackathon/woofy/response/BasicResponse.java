package com.hackathon.woofy.response;

public class BasicResponse {
	public String status;
	public Object dataHeader;
	public Object dataBody;
	
	@Override
	public String toString() {
		return "BasicResponse [status=" + status + ", dataHeader=" + dataHeader + ", dataBody="
				+ dataBody + "]";
	}
}
