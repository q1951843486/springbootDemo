package com.example.controller;

import com.example.config.RedisBloomFilter;
import com.example.config.RedisBloomFilterUtils;
import com.example.modul.Student;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

        boolean include = redisBloomFilterUtils.includeByBloomFilter(studentRedisBloomFilter, "suspects", name);

        if (include){

            return "存在";

        }else {
            return "不存在";
        }

    }




}
