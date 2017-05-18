package com.infrastructure.common;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConvert implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		if (value==null) {
			return null;
			
		}
		String changed = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			changed = df.format(value);
			return df.parse(changed);
		} catch (Exception e) {

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			changed = df.format(value);
			try {
				return df.parse(changed);
			} catch (ParseException e1) {
				return null;
			}

		}

	}

}
