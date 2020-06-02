package com.example.service.impl;

import com.example.dao.PopulationDao;
import com.example.modul.Population;
import com.example.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @ClassName PopulationServiceImpl
 * @Author Administrator
 * @date 2020.05.20 17:15
 */
@Service
public class PopulationServiceImpl implements PopulationService {

    @Resource
    private PopulationDao populationDao;


    @Override
    public String insert(Population population) {
        return populationDao.insert(population);
    }

    @Override
    public Population queryPopulationById(Long id) {
        return populationDao.selectById(id);
    }

    @Override
    public List queryPopulationByObject(Population population) {
        return populationDao.selectByObject(population);
    }
}
