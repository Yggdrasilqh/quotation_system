package com.yggdrasil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by yggdrasil on 2017/4/3.
 */
@Entity
public class SchemeList {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private Date date = new Date(new java.util.Date().getTime());

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
