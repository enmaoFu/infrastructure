package org.springframework.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.*;

/**
 * XMemcached 缓存管理器
 *
 * @author tyq
 *
 * @date 2016/1/14
 */
public class MemcachedCacheManager extends AbstractTransactionSupportingCacheManager {

    private MemcachedClient memcachedClient;   //XMemcached的客户端
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();  //缓存的时间映射 名称 > 时间

    public MemcachedCacheManager() {
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Iterator<Map.Entry<String, Integer>> iterator = expireMap.entrySet().iterator();
        Set<MemcachedCache> caches = new HashSet<MemcachedCache>();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            MemcachedCache cache = new MemcachedCache(next.getKey(), next.getValue().intValue(), memcachedClient);
            caches.add(cache);
        }
        return caches;
    }

    @Override
    protected Cache getMissingCache(String name) {
        int expire = 0;
        if (expireMap.containsKey(name))
            expire = expireMap.get(name).intValue();
        return new MemcachedCache(name, expire, memcachedClient);
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setExpireMap(Map<String, Integer> expireMap) {
        this.expireMap = expireMap;
    }
}
