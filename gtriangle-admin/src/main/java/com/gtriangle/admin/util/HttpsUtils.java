package com.gtriangle.admin.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * HttpClient Utils
 *
 * @author Lynch
 */
@SuppressWarnings("all")
public class HttpsUtils {

    public static String Method_GET = "GET";
    public static String Method_POST = "POST";
    public static String Method_PUT = "PUT";
    public static String Method_DELETE = "DELETE";

    public static Map<String, Object> sendGet(final String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).setConnectionRequestTimeout(1000).build();
            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            ResponseHandler<Map<String, Object>> responseHandler = new ResponseHandler<Map<String, Object>>() {
                @Override
                public Map<String, Object> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    return Json2Map(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
                }
            };
            return httpclient.execute(get, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sendGetStr(final String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(1000).setConnectionRequestTimeout(1000).build();
            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    return IOUtils.toString(response.getEntity().getContent(), "utf-8");
                }
            };
            return httpclient.execute(get, responseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 发送Post请求
     */
    public static String sendPostStr(String url, Map params_map) {
        int status = 0;
        String result = "";
        try {
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Iterator it = params_map.entrySet().iterator();
            while (it.hasNext()) {// 添加参数
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                params.add(new BasicNameValuePair(key.toString(), value.toString()));
            }

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httppost.setConfig(requestConfig);
            HttpResponse response = HttpClients.createDefault().execute(httppost);
            status = response.getStatusLine().getStatusCode();
            if (status == 200) {// 如果状态码为200,就是正常返回
                result = EntityUtils.toString(response.getEntity());
                return result;
            } else {

            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 发送Post请求
     */
    public static Map<String, Object> sendPost(String url, Map params_map) {
        int status = 0;
        String result = "";
        try {
            HttpPost httppost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Iterator it = params_map.entrySet().iterator();
            while (it.hasNext()) {// 添加参数
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                params.add(new BasicNameValuePair(key.toString(), value.toString()));
            }

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httppost.setConfig(requestConfig);
            HttpResponse response = HttpClients.createDefault().execute(httppost);


            status = response.getStatusLine().getStatusCode();
            if (status == 200) {// 如果状态码为200,就是正常返回
                return Json2Map(IOUtils.toString(response.getEntity().getContent(), "utf-8"));
            } else {

            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * Convert Map to JSON
     *
     * @throws IOException
     * @throws org.codehaus.jackson.JsonParseException
     */
    public static String Map2Json(Map<String, Object> jsonMap) {

        String json = "";

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            // convert Map string to JSON
            json = objectMapper.writeValueAsString(jsonMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * Convert Json to Map
     *
     * @throws IOException
     * @throws org.codehaus.jackson.JsonParseException
     */
    public static Map<String, Object> Json2Map(String jsonStr) {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            // convert JSON string to Map
            map = mapper.readValue(jsonStr, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static String paramToQueryString(Map<String, Object> params)
            throws UnsupportedEncodingException
    {
        return paramToQueryString(params,"UTF-8");
    }

    public static String paramToQueryString(Map<String, Object> params, String charset)
            throws UnsupportedEncodingException
    {
        if ((params == null) || (params.size() == 0)) {
            return null;
        }

        StringBuilder paramString = new StringBuilder();
        boolean first = true;
        for (Map.Entry p : params.entrySet()) {
            String key = String.valueOf(p.getKey());
            String val = String.valueOf(p.getValue());

            if (!first) {
                paramString.append("&");
            }

            paramString.append(key);
            if (val != null)
            {
                paramString.append("=").append(urlEncode(val, charset));
            }

            first = false;
        }

        return paramString.toString();
    }

    public static String urlEncode(String value, String charset)
            throws UnsupportedEncodingException
    {
        return value != null ? URLEncoder.encode(value, charset).replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }
}