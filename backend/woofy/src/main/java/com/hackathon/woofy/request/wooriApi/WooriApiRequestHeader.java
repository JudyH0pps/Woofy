package com.hackathon.woofy.request.wooriApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class WooriApiRequestHeader {

	private String UTZPE_CNCT_IPAD;
	private String UTZPE_CNCT_MCHR_UNQ_ID;
	private String UTZPE_CNCT_TEL_NO_TXT;
	private String UTZPE_CNCT_MCHR_IDF_SRNO;
	private String UTZ_MCHR_OS_DSCD;
	private String UTZ_MCHR_OS_VER_NM;
	private String UTZ_MCHR_MDL_NM;
	private String UTZ_MCHR_APP_VER_NM;
	
	public WooriApiRequestHeader(String uTZPE_CNCT_IPAD, String uTZPE_CNCT_MCHR_UNQ_ID, String uTZPE_CNCT_TEL_NO_TXT,
			String uTZPE_CNCT_MCHR_IDF_SRNO, String uTZ_MCHR_OS_DSCD, String uTZ_MCHR_OS_VER_NM, String uTZ_MCHR_MDL_NM,
			String uTZ_MCHR_APP_VER_NM) {
		super();
		UTZPE_CNCT_IPAD = uTZPE_CNCT_IPAD;
		UTZPE_CNCT_MCHR_UNQ_ID = uTZPE_CNCT_MCHR_UNQ_ID;
		UTZPE_CNCT_TEL_NO_TXT = uTZPE_CNCT_TEL_NO_TXT;
		UTZPE_CNCT_MCHR_IDF_SRNO = uTZPE_CNCT_MCHR_IDF_SRNO;
		UTZ_MCHR_OS_DSCD = uTZ_MCHR_OS_DSCD;
		UTZ_MCHR_OS_VER_NM = uTZ_MCHR_OS_VER_NM;
		UTZ_MCHR_MDL_NM = uTZ_MCHR_MDL_NM;
		UTZ_MCHR_APP_VER_NM = uTZ_MCHR_APP_VER_NM;
	}
	
}
