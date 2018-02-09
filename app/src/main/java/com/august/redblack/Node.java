package com.august.redblack;

import android.graphics.Color;

/**
 * Created by mrx on 2017-12-25.
 */

public class Node {

    private int value, color, layer, position;
    private float x, y;

    public Node(int value, String color) {
        this.value = value;
        this.color = color.equals("Black")? Color.BLACK: Color.RED;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public float getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLayer() {

        return layer;
    }

    public int getPosition() {
        return position;
    }
}
