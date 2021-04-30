package com.hackathon.woofy.request.wooriApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ExecuteCellCertiRequestBody {

	private String RRNO_BFNB;
	private String ENCY_RRNO_LSNM;
	private String ENCY_SMS_CRTF_NO;
	private String CRTF_UNQ_NO;

	public ExecuteCellCertiRequestBody(String RRNO_BFNB, String ENCY_RRNO_LSNM, String ENCY_SMS_CRTF_NO, String CRTF_UNQ_NO) {
		this.RRNO_BFNB = RRNO_BFNB;
		this.ENCY_RRNO_LSNM = ENCY_RRNO_LSNM;
		this.ENCY_SMS_CRTF_NO = ENCY_SMS_CRTF_NO;
		this.CRTF_UNQ_NO = CRTF_UNQ_NO;
	}
}
