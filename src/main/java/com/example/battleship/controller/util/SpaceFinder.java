package com.example.battleship.controller.util;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SpaceFinder {

    private static <T> List<Boolean> getBooleanList(List<ItemWrapper<T>> list) {
        List<Boolean> blockList = new ArrayList<>();
        for (ItemWrapper<T> item : list) {
            blockList.add(item.isUsed());
        }

        return blockList;
    }

    public static <T> List<Integer> findSpaceFromItemWrapper(
            List<ItemWrapper<T>> list,
            int startIndex,
            int width,
            int height,
            int size,
            boolean isVertical) {
        return findSpace(getBooleanList(list), startIndex, width, height, size, isVertical);
    }

    public static List<Integer> findSpace(
            List<Boolean> list,
            int startIndex,
            int width,
            int height,
            int size,
            boolean isVertical) {
        List<Integer> spaceIndexList = new ArrayList<>();

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
