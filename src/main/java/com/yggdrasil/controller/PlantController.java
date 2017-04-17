package com.yggdrasil.controller;

import com.yggdrasil.entity.Plant;
import com.yggdrasil.entity.Scheme;
import com.yggdrasil.repository.PlantRepository;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
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
    @Resource
    private SchemeRepository schemeRepository;

    /*
     * @RequestMapping(value = "/add", method = RequestMethod.POST)
     * == @PostMapping(value = "/add")
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(String name, String type, float price,
                      @RequestParam(value = "image", required = true) MultipartFile image) {
        Plant plant = new Plant();
        plant.setName(name);
        plant.setType(type);
        plant.setPrice((float) (Math.round(price * 100)) / 100);
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
    public Page<Plant> getPlant(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "15") Integer size) {
        Pageable pageable = new PageRequest(page,size);
        return plantRepository.findAll(pageable);
    }

    @RequestMapping("/selectInfo")
    @ResponseBody
    public HashMap<Integer, String> selectInfo(@RequestParam("type") String type) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        List<Plant> plants;
        if (type != null && !type.isEmpty()) {
            plants = plantRepository.findByType(type);
            for (Plant plant : plants) {
                hashMap.put(plant.getId(), plant.getName());
            }
        }
        return hashMap;
    }

    @RequestMapping("/image")
    public ResponseEntity<byte[]> getImgOfPlant(int id) throws IOException {
        byte[] image = plantRepository.findOneOnlyImage(id);
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
            schemeRepository.deleteByPlantID(id);
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

    @RequestMapping("/findById")
    @ResponseBody
    public Plant findById(int id) {
        return plantRepository.findOne(id);
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(String name,@RequestParam(value = "value",required = false) String value, int pk,@RequestParam(value = "image",required = false)String image) {

        Plant plant = plantRepository.findOne(pk);
        switch (name) {
            case "name":
                plant.setName(value);
                plantRepository.saveAndFlush(plant);
                break;
            case "type":
                plant.setType(value);
                plantRepository.saveAndFlush(plant);
                break;
            case "price":
                float value_float = Integer.valueOf(value);
                plant.setPrice(value_float);
                plantRepository.saveAndFlush(plant);
                break;
            case "image":
                byte[] imageByte = org.springframework.util.Base64Utils.decodeFromString(image);
                plant.setImage(imageByte);
                plantRepository.saveAndFlush(plant);
                break;

        }
//        System.out.println(name);
//        System.out.println(value);
        return "success";
    }
}
