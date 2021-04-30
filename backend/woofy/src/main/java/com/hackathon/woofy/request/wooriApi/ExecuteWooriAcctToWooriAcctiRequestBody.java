package com.hackathon.woofy.request.wooriApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class ExecuteWooriAcctToWooriAcctiRequestBody {

	private String WDR_ACNO;
	private String TRN_AM;
	private String RCV_BKCD;
	private String RCV_ACNO;
	private String PTN_PBOK_PRNG_TXT;

}
