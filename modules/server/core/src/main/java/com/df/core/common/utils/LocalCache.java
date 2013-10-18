package com.df.core.common.utils;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * A simple key-value based cache solution.
 */
public class LocalCache<Key, Value> {

    private ConcurrentHashMap<Key, SoftReference<CacheEntry>> slots;

    private long period;

    public LocalCache(TimeUnit unit, int expireDuration) {
	period = unit.toMillis(expireDuration);
	slots = new ConcurrentHashMap<Key, SoftReference<CacheEntry>>();
    }

    public LocalCache() {
	period = TimeUnit.MILLISECONDS.toMillis(Long.MAX_VALUE);
	slots = new ConcurrentHashMap<Key, SoftReference<CacheEntry>>();
    }

    public void put(Key key, Value object) {
	SoftReference<CacheEntry> sf = new SoftReference<CacheEntry>(new CacheEntry(object));
	slots.put(key, sf);
    }

    public synchronized Value get(Key key) {
	SoftReference<CacheEntry> sf = slots.get(key);
	if (sf != null) {
	    CacheEntry entry = sf.get();
	    if (entry != null) {
		Value value = getValidValue(key, sf.get());
		if (value != null) {
		    return value;
		} else {
		    evict(key);
		}
	    }
	}
	return null;
    }

    public void evict(Key key) {
	slots.remove(key);
    }

    public void clear() {
	slots.clear();
    }

    private Value getValidValue(Key key, CacheEntry entry) {
	if (entry.getCreateTime() > new Date().getTime() - period) {
	    return entry.getValue();
	} else {
	    slots.remove(key);
	    return null;
	}
    }

    class CacheEntry {
	private long createTime;

	private Value value;

	public CacheEntry(Value value) {
	    this.createTime = new Date().getTime();
	    this.value = value;
	}

	public Value getValue() {
	    return value;
	}

	public long getCreateTime() {
	    return createTime;
	}
    }
}
