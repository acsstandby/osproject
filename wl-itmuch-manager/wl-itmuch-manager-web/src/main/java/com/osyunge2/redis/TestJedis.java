package com.osyunge2.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Slf4j
public class TestJedis {
    @Test
    public void testMethod(){
        Jedis jedis=new Jedis("192.168.150.129",6379);
        jedis.set("key1","jedis_test");
        String string=jedis.get("key1");
        System.out.println(string);
        jedis.close();


    }
    @Test
    public void testSpring(){
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool jedisPool=(JedisPool) context.getBean("redisClient");
        Jedis jedis=jedisPool.getResource();
        String value= jedis.get("key1");
        System.out.println(value);

    }
}
