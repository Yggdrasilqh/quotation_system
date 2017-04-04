package com.yggdrasil.controller;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody List<Scheme> schemes) {
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

    @RequestMapping("/delete")
    public String delete(int schemeID, int row) {
        try {
            schemeRepository.delete(new SchemePK(row, schemeID));
        } catch (Exception e) {
            return "error";
        }
        return "error";
    }
}
