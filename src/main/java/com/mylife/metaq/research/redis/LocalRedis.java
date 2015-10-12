package com.mylife.metaq.research.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by zhangyong on 15-10-12.
 */
public class LocalRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        long start = System.currentTimeMillis();
        jedis.set("foo", "bar");
        System.out.println("cost time :" + (System.currentTimeMillis() - start) + "毫秒");
        long start1 = System.currentTimeMillis();
        String value = jedis.get("foo");
        System.out.println("cost time :" + (System.currentTimeMillis() - start1) + "毫秒");
        System.out.println("value:" + value);
    }

}
