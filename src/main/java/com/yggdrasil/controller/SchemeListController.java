package com.yggdrasil.controller;

import com.yggdrasil.entity.SchemeList;
import com.yggdrasil.repository.SchemeListRepository;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yggdrasil on 2017/4/3.
 */
@RestController
@RequestMapping("/scheme")
public class SchemeListController {
    @Resource
    private SchemeListRepository schemeListRepository;

    @RequestMapping("/getAll")
    public List<SchemeList> getAll() {
        return schemeListRepository.findAll();
    }
}
