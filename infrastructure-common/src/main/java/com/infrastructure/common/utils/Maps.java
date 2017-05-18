package com.infrastructure.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Map 集合快速构建类
 *
 * @author tyq
 * @data 2016/1/11
 */
public final class Maps<K, V> {

    private Map<K, V> map;

    private Maps() {
        this.map = new HashMap<>();
    }

    public Maps<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Maps<K, V> remove(K key) {
        map.remove(key);
        return this;
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Map<K, V> map() {
        return map;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(map);
    }

    public static final <K, V> Maps<K, V> create() {
        return new Maps<K, V>();
    }


}
