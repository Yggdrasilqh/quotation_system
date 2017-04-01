package com.yggdrasil.controller;

import com.yggdrasil.entity.PlantType;
import com.yggdrasil.repository.PlantTypeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/1.
 */
@Controller
@RequestMapping("/plantType")
public class PlantTypeController {

    @Resource
    PlantTypeRepository plantTypeRepository;

    @RequestMapping("/getAll")
    @ResponseBody
    public List<PlantType> getAll(){
        return plantTypeRepository.findAll();
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(String name, float price) {
        PlantType plantType = new PlantType();
        plantType.setName(name);
        plantType.setPrice(price);
        plantTypeRepository.save(plantType);
        return "success";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String add(int id) {
        plantTypeRepository.delete(id);
        return "success";
    }
}
