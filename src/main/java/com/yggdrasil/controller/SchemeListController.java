package com.yggdrasil.controller;

import com.yggdrasil.entity.SchemeList;
import com.yggdrasil.repository.SchemeListRepository;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/3.
 */
@RestController
@RequestMapping("/schemeList")
public class SchemeListController {
    @Resource
    private SchemeListRepository schemeListRepository;
    @Resource
    private SchemeRepository schemeRepository;

    @RequestMapping("/getAll")
    public List<SchemeList> getAll() {
        return schemeListRepository.findAll();
    }

    @RequestMapping("/add")
    public String add(String name) {
        try {
            schemeListRepository.saveAndFlush(
                    new SchemeList(name,
                            new Date(new java.util.Date().getTime())));
        } catch (Exception e){
            return "error";
        }
        return "success";
    }

    @RequestMapping("/delete")
    public String delete(int id) {
        try {
            schemeRepository.deleteBySchemeID(id);
            schemeListRepository.delete(id);
        } catch (Exception e){
            return "error";
        }
        return "success";
    }
}
