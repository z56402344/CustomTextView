package com.example.customtextview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;

/**
 * 字符串处理类
 */
public class StringUtil
{
	/**
	 * 判断邮箱的正则表达式
	 */
	private final static Pattern EMAIL_FORMAT = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	/**
	 * 效验密码的正则表达式
	 */
	private final static Pattern PWD_FORMAT = Pattern.compile("^[a-zA-Z0-9]{6,15}$");
	public static int BUFFER_SIZE = 512;

	/**
	 * 判断字符串不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str)
	{
		return str != null && !"".equals(str.trim());
	}
	
	/**
	 * 判断字符串是否为�?
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		if ((str == null) || str.equals("") || str.equals("null")
				|| str.equals("NULL")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断给定字符串是否空白串�?
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String str ) 
	{
		if ( str == null || "".equals( str ) )
			return true;
		
		for ( int i = 0; i < str.length(); i++ ) 
		{
			char c = str.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param src
	 * @param objects
	 * @return
	 */
	public static String format(String src,Object... objects)
	{
		int k = 0;
		for(Object obj : objects)
		{
			src = src.replace("{" + k + "}", obj.toString());
			k++;
		}
		return src;
	}
	
	/**
	 * 如果传入的字符串不为空，则返回原字符串，如果为空，则返回""字符
	 * @param str
	 * @return
	 */
	public static String null2Blank(String str){
		return (str==null?"":str);
	}
	
	/**
	 * 输入流转换成字符�?
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamToString(InputStream in) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while((count = in.read(data, 0, BUFFER_SIZE)) != -1)
		{
			outStream.write(data, 0, count);
		}
		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
		
	}
	
	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return EMAIL_FORMAT.matcher(email).matches();
	}
	
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue 如果异常则返回默认�?
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	
	/**
	 * 对象转整�?
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	
	/**
	 * 对象转整�?
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	
	/**
	 * 字符串转布尔�?
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	/**
	 * 小数点后两位小数
	 */
	public static void formatDot(Editable edt ){
		String temp = edt.toString(); 
        int posDot = temp.indexOf("."); 
        if (posDot <= 0) return; 
        if (temp.length() - posDot - 1 > 2) 
        { 
            edt.delete(posDot + 3, posDot + 4); 
        } 
	}
	
	/**
	 * 将输入流转化成字符串
	 * 
	 * @param inputStream输入�?
	 * @param encoding
	 *            字符编码类型,如果encoding传入null，则默认使用utf-8编码�?
	 * @return 字符�?
	 * @throws IOException
	 * @author lvmeng
	 */
	public static String inputToString(InputStream inputStream, String encoding)
			throws IOException {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		inputStream.close();
		bos.close();
		if (TextUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return new String(bos.toByteArray(), encoding);
	}

	/**
	 * 设置�?��高亮的字
	 * 
	 * @param wholeText
	 *            原始字符�?
	 * @param spanableText
	 *            �?��高亮的字符串
	 * @return 高亮后的字符�?
	 */
	public static SpannableString getSpanableText(String wholeText,
			String spanableText) {
		if (TextUtils.isEmpty(wholeText))
			wholeText = "";
		SpannableString spannableString = new SpannableString(wholeText);
		if (spanableText.equals(""))
			return spannableString;
		wholeText = wholeText.toLowerCase();
		spanableText = spanableText.toLowerCase();
		int startPos = wholeText.indexOf(spanableText);
		if (startPos == -1) {
			int tmpLength = spanableText.length();
			String tmpResult = "";
			for (int i = 1; i <= tmpLength; i++) {
				tmpResult = spanableText.substring(0, tmpLength - i);
				int tmpPos = wholeText.indexOf(tmpResult);
				if (tmpPos == -1) {
					tmpResult = spanableText.substring(i, tmpLength);
					tmpPos = wholeText.indexOf(tmpResult);
				}
				if (tmpPos != -1)
					break;
				tmpResult = "";
			}
			if (tmpResult.length() != 0) {
				return getSpanableText(wholeText, tmpResult);
			} else {
				return spannableString;
			}
		}
		int endPos = startPos + spanableText.length();
		do {
			endPos = startPos + spanableText.length();
			spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW),
					startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			startPos = wholeText.indexOf(spanableText, endPos);
		} while (startPos != -1);
		return spannableString;
	}
	
	/**
	 * 去掉字符串首尾，中间的空格，trim()，不仅仅是去掉空格，此处主要是增加去掉中间的空格
	 * @param str 
	 * @return
	 */
	public static String removeSpace(String str){
		
		if(!TextUtils.isEmpty(str)){
			return str.trim().replaceAll(" ", "");
		}else{
			return str;
		}
	}
	
	/**
	 * 去掉负号，截取负号后面的String
	 * @param str
	 * @return
	 */
	public static String removeSign(String str){
		if(str.contains("-")){
			return str.substring(str.indexOf("-")+1);
		}else{
			return str;
		}
	}
	/**
	 * 校验密码是否符合要求
	 * 密码�?-15位的英文、数字组�?
	 * @param password
	 * @return
	 */
	public static boolean isPassword(String password){
		if(password == null || password.trim().length()==0) 
			return false;
	    return PWD_FORMAT.matcher(password).matches();
	}

	/**
	 * 将手机号中间4位替换为*
	 * @param mobileNo 手机�?
	 * @return
	 */
	public static String formatMobileNo(String mobileNo) {
		if (isNotEmpty(mobileNo)) {
			return mobileNo.replace(mobileNo.substring(3, 7), "****");
		}
		return "";
	}
	/**
	 * 将�?yyyyMMdd  hhmmss”转成�?yyyy-MM-dd  hh:mm:ss�?
	 * @param time
	 * @return
	 */
	public  static String Now(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd  hhmmss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
		Date curDate;
        try {
            curDate = formatter.parse(time);
            return formatter2.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return formatter2.format(System.currentTimeMillis());
        }
	}

}
