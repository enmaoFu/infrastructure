
package com.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName(类名) : JsonUtils
 * @Description(描述) : json工具类
 * @author(作者) ：suyuanliu
 */

public abstract class JsonUtils {
    private static Logger log = Logger.getLogger(JsonUtils.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static ObjectMapper mapper = null;

    /**
     * @return ObjectMapper
     * @throws :
     * @Description(功能描述) : 单例获取 ObjectMapper
     * @author(作者) ： suyuanliu
     * @date (开发日期) : 2015年3月5日 上午8:56:07
     */
    public static ObjectMapper buildObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setDateFormat(sdf);
        }
        return mapper;
    }

    /**
     * @param json :json字符串
     * @return Map<String,Object>
     * @throws :
     * @Description(功能描述) : json 转换成 map
     * @author(作者) ： suyuanliu
     * @date (开发日期) : 2015年3月5日 上午9:03:18
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;
        try {
            map = buildObjectMapper().readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            log.error("json转换出错：", e);
        }
        return map;
    }

    /**
     * @param json   ：json字符串
     * @param entity ：实体class
     * @param <T>    : 泛型
     * @return T : 返回实体
     * @throws :
     * @Description(功能描述) : json 转换成 实体
     * @author(作者) ： suyuanliu
     * @date (开发日期) : 2015年3月5日 上午8:57:13
     */
    public static <T> T jsonToEntity(String json, Class<T> entity) {
        try {
            return buildObjectMapper().readValue(json, entity);
        } catch (Exception e) {
            log.error("json转换出错：", e);
            return null;
        }
    }

    /**
     * @param json   :json字符串
     * @param entity ：实体class
     * @param <T>    : 泛型
     * @return List<T> ： 返回实体集合
     * @throws :
     * @Description(功能描述) : json转成实体集合
     * @author(作者) ： suyuanliu
     * @date (开发日期) : 2015年3月5日 上午9:06:15
     */
    public static <T> List<T> jsonToEntityList(String json, final Class<T> entity) {
        TypeFactory t = TypeFactory.defaultInstance();
        try {
            return buildObjectMapper().readValue(json, t.constructCollectionType(ArrayList.class, entity));
        } catch (Exception e) {
            log.error("json转换出错：", e);
            return null;
        }
    }

    /**
     * @param obj :对象
     * @return String ：json字符转结果
     * @throws :
     * @Description(功能描述) : 对象转换成json字符串
     * @author(作者) ： suyuanliu
     * @date (开发日期) : 2015年3月5日 上午10:59:16
     */
    public static String stringify(Object obj) {
        try {
            return buildObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json转换出错：", e);
            return null;
        }
    }

}
