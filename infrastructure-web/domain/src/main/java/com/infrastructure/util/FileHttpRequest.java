package com.infrastructure.util;

import com.infrastructure.model.FileModel;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 文件HTTP传输工具类
 *
 * @author tyq
 * @data 2016/1/14
 */
public final class FileHttpRequest {

    /**
     * 上传文件
     *
     * @param url  上传地址
     * @param file 文件模型
     * @return
     */
    public static final String upload(String url, FileModel file) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        try {
            // 添加文件
            builder.addPart(FileModel.DEFAULT_UPLOAD_FILE_NAME, new ByteArrayBody(file.getBytes(), ContentType.DEFAULT_BINARY, file.getName()));

            // 添加文件信息
            Map<String, Object> params = file.map();
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value == null) continue;
                builder.addPart(key, new StringBody(String.valueOf(value), ContentType.create("text/plain", Consts.UTF_8)));
            }

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
            throw new IllegalArgumentException("不支持的字符编码 - " + ue.getMessage());
        } catch (ClientProtocolException cp) {
            throw new IllegalArgumentException("不支持的请求协议 - " + cp.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
     * @param url    上传地址
     * @param in     文件流
     * @param params 参数
     * @return
     */
    public static final String upload(String url, InputStream in, Map<String, Object> params) {
        try {
            byte[] bytes = IOUtils.toByteArray(in);
            return upload(url, bytes, params);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("IO 操作异常 " + e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param url    文件地址
     * @param bytes  二进制
     * @param params 参数
     * @return
     */
    public static final String upload(String url, byte[] bytes, Map<String, Object> params) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        try {
            // 添加文件
            builder.addPart(FileModel.DEFAULT_UPLOAD_FILE_NAME, new ByteArrayBody(bytes, ContentType.DEFAULT_BINARY, FileModel.DEFAULT_UPLOAD_FILE_NAME));

            // 附加信息
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    Object value = params.get(key);
                    if (value == null) continue;
                    builder.addPart(key, new StringBody(String.valueOf(value), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }

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
            throw new IllegalArgumentException("不支持的字符编码 - " + ue.getMessage());
        } catch (ClientProtocolException cp) {
            throw new IllegalArgumentException("不支持的请求协议 - " + cp.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
     * @param file   文件
     * @param params 参数
     * @return
     */
    public static final String upload(String url, File file, Map<String, String> params) {
        // 创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // 添加文件
        if (file != null && file.isFile())
            builder.addPart(FileModel.DEFAULT_UPLOAD_FILE_NAME, new FileBody(file));

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
            throw new IllegalArgumentException("不支持的字符编码 - " + ue.getMessage());
        } catch (ClientProtocolException cp) {
            throw new IllegalArgumentException("不支持的请求协议 - " + cp.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载一个文件
     *
     * @param url 文件地址
     * @return
     */
    public static final byte[] download(String url) {
        // 客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        try {

            // 执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 响应结果
            HttpEntity entity = httpResponse.getEntity();

            byte[] bytes = null;

            if (entity != null) {
                InputStream in = entity.getContent();
                bytes = IOUtils.toByteArray(in);
            }
            // 销毁
            EntityUtils.consume(entity);
            httpResponse.close();
            // 返回
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
