package com.etianxia.concurrent;

/**
 *
 * 缓存为空时，填充缓存接口
 * 实现： 业务代码，如查询数据库等
 * Created by XuHe on 18/2/2.
 */
public interface CacheLoadable<T> {
    T load();
}
