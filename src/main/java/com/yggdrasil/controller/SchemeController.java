package com.yggdrasil.controller;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/3.
 */
@RestController
@RequestMapping("/scheme")
public class SchemeController {
    @Resource
    private SchemeRepository schemeRepository;


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(@RequestBody List<Scheme> schemes){
        try {
            schemeRepository.save(schemes);
            schemeRepository.flush();
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @RequestMapping("/findBySchemeID")
    public List<Scheme> findByID(int schemeID) {
        return schemeRepository.findBySchemeIDOrderByRow(schemeID);
    }
}
