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
import com.hackathon.woofy.request.wooriApi.ExecuteCellCertiRequestBody;
import com.hackathon.woofy.request.wooriApi.ExecuteWooriAcctToOtherAcctRequestBody;
import com.hackathon.woofy.request.wooriApi.ExecuteWooriAcctToWooriAcctiRequestBody;
import com.hackathon.woofy.request.wooriApi.GetAccBasicInfoRequestBody;
import com.hackathon.woofy.request.wooriApi.GetCellCertiRequestBody;
import com.hackathon.woofy.request.wooriApi.WooriApiRequestHeader;


public class WooriFunc {

   private Keys keys = new Keys();

   private String getAES256EncStr(String BFNB) {

      try {
         String str = BFNB;
         String key = keys.getWooriSecretkey();
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

         return encStr.toString();
      } catch (Exception e) {
         e.printStackTrace();
         return BFNB;
      }
   }

   private String WooriAPIRequest(String targetURL, Map<String, Object> targetRequestBodyMap)
         throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
         InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      StringBuilder urlBuilder = new StringBuilder(targetURL);
      URL url = new URL(urlBuilder.toString());

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setRequestProperty("appKey", keys.getWooriAppKey());
      conn.setRequestProperty("Content-Type", "application/json");

      Gson gson = new Gson();
      String json = gson.toJson(targetRequestBodyMap);
      byte[] body = json.getBytes();

      System.out.println(body);

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

      System.out.println("FROM: " + targetURL + " " + sb.toString());

      // String -> JSON
      return sb.toString();
   }

   public String getCellCerti(String COMC_DIS, String HP_NO, String HP_CRTF_AGR_YN, String FNM, String RRNO_BFNB,
         String ENCY_RRNO_LSNM) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
         NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      String targetURL = "https://openapi.wooribank.com:444/oai/wb/v1/login/getCellCerti";
      String enc = getAES256EncStr(ENCY_RRNO_LSNM);

      WooriApiRequestHeader wooriApiRequestHeader = new WooriApiRequestHeader("", "", "", "", "", "", "", "");
      GetCellCertiRequestBody getCellCertiRequestBody = new GetCellCertiRequestBody(COMC_DIS, HP_NO, HP_CRTF_AGR_YN,
            FNM, RRNO_BFNB, enc);

      Map<String, Object> targetRequestBodyMap = new HashMap<>();

      targetRequestBodyMap.put("dataHeader", wooriApiRequestHeader);
      targetRequestBodyMap.put("dataBody", getCellCertiRequestBody);

      return WooriAPIRequest(targetURL, targetRequestBodyMap);
   }

   public String executeCellCerti(String RRNO_BFNB, String ENCY_RRNO_LSNM, String ENCY_SMS_CRTF_NO, String CRTF_UNQ_NO)
         throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
         InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      String targetURL = "https://openapi.wooribank.com:444/oai/wb/v1/login/executeCellCerti";
      String enc = getAES256EncStr(ENCY_RRNO_LSNM);

      WooriApiRequestHeader wooriApiRequestHeader = new WooriApiRequestHeader("", "", "", "", "", "", "", "");
      ExecuteCellCertiRequestBody executeCellCertiRequestBody = new ExecuteCellCertiRequestBody(RRNO_BFNB, enc,
            ENCY_SMS_CRTF_NO, CRTF_UNQ_NO);

      Map<String, Object> targetRequestBodyMap = new HashMap<>();

      targetRequestBodyMap.put("dataHeader", wooriApiRequestHeader);
      targetRequestBodyMap.put("dataBody", executeCellCertiRequestBody);

      return WooriAPIRequest(targetURL, targetRequestBodyMap);
   }

   public String getAccBasicInfo(String INQ_ACNO, String INQ_BAS_DT, String ACCT_KND, String INQ_CUCD)
         throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
         InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      String targetURL = "https://openapi.wooribank.com:444/oai/wb/v1/finance/getAccBasicInfo";

      WooriApiRequestHeader wooriApiRequestHeader = new WooriApiRequestHeader("", "", "", "", "", "", "", "");
      GetAccBasicInfoRequestBody getAccBasicInfoRequestBody = new GetAccBasicInfoRequestBody(INQ_ACNO, INQ_BAS_DT,
            ACCT_KND, INQ_CUCD);

      Map<String, Object> targetRequestBodyMap = new HashMap<>();

      targetRequestBodyMap.put("dataHeader", wooriApiRequestHeader);
      targetRequestBodyMap.put("dataBody", getAccBasicInfoRequestBody);
      
      return WooriAPIRequest(targetURL, targetRequestBodyMap);
   }

   public String executeWooriAcctToWooriAcct(String WDR_ACNO, String TRN_AM, String RCV_BKCD, String RCV_ACNO,
         String PTN_PBOK_PRNG_TXT) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
         NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      String targetURL = "https://openapi.wooribank.com:444/oai/wb/v1/trans/executeWooriAcctToWooriAcct";

      WooriApiRequestHeader wooriApiRequestHeader = new WooriApiRequestHeader("", "", "", "", "", "", "", "");
      ExecuteWooriAcctToWooriAcctiRequestBody executeWooriAcctToWooriAcctiRequestBody = new ExecuteWooriAcctToWooriAcctiRequestBody(
            WDR_ACNO, TRN_AM, RCV_BKCD, RCV_ACNO, PTN_PBOK_PRNG_TXT);

      Map<String, Object> targetRequestBodyMap = new HashMap<>();

      targetRequestBodyMap.put("dataHeader", wooriApiRequestHeader);
      targetRequestBodyMap.put("dataBody", executeWooriAcctToWooriAcctiRequestBody);
      
      return WooriAPIRequest(targetURL, targetRequestBodyMap);
   }

   public String executeWooriAcctToOtherAcct(String WDR_ACNO, String TRN_AM, String RCV_BKCD, String RCV_ACNO,
         String PTN_PBOK_PRNG_TXT) throws IOException, InvalidKeyException, NoSuchAlgorithmException,
         NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

      String targetURL = "https://openapi.wooribank.com:444/oai/wb/v1/trans/executeWooriAcctToOtherAcct";

      WooriApiRequestHeader wooriApiRequestHeader = new WooriApiRequestHeader("", "", "", "", "", "", "", "");
      ExecuteWooriAcctToOtherAcctRequestBody executeWooriAcctToOtherAcctRequestBody = new ExecuteWooriAcctToOtherAcctRequestBody(
            WDR_ACNO, TRN_AM, RCV_BKCD, RCV_ACNO, PTN_PBOK_PRNG_TXT);

      Map<String, Object> targetRequestBodyMap = new HashMap<>();

      targetRequestBodyMap.put("dataHeader", wooriApiRequestHeader);
      targetRequestBodyMap.put("dataBody", executeWooriAcctToOtherAcctRequestBody);

      return WooriAPIRequest(targetURL, targetRequestBodyMap);
   }

}