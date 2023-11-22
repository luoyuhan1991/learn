package org.luoyuhan.learn.java.serialiazation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyuhan
 */
@Data
public class Object1 implements Serializable {
    private int id;
    private String name;
    private InnerClass innerClass;

    public Object1(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
