package com.hackathon.woofy.response;

public class ApiResponse {

	// Response와 같은 이름이어야한다.
	private ApiDataHeaderResponse dataHeader;
	private ApiDataBodyResponse dataBody;
	
	@Override
	public String toString() {
		return "ApiResponse [dataHeader=" + dataHeader + ", dataBody=" + dataBody + "]";
	}
	
	
}
