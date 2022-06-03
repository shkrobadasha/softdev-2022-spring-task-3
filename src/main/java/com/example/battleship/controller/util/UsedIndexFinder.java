package com.example.battleship.controller.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsedIndexFinder {

    private static <T> List<Boolean> getBooleanList(List<ItemWrapper<T>> list) {
        List<Boolean> blockList = new ArrayList<>();

        for (ItemWrapper<T> item : list) {
            blockList.add(item.isUsed());
        }

        return blockList;
    }

    public static <T> Set<Integer> findUsedIndexesFromItemWrapper(List<ItemWrapper<T>> list, int startIndex, int width, int height) {
        return findUsedIndexes(getBooleanList(list), startIndex, width, height);
    }

    public static Set<Integer> findUsedIndexes(List<Boolean> list, int startIndex, int width, int height) {
        return findUsedIndexes(list, startIndex, startIndex, width, height);
    }

    private static Set<Integer> findUsedIndexes(List<Boolean> list, int startIndex, int previousIndex, int width, int height) {
        Set<Integer> blockIndexList = new HashSet<>();

        if (!list.get(startIndex)) return blockIndexList;

        blockIndexList.add(startIndex);

        if (startIndex % width > 0 && startIndex - 1 != previousIndex) {// Поиск влево
            blockIndexList.addAll(findUsedIndexes(list, startIndex - 1, startIndex, width, height));
        }

        if (startIndex % width < width - 1 && startIndex + 1 != previousIndex) {// Поиск вправо
            blockIndexList.addAll(findUsedIndexes(list, startIndex + 1, startIndex, width, height));
        }

        if (startIndex / height > 0 && startIndex - width != previousIndex) {// Поиск вверх
            blockIndexList.addAll(findUsedIndexes(list, startIndex - width, startIndex, width, height));
        }

        if (startIndex / height < height - 1 && startIndex + width != previousIndex) {// Поиск вниз
            blockIndexList.addAll(findUsedIndexes(list, startIndex + width, startIndex, width, height));
        }

        return blockIndexList;
    }

    public static <T> boolean hasUsedNeighbourFromItemWrapper(List<ItemWrapper<T>> list, Set<Integer> indexes, int width, int height) {
        return hasUsedNeighbour(getBooleanList(list), indexes, width, height);
    }

    public static boolean hasUsedNeighbour(List<Boolean> list, Set<Integer> indexes, int width, int height) {

        for (int startIndex : indexes) {
            if (startIndex % width > 0 && list.get(startIndex - 1)) {// Поиск влево
                return true;
            }

            if (startIndex % width < width - 1 && list.get(startIndex + 1)) {// Поиск вправо
                return true;
            }

            if (startIndex / height > 0 && list.get(startIndex - width)) {// Поиск вверх
                return true;
            }

            if (startIndex / height < height - 1 && list.get(startIndex + width)) {// Поиск вниз
                return true;
            }

            //Диагональ
            if (startIndex % width > 0) {

                if (startIndex / height > 0 && list.get(startIndex - 1 - width)) {
                    return true;
                }

                if (startIndex / height < height - 1 && list.get(startIndex - 1 + width)) {
                    return true;
                }
            }

            if (startIndex % width < width - 1) {
                if (startIndex / height > 0 && list.get(startIndex + 1 - width)) {
                    return true;
                }
                if (startIndex / height < height - 1 && list.get(startIndex + 1 + width)) {
                    return true;
                }
            }

        }

        return false;
    }

}
