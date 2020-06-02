package com.example;

import com.example.controller.DateInputController;
import com.example.modul.Population;
import com.example.service.PopulationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName Test
 * @Author Administrator
 * @date 2020.05.25 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {




    @Autowired
    private MockMvc mvc;


    /*@Resource
    private PopulationService service;

    @Test
    public void Test(){

        Population population = new Population();
        List list = service.queryPopulationByObject(population);
        System.out.println(list.size());

    }*/
    @Test
    public void girlList() throws Exception {


        Map<String,String> map = new HashMap();
        map.put("name","张三");
        mvc.perform(MockMvcRequestBuilders.post("/es/population/queryByObject").param("name","张三"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("abc"));

    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time="2018-09-29 16:39:00";
        Date date = format.parse(time);
        //日期转时间戳（毫秒）
        long time1=date.getTime();
        System.out.print("Format To times:"+time1);
    }
}
