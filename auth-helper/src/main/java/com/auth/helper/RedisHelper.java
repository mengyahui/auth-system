package com.auth.helper;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author MYH
 * @time 2023/04/05 下午 02:37
 */
@Component
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisHelper {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key 缓存键值
     * @param time 时间（秒）
     * @return bool
     */
    public boolean expire(String key, long time) {
        if (time > 0 && hasKey(key)) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 根据key获取缓存的过期时间
     * @param key 缓存键值
     * @return 过期时间（秒），返回0代表永久有效
     */
    public long getExpire(String key) {

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断是否存在key
     * @param key 缓存键值
     * @return bool
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除指定key的缓存
     * @param key 缓存键值
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 对象缓存获取
     * @param key 缓存键值
     * @return 缓存对象
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 存入对象缓存
     * @param key 缓存键值
     * @param value 缓存值
     * @return bool
     */
    public <T> boolean setCacheObject(String key, T value) {
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 存入对象缓存并设置失效时间
     * @param key 缓存键值
     * @param value 缓存值
     * @param time 失效时间
     * @return bool
     */
    public <T> boolean setCacheObject(String key,T value,long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置缓存列表
     * @param key 缓存键值
     * @param data 缓存数据列表
     * @return bool
     * @param <T> 缓存列表的数据类型
     */
    public <T> boolean setCacheList(String key, List<T> data) {
        Long count = redisTemplate.opsForList().rightPushAll(key, data);
        return count != null;
    }

    /**
     * 获取缓存列表
     * @param key 缓存键值
     * @return 缓存列表
     * @param <T> 缓存列表的数据类型
     */
    public <T> List<T> getCacheList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 设置缓存set集合
     * @param key 缓存键值
     * @param dataSet set集合数据
     * @return bool
     * @param <T>
     */
    public <T> boolean setCacheSet(String key, Set<T> dataSet) {
        Long result = null;
        try {
            result = redisTemplate.opsForSet().add(dataSet);
        } catch (Exception e) {
            return false;
        }
        return result > 0;
    }


    /**
     * 获取set集合
     * @param key 缓存键值
     * @return set集合列表
     * @param <T> set集合存储的数据类型
     */
    public <T> Set<T> getCacheSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 设置map缓存
     * @param key 缓存键值
     * @param dataMap map集合
     * @param <T> map集合中存储数据的value数据类型
     */
    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获取map集合缓存
     * @param key 缓存键值
     * @return map集合
     * @param <T> map集合中存储数据的value数据类型
     */
    public <T> Map<String, T> getCacheMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 设置或修改map缓存中key对应的value
     * @param key 缓存键值
     * @param hKey hash键值
     * @param value hash value
     * @param <T> hash对应的value数据类型
     */
    public <T> void setCacheMapValue(String key, String hKey, T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取map集合中指定hash的value
     * @param key 缓存键值
     * @param hKey map中的hash键值
     * @return map集合
     * @param <T> hash对应的value数据类型
     */
    public <T> T getCacheMapValue(String key, String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 设置map集合中指定hash的value递增
     * @param key 缓存键值
     * @param hKey map中的hash键值
     * @param v 递增值
     */
    public void incrementCacheMapValue(String key,String hKey,long v){
        redisTemplate.boundHashOps(key).increment(hKey, v);
    }

    /**
     * 删除map集合中指定的hash
     * @param key 缓存键值
     * @param hKey map中的hash键值
     */
    public void delCacheMapValue(String key, String hKey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hKey);
    }




}
