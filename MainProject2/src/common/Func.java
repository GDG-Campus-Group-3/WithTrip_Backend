package common;

import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

public class Func {
	public static boolean checkInjectionBool(String str) {
		if(str == null || str.equals("")) {
			return false;
		}
		
		str = str.toUpperCase();
		
		String[] bannedWords = {"SELECT", "UNION", "INSERT", "UPDATE", "DELETE", "DROP", "DECLARE", "\"", "#", "*", "'", "=", "<if"};
		
		for(int i = 0; i < bannedWords.length; i++) {
			if(str.indexOf(i) != -1) {
				return false;
			}
		}
		
		for(int i = 0; i < str.length(); i++) {
			char codeNum = str.charAt(i);
			
			if( (codeNum > 0xd7ff || 0xac00 > codeNum) && (codeNum > 0xa97f || 0xa960 > codeNum) && (codeNum > 0x3163 || 0x3130 > codeNum) && (codeNum > 0x11ff || 0x1100 > codeNum) )
			{
				if( (codeNum > 0x005a || 0x0041 > codeNum) && (codeNum > 0x007a || 0x0061 > codeNum) && (codeNum > 0x0039 || 0x0030 > codeNum) )
				{
					if( (codeNum > 0x2fdf || 0x2e80 > codeNum) && (codeNum > 0x4dbf || 0x3400 > codeNum) && (codeNum > 0x9fff || 0x4e00 > codeNum) && (codeNum > 0xfaff || 0xf900 > codeNum) )
					{
						return false;
					}
				}
			}
		}

		return true;
	}
	
	public static String checkInjection(String str) {
		if (str != null) {
			str = str.toUpperCase();
			str = str.replace("SELECT", "");
			str = str.replace("UNION", "");
			str = str.replace("INSERT", "");
			str = str.replace("UPDATE", "");
			str = str.replace("DELETE", "");
			str = str.replace("DROP", "");
			str = str.replace("DECLARE", "");
			str = str.replace("\"", "");
			str = str.replace("#", "");
			str = str.replace("*", "");
			str = str.replace("'", "");
			str = str.replace("=", "");
			str = str.replace("<if", "");
			str = str.replace("?", "");

			//str = str.replace(";", "");
			//str = str.replaceAll("--", "");
			//str = str.replaceAll("*/", "");
			//str = str.replaceAll("\\", "");
		}

		return str;
	}
	
	public static String checkInjection_Special(String str) {
		if (str != null) {
			str = str.replace("\"", "");
			str = str.replace("#", "");
			str = str.replace("*", "");
			str = str.replace("'", "");
			str = str.replace("=", "");
		}

		return str;
	}

	public static String checkInjection_Sentence(String str) {
		if (str != null) {
			str = str.replace("\"", "");
			str = str.replace("#", "");
			str = str.replace("*", "");
			str = str.replace("'", "");
			str = str.replace("=", "");
			str = str.replace("<", "");
			str = str.replace("<if", "");
		}

		return str;
	}
	
	public static String StrToMd5(String str) throws Exception	{
		StringBuffer sb = new StringBuffer();
		String apikey = str;
		byte[] digest = MessageDigest.getInstance("MD5").digest(apikey.getBytes());
		sb.setLength(0);
		for(int i = 0 ; i < digest.length ; i++)
		{
			sb.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
			sb.append(Integer.toString(digest[i] & 0x0f, 16));
		}
		apikey = sb.toString();
		
		return apikey;
	}
	
	
	public static int minmax(int value, int minValue, int maxValue )
	{
		return Math.min( Math.max(value, minValue) , maxValue );
	}
	
	public static boolean setRSAKey(HttpSession session, boolean recreate )
    {
    	try{
    		PrivateKey privateKey = (PrivateKey) session.getAttribute("__rsaPrivateKey__");
    		
    		if (privateKey != null && recreate==false) 
    			return true;
    	
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			PublicKey publicKey = keyPair.getPublic();
			privateKey = keyPair.getPrivate();
			
			session.setAttribute("__rsaPrivateKey__", privateKey);
			
			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			
			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
			
			session.setAttribute("publicKeyModulus", publicKeyModulus);
			session.setAttribute("publicKeyExponent", publicKeyExponent);
		}
		catch(Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public static void clearRSAKey(HttpSession session )
    {
		session.removeAttribute("__rsaPrivateKey__");
		session.removeAttribute("publicKeyModulus");
		session.removeAttribute("publicKeyExponent");
	}

	public static void callResult(PrintWriter out, int error )
	{
		StringBuffer sb = new StringBuffer("<RESULT_SET RESULT='");
		sb.append(error);
		sb.append("' />");
		out.write(sb.toString());
		return;
	}
	
	public static String getQuery( String query, Object...objects ) {
		for( int i = 0 ; i < objects.length ; i++ ) {
			query = query.replaceFirst("\\?", objects[i].toString());
		}
		
		return query;
	}
	
	public static String getNowTime(String format) {
		
		SimpleDateFormat 	fmt = new SimpleDateFormat(format);
		GregorianCalendar 	cal = new GregorianCalendar();
		Date date = cal.getTime();
		
		return fmt.format(date);
	}
	
	public static String getResultXml( String result, Object...objects ) {
		for( int i = 0 ; i < objects.length ; i++ ) {
			result = result.replaceFirst("\\?", objects[i].toString());
		}
		
		return result;
	}
	
	public static String getErrorResult( int errorCode ) {
		
		return getResultXml("<RESULT_SET RESULT='?'/>", errorCode);
	}
	
	public static String getResultJson( JsonObject result ) {
		JsonObject json = new JsonObject();
		
		json.add("R", result);
		
		return json.toString();
	}
	public static String getZipJson( String bin ) {
		JsonObject json = new JsonObject();
		
		json.addProperty("Z", bin);
		
		return json.toString();
	}
	
	public static class RseBinJson {
		JsonObject R;
	}
	
	public static void appendStringBuilder( StringBuilder sb, Object...objects ) {
		for( int i = 0 ; i < objects.length ; i++ ) {
			sb.append(objects[i]);
		}
	}
	
	public static void appendStringBuffer( StringBuffer sb, Object...objects ) {
		for( int i = 0 ; i < objects.length ; i++ ) {
			sb.append(objects[i]);
		}
	}
	
	public static void returnError(PrintWriter out, int error, String act, String cmd )
	{
	   out.write(String.format("<RESULT_SET RESULT='%1$s' ACT='%2$s' CMD='%3$s'/>", error, act, cmd));
	}
	
	public static String intListToString(List<Integer> list) {
		StringBuilder result = new StringBuilder();
		for( int i = 0 ; i < list.size() ; i++ ) {
			if( i != 0 ) result.append(",");
			result.append(list.get(i));
		}
		return result.toString();
	}
	
	public static String longListToString(List<Long> list) {
		StringBuilder result = new StringBuilder();
		for( int i = 0 ; i < list.size() ; i++ ) {
			if( i != 0 ) result.append(",");
			result.append(list.get(i));
		}
		return result.toString();
	}
	
	public static int[] StringToIntArray(String selectList, String regex) {
		if( selectList == null || selectList.equals("") ) return new int[0];
		String selectArr[] = selectList.split(regex);
		int newArr[] = new int[selectArr.length];
		for( int i = 0 ; i < selectArr.length ; i++ ) {
			newArr[i] = Integer.parseInt(selectArr[i]);
		}
		
		return newArr;
	}
	public static int[] StringToIntArray(String selectList) {
		return StringToIntArray(selectList, ",");
	}
}