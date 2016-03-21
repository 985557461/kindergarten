package com.xy.MeiFour.util;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoyu on 2016/3/21.
 */
public class CommonUtil {
    //判断电话号码是否合法的方法
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
       /*
        * 可接受的电话格式有：
	    */
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
	   /*
	    * 可接受的电话格式有：
	    */
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void printHttpUrl(String url,Map<String,String> params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        int count = params.size();
//        if(count > 0){
//            Map.Entry<String,String> entries = (Map.Entry<String, String>) params.entrySet();
//            for(Map.Entry<String,String> entry:entries){
//
//            }
//        }
    }
}
