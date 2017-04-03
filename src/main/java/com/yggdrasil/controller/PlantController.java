package com.yggdrasil.controller;

import com.yggdrasil.entity.Plant;
import com.yggdrasil.entity.Scheme;
import com.yggdrasil.repository.PlantRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yggdrasil on 2017/3/31.
 */
@Controller
@RequestMapping("/plant")
public class PlantController {


    @Resource
    private PlantRepository plantRepository;



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(String name, String type, float price,
                      @RequestParam(value = "image",required = true) MultipartFile image) {
        Plant plant = new Plant();
        plant.setName(name);
        plant.setType(type);
        plant.setPrice(price);
        if (!image.isEmpty()) {
            try {
                plant.setImage(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        plantRepository.save(plant);
        return "success";
    }
    @RequestMapping("/allInfo")
    @ResponseBody
    public List<Plant> getPlant() {
        List<Plant> plants = plantRepository.findAll();
        return plants;
    }

    @RequestMapping("/selectInfo")
    @ResponseBody
    public HashMap<Integer, String> selectInfo(@RequestParam("type") String type) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        List<Plant> plants;
        if(type != null && !type.isEmpty()) {
            plantRepository.findByType(type).forEach(plant -> hashMap.put(plant.getId(), plant.getName()));
        }
        return hashMap;

    }

    @RequestMapping("/image")
    public ResponseEntity<byte[]> getImgOfPlant(int id) throws IOException {
        byte[] image = plantRepository.fineOneOnlyImage(id);
//        Stream方法
//        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        OutputStream outputStream = httpServletResponse.getOutputStream();
//        outputStream.write(image);
//        outputStream.flush();
//        outputStream.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setCacheControl("no-cache");
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deletePlant(int id) {
        try {
            plantRepository.delete(id);
        } catch (Exception e) {
            return "false";
        }
        return "success";
    }

    @RequestMapping("/getPrice")
    @ResponseBody
    public float getPrice(int id) {
        float price = plantRepository.findOne(id).getPrice();
        return price;
    }
}
