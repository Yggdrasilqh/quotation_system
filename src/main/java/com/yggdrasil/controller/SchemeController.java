package com.yggdrasil.controller;

import com.yggdrasil.entity.Scheme;
import com.yggdrasil.entity.SchemePK;
import com.yggdrasil.repository.SchemeRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
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
            for (Scheme scheme : schemes) {
                byte[] img = scheme.getCommentImage();
                if (img != null && img.length < 60 && img.length > 0) {
                    scheme.setCommentImage(schemeRepository.findOneOnlyImage(scheme.getSchemeID()
                            , Integer.parseInt(new String(img, "UTF-8"))));
                }
            }
            schemeRepository.save(schemes);
            schemeRepository.flush();
        } catch (Exception e) {
            e.printStackTrace();
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
        return "success";
    }

    @RequestMapping("/image")
    public ResponseEntity<byte[]> getImage(int schemeID, int row) {
        byte[] image = schemeRepository.findOneOnlyImage(schemeID, row);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
