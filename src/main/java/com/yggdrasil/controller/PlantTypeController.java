package com.yggdrasil.controller;

import com.yggdrasil.entity.Plant;
import com.yggdrasil.entity.PlantType;
import com.yggdrasil.repository.PlantRepository;
import com.yggdrasil.repository.PlantTypeRepository;
import com.yggdrasil.repository.SchemeRepository;
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
    @Resource
    SchemeRepository schemeRepository;
    @Resource
    PlantRepository plantRepository;


    @RequestMapping("/getAll")
    public List<PlantType> getAll(){
        return plantTypeRepository.findAll();
    }

    @RequestMapping("/getSelect")
    public HashMap<String,String> getSelet(){
        List<PlantType> list = plantTypeRepository.findAll();
        HashMap<String, String> hashMap = new HashMap<>();
        for (PlantType p : list) {
            hashMap.put(p.getName(), p.getName());
        }
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
    public String delete(int id) {
        String typeName = plantTypeRepository.findOne(id).getName();
        for (Plant plant : plantRepository.findByType(typeName)) {
            schemeRepository.deleteByPlantID(plant.getId());
        }
        plantRepository.deleteByType(typeName);
        plantTypeRepository.delete(id);
        return "success";
    }

    @RequestMapping("/getPrice")
    public float price(String name) {
        if(name != null && !name.equals(""))
        return plantTypeRepository.
                findByName(name).
                getPrice();
        return 0;
    }

    @RequestMapping("/update")
    public String update(int pk, String name, String value) {
        PlantType plantType = plantTypeRepository.findOne(pk);
        if ("name".equalsIgnoreCase(name)) {
            plantType.setName(value);
        } else if ("price".equalsIgnoreCase(name)) {
            plantType.setPrice(Float.valueOf(value));
        }
        plantTypeRepository.saveAndFlush(plantType);
        return "success";
    }
}
