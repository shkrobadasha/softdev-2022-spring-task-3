package test;

import com.example.battleship.controller.util.UsedIndexFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UsedIndexFinderTest {

    @Test
    public void findBlockIndexesTest() {
        List<Boolean> list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{true, true, true, true, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        Set<Integer> result = UsedIndexFinder.findUsedIndexes(list, 6, 5, 5);
        Set<Integer> expected = new HashSet<>() {{
            add(5);
            add(6);
            add(7);
            add(8);
        }};
        assertEquals(expected, result);

        list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, true, true}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        result = UsedIndexFinder.findUsedIndexes(list, 7, 5, 5);
        expected = new HashSet<>() {{
            add(7);
            add(8);
            add(9);
        }};
        assertEquals(expected, result);

        list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        result = UsedIndexFinder.findUsedIndexes(list, 7, 5, 5);
        expected = new HashSet<>() {{
            add(2);
            add(7);
            add(12);
        }};
        assertEquals(expected, result);


        list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, false, false}));
        }};

        result = UsedIndexFinder.findUsedIndexes(list, 7, 5, 5);
        expected = new HashSet<>() {{
            add(7);
            add(12);
            add(17);
            add(22);
        }};
        assertEquals(expected, result);
    }

    @Test
    public void hasUsedNeighbourTest() {
        List<Boolean> list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, true, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        boolean result = UsedIndexFinder.hasUsedNeighbour(
                list,
                new HashSet<>() {{
                    add(3);
                }},
                5,
                5);

        assertTrue(result);

        result = UsedIndexFinder.hasUsedNeighbour(
                list,
                new HashSet<>() {{
                    add(11);
                }},
                5,
                5);

        assertTrue(result);

        result = UsedIndexFinder.hasUsedNeighbour(
                list,
                new HashSet<>() {{
                    add(1);
                }},
                5,
                5);

        assertTrue(result);

        result = UsedIndexFinder.hasUsedNeighbour(
                list,
                new HashSet<>() {{
                    add(5);
                }},
                5,
                5);

        assertFalse(result);

        list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, true, false}));
            addAll(List.of(new Boolean[]{true, true, true, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        result = UsedIndexFinder.hasUsedNeighbour(
                list,
                new HashSet<>() {{
                    add(10);
                    add(11);
                    add(12);
                }},
                5,
                5);

        assertTrue(result);
    }
}
