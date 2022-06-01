package com.example.battleship.controller.util;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SpaceFinder {

    private static <T> ArrayList<Boolean> getBooleanList(ArrayList<ItemWrapper<T>> list) {
        ArrayList<Boolean> blockList = new ArrayList<>();
        for (ItemWrapper<T> item : list) {
            blockList.add(item.isUsed());
        }

        return blockList;
    }

    public static <T> ArrayList<Integer> findSpaceFromItemWrapper(
            ArrayList<ItemWrapper<T>> list,
            int startIndex,
            int width,
            int height,
            int size,
            boolean isVertical) {
        return findSpace(getBooleanList(list), startIndex, width, height, size, isVertical);
    }

    public static ArrayList<Integer> findSpace(
            ArrayList<Boolean> list,
            int startIndex,
            int width,
            int height,
            int size,
            boolean isVertical) {
        ArrayList<Integer> spaceIndexList = new ArrayList<>();

        int index;

        if (isVertical) {
            int endIndex = startIndex + (size - 1) * height;
            if (endIndex / height >= height) return spaceIndexList;//нельзя разместить

            for (int i = 0; i < size; i++) {//заполняем сп от начального до длины
                index = startIndex + i * height;
                spaceIndexList.add(index);

                if (list.get(index)) {//если там уже занято
                    spaceIndexList.clear();
                    break;
                }
            }
        } else {
            int endIndex = startIndex + (size - 1);
            if (endIndex / width != startIndex / width) return spaceIndexList;

            for (int i = 0; i < size; i++) {

                index = startIndex + i;
                spaceIndexList.add(index);

                if (list.get(index)) {
                    spaceIndexList.clear();
                    break;
                }
            }
        }

        return spaceIndexList;
    }
}
