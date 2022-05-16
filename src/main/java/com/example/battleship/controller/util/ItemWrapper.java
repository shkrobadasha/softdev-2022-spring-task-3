package com.example.battleship.controller.util;

public class ItemWrapper<T> {
    T item;

    public T getItem() {
        return item;
    }

    private int x;
    private int y;

    public static int toIndex(int x, int y, int width) {
        return x * width + y;
    }

    public int toIndex(int width) {
        return toIndex(x, y, width);
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
