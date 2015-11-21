package com.hichlink.funion.common.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;
/**
 * 
 * <b>Title：</b>CustomObjectMapper.java<br/>
 * <b>Description：</b> 自定义返回JSON格式化<br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014年3月15日 下午4:17:04<br/>  
 * <b>Copyright (c) 2014 ASPire Tech.</b>   
 *
 */
public class CustomObjectMapper extends ObjectMapper {  
  
    public CustomObjectMapper(){  
        CustomSerializerFactory factory = new CustomSerializerFactory();  
        factory.addGenericMapping(Date.class, new JsonSerializer<Date>(){  
            @Override  
            public void serialize(Date value,   
                    JsonGenerator jsonGenerator,   
                    SerializerProvider provider)  
                    throws IOException, JsonProcessingException {  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                jsonGenerator.writeString(sdf.format(value));  
            }  
        });  
        this.setSerializerFactory(factory);  
    }  

}
