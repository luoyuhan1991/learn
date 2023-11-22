package org.luoyuhan.learn.java.serialiazation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyuhan
 */
@Data
public class InnerClass implements Serializable {
    private int id;

    public InnerClass(int id) {
        this.id = id;
    }
}
