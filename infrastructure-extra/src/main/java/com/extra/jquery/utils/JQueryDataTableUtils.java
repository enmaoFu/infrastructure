package com.extra.jquery.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JQueryDataGridUtils
 * <p/>
 * JQuery.dataGrid 插件辅助工具类<br/>
 * 提供搜索参数转换成查询语句功能
 *
 * @author tyq
 * @date 2016/1/14
 */
public abstract class JQueryDataTableUtils {

    /**
     * 构建SQL查询语句
     * <p/>
     * 根据JQueryDataTable插件回传参数来自动构建搜索条件语句
     *
     * @param requestMap 请求参数集合
     * @param clazz      实体类型
     * @param <M>        实体泛型标识
     * @return where 语句
     */
    public static <M> String buildSql(Map<String, String[]> requestMap, Class<M> clazz) {

        StringBuilder _sql = new StringBuilder();

        Iterator<Map.Entry<String, String[]>> iterator = requestMap.entrySet().iterator();

        List<Map<String,Object>> searchers = Lists.newArrayList();//搜索条件
        List<String> sorts = Lists.newArrayList(); // 排序
        //排序方式
        String order = "asc";

        while (iterator.hasNext()) {
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue()[0];
            //排除分页条件
            if("page".equals(key) || "rows".equals(key)) continue;;
            if("sort".equals(key)){
                //此列用于排序
                sorts.add(value);
            }else if("order".equals(key)){
                //排序方式
                order = value;
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put(key, value);
                searchers.add(map);
            }

        }

        //搜索条件
        for (Map<String, Object> map : searchers) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                _sql.append(" and "+entry.getKey()+" like "+" '%"+entry.getValue()+"%' ");
            }
        }

        if(sorts.size() > 0){
            //排序
            _sql.append(" order by ");
            for (int i = 0; i < sorts.size(); i++) {
                if(i+1 < sorts.size()){
                    _sql.append(sorts.get(i)+",");
                }else {
                    _sql.append(sorts.get(i));
                }
            }
            _sql.append(" "+order);
        }
        return _sql.toString();
    }
}
