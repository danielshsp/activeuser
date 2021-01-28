package com.nice.activeuser.config;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheListenerConfig implements CacheEventListener<Object, Object> {

    //Monitored activity of cache
    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        log.info("custom Caching event {} {} {} {} ", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }

}
