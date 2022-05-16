package com.example.battleship.controller.util;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SpaceFinder {

    private static ArrayList<Boolean> getBooleanList(ArrayList<ItemWrapper<Rectangle>> list) {
        ArrayList<Boolean> blockList = new ArrayList<>();
        for (ItemWrapper<Rectangle> item : list) {
            blockList.add(item.isUsed());
        }

        return blockList;
    }

    public static ArrayList<Integer> findSpaceFromItemWrapper(
            ArrayList<ItemWrapper<Rectangle>> list,
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
            if (endIndex / height >= height) return spaceIndexList;

            for (int i = 0; i < size; i++) {
                index = startIndex + i * height;
                spaceIndexList.add(index);

                if (list.get(index)) {
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
