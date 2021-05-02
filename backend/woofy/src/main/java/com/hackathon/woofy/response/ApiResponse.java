package com.hackathon.woofy.response;

public class ApiResponse {
	private ApiDataHeaderResponse dataHeader;
	private ApiDataBodyResponse dataBody;
	
	@Override
	public String toString() {
		return "ApiResponse [dataHeader=" + dataHeader + ", dataBody=" + dataBody + "]";
	}
	
	
}
