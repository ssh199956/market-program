package cn.smbms.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConversion implements Converter<String, Date> {
	String dateFormat;

	public StringToDateConversion(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public Date convert(String str) {
		Date date = null;
		try {
			date = new SimpleDateFormat(dateFormat).parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
