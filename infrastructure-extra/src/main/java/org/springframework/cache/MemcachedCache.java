package org.springframework.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * Memcached 缓存实现
 *
 * @author tyq
 * @date 2016/1/14
 */
public class MemcachedCache implements Cache {

    private final MemCache memCache;

    /**
     * 构造函数
     *
     * @param name 缓存名字
     * @param expire 存活时间 (单位:s)
     * @param memcachedClient 缓存客户端
     */
    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient) {
        this.memCache = new MemCache(name, expire, memcachedClient);
    }

    @Override
    public String getName() {
        return memCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return memCache;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = null;
        Object value = memCache.get(key.toString());
        if (value != null) {
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        Object cacheValue = memCache.get(key.toString());
        if (cacheValue != null) {
            if (type.isInstance(cacheValue))
                return (T) cacheValue;
            throw new IllegalStateException(String.format("缓存值不是要求类型 %s 的值", type.getSimpleName()));
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        memCache.put(key.toString(), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        memCache.put(key.toString(), value);
        return new SimpleValueWrapper(value);
    }

    @Override
    public void evict(Object key) {
        memCache.delete(key.toString());
    }

    @Override
    public void clear() {
        memCache.clear();
    }
}
