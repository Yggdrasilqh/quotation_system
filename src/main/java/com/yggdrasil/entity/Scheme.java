package com.yggdrasil.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Calendar;

/**
 * Created by yggdrasil on 2017/4/3.
 */
@Entity
@IdClass(SchemePK.class)
public class Scheme {
    /**
     * row 表的行号
     * schemeID 该表的ID
     * position1 位置1
     * position2 位置2
     * plantID 植物ID
     * number 植物数量
     * comment 备注
     */
    @Id
    private int row;
    @Id
    private int schemeID;
    @Column
    private String position1;
    @Column
    private String position2;
    @Column(nullable = false)
    private String plantID;
    @Column
    private int number;
    @Column
    private String comment;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(int schemeID) {
        this.schemeID = schemeID;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
