package com.infrastructure.model;


import com.infrastructure.enums.FileModule;
import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件模型
 *
 * @author tyq
 * @data 2016/1/14
 */
public class FileModel implements Serializable {

    public static final String DEFAULT_UPLOAD_FILE_NAME = "file";

    private static final long serialVersionUID = -3238850881486251972L;

    /**
     * 文件名
     */
    private String name;

    /**
     * 新名称
     */
    private String newName;

    /**
     * Base64 字符串
     */
    private String base64;

    /**
     * 字节流
     */
    private byte[] bytes;

    /**
     * 文件模块
     */
    private FileModule module;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件块数
     */
    private Integer chunks;

    /**
     * 当前块
     */
    private Integer chunk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
        this.bytes = Base64.decodeBase64(base64);
    }

    public FileModule getModule() {
        return module;
    }

    public void setModule(FileModule module) {
        this.module = module;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getChunks() {
        return chunks;
    }

    public void setChunks(Integer chunks) {
        this.chunks = chunks;
    }

    public Integer getChunk() {
        return chunk;
    }

    public void setChunk(Integer chunk) {
        this.chunk = chunk;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 属性转换成为Map集合
     *
     * @return
     */
    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("module", module);
        map.put("path", path);
        map.put("chunk", chunk);
        map.put("chunks", chunks);
        map.put("newName", newName);
        return map;
    }

}
