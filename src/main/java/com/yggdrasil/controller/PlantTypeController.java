package com.yggdrasil.controller;

import com.yggdrasil.entity.Plant;
import com.yggdrasil.entity.PlantType;
import com.yggdrasil.repository.PlantTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/1.
 */
@RestController
@RequestMapping("/plantType")
public class PlantTypeController {

    @Resource
    PlantTypeRepository plantTypeRepository;

    @RequestMapping("/getAll")
    public List<PlantType> getAll(){
        return plantTypeRepository.findAll();
    }

    @RequestMapping("/getSelect")
    public HashMap<String,String> getSelet(){
        List<PlantType> list = plantTypeRepository.findAll();
        HashMap<String, String> hashMap = new HashMap<>();
        list.forEach(plantType -> hashMap.put(plantType.getName(), plantType.getName()));
        return hashMap;
    }

    @RequestMapping("/add")
    public String add(String name, float price) {
        PlantType plantType = new PlantType();
        plantType.setName(name);
        plantType.setPrice(price);
        plantTypeRepository.save(plantType);
        return "success";
    }

    @RequestMapping("/delete")
    public String add(int id) {
        plantTypeRepository.delete(id);
        return "success";
    }

    @RequestMapping("/getPrice")
    public float price(String name) {
        return plantTypeRepository.
                findByName(name).
                getPrice();
    }
}
