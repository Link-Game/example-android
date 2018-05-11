package com.cloududu.linkgame.opensdk.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringUtils {
    private static long firsttime = 0;

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId
                .substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /***
     * 是否连续按了两次
     *
     * @return
     */
    public static boolean isDoubleTouch() {
        if (System.currentTimeMillis() - firsttime > 500) {
            firsttime = System.currentTimeMillis();
            return false;
        } else {
            firsttime = System.currentTimeMillis();
            return true;
        }
    }

    /**
     * 加密
     *
     * @param datasource byte[]
     * @return byte[]
     */
    public static byte[] DESencrypt(byte[] datasource, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] EDSdecrypt(byte[] src, String password)
            throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String MD5(String string) {
        if (string != null) {
            byte[] buffer = string.getBytes();
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(buffer);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return null;
            }
        }
        return null;


//	byte[] hash;
//
//	try {
//	    hash = MessageDigest.getInstance("MD5").digest(
//		    string.getBytes("UTF-8"));
//	} catch (NoSuchAlgorithmException e) {
//	    e.printStackTrace();
//	    return null;
//	} catch (UnsupportedEncodingException e) {
//	    e.printStackTrace();
//	    return null;
//	}
//
//	StringBuilder hex = new StringBuilder(hash.length * 2);
//	for (byte b : hash) {
//	    if ((b & 0xFF) < 0x10)
//		hex.append("0");
//	    hex.append(Integer.toHexString(b & 0xFF));
//	}
//
//	return hex.toString();
    }


    /**
     * 将字符串转成SHA1值
     *
     * @param string
     * @return
     */
    public static String SHA1(String string) {

        if (string != null) {
            byte[] buffer = string.getBytes();
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                MessageDigest mdTemp = MessageDigest.getInstance("SHA-1");
                mdTemp.update(buffer);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                return null;
            }
        }
        return null;

    }


    public static int StrToInteger(String str) {
        int num = -1;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            return num;
        }
        return num;
    }

    public static double StrToDouble(String str) {
        double d = 0;
        try {
            d = Double.parseDouble(str);
        } catch (Exception e) {

        }
        return d;
    }

    /**
     * 是否是电话号码
     *
     * @param mobile
     * @return
     */
    public static boolean isPhoneNumber(String mobile) {

        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    /**
     * 从字符串中提取手机号码
     *
     * @param str
     * @return
     */
    public static String getPhoneFromStr(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer bf = new StringBuffer(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
        }
        return bf.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String longToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        Date dt = new Date();
        String sDateTime = sdf.format(dt); // 得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }

    public static boolean isNull(String str) {
        return str == null || str.equals("") ? true : false;
    }

    public static String NullToString(String str) {
        return str == null ? "" : str;
    }

    public static String NullToString(String str, boolean isint) {
        String s = "";
        if (isint) {
            s = "0";
        }
        return null == str || str.equals("") ? s : str;
    }

    public static int StringToInteger(String str) {
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    /*************************
     * 判断身份证号是否有效
     ************************/
    public static boolean vIDNumByRegex(String idNum) {

        String curYear = "" + Calendar.getInstance().get(Calendar.YEAR);
        int y3 = Integer.valueOf(curYear.substring(2, 3));
        int y4 = Integer.valueOf(curYear.substring(3, 4));
        // 43 0103 1973 0 9 30 051 9
        return idNum
                .matches("^(1[1-5]|2[1-3]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|8[1-2])\\d{4}(19\\d{2}|20([0-"
                        + (y3 - 1)
                        + "][0-9]|"
                        + y3
                        + "[0-"
                        + y4
                        + "]))(((0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])))\\d{3}([0-9]|x|X)$");
        // 44 1825 1988 0 7 1 3 003 4
    }

    private static int ID_LENGTH = 17;

    public static boolean vIDNumByCode(String idNum) {

        // 系数列表
        int[] ratioArr = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        // 校验码列表
        char[] checkCodeList = {'1', '0', 'X', '9', '8', '7', '6', '5', '4',
                '3', '2'};

        // 获取身份证号字符数组
        char[] cIds = idNum.toCharArray();

        // 获取最后一位（身份证校验码）
        char oCode = cIds[ID_LENGTH];
        int[] iIds = new int[ID_LENGTH];
        int idSum = 0;// 身份证号第1-17位与系数之积的和
        int residue = 0;// 余数(用加出来和除以11，看余数是多少？)

        for (int i = 0; i < ID_LENGTH; i++) {
            iIds[i] = cIds[i] - '0';
            idSum += iIds[i] * ratioArr[i];
        }

        residue = idSum % 11;// 取得余数

        return Character.toUpperCase(oCode) == checkCodeList[residue];
    }

    public static boolean vId(String idNum) {
        if (idNum.length() != 15 || idNum.length() != 18) {
            return false;
        }
        return vIDNumByCode(idNum) && vIDNumByRegex(idNum);
    }

    /**
     * Unicode 转码
     ***/
    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }


    public static String timeFormat(String timeStr) {
        String result = "";

        SimpleDateFormat format;


        long timeLong = Long.parseLong(timeStr) * 1000;
        long nowTime = System.currentTimeMillis();


        Date thereDate = new Date(timeLong);
        Date nowDate = new Date(nowTime);
        if (thereDate.getDay() == nowDate.getDay()) {
            format = new SimpleDateFormat("HH:mm");
            result = result + "今天 " + format.format(thereDate);
        } else if (thereDate.getDay() == nowDate.getDay() - 1) {
            format = new SimpleDateFormat("HH:mm");
            result = result + "昨天 " + format.format(thereDate);
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            result = format.format(thereDate);
        }
        return result;
    }

    /**
     * 去除首尾字符
     *
     * @param str
     * @param stripChars
     * @return
     */
    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        } else {
            str = stripStart(str, stripChars);
            return stripEnd(str, stripChars);
        }
    }

    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            int start = 0;
            if (stripChars == null) {
                while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                    ++start;
                }
            } else {
                if (stripChars.length() == 0) {
                    return str;
                }

                while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                    ++start;
                }
            }

            return str.substring(start);
        } else {
            return str;
        }
    }

    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str != null && (end = str.length()) != 0) {
            if (stripChars == null) {
                while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                    --end;
                }
            } else {
                if (stripChars.length() == 0) {
                    return str;
                }

                while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                    --end;
                }
            }

            return str.substring(0, end);
        } else {
            return str;
        }
    }

    public static boolean textEmpty(TextView textView) {
        int length = textView.getText().toString().length();
        if (length == 0) {
            return true;
        }
        return false;
    }

    public static boolean editEmpty(EditText editText) {
        int length = editText.getText().toString().length();
        if (length == 0) {
            return true;
        }
        return false;
    }



    /**
     * 从字符串中提取数字
     */
    public static String drawNum(String a) {
        String regEx = "[^0-9]";
        StringBuilder stringBuilder = new StringBuilder(a);
        stringBuilder.substring(5, 7);
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
//        return m.replaceAll("").trim();
        return stringBuilder.toString();
    }

    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username:chenziwen,password:1234
     * 返回值:Map
     */
    public static HashMap<String, String> transStringToMap(String mapString) {
        HashMap<String, String> map = new HashMap();
        StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, ","); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? items.nextToken() : null))
            items = new StringTokenizer(entrys.nextToken(), ":");
        return map;
    }

    public static boolean isEmpty(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean isEqual(String s, String a) {
        if (!TextUtils.isEmpty(s) && s.equals(a)) {
            return true;
        }
        return false;
    }
}
