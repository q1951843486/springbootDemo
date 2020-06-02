package com.example.controller;

import com.example.common.HttpClientUtils;
import com.example.common.SequenceFactory;
import com.example.config.RedisBloomFilter;
import com.example.config.RedisBloomFilterUtils;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @ClassName RedisController
 * @Author Administrator
 * @date 2020.03.25 15:04
 */
@Controller
public class RedisController {

    public   static  AtomicInteger atomicInteger = new AtomicInteger(1);

    @Autowired
    private RedisBloomFilterUtils redisBloomFilterUtils;
    private RedisBloomFilter<String> studentRedisBloomFilter = new RedisBloomFilter<String>(
            (Funnel<String>) (from,into) ->into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8),1000000,0.01);

    @RequestMapping(value = "/redis/add")
    @ResponseBody
    public String add(){
        AtomicInteger atomicInteger = new AtomicInteger(1);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1000,10000,1L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100000));

        int count = 10000;
        for (int i = 0 ;i<=count;i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    redisBloomFilterUtils.addByBloomFilter(studentRedisBloomFilter,"suspects","ll"+RedisController.atomicInteger.incrementAndGet());
                    System.out.println(RedisController.atomicInteger);
                }
            });


        }
        /*for (int i = 1;i<=100000;i++){
            redisBloomFilterUtils.addByBloomFilter(studentRedisBloomFilter,"suspects","lihua"+i);
            System.out.println(i);

        }*/


        //redisBloomFilterUtils.addByBloomFilter(studentRedisBloomFilter,"suspects","lisi");

        return "增加成功";
    }
    @ResponseBody
    @RequestMapping(value = "/redis/{name}")
    public String get(@PathVariable("name")String name){


        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());


        boolean include = redisBloomFilterUtils.includeByBloomFilter(studentRedisBloomFilter, "suspects", name);

        if (include){

            return "存在";

        }else {
            return "不存在";
        }

    }
    @RequestMapping(value = "/getName",method = RequestMethod.POST)
    @ResponseBody
    public String getName(String name){

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        boolean include = redisBloomFilterUtils.includeByBloomFilter(studentRedisBloomFilter, "suspects", name);
        if (include){

            return "存在";

        }else {
            return "不存在";
        }
    }
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){

        for (int i = 0; i < 10000; i++) {
            String url = "http://10.200.1.194/getName";
            Map parmMap = new HashMap();
            String s = new String();
            parmMap.put("name","张三");
            try {
                s = HttpClientUtils.doPost(url, parmMap);
                System.out.println(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return "ll";

    }
  


}
