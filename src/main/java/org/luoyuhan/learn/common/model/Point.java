package org.luoyuhan.learn.common.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Point {
    public int x;
    public int y;

    @Override
    public int hashCode() {
        return new Integer(x).hashCode() + new Integer(y).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        return x == ((Point) obj).x && y == ((Point) obj).y;
    }
}
