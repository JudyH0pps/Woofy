package com.hackathon.woofy;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hackathon.woofy.config.Keys;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@SpringBootTest
public class SMSTest {
	private Keys keys = new Keys();
	
	@Test
	void SMSSendTest() {
		String api_key = keys.getSmsAppKey();
	    String api_secret = keys.getSmsSecretKey();
	    Message coolsms = new Message(api_key, api_secret);

	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("from", keys.getSmsSenderNumber()); // �߽Ź�ȣ
	    params.put("to", keys.getSmsDebugReceiver()); // ���Ź�ȣ
	    params.put("type", "SMS"); // Message type ( SMS, LMS, MMS, ATA )
	    params.put("text", "Test Message 1234"); // ���ڳ���    
	    params.put("app_version", "JAVA SDK v1.2"); // application name and version

	    // Optional parameters for your own needs
	    // params.put("image", "desert.jpg"); // image for MMS. type must be set as "MMS"
	    // params.put("image_encoding", "binary"); // image encoding binary(default), base64 
	    // params.put("mode", "test"); // 'test' ���. ������ �߼۵��� ������ ���۳����� 60 �����ڵ�� ��ϴ�. ������ ĳ���� ������ ������ ���� �˴ϴ�.
	    // params.put("delay", "10"); // 0~20������ ������ �������� �ð��� �� �� �ֽ��ϴ�.
	    // params.put("force_sms", "true"); // Ǫ�� �� �˸��� �̿�ÿ��� ������ SMS�� �߼۵ǵ��� �� �� �ֽ��ϴ�.
	    // params.put("refname", ""); // Reference name
	    // params.put("country", "KR"); // Korea(KR) Japan(JP) America(USA) China(CN) Default is Korea
	    // params.put("sender_key", "5554025sa8e61072frrrd5d4cc2rrrr65e15bb64"); // �˸��� ����� ���� �ʿ��մϴ�. ��û��� : http://www.coolsms.co.kr/AboutAlimTalk
	    // params.put("template_code", "C004"); // �˸��� template code �Դϴ�. �ڼ��� ������ http://www.coolsms.co.kr/AboutAlimTalk�� �������ּ���. 
	    // params.put("datetime", "20140106153000"); // Format must be(YYYYMMDDHHMISS) 2014 01 06 15 30 00 (2014 Jan 06th 3pm 30 00)
	    // params.put("mid", "mymsgid01"); // set message id. Server creates automatically if empty
	    // params.put("gid", "mymsg_group_id01"); // set group id. Server creates automatically if empty
	    // params.put("subject", "Message Title"); // set msg title for LMS and MMS
	    // params.put("charset", "euckr"); // For Korean language, set euckr or utf-8
	    // params.put("app_version", "Purplebook 4.1") // ���ø����̼� ����

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	    }
	}
}
