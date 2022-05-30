package com.example.battleship.controller.util;

public class ItemWrapper<T> {
    T item;

    public T getItem() {
        return item;
    }

    private int x;
    private int y;

    public static int getIndex(int x, int y, int width) {
        return x * width + y;
    }

    public int getIndex(int width) {
        return getIndex(x, y, width);
    }

    public int getIndex() {
        return getIndex(10);
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    private boolean isUsed;

    public ItemWrapper(T item, int x, int y) {
        this.item = item;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
