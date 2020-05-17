package cn.smbms.tools;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * --工具类
 * 
 * @author ASUS123
 * 
 */
public class Tools {

	/**
	 * 将字符转换为UTF-8
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             (不支持的编码异常)
	 */
	public static String toUTF_8(String str)
			throws UnsupportedEncodingException {
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}

	/**
	 * 将String转Date类型
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date StringtoDate(String str) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(str);
	}

}
