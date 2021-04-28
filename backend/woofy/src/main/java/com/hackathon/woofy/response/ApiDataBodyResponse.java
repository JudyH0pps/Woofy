package com.hackathon.woofy.response;

public class ApiDataBodyResponse {

	private String CRTF_UNQ_NO; // 계좌번호
	private String VCNT;
	
	// 휴대폰 인증 response
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
