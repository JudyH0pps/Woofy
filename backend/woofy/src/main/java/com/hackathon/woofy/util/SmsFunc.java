package com.hackathon.woofy.util;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.woofy.config.Keys;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

public class SmsFunc {

	private Keys keys = new Keys();

	public void sendMessage(String to, String content) {
		String api_key = keys.getSmsAppKey();
	    String api_secret = keys.getSmsSecretKey();
	    Message coolsms = new Message(api_key, api_secret);

	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("from", keys.getSmsSenderNumber()); // �߽Ź�ȣ
	    params.put("to", to); // ���Ź�ȣ
	    params.put("text", content); // ���ڳ���    
	    params.put("type", "LMS"); // Message type ( SMS, LMS, MMS, ATA )
	    params.put("app_version", "JAVA SDK v1.2"); // application name and version

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
//	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	}
}
