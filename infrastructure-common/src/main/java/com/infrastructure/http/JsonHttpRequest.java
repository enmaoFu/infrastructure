package com.infrastructure.http;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonHttpRequest
 *
 * @author tyq
 * @date 2016/1/11
 */
public final class JsonHttpRequest {
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private final Map<String, String> headers;

    public JsonHttpRequest() {
        headers = new HashMap<String, String>();
        addHeader(HTTP.CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
    }

    /**
     * 添加请求头
     *
     * @param name  名称
     * @param value 值
     * @return
     */
    public JsonHttpRequest addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * 发送GET请求
     *
     * @param url 地址
     * @return
     */
    public String get(String url) {
        // 创建链接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头
        for (String key : headers.keySet())
            httpGet.addHeader(key, headers.get(key));

        try {
            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // 响应结果
            String res = null;
            if (entity != null)
                res = EntityUtils.toString(entity);

            // 销毁
            EntityUtils.consume(entity);
            response.close();

            return res;
        } catch (ClientProtocolException cp) {
            return handleExceptionMessage(cp.getClass().getName(), "不支持的请求协议 - " + cp.getMessage());
        } catch (IOException e) {
            return handleExceptionMessage(e.getClass().getName(), e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送 POST 请求
     *
     * @param url    地址
     * @param params 参数
     * @return
     */
    public String post(String url, Map<String, String> params) {
        return post(url, JSON.toJSONString(params));
    }

    /**
     * 发送 POST 请求
     *
     * @param url  地址
     * @param data 请求数据
     * @return
     */
    public String post(String url, String data) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头
        for (String key : headers.keySet())
            httpPost.addHeader(key, headers.get(key));

        try {
            // HTTP 实体
            HttpEntity json = new StringEntity(data, "utf-8");
            httpPost.setEntity(json);

            // 执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            // 响应实体
            HttpEntity responseEntity = httpResponse.getEntity();

            String res = null;
            if (responseEntity != null)
                res = EntityUtils.toString(responseEntity, "utf-8");
            // 销毁
            EntityUtils.consume(responseEntity);
            httpResponse.close();
            // 返回
            return res;

        } catch (UnsupportedEncodingException ue) {
            return handleExceptionMessage(ue.getClass().getName(), "不支持的字符编码 - " + ue.getMessage());
        } catch (ClientProtocolException cp) {
            return handleExceptionMessage(cp.getClass().getName(), "不支持的请求协议 - " + cp.getMessage());
        } catch (IOException e) {
            return handleExceptionMessage(e.getClass().getName(), e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取请求
     *
     * @return
     */
    public static final JsonHttpRequest getRequest() {
        return new JsonHttpRequest();
    }

    /**
     * 处理异常信息
     *
     * @param exception
     * @param msg
     * @return
     */
    private String handleExceptionMessage(String exception, String msg) {
        return "{\"exception\":\"" + exception + "\", \"message\": \"" + msg + "\"}";
    }
}
