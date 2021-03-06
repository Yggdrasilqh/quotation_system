package com.yggdrasil.entity;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by yggdrasil on 2017/3/31.
 */
@Entity
public class Plant {
    @GeneratedValue
    @Id
    private int id;

    @Column
    private String name;

    @Lob
//    @Basic(fetch = FetchType.LAZY)
    @Column
    private byte[] image;

    @Column
    private String type;

    @Column
    private float price;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}
