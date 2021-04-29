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
class WoofyApplicationTests {
	private final WooriFunc wooriFunc = new WooriFunc(); 
	
	@Test
	void wooriApiTest01() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		System.out.println(wooriFunc.getCellCerti());
	}

}
