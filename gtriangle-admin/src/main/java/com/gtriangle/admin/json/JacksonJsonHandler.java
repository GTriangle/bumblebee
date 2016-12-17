/**
 * 
 */
package com.gtriangle.admin.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.gtriangle.admin.exception.ResultParseException;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

/**
 * @Description: JacksonJsonHandler
 * @author Brian
 * @date 2014年5月16日 下午1:44:03
 * @version
 */
public class JacksonJsonHandler implements JsonHandler {
	
	private static ObjectMapper objectMapper;
	
	/* (non-Javadoc)
	 * @see com.hyousoft.common.json.JsonHandler#unmarshaller(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T unmarshaller(String content, Class<T> objectType) throws ResultParseException {
		try {
			return getObjectMapper().readValue(content, objectType);
		} catch (JsonParseException e) {
			throw new ResultParseException(e);
		} catch (JsonMappingException e) {
			throw new ResultParseException(e);
		} catch (IOException e) {
			throw new ResultParseException(e);
		}
	}

	@Override
	public void marshaller(Object object, OutputStream outputStream) throws ResultParseException {
		JsonGenerator jsonGenerator;
		try {
			jsonGenerator = getObjectMapper().getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
			getObjectMapper().writeValue(jsonGenerator, object);
		} catch (IOException e) {
			throw new ResultParseException(e);
		}
       
	}

	@Override
	public String marshaller(Object object) throws ResultParseException {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new ResultParseException(e);
		} catch (JsonMappingException e) {
			throw new ResultParseException(e);
		} catch (IOException e) {
			throw new ResultParseException(e);
		}
	}
	
	public static void main(String[] args) throws ResultParseException {
		List<String> a = new ArrayList<String>();
		a.add("dd");
		a.add("ee");
		a.add("ff");
		a.add("gg");
		HashMap<String, Object> b = new HashMap<String, Object>();
		b.put("aaa", "333");
		b.put("bbb", a);
		
		JacksonJsonHandler d = new JacksonJsonHandler();
		System.out.println(d.marshaller(b));
	}
	
	public <T> T unmarshaller(InputStream content, Class<T> objectType) throws ResultParseException {
		try {
			return getObjectMapper().readValue(content, objectType);
		} catch (JsonParseException e) {
			throw new ResultParseException(e);
		} catch (JsonMappingException e) {
			throw new ResultParseException(e);
		} catch (IOException e) {
			throw new ResultParseException(e);
		}
	}
	
	public <T> T unmarshaller(HttpServletRequest request, Class<T> objectType) throws ResultParseException {
		try {
			return getObjectMapper().readValue(request.getInputStream(), objectType);
		} catch (JsonParseException e) {
			throw new ResultParseException(e);
		} catch (JsonMappingException e) {
			throw new ResultParseException(e);
		} catch (IOException e) {
			throw new ResultParseException(e);
		}
	}
	
	private ObjectMapper getObjectMapper() throws IOException {
        if (this.objectMapper == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
            SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
            serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE)
                                                     //.with(SerializationConfig.Feature.INDENT_OUTPUT)
                                                     .withAnnotationIntrospector(introspector)
                                                     .withDateFormat(DF_LONG);
            DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
            deserializationConfig = deserializationConfig.without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES)
            		.withDateFormat(DF_LONG);
            objectMapper.setSerializationConfig(serializationConfig);
            objectMapper.setDeserializationConfig(deserializationConfig);
            this.objectMapper = objectMapper;
        }
        return this.objectMapper;
    }
}
