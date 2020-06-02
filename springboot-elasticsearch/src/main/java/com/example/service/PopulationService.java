package com.example.service;

import com.example.modul.Population;

import java.util.List;

/**
 * @Description
 * @ClassName PopulationService
 * @Author Administrator
 * @date 2020.05.20 17:15
 */
public interface PopulationService {
    String insert(Population population);

    Population queryPopulationById(Long id);

    List queryPopulationByObject(Population population);
}
