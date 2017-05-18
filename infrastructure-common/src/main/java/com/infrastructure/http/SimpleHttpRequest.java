package com.infrastructure.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简易HTTP请求
 *
 * @author tyq
 * @data 2016/1/11
 */
public final class SimpleHttpRequest {

    /**
     * 发送GET请求
     *
     * @param url 地址
     * @return
     */
    public static final String get(String url) {
        // 创建链接
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
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
     * 发送POST请求
     *
     * @param url    地址
     * @param params 参数
     * @return
     */
    public static final String post(String url, Map<String, Object> params) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建POST请求
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value == null) continue;
                pairs.add(new BasicNameValuePair(key, String.valueOf(value)));
            }
        }

        try {
            // HTTP 表单实体
            HttpEntity json = new UrlEncodedFormEntity(pairs, "utf-8");
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
     * 上传文件
     *
     * @param url    地址
     * @param files  文件集合
     * @param params 参数集合
     * @return
     */
    public static final String upload(String url, Map<String, File> files, Map<String, String> params) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // 添加文件
        if (files != null && !files.isEmpty()) {
            for (String f : files.keySet()) {
                builder.addPart(f, new FileBody(files.get(f)));
            }
        }
        // 添加其他参数
        if (params != null && !params.isEmpty()) {
            for (String p : params.keySet()) {
                builder.addPart(p, new StringBody(params.get(p), ContentType.create("text/plain", Consts.UTF_8)));
            }
        }

        try {
            // 构建请求参数
            HttpEntity request = builder.build();
            httpPost.setEntity(request);
            // 执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            // 处理响应结果
            String res = null;
            if (entity != null)
                res = EntityUtils.toString(entity);
            // 销毁
            EntityUtils.consume(entity);
            httpResponse.close();
            // 返回结果
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
     * 下载文件
     *
     * @param url    文件地址
     * @param target 存储地址
     */
    public static final void download(String url, String target) {
        // 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                File storage = new File(target);
                // 创建目录
                if (!storage.getParentFile().exists()) {
                    storage.getParentFile().mkdirs();
                }
                OutputStream out = new FileOutputStream(storage);

                // 写入磁盘
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1)
                    out.write(buffer, 0, len);
                in.close();
                out.close();
            }
            // 销毁
            EntityUtils.consume(entity);
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理异常信息
     *
     * @param exception
     * @param msg
     * @return
     */
    private static final String handleExceptionMessage(String exception, String msg) {
        return "{\"exception\":\"" + exception + "\", \"message\": \"" + msg + "\"}";
    }
}
