package com.hackathon.woofy.response;

public class ApiDataBodyResponse {

	private String CRTF_UNQ_NO;
	private String VCNT;

	public ApiDataBodyResponse(String CRTF_UNQ_NO, String VCNT) {
		super();
		this.CRTF_UNQ_NO = CRTF_UNQ_NO;
		this.VCNT = VCNT;
	}

	@Override
	public String toString() {
		return "ApiDataBodyResponse [CRTF_UNQ_NO=" + CRTF_UNQ_NO + ", VCNT=" + VCNT + "]";
	}
	
	

}
