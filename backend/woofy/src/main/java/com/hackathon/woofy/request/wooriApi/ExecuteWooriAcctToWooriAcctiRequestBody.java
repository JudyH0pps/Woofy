package com.hackathon.woofy.request.wooriApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @AllArgsConstructor
public class ExecuteWooriAcctToWooriAcctiRequestBody {

	private String WDR_ACNO; // 출금계좌번호
	private String TRN_AM; // 거래금액
	private String RCV_BKCD; // 입금은행코드
	private String RCV_ACNO; // 입금계좌번호
	private String PTN_PBOK_PRNG_TXT; // 상대통장인자내용

}
