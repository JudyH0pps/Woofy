package com.hackathon.woofy;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hackathon.woofy.util.WooriFunc;

@SpringBootTest
public class WooriAPITest {
	private WooriFunc wooriFunc = new WooriFunc();
	
	@Test
	void getCellCertiTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		wooriFunc.getCellCerti("1", "01012345667", "Y", "홍길동", "111121", "1234567");
	}
	
	@Test
	void executeCellCertiTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		wooriFunc.executeCellCerti("901121", "245354", "112100", "MG97792233387924034995");
	}

	@Test
	void getAccBasicInfoTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		wooriFunc.getAccBasicInfo("1002123456789", "20210220", "KRW", "P");
	}

	@Test
	void executeWooriAcctToWooriAcctTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		wooriFunc.executeWooriAcctToWooriAcct("1002123456789", "500000", "020", "1002987654321", "보너스");
	}

	@Test
	void executeWooriAcctToOtherAcctTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		wooriFunc.executeWooriAcctToWooriAcct("1002123456789", "500000", "081", "110123456789", "월급여");
	}
	
}
