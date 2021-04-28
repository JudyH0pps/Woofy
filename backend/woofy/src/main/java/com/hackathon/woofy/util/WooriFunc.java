package com.hackathon.woofy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.Gson;
import com.hackathon.woofy.config.Keys;
import com.hackathon.woofy.request.ApiDataBodyRequest;
import com.hackathon.woofy.request.ApiDataHeaderRequest;
import com.hackathon.woofy.response.ApiResponse;


public class WooriFunc {
	
	private Keys keys;

	// �ֹε�Ϲ�ȣ ��ȣȭ
	public String getAES256EncStr(String BFNB) {

		try {
			String str = BFNB; // ��ȣȭ ��� �ֹε�Ϲ�ȣ
			String key = keys.getWooriSecretkey(); // ��ũ�� Ű
			String iv = "0000000000000000";

			Key keySpec;
			byte[] keyBytes = new byte[key.length()];
			byte[] b = key.getBytes("UTF-8");
			int len = b.length;

			if (len > keyBytes.length) {
				len = keyBytes.length;
			}

			System.arraycopy(b, 0, keyBytes, 0, len);
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			String encStr = new String(Base64.encodeBase64(encrypted));

			System.out.println("�ֹι�ȣ ��ȣȭ " + encStr);

			return encStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return BFNB;
		}
	}

	public String getCellCerti() throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		StringBuilder urlBuilder = new StringBuilder("https://openapi.wooribank.com:444/oai/wb/v1/login/getCellCerti");
		URL url = new URL(urlBuilder.toString());

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("appKey", keys.getWooriAppKey());
		conn.setRequestProperty("Content-Type", "application/json");

		// === Map -> JSON
		Map<String, Object> map = new HashMap<>();

		ApiDataHeaderRequest apiDataHeaderRequest = new ApiDataHeaderRequest(null, null, null, null, null, null, null, null);
		map.put("dataHeader", apiDataHeaderRequest);

		String enc = getAES256EncStr("123456"); // �ֹε�Ϲ�ȣ ��ȣȭ

		ApiDataBodyRequest apiDataBodyRequest = new ApiDataBodyRequest("1", "01064103518", "Y", "�ȼ�ȣ", "950128", enc);
		map.put("dataBody", apiDataBodyRequest);

		Gson gson = new Gson();
		String json = gson.toJson(map);
		byte[] body = json.getBytes("utf-8");
		// Map -> JSON ===

		conn.setFixedLengthStreamingMode(body.length);
		conn.setDoOutput(true);

		OutputStream out = conn.getOutputStream();
		out.write(body);

		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		rd.close();
		conn.disconnect();

		System.out.println(sb.toString());

		// ����� String -> JSON �Ľ�
		gson = new Gson();
		ApiResponse apiResponse = gson.fromJson(sb.toString(), ApiResponse.class);
		System.out.println(apiResponse.toString());

		return sb.toString();
	}
}
