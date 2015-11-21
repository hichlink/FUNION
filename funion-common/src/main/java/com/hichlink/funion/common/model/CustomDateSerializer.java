package com.hichlink.funion.common.model;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;
/**
 * 
 * <b>Title：</b>CustomDateSerializer.java<br/>
 * <b>Description：</b> 返回的日期格式化为yyyy-MM-dd<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年3月15日 下午4:16:27<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *
 */
@Component
public class CustomDateSerializer extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}
}
