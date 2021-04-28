package com.hackathon.woofy.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDataBodyRequest {

	private String COMC_DIS;
	private String HP_NO;
	private String HP_CRTF_AGR_YN;
	private String FNM;
	private String RRNO_BFNB; // 주민등록번호
	private String ENCY_RRNO_LSNM;

	// 휴대폰 인증을 위한 생성자
	public ApiDataBodyRequest(String COMC_DIS, String HP_NO, String HP_CRTF_AGR_YN, String FNM, String RRNO_BFNB,
			String ENCY_RRNO_LSNM) {
		this.COMC_DIS = COMC_DIS;
		this.HP_NO = HP_NO;
		this.HP_CRTF_AGR_YN = HP_CRTF_AGR_YN;
		this.FNM = FNM;
		this.RRNO_BFNB = RRNO_BFNB;
		this.ENCY_RRNO_LSNM = ENCY_RRNO_LSNM;
	}

	@Override
	public String toString() {
		return "ApiDataBody [COMC_DIS=" + COMC_DIS + ", HP_NO=" + HP_NO + ", HP_CRTF_AGR_YN=" + HP_CRTF_AGR_YN
				+ ", FNM=" + FNM + ", RRNO_BFNB=" + RRNO_BFNB + ", ENCY_RRNO_LSNM=" + ENCY_RRNO_LSNM + "]";
	}

}
