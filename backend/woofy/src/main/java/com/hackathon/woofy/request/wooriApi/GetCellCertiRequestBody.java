package com.hackathon.woofy.request.wooriApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class GetCellCertiRequestBody {

	private String COMC_DIS;
	private String HP_NO;
	private String HP_CRTF_AGR_YN;
	private String FNM;
	private String RRNO_BFNB;
	private String ENCY_RRNO_LSNM;

	public GetCellCertiRequestBody(String COMC_DIS, String HP_NO, String HP_CRTF_AGR_YN, String FNM, String RRNO_BFNB, String ENCY_RRNO_LSNM) {
		this.COMC_DIS = COMC_DIS;
		this.HP_NO = HP_NO;
		this.HP_CRTF_AGR_YN = HP_CRTF_AGR_YN;
		this.FNM = FNM;
		this.RRNO_BFNB = RRNO_BFNB;
		this.ENCY_RRNO_LSNM = ENCY_RRNO_LSNM;
	}

}
