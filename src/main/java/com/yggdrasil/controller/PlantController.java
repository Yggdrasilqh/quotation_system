package com.yggdrasil.controller;

import com.yggdrasil.entity.Plant;
import com.yggdrasil.repository.PlantRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Blob;
import java.util.List;

/**
 * Created by yggdrasil on 2017/3/31.
 */
@RestController
public class PlantController {

    @Resource
    PlantRepository plantRepository;

    @RequestMapping("/add")
    public void addPlant() {
        Plant plant = new Plant();

        plant.setName("幸福树");
        plant.setType("特大");
        plant.setPrice(750);
        plantRepository.saveAndFlush(plant);
    }

    @RequestMapping("/getPlant")
    public List<Plant> getPlant() {
        return plantRepository.findAll();
    }
}
