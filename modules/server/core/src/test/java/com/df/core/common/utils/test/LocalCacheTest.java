package com.df.core.common.utils.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.core.common.utils.LocalCache;

public class LocalCacheTest {

    @Test
    public void testGet() {
	LocalCache<String, Integer> cache = new LocalCache<String, Integer>();
	TestCase.assertNull(cache.get("KEY1"));
	cache.put("KEY1", 1);
	TestCase.assertEquals(new Integer(1), cache.get("KEY1"));
	cache.evict("KEY1");
	TestCase.assertNull(cache.get("KEY1"));
    }
}
