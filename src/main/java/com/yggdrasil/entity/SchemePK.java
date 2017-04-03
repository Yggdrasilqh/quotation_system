package com.yggdrasil.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by yggdrasil on 2017/4/3.
 */
public class SchemePK implements Serializable {
    private int row;
    private int schemeID;

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
}
